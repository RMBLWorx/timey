package rmblworx.tools.timey.gui.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import rmblworx.tools.timey.gui.DateTimeUtil;

import com.google.common.base.Predicate;

/**
 * GUI-Tests für die TimePicker-Komponente.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class TimePickerTest extends GuiTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	/**
	 * @return Elternknoten der GUI-Elemente
	 */
	protected final Parent getRootNode() {
		return new TimePicker();
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();
	}

	/**
	 * Testet den Ausgangszustand der Komponente.
	 */
	@Test
	public final void testInitialState() {
		final TextField hoursTextField = (TextField) scene.lookup("#hoursTextField");
		final TextField minutesTextField = (TextField) scene.lookup("#minutesTextField");
		final TextField secondsTextField = (TextField) scene.lookup("#secondsTextField");

		assertEquals("00", hoursTextField.getText());
		assertEquals("00", minutesTextField.getText());
		assertEquals("00", secondsTextField.getText());

		final Slider hoursSlider = (Slider) scene.lookup("#hoursSlider");
		final Slider minutesSlider = (Slider) scene.lookup("#minutesSlider");
		final Slider secondsSlider = (Slider) scene.lookup("#secondsSlider");

		assertEquals(0L, (long) hoursSlider.getValue());
		assertEquals(0L, (long) minutesSlider.getValue());
		assertEquals(0L, (long) secondsSlider.getValue());
	}

	/**
	 * Testet die bidirektionale Verbindung zwischen Textfeldern und Slidern.
	 */
	@Test
	public final void testBidirectionalBoundingOfTextFieldsAndSliders() {
		final TextField hoursTextField = (TextField) scene.lookup("#hoursTextField");
		final TextField minutesTextField = (TextField) scene.lookup("#minutesTextField");
		final TextField secondsTextField = (TextField) scene.lookup("#secondsTextField");

		final Slider hoursSlider = (Slider) scene.lookup("#hoursSlider");
		final Slider minutesSlider = (Slider) scene.lookup("#minutesSlider");
		final Slider secondsSlider = (Slider) scene.lookup("#secondsSlider");

		final List<TextFieldAndSliderValue> testCases = new Vector<TextFieldAndSliderValue>();
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
			testCase.slider.setValue(testCase.sliderValue);
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
			testCase.textField.setText(testCase.textFieldValue);
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
	 * Testet das Verhalten der Textfelder bei Eingabe ungültiger Werte.
	 */
	@Test
	public final void testTextFieldsInvalidInput() {
		final TextField hoursTextField = (TextField) scene.lookup("#hoursTextField");
		final TextField minutesTextField = (TextField) scene.lookup("#minutesTextField");
		final TextField secondsTextField = (TextField) scene.lookup("#secondsTextField");

		final List<TextFieldInputAndValue> testCases = new Vector<TextFieldInputAndValue>();
		// zu kurz Eingabe
		testCases.add(new TextFieldInputAndValue(hoursTextField, "1", "01"));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "1", "01"));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "1", "01"));
		// zu lange Eingabe
		testCases.add(new TextFieldInputAndValue(hoursTextField, "111", "23"));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "111", "59"));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "111", "59"));
		// überschrittener Maximalwert
		testCases.add(new TextFieldInputAndValue(hoursTextField, "99", "23"));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "99", "59"));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "99", "59"));
		// keine Zahl
		testCases.add(new TextFieldInputAndValue(hoursTextField, "x", "00"));
		testCases.add(new TextFieldInputAndValue(minutesTextField, "x", "00"));
		testCases.add(new TextFieldInputAndValue(secondsTextField, "x", "00"));

		final List<InputMode> inputModes = new Vector<InputMode>();
		inputModes.add(InputMode.TYPE);
		inputModes.add(InputMode.COPY_PASTE);

		for (final InputMode inputMode : inputModes) {
			// Inhalt des Textfelds setzen und sicherstellen, dass Inhalt der Erwartung entspricht
			for (final TextFieldInputAndValue testCase : testCases) {
				testCase.textField.requestFocus(); // Feld fokussieren
				switch (inputMode) {
					case TYPE:
						type(testCase.input);
						break;
					case COPY_PASTE:
						testCase.textField.setText(testCase.input);
						break;
					default:
						fail("unbekannter Modus");
				}
				type(KeyCode.TAB); // Feld muss Fokus wieder verlieren
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
					testCase.textField.setText("00");
				}
			}
		}
	}

	/**
	 * Testet das Setzen der Zeit.
	 */
	@Test
	public final void testSetTime() {
		final TextField hoursTextField = (TextField) scene.lookup("#hoursTextField");
		final TextField minutesTextField = (TextField) scene.lookup("#minutesTextField");
		final TextField secondsTextField = (TextField) scene.lookup("#secondsTextField");

		final TimePicker timePicker = (TimePicker) scene.getRoot();

		final List<TimePartsAndTimeValue> testCases = new Vector<TimePartsAndTimeValue>();
		testCases.add(new TimePartsAndTimeValue("23", "59", "59", "23:59:59"));
		testCases.add(new TimePartsAndTimeValue("00", "00", "00", "00:00:00"));

		for (final TimePartsAndTimeValue testCase : testCases) {
			final Calendar time = DateTimeUtil.getCalendarForString(testCase.timeString);
			timePicker.setTime(time);
			assertEquals(testCase.hours, hoursTextField.getText());
			assertEquals(testCase.minutes, minutesTextField.getText());
			assertEquals(testCase.seconds, secondsTextField.getText());
			assertEquals(time.getTime(), timePicker.getTimeProperty().get().getTime());
		}
	}

	/**
	 * Testet das Abrufen der Zeit.
	 */
	@Test
	public final void testGetTime() {
		final TextField hoursTextField = (TextField) scene.lookup("#hoursTextField");
		final TextField minutesTextField = (TextField) scene.lookup("#minutesTextField");
		final TextField secondsTextField = (TextField) scene.lookup("#secondsTextField");

		final TimePicker timePicker = (TimePicker) scene.getRoot();

		final List<TimePartsAndTimeValue> testCases = new Vector<TimePartsAndTimeValue>();
		testCases.add(new TimePartsAndTimeValue("23", "59", "59", "23:59:59"));
		testCases.add(new TimePartsAndTimeValue("00", "00", "00", "00:00:00"));

		for (final TimePartsAndTimeValue testCase : testCases) {
			final Calendar time = DateTimeUtil.getCalendarForString(testCase.timeString);
			hoursTextField.setText(testCase.hours);
			minutesTextField.setText(testCase.minutes);
			secondsTextField.setText(testCase.seconds);
			assertEquals(time.getTimeInMillis(), timePicker.getTime().getTimeInMillis());
			assertEquals(time.getTimeInMillis(), timePicker.getTimeProperty().get().getTimeInMillis());
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

	private final class TextFieldInputAndValue {

		public final TextField textField;
		public final String input;
		public final String value;

		public TextFieldInputAndValue(final TextField textField, final String input, final String value) {
			this.textField = textField;
			this.input = input;
			this.value = value;
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
