package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.TimeZone;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import jfxtras.labs.scene.control.CalendarTextField;

import org.junit.Before;
import org.junit.Test;

import rmblworx.tools.timey.gui.component.TimePicker;

/**
 * GUI-Tests für den Dialog zum Bearbeiten eines Alarms.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class AlarmEditDialogControllerTest extends FxmlGuiTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "AlarmEditDialogGui.fxml";
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

		// Alarm anlegen
		final Alarm alarm = new Alarm(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00"), "Test");
		controller.setAlarm(alarm);

		// sicherstellen, dass Formularfelder korrekt gefüllt sind
		assertTrue(alarmEnabledCheckbox.isSelected());
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014").getTime(), alarmDatePicker.getValue().getTime());
		assertEquals(DateTimeUtil.getCalendarForString("12:00:00").getTime(), alarmTimePicker.getTime().getTime());
		assertEquals("Test", alarmDescriptionTextField.getText());
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

		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm anlegen
		final Alarm alarm = new Alarm(DateTimeUtil.getCalendarForString("01.01.1970"), "Bla");
		controller.setAlarm(alarm);

		// deaktivieren
		alarmEnabledCheckbox.fire();

		// Datum setzen
		alarmDatePicker.setValue(DateTimeUtil.getCalendarForString("24.12.2014"));

		// Zeit setzen
		alarmTimePicker.setTime(DateTimeUtil.getCalendarForString("12:00:00"));

		// Beschreibung setzen
		alarmDescriptionTextField.setText("Test");

		// Speichern-Schaltfläche betätigen
		final Button alarmSaveButton = (Button) scene.lookup("#alarmSaveButton");
		alarmSaveButton.fire();

		// TODO auf Schließen des Dialogs warten
		sleep(50); // TODO anders lösen

		// TODO sicherstellen, dass Dialog geschlossen wurde
		// assertFalse(stage.isShowing());

		// sicherstellen, dass Alarm neue Werte hat
		assertFalse(alarm.isEnabled());
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014").getTime(), DateTimeUtil.getDatePart(alarm.getDateTime()).getTime());
		assertEquals(DateTimeUtil.getCalendarForString("12:00:00").getTime(), DateTimeUtil.getTimePart(alarm.getDateTime()).getTime());
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00").getTime(), alarm.getDateTime().getTime());
		assertEquals("Test", alarm.getDescription());
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

		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm anlegen
		final Calendar dateTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		dateTime.clear();
		final Alarm alarm = new Alarm(dateTime, "bla");
		controller.setAlarm(alarm);

		// deaktivieren
		alarmEnabledCheckbox.fire();

		// Datum setzen
		alarmDatePicker.setValue(DateTimeUtil.getCalendarForString("24.12.2014"));

		// Zeit setzen
		alarmTimePicker.setTime(DateTimeUtil.getCalendarForString("12:00:00"));

		// Beschreibung setzen
		alarmDescriptionTextField.setText("Test");

		// Abbrechen-Schaltfläche betätigen
		final Button alarmCancelButton = (Button) scene.lookup("#alarmCancelButton");
		alarmCancelButton.fire();

		// TODO auf Schließen des Dialogs warten
		sleep(50); // TODO anders lösen

		// TODO sicherstellen, dass Dialog geschlossen wurde
		// assertFalse(stage.isShowing());

		// sicherstellen, dass Alarm ursprüngliche Werte hat
		assertTrue(alarm.isEnabled());
		assertEquals(0, alarm.getDateTime().getTimeInMillis());
		assertEquals("bla", alarm.getDescription());
	}

}
