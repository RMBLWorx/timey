package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.Vector;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import jfxtras.labs.scene.control.CalendarTextField;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.utils.FXTestUtils;

import rmblworx.tools.timey.gui.component.TimePicker;

/**
 * GUI-Tests für den Dialog zum Bearbeiten eines Alarms.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
@Category(TimeyGuiTest.class)
public class AlarmEditDialogControllerTest extends FxmlGuiControllerTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "AlarmEditDialog.fxml";
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();
	}

	/**
	 * Testet den Zustand der Steuerelemente nach Öffnen des Dialogs.
	 */
	@Test
	public final void testInitialState() {
		final AlarmEditDialogController controller = (AlarmEditDialogController) getController();
		final CheckBox alarmEnabledCheckbox = (CheckBox) scene.lookup("#alarmEnabledCheckbox");
		final CalendarTextField alarmDatePicker = (CalendarTextField) scene.lookup("#alarmDatePicker");
		final TimePicker alarmTimePicker = (TimePicker) scene.lookup("#alarmTimePicker");
		final TextField alarmDescriptionTextField = (TextField) scene.lookup("#alarmDescriptionTextField");
		final Button alarmSelectSoundButton = (Button) scene.lookup("#alarmSelectSoundButton");
		final Button alarmNoSoundButton = (Button) scene.lookup("#alarmNoSoundButton");
		final Button alarmPlaySoundButton = (Button) scene.lookup("#alarmPlaySoundButton");

		// Alarm vorgeben
		controller.setAlarm(new Alarm(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00"), "Test"));
		FXTestUtils.awaitEvents();

		// sicherstellen, dass Formularfelder korrekt gefüllt sind
		assertTrue(alarmEnabledCheckbox.isSelected());
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014").getTime(), alarmDatePicker.getValue().getTime());
		assertEquals(DateTimeUtil.getCalendarForString("12:00:00").getTime(), alarmTimePicker.getTime().getTime());
		assertEquals("Test", alarmDescriptionTextField.getText());

		assertEquals("kein Klingelton gewählt", alarmSelectSoundButton.getText());
		assertTrue(alarmNoSoundButton.isDisabled());
		assertTrue(alarmPlaySoundButton.isDisabled());
	}

	/**
	 * Testet das Speichern der Änderungen.
	 */
	@Test
	public final void testApplyChanges() {
		final AlarmEditDialogController controller = (AlarmEditDialogController) getController();
		final CheckBox alarmEnabledCheckbox = (CheckBox) scene.lookup("#alarmEnabledCheckbox");
		final CalendarTextField alarmDatePicker = (CalendarTextField) scene.lookup("#alarmDatePicker");
		final TimePicker alarmTimePicker = (TimePicker) scene.lookup("#alarmTimePicker");
		final TextField alarmDescriptionTextField = (TextField) scene.lookup("#alarmDescriptionTextField");
		final Button alarmSelectSoundButton = (Button) scene.lookup("#alarmSelectSoundButton");
		final Button alarmSaveButton = (Button) scene.lookup("#alarmSaveButton");

		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm vorgeben
		final Alarm alarm = new Alarm(DateTimeUtil.getCalendarForString("01.01.1970"), "Bla");
		controller.setAlarm(alarm);
		FXTestUtils.awaitEvents();

		// deaktivieren
		alarmEnabledCheckbox.fire();

		// Datum setzen
		alarmDatePicker.setValue(DateTimeUtil.getCalendarForString("24.12.2014"));

		// Zeit setzen
		alarmTimePicker.setTime(DateTimeUtil.getCalendarForString("12:00:00"));

		// Beschreibung setzen
		alarmDescriptionTextField.setText("Test");

		// Sound setzen
		controller.setRingtone("Sound");
		FXTestUtils.awaitEvents();

		// Speichern-Schaltfläche betätigen
		alarmSaveButton.fire();
		FXTestUtils.awaitEvents();

		// TODO auf Schließen des Dialogs warten

		// TODO sicherstellen, dass Dialog geschlossen wurde
		// assertFalse(stage.isShowing());

		// sicherstellen, dass Alarm neue Werte hat
		assertFalse(alarm.isEnabled());
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014").getTime(), DateTimeUtil.getDatePart(alarm.getDateTime()).getTime());
		assertEquals(DateTimeUtil.getCalendarForString("12:00:00").getTime(), DateTimeUtil.getTimePart(alarm.getDateTime()).getTime());
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00").getTime(), alarm.getDateTime().getTime());
		assertEquals("Test", alarm.getDescription());
		assertEquals("Sound", alarm.getSound());
	}

	/**
	 * Testet Anzeige der Fehlermeldung bei leerem Datum.
	 */
	@Test
	public final void testErrorDateEmpty() {
		final AlarmEditDialogController controller = (AlarmEditDialogController) getController();
		final CalendarTextField alarmDatePicker = (CalendarTextField) scene.lookup("#alarmDatePicker");
		final Button alarmSaveButton = (Button) scene.lookup("#alarmSaveButton");

		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm vorgeben
		controller.setAlarm(new Alarm());
		FXTestUtils.awaitEvents();

		// Datum leeren
		alarmDatePicker.setValue(null);

		final GuiHelper guiHelper = mock(GuiHelper.class);
		controller.setGuiHelper(guiHelper);

		// Speichern-Schaltfläche betätigen
		alarmSaveButton.fire();
		FXTestUtils.awaitEvents();

		// sicherstellen, dass Fehlermeldung erscheint
		verify(guiHelper).showDialogMessage(anyString(), eq("Ein Datum muss angegeben werden.\n"), isA(ResourceBundle.class));
	}

	/**
	 * Testet Anzeige bzw. Ausbleiben der Fehlermeldung bei Alarm mit identischem Zeitstempel.
	 */
	@Test
	public final void testErrorOtherAlarmWithSameTimestampAlreadyExists() {
		final AlarmEditDialogController controller = (AlarmEditDialogController) getController();
		final Button alarmSaveButton = (Button) scene.lookup("#alarmSaveButton");

		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		final List<DataErrors> testCases = new Vector<DataErrors>();
		// Anlegen eines Alarms mit identischem Zeitstempel
		testCases.add(new DataErrors(1, "Ein anderer Alarm mit demselben Zeitpunkt existiert bereits.\n",
				Arrays.asList(new Alarm(DateTimeUtil.getCalendarForString("01.01.1970"), "Alarm1")),
				new Alarm(DateTimeUtil.getCalendarForString("01.01.1970"), "Alarm2")));
		// Anlegen eines Alarms mit unterschiedlichem Zeitstempel
		testCases.add(new DataErrors(0, null,
				Arrays.asList(new Alarm(DateTimeUtil.getCalendarForString("01.01.1970"), "Alarm1")),
				new Alarm(DateTimeUtil.getCalendarForString("11.11.2000"), "Alarm2")));
		// Bearbeiten eines Alarms ohne Änderung des Zeitstempels
		final Alarm alarm = new Alarm(DateTimeUtil.getCalendarForString("01.01.1970"), "Alarm1"); // genau ein Objekt
		testCases.add(new DataErrors(0, null,
				Arrays.asList(alarm),
				alarm));

		for (final DataErrors testCase : testCases) {
			controller.setExistingAlarms(testCase.existingAlarms);
			controller.setAlarm(testCase.alarm);
			FXTestUtils.awaitEvents();

			final GuiHelper guiHelper = mock(GuiHelper.class);
			controller.setGuiHelper(guiHelper);

			// Speichern-Schaltfläche betätigen
			alarmSaveButton.fire();
			FXTestUtils.awaitEvents();

			// sicherstellen, dass Fehlermeldung erscheint bzw. nicht
			verify(guiHelper, times(testCase.numberOfCalls)).showDialogMessage(anyString(),
					testCase.errorMessage == null ? anyString() : eq(testCase.errorMessage), isA(ResourceBundle.class));
		}
	}

	/**
	 * Testet das Verwerfen der Änderungen.
	 */
	@Test
	public final void testCancel() {
		final AlarmEditDialogController controller = (AlarmEditDialogController) getController();
		final CheckBox alarmEnabledCheckbox = (CheckBox) scene.lookup("#alarmEnabledCheckbox");
		final CalendarTextField alarmDatePicker = (CalendarTextField) scene.lookup("#alarmDatePicker");
		final TimePicker alarmTimePicker = (TimePicker) scene.lookup("#alarmTimePicker");
		final TextField alarmDescriptionTextField = (TextField) scene.lookup("#alarmDescriptionTextField");
		final Button alarmSelectSoundButton = (Button) scene.lookup("#alarmSelectSoundButton");
		final Button alarmCancelButton = (Button) scene.lookup("#alarmCancelButton");

		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm vorgeben
		final Calendar dateTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		dateTime.clear();
		final Alarm alarm = new Alarm(dateTime, "bla");
		controller.setAlarm(alarm);
		FXTestUtils.awaitEvents();

		// deaktivieren
		alarmEnabledCheckbox.fire();

		// Datum setzen
		alarmDatePicker.setValue(DateTimeUtil.getCalendarForString("24.12.2014"));

		// Zeit setzen
		alarmTimePicker.setTime(DateTimeUtil.getCalendarForString("12:00:00"));

		// Beschreibung setzen
		alarmDescriptionTextField.setText("Test");

		// Sound setzen
		controller.setRingtone("Sound");
		FXTestUtils.awaitEvents();

		// Abbrechen-Schaltfläche betätigen
		alarmCancelButton.fire();
		FXTestUtils.awaitEvents();

		// TODO auf Schließen des Dialogs warten

		// TODO sicherstellen, dass Dialog geschlossen wurde
		// assertFalse(stage.isShowing());

		// sicherstellen, dass Alarm ursprüngliche Werte hat
		assertTrue(alarm.isEnabled());
		assertEquals(0, alarm.getDateTime().getTimeInMillis());
		assertEquals("bla", alarm.getDescription());
		assertEquals(null, alarm.getSound());
	}

	/**
	 * Testet das Auswählen, Anhören und Löschen eines Klingeltons.
	 */
	@Test
	public final void testSoundSelection() {
		final AlarmEditDialogController controller = (AlarmEditDialogController) getController();
		final Button alarmSelectSoundButton = (Button) scene.lookup("#alarmSelectSoundButton");
		final Button alarmNoSoundButton = (Button) scene.lookup("#alarmNoSoundButton");
		final Button alarmPlaySoundButton = (Button) scene.lookup("#alarmPlaySoundButton");

		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm vorgeben
		final Alarm alarm = new Alarm(DateTimeUtil.getCalendarForString("01.01.1970"), "Bla");
		controller.setAlarm(alarm);
		FXTestUtils.awaitEvents();

		// Sound setzen
		controller.setRingtone("Sound");
		FXTestUtils.awaitEvents();

		assertFalse(alarmNoSoundButton.isDisabled());
		assertFalse(alarmPlaySoundButton.isDisabled());

		// Sound-Abspielen-Schaltfläche betätigen
		final AudioPlayer player = mock(AudioPlayer.class);
		controller.setAudioPlayer(player);
		alarmPlaySoundButton.fire();
		FXTestUtils.awaitEvents();
		verify(player).playInThread(eq("Sound"), isA(Thread.UncaughtExceptionHandler.class));

		// Sound-Löschen-Schaltfläche betätigen
		alarmNoSoundButton.fire();
		FXTestUtils.awaitEvents();

		assertEquals("kein Klingelton gewählt", alarmSelectSoundButton.getText());
	}

	private final class DataErrors {

		public final int numberOfCalls;
		public final String errorMessage;
		public final List<Alarm> existingAlarms;
		public final Alarm alarm;

		public DataErrors(final int numberOfCalls, final String messageMatcher, final List<Alarm> existingAlarms, final Alarm alarm) {
			this.numberOfCalls = numberOfCalls;
			this.errorMessage = messageMatcher;
			this.existingAlarms = existingAlarms;
			this.alarm = alarm;
		}

	}

}
