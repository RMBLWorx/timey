package rmblworx.tools.timey.gui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import rmblworx.tools.timey.ITimey;
import rmblworx.tools.timey.event.AlarmExpiredEvent;
import rmblworx.tools.timey.event.TimeyEvent;
import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Controller für die Alarm-GUI.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class AlarmController extends Controller implements TimeyEventListener {

	/**
	 * Formatiert Zeitstempel als Datum/Zeit-Werte.
	 */
	private DateTimeFormatter dateTimeFormatter;

	@FXML
	private ResourceBundle resources;

	@FXML
	private Node alarmContainer;

	@FXML
	private Node alarmProgressContainer;

	@FXML
	private TableView<Alarm> alarmTable;

	@FXML
	private TableColumn<Alarm, LocalDateTime> alarmDateTimeColumn;

	@FXML
	private TableColumn<Alarm, String> alarmDescriptionColumn;

	@FXML
	private Button alarmEditButton;

	@FXML
	private Button alarmDeleteButton;

	@FXML
	private void initialize() {
		alarmDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Alarm, LocalDateTime>("dateTime"));

		alarmDateTimeColumn.setCellFactory(new Callback<TableColumn<Alarm, LocalDateTime>, TableCell<Alarm, LocalDateTime>>() {
			public TableCell<Alarm, LocalDateTime> call(final TableColumn<Alarm, LocalDateTime> param) {
				return new TableCell<Alarm, LocalDateTime>() {
					protected void updateItem(final LocalDateTime item, final boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : dateTimeFormatter.format(item));
					}
				};
			}
		});

		alarmDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Alarm, String>("description"));

		// CSS-Klasse für inaktive Alarme setzen
		alarmTable.setRowFactory(new Callback<TableView<Alarm>, TableRow<Alarm>>() {
			public TableRow<Alarm> call(final TableView<Alarm> tableView) {
				final TableRow<Alarm> row = new TableRow<Alarm>() {
					protected void updateItem(final Alarm alarm, final boolean empty) {
						super.updateItem(alarm, empty);
						if (alarm != null && !alarm.isEnabled()) {
							getStyleClass().add("alarm-disabled");
						}
					}
				};
				return row;
			}
		});

		// Platzhaltertext (für Tabelle ohne Einträge) setzen
		alarmTable.setPlaceholder(new Text(resources.getString("noAlarmsDefined.placeholder")));

		// Bearbeiten- und Löschen-Schaltflächen nur aktivieren, wenn Eintrag ausgewählt
		alarmTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alarm>() {
			public void changed(final ObservableValue<? extends Alarm> property, final Alarm oldValue, final Alarm newValue) {
				final boolean isItemSelected = newValue != null;
				alarmEditButton.setDisable(!isItemSelected);
				alarmDeleteButton.setDisable(!isItemSelected);
			}
		});

		Platform.runLater(new Runnable() {
			public void run() {
				reloadAlarms();
			}
		});

		alarmEditButton.setDisable(true);
		alarmDeleteButton.setDisable(true);

		final TimeyEventListener eventListener = this;
		Platform.runLater(new Runnable() {
			public void run() {
				getGuiHelper().getFacade().addEventListener(eventListener);
			}
		});

		setupDateTimeFormatter();
	}

	/**
	 * Aktion bei Betätigen der Hinzufügen-Schaltfläche.
	 */
	@FXML
	private void handleAddButtonAction() {
		Platform.runLater(new Runnable() {
			public void run() {
				final Alarm alarm = new Alarm();
				if (showAlarmEditDialog(alarm, resources.getString("alarmEdit.title.add"))) {
					showProgress();

					getGuiHelper().runInThread(new Task<Void>() {
						public Void call() {
							alarmTable.getItems().add(alarm);
							refreshTable(false);
							getGuiHelper().getFacade().setAlarm(AlarmDescriptorConverter.getAsAlarmDescriptor(alarm));

							/*
							 * Neuen Alarm auswählen.
							 * Muss verzögert ausgeführt werden, um Darstellungsproblem beim Hinzufügen des ersten Alarms zu vermeiden.
							 * (Hinweis von https://community.oracle.com/message/10389376#10389376.)
							 */
							Platform.runLater(new Runnable() {
								public void run() {
									alarmTable.scrollTo(alarm);
									alarmTable.getSelectionModel().select(alarm);
									alarmTable.requestFocus();
								}
							});

							hideProgress();

							return null;
						}
					}, resources);
				}
			}
		});
	}

	/**
	 * Aktion bei Betätigen der Bearbeiten-Schaltfläche.
	 */
	@FXML
	private void handleEditButtonAction() {
		Platform.runLater(new Runnable() {
			public void run() {
				editAlarm();
			}
		});
	}

	/**
	 * Aktion bei Betätigen der Löschen-Schaltfläche.
	 */
	@FXML
	private void handleDeleteButtonAction() {
		Platform.runLater(new Runnable() {
			public void run() {
				final Alarm alarm = alarmTable.getSelectionModel().getSelectedItem();
				if (alarm != null) {
					showProgress();

					getGuiHelper().runInThread(new Task<Void>() {
						public Void call() {
							alarmTable.getSelectionModel().clearSelection(); // Auswahl aufheben
							alarmTable.getItems().remove(alarm);
							getGuiHelper().getFacade().removeAlarm(AlarmDescriptorConverter.getAsAlarmDescriptor(alarm));

							hideProgress();

							return null;
						}
					}, resources);
				}
			}
		});
	}

	/**
	 * Aktion bei Klick auf Tabelle.
	 * @param event Mausereignis
	 */
	@FXML
	private void handleTableClick(final MouseEvent event) {
		// Eintrag bearbeiten bei Doppelklick
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
			editAlarm();
		}
	}

	/**
	 * Bearbeitet den ausgewählten Alarm.
	 */
	private void editAlarm() {
		final Alarm alarm = alarmTable.getSelectionModel().getSelectedItem();
		if (alarm != null) {
			final AlarmDescriptor oldAlarm = AlarmDescriptorConverter.getAsAlarmDescriptor(alarm); // Instanz vorm Bearbeiten anlegen
			if (showAlarmEditDialog(alarm, resources.getString("alarmEdit.title.edit"))) {
				showProgress();

				getGuiHelper().runInThread(new Task<Void>() {
					public Void call() {
						refreshTable();

						final ITimey facade = getGuiHelper().getFacade();
						facade.removeAlarm(oldAlarm);
						facade.setAlarm(AlarmDescriptorConverter.getAsAlarmDescriptor(alarm));

						hideProgress();

						return null;
					}
				}, resources);
			}
		}
	}

	/**
	 * Öffnet den Dialog zum Hinzufügen/Bearbeiten eines Alarms.
	 * @param alarm Alarm
	 * @param title Titel des Fensters
	 * @return ob der Alarm geändert wurde
	 */
	private boolean showAlarmEditDialog(final Alarm alarm, final String title) {
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("AlarmEditDialog.fxml"), resources);
			final Parent root = (Parent) loader.load();
			final Stage dialogStage = new Stage(StageStyle.UTILITY);
			dialogStage.setScene(new Scene(root));
			dialogStage.setTitle(title);
			dialogStage.setResizable(false);
			dialogStage.initModality(Modality.APPLICATION_MODAL);
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);

			final AlarmEditDialogController controller = loader.getController();
			controller.setGuiHelper(getGuiHelper());
			controller.setDialogStage(dialogStage);
			controller.setExistingAlarms(alarmTable.getItems());
			controller.setAlarm(alarm);

			dialogStage.showAndWait();

			return controller.isChanged();
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Aktualisiert den Inhalt der Tabelle.
	 */
	private void refreshTable() {
		refreshTable(true);
	}

	/**
	 * Aktualisiert den Inhalt der Tabelle.
	 * @param preserveSelection Ob die Auswahl erhalten bleiben soll.
	 */
	private void refreshTable(final boolean preserveSelection) {
		final Alarm selectedItem = alarmTable.getSelectionModel().getSelectedItem();

		Platform.runLater(new Runnable() {
			public void run() {
				FXCollections.sort(alarmTable.getItems());

				if (preserveSelection) {
					alarmTable.scrollTo(selectedItem);
					alarmTable.getSelectionModel().select(selectedItem);
				}
			}
		});
	}

	/**
	 * Initialisiert den Datum/Zeit-Formatierer.
	 */
	private void setupDateTimeFormatter() {
		if (dateTimeFormatter == null) {
			dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		}
	}

	private void showProgress() {
		switchProgress(true);
	}

	private void hideProgress() {
		switchProgress(false);
	}

	private void switchProgress(final boolean visible) {
		Platform.runLater(new Runnable() {
			public void run() {
				alarmProgressContainer.setVisible(visible);
				alarmContainer.setVisible(!visible);
			}
		});
	}

	/**
	 * Lädt die Alarme aus der Datenbank.
	 */
	private void reloadAlarms() {
		showProgress();

		getGuiHelper().runInThread(new Task<Void>() {
			public Void call() {
				final List<Alarm> alarms = AlarmDescriptorConverter.getAsAlarms(getGuiHelper().getFacade().getAllAlarms());
				final int selectedIndex = alarmTable.getSelectionModel().getSelectedIndex();
				final ObservableList<Alarm> tableData = alarmTable.getItems();

				Platform.runLater(new Runnable() {
					public void run() {
						tableData.clear();
						tableData.addAll(alarms);

						refreshTable(false);
						alarmTable.getSelectionModel().select(selectedIndex);
						hideProgress();
					}
				});

				return null;
			}
		}, resources);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void handleEvent(final TimeyEvent event) {
		if (event instanceof AlarmExpiredEvent) {
			final Alarm alarm = AlarmDescriptorConverter.getAsAlarm(((AlarmExpiredEvent) event).getAlarmDescriptor());

			reloadAlarms();

			getGuiHelper().showTrayMessageWithFallbackToDialog(resources.getString("alarm.event.triggered.title"), alarm.getDescription(),
					resources);
			getGuiHelper().playSoundInThread(alarm.getSound(), resources);
		}
	}

}
