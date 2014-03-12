package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import rmblworx.tools.timey.SimpleCountdown;
import rmblworx.tools.timey.vo.TimeDescriptor;

public class CountdownController {

	// private TimeyFacade facade = new TimeyFacade();
	private SimpleCountdown countdown = new SimpleCountdown();

	private SimpleDateFormat dateFormatter;
	private SimpleDateFormat hoursFormatter;
	private SimpleDateFormat minutesFormatter;
	private SimpleDateFormat secondsFormatter;

	private static final long MAX_HOURS = 23L;
	private static final long MAX_MINUTES = 59L;
	private static final long MAX_SECONDS = 59L;

	@FXML
	private ResourceBundle resources;

	@FXML
	private Button countdownStartButton;

	@FXML
	private Button countdownStopButton;

	@FXML
	private TextField countdownHoursField;

	@FXML
	private TextField countdownMinutesField;

	@FXML
	private TextField countdownSecondsField;

	@FXML
	private Slider countdownHoursSlider;

	@FXML
	private Slider countdownMinutesSlider;

	@FXML
	private Slider countdownSecondsSlider;

	@FXML
	private Label countdownTimeLabel;

	@FXML
	private AnchorPane countdownTimeInputPane;

	private boolean countdownRunning = false;
	private long countdownValue;

