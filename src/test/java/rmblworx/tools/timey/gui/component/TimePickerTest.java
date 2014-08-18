package rmblworx.tools.timey.gui.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalTime;
import java.util.List;
import java.util.Vector;

import javafx.application.Platform;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;
import org.loadui.testfx.utils.FXTestUtils;

import rmblworx.tools.timey.gui.DateTimeUtil;
import rmblworx.tools.timey.gui.JavaFxGuiTest;

import com.google.common.base.Predicate;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * GUI-Tests für die TimePicker-Komponente.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@Category(TestFX.class)
public class TimePickerTest extends JavaFxGuiTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	// GUI-Elemente
	private TimePicker timePicker;
	private TextField hoursTextField;
	private TextField minutesTextField;
	private TextField secondsTextField;
	private Slider hoursSlider;
	private Slider minutesSlider;
	private Slider secondsSlider;

	/**
	 * @return Elternknoten der GUI-Elemente
	 */
	protected final Parent getRootNode() {
		return WRAP_IN_CONTAINER ? wrapInContainer(new TimePicker()) : new TimePicker();
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();

		timePicker = (TimePicker) (WRAP_IN_CONTAINER ? scene.getRoot().getChildrenUnmodifiable().get(0) : scene.getRoot());

		hoursTextField = (TextField) scene.lookup("#hoursTextField");
		minutesTextField = (TextField) scene.lookup("#minutesTextField");
		secondsTextField = (TextField) scene.lookup("#secondsTextField");

		hoursSlider = (Slider) scene.lookup("#hoursSlider");
		minutesSlider = (Slider) scene.lookup("#minutesSlider");
		secondsSlider = (Slider) scene.lookup("#secondsSlider");
	}

	/**
	 * Testet den Ausgangszustand der Komponente.
	 */
	@Test
	public final void testInitialState() {
		assertEquals("00", hoursTextField.getText());
		assertEquals("00", minutesTextField.getText());
		assertEquals("00", secondsTextField.getText());

		assertEquals(0L, (long) hoursSlider.getValue());
		assertEquals(0L, (long) minutesSlider.getValue());
		assertEquals(0L, (long) secondsSlider.getValue());
	}

	/**
	 * Testet die bidirektionale Verbindung zwischen Textfeldern und Slidern.
	 */
	@Test
	public final void testBidirectionalBoundingOfTextFieldsAndSliders() {
		final List<TextFieldAndSliderValue> testCases = new Vector<>();
		testCases.add(new TextFieldAndSliderValue(hoursSlider, 1, hoursTextField, "01"));
		testCases.add(new TextFieldAndSliderValue(hoursSlider, 23, hoursTextField, "23"));
		testCases.add(new TextFieldAndSliderValue(minutesSlider, 59, minutesTextField, "59"));
		testCases.add(new TextFieldAndSliderValue(secondsSlider, 59, secondsTextField, "59"));
		// abschließend alle Slider zurücksetzen
		testCases.add(new TextFieldAndSliderValue(hoursSlider, 0, hoursTextField, "00"));
		testCases.add(new TextFieldAndSliderValue(minutesSlider, 0, minutesTextField, "00"));
		testCases.add(new TextFieldAndSliderValue(secondsSlider, 0, secondsTextField, "00"));

		// Wert des Sliders setzen und sicherstellen, dass Textfeld-Inhalt der Erwartung entspricht
		for (final TextFieldAndSliderValue testCase : testCases) {
			Platform.runLater(new Runnable() {
				public void run() {
					testCase.slider.setValue(testCase.sliderValue);
				}
			});
			FXTestUtils.awaitEvents();
			try {
				waitUntil(testCase.textField, new Predicate<TextField>() {
					public boolean apply(final TextField textField) {
						return testCase.textFieldValue.equals(textField.getText());
					}
				}, 1);
			} catch (final RuntimeException e) {
				fail(String.format("test case %d: '%s' doesn't match expected '%s'.", testCases.indexOf(testCase),
						testCase.textField.getText(), testCase.textFieldValue));
			}
		}

		// Inhalt des Textfelds setzen und sicherstellen, dass Slider-Wert der Erwartung entspricht
		for (final TextFieldAndSliderValue testCase : testCases) {
			Platform.runLater(new Runnable() {
				public void run() {
					testCase.textField.setText(testCase.textFieldValue);
				}
			});
			FXTestUtils.awaitEvents();
			try {
				waitUntil(testCase.slider, new Predicate<Slider>() {
					public boolean apply(final Slider slider) {
						return testCase.slider.getValue() == testCase.sliderValue;
					}
				}, 1);
			} catch (final RuntimeException e) {
				fail(String.format("test case %d: '%.0f' doesn't match expected '%s'.", testCases.indexOf(testCase),
						testCase.slider.getValue(), testCase.sliderValue));
			}
		}
	}

	/**
	 * Testet Wertänderung durch Scrollen innerhalb von Textfeldern.
	 */
	@Test
	public final void testTextFieldScrolling() {
		/*
		 * Zunächst vom Betriebssystem vorgegebene Scrollrichtung ermitteln.
		 * Kann z. B. unter Mac OS X frei festgelegt werden und ist standardmäßig umgekehrt, ergo verringert hochscrollen den Wert.
		 */
		click(hoursTextField);
		scroll(VerticalDirection.UP);
		final boolean scrollingUpIncreasesValue = hoursSlider.getValue() == 1.0;
		if (scrollingUpIncreasesValue) {
			// ursprünglichen Zustand wiederherstellen
			scroll(VerticalDirection.DOWN);
		}

		// Scrollrichtungen entsprechend definieren
		final VerticalDirection increaseValue = scrollingUpIncreasesValue ? VerticalDirection.UP : VerticalDirection.DOWN;
		final VerticalDirection decreaseValue = scrollingUpIncreasesValue ? VerticalDirection.DOWN : VerticalDirection.UP;

		// nun zum eigentlichen Test ...
		final TextFieldScrollingData[] testCases = new TextFieldScrollingData[] {
			new TextFieldScrollingData(minutesTextField, hoursTextField, hoursSlider),
			new TextFieldScrollingData(secondsTextField, minutesTextField, minutesSlider),
			new TextFieldScrollingData(hoursTextField, secondsTextField, secondsSlider),
		};

		for (final TextFieldScrollingData testCase : testCases) {
			click(testCase.focusedTextField); // anderes Feld fokussieren
			// Scrollen über Feld ohne Fokus hat keine Auswirkung
			move(testCase.textField);
			scroll(increaseValue);
			assertEquals(0.0, testCase.slider.getValue(), 0.0);

			click(testCase.textField); // richtiges Feld fokussieren
			scroll(increaseValue);
			assertEquals(1.0, testCase.slider.getValue(), 0.0);
			scroll(decreaseValue);
			assertEquals(0.0, testCase.slider.getValue(), 0.0);
		}
	}

	/**
	 * Testet das Verhalten der Textfelder bei Eingabe ungültiger Werte.
	 */
	@Test
	public final void testTextFieldsInvalidInput() {
		final List<TextFieldInputAndValue> testCases = new Vector<>();
		// zu kurze Eingabe
		testCases.add(new TextFieldInputAndValue(hoursTextField, "1", "01", true));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "1", "01", true));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "1", "01", true));
		// zu lange Eingabe
		testCases.add(new TextFieldInputAndValue(hoursTextField, "111", "23", true));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "111", "59", true));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "111", "59", true));
		testCases.add(new TextFieldInputAndValue(hoursTextField, "011", "11", false));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "011", "11", false));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "011", "11", false));
		// überschrittener Maximalwert
		testCases.add(new TextFieldInputAndValue(hoursTextField, "99", "23", true));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "99", "59", true));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "99", "59", true));
		// keine Zahl
		testCases.add(new TextFieldInputAndValue(hoursTextField, "x", "00", true));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "x", "00", true));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "x", "00", true));

		for (final InputMode inputMode : new InputMode[] { InputMode.TYPE, InputMode.COPY_PASTE }) {
			// Inhalt des Textfelds setzen und sicherstellen, dass Inhalt der Erwartung entspricht
			for (final TextFieldInputAndValue testCase : testCases) {
				Platform.runLater(new Runnable() {
					public void run() {
						testCase.textField.requestFocus(); // Feld fokussieren
					}
				});
				FXTestUtils.awaitEvents();
				assertTrue(testCase.textField.isFocused());
				switch (inputMode) {
					case TYPE:
						type(testCase.input);
						break;
					case COPY_PASTE:
						Platform.runLater(new Runnable() {
							public void run() {
								testCase.textField.setText(testCase.input);
							}
						});
						FXTestUtils.awaitEvents();
						break;
					default:
						fail("unbekannter Modus");
				}
				if (testCase.loseFocusAfterwards) {
					type(KeyCode.TAB); // Feld muss Fokus wieder verlieren
					assertFalse(testCase.textField.isFocused());
				}
				try {
					waitUntil(testCase.textField, new Predicate<TextField>() {
						public boolean apply(final TextField textField) {
							return testCase.value.equals(textField.getText());
						}
					}, 1);
				} catch (final RuntimeException e) {
					fail(String.format("test case %d [%s]: '%s' doesn't match expected '%s'.", testCases.indexOf(testCase),
							inputMode.toString().toLowerCase(), testCase.textField.getText(), testCase.value));
				} finally {
					// Feldinhalt auf Ausgangswert zurücksetzen
					Platform.runLater(new Runnable() {
						public void run() {
							testCase.textField.setText("00");
						}
					});
					FXTestUtils.awaitEvents();
				}
			}
		}
	}

	/**
	 * Testet das Setzen der Zeit.
	 */
	@Test
	public final void testSetTime() {
		final TimePartsAndTimeValue[] testCases = new TimePartsAndTimeValue[] {
			new TimePartsAndTimeValue("23", "59", "59", "23:59:59"),
			new TimePartsAndTimeValue("00", "00", "00", "00:00:00"),
		};

		for (final TimePartsAndTimeValue testCase : testCases) {
			final LocalTime time = DateTimeUtil.getLocalTimeForString(testCase.timeString);
			Platform.runLater(new Runnable() {
				public void run() {
					timePicker.setValue(time);
				}
			});
			FXTestUtils.awaitEvents();
			assertEquals(testCase.hours, hoursTextField.getText());
			assertEquals(testCase.minutes, minutesTextField.getText());
			assertEquals(testCase.seconds, secondsTextField.getText());
			assertEquals(time, timePicker.getTimeProperty().get());
		}
	}

	/**
	 * Testet das Abrufen der Zeit.
	 */
	@Test
	public final void testGetTime() {
		final TimePartsAndTimeValue[] testCases = new TimePartsAndTimeValue[] {
			new TimePartsAndTimeValue("23", "59", "59", "23:59:59"),
			new TimePartsAndTimeValue("00", "00", "00", "00:00:00"),
		};

		for (final TimePartsAndTimeValue testCase : testCases) {
			final LocalTime time = DateTimeUtil.getLocalTimeForString(testCase.timeString);
			Platform.runLater(new Runnable() {
				public void run() {
					hoursTextField.setText(testCase.hours);
					minutesTextField.setText(testCase.minutes);
					secondsTextField.setText(testCase.seconds);
				}
			});
			FXTestUtils.awaitEvents();
			assertEquals(time, timePicker.getValue());
			assertEquals(time, timePicker.getTimeProperty().get());
		}
	}

	private enum InputMode {
		/**
		 * Eingabe per Tippen.
		 */
		TYPE,
		/**
		 * Einfügen per Copy & Paste.
		 */
		COPY_PASTE,
	}

	private final class TextFieldAndSliderValue {

		public final Slider slider;
		public final long sliderValue;
		public final TextField textField;
		public final String textFieldValue;

		public TextFieldAndSliderValue(final Slider slider, final long sliderValue, final TextField textField,
				final String textFieldValue) {
			this.slider = slider;
			this.sliderValue = sliderValue;
			this.textField = textField;
			this.textFieldValue = textFieldValue;
		}

	}

	private final class TextFieldScrollingData {

		public final TextField focusedTextField;
		public final TextField textField;
		public final Slider slider;

		public TextFieldScrollingData(final TextField focusedTextField, final TextField textField, final Slider slider) {
			this.focusedTextField = focusedTextField;
			this.textField = textField;
			this.slider = slider;
		}

	}

	private final class TextFieldInputAndValue {

		public final TextField textField;
		public final String input;
		public final String value;
		public final boolean loseFocusAfterwards;

		public TextFieldInputAndValue(final TextField textField, final String input, final String value,
				final boolean loseFocusAfterwards) {
			this.textField = textField;
			this.input = input;
			this.value = value;
			this.loseFocusAfterwards = loseFocusAfterwards;
		}

	}

	private final class TimePartsAndTimeValue {

		public final String hours;
		public final String minutes;
		public final String seconds;
		public final String timeString;

		public TimePartsAndTimeValue(final String hours, final String minutes, final String seconds, final String timeString) {
			this.hours = hours;
			this.minutes = minutes;
			this.seconds = seconds;
			this.timeString = timeString;
		}

	}

}