	@FXML
	void initialize() {
		assert countdownHoursField != null : "fx:id='countdownHoursField' was not injected";
		assert countdownMinutesField != null : "fx:id='countdownMinuteField' was not injected";
		assert countdownSecondsField != null : "fx:id='countdownSecondsField' was not injected";
		assert countdownHoursSlider != null : "fx:id='countdownHoursSlider' was not injected";
		assert countdownMinutesSlider != null : "fx:id='countdownMinutesSlider' was not injected";
		assert countdownSecondsSlider != null : "fx:id='countdownSecondsSlider' was not injected";
		assert countdownStartButton != null : "fx:id='countdownStartButton' was not injected";
		assert countdownStopButton != null : "fx:id='countdownStopButton' was not injected";
		assert countdownTimeLabel != null : "fx:id='countdownTimeLabel' was not injected";
		assert countdownTimeInputPane != null : "fx:id='countdownTimeInputPane' was not injected";

		if (countdownStartButton != null) {
			countdownStartButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					if (countdownRunning) {
						return;
					}

					final TimeDescriptor td = new TimeDescriptor(getTimeFromInputFields());
					startCountdown(td);

					final Task<Void> task = new Task<Void>() {
						private static final long SLEEP_TIME_COARSE_GRAINED = 1000L;

						public Void call() throws InterruptedException {
							while (countdownRunning) {
								countdownValue = td.getMilliSeconds();
								updateMessage(dateFormatter.format(countdownValue));
								Thread.sleep(SLEEP_TIME_COARSE_GRAINED);
							}

							return null;
						}
					};

					task.messageProperty().addListener(new ChangeListener<String>() {
						public void changed(final ObservableValue<? extends String> property, final String oldValue,
								final String newValue) {
							countdownTimeLabel.setText(newValue);
						}
					});

					final Thread thread = new Thread(task);
					thread.setDaemon(true);
					thread.setPriority(Thread.MIN_PRIORITY);
					thread.start();
				}
			});
		}

		if (countdownStopButton != null) {
			countdownStopButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					if (!countdownRunning) {
						return;
					}

					stopCountdown();
				}
			});
		}

		if (countdownHoursField != null) {
			bindTextInputListenersAndSlider(countdownHoursField, countdownHoursSlider, MAX_HOURS);
		}

		if (countdownMinutesField != null) {
			bindTextInputListenersAndSlider(countdownMinutesField, countdownMinutesSlider, MAX_MINUTES);
		}

		if (countdownSecondsField != null) {
			bindTextInputListenersAndSlider(countdownSecondsField, countdownSecondsSlider, MAX_SECONDS);
		}

		setupDateFormatters();
	}

	protected void bindTextInputListenersAndSlider(final TextField textField, final Slider slider, final long maxValue) {
		final StringProperty textProperty = textField.textProperty();

		// Eingaben auf Zahlen beschränken
		textField.addEventFilter(KeyEvent.KEY_TYPED, new AllowOnlyNumericKeysKeyEventHandler());

		// Inhalt auf gültigen Wertebereich beschränken
		textProperty.addListener(new CountdownTimePartChangeListener(textProperty, maxValue));

		// Start-Schaltfläche nur aktivieren, wenn Zeit > 0
		countdownStartButton.setDisable(true);
		textProperty.addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				countdownStartButton.setDisable(getTimeFromInputFields() == 0);
			}
		});

		// bei Verlassen des Feldes sicherstellen, dass Wert zweistellig
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(final ObservableValue<? extends Boolean> property, final Boolean oldValue, final Boolean newValue) {
				if (Boolean.FALSE.equals(newValue)) {
					textProperty.setValue(String.format("%02d", Long.parseLong(textProperty.get())));
				}
			}
		});

		// Textfeld mit Slider koppeln
		if (slider != null) {
			slider.setMax(maxValue);
			textProperty.bindBidirectional(slider.valueProperty(), new CountdownTimePartConverter());
		}
	}

	protected void startCountdown(final TimeDescriptor td) {
		countdownRunning = true;
		countdownStartButton.setVisible(false);
		countdownStopButton.setVisible(true);
		countdownStopButton.requestFocus();
		enableTimeInput(false);

		transferTimeFromInputToLabel();

		countdown.setCountdownTime(td);
		countdown.startCountdown();
	}

	protected void stopCountdown() {
		countdown.stopCountdown();
		countdownRunning = false;

		transferTimeToInput();

		countdownStartButton.setVisible(true);
		countdownStartButton.requestFocus();
		countdownStopButton.setVisible(false);
		enableTimeInput(true);
	}

	protected void enableTimeInput(final boolean enabled) {
		countdownTimeLabel.setVisible(!enabled);
		countdownTimeInputPane.setVisible(enabled);
		countdownTimeInputPane.setDisable(!enabled);
	}

	private void setupDateFormatters() {
		final TimeZone timeZone = TimeZone.getTimeZone("UTC");

		if (dateFormatter == null) {
			dateFormatter = new SimpleDateFormat();
			dateFormatter.setTimeZone(timeZone);
			dateFormatter.applyPattern("HH:mm:ss");
		}

		if (hoursFormatter == null) {
			hoursFormatter = new SimpleDateFormat();
			hoursFormatter.setTimeZone(timeZone);
			hoursFormatter.applyPattern("HH");
		}

		if (minutesFormatter == null) {
			minutesFormatter = new SimpleDateFormat();
			minutesFormatter.setTimeZone(timeZone);
			minutesFormatter.applyPattern("mm");
		}

		if (secondsFormatter == null) {
			secondsFormatter = new SimpleDateFormat();
			secondsFormatter.setTimeZone(timeZone);
			secondsFormatter.applyPattern("ss");
		}
	}

	protected long getTimeFromInputFields() {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.clear();
		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(countdownHoursField.getText()));
		cal.add(Calendar.MINUTE, Integer.parseInt(countdownMinutesField.getText()));
		cal.add(Calendar.SECOND, Integer.parseInt(countdownSecondsField.getText()));

		return cal.getTime().getTime();
	}

	protected void transferTimeFromInputToLabel() {
		countdownTimeLabel.setText(dateFormatter.format(getTimeFromInputFields()));
	}

	protected void transferTimeToInput() {
		countdownHoursField.setText(hoursFormatter.format(countdownValue));
		countdownMinutesField.setText(minutesFormatter.format(countdownValue));
		countdownSecondsField.setText(secondsFormatter.format(countdownValue));
	}

}
