package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.utils.FXTestUtils;

/**
 * GUI-Tests für die Alarm-Funktionalität.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class AlarmControllerTest extends FxmlGuiTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "Alarm.fxml";
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();

		// Tabelle leeren
		final TableView<Alarm> alarmTable = (TableView<Alarm>) scene.lookup("#alarmTable");
		alarmTable.getItems().clear();
	}

	/**
	 * Testet das Löschen von Alarmen.
	 */
	@Test
	public final void testDeleteAlarm() {
		final TableView<Alarm> alarmTable = (TableView<Alarm>) scene.lookup("#alarmTable");

		// zwei Alarme anlegen
		final ObservableList<Alarm> tableData = alarmTable.getItems();
		final Alarm alarm1 = new Alarm();
		final Alarm alarm2 = new Alarm();
		tableData.add(alarm1);
		tableData.add(alarm2);

		final Button alarmDeleteButton = (Button) scene.lookup("#alarmDeleteButton");

		// Zustand der Schaltflächen testen
		assertTrue(alarmDeleteButton.isVisible());
		assertTrue(alarmDeleteButton.isDisabled());

		// zweiten Alarm auswählen
		alarmTable.getSelectionModel().select(alarm2);

		// Zustand der Schaltflächen testen
		assertTrue(alarmDeleteButton.isVisible());
		assertFalse(alarmDeleteButton.isDisabled());

		// Alarm löschen
		alarmDeleteButton.fire();
		FXTestUtils.awaitEvents();

		// sicherstellen, dass zweiter Alarm gelöscht ist, erster aber nicht
		assertTrue(tableData.contains(alarm1));
		assertFalse(tableData.contains(alarm2));

		// sicherstellen, dass kein anderer Alarm ausgewählt ist
		assertNull(alarmTable.getSelectionModel().getSelectedItem());

		// Zustand der Schaltflächen testen
		assertTrue(alarmDeleteButton.isVisible());
		assertTrue(alarmDeleteButton.isDisabled());

		// ersten Alarm auswählen
		alarmTable.getSelectionModel().select(alarm1);

		// Alarm löschen
		alarmDeleteButton.fire();
		FXTestUtils.awaitEvents();

		// sicherstellen, dass keine Alarme mehr existieren
		assertTrue(tableData.isEmpty());

		// Zustand der Schaltflächen testen
		assertTrue(alarmDeleteButton.isVisible());
		assertTrue(alarmDeleteButton.isDisabled());
	}

	/**
	 * Testet die Darstellung von Alarmen in der Tabelle.
	 */
	@Test
	public final void testAlarmTableRendering() {
		final TableView<Alarm> alarmTable = (TableView<Alarm>) scene.lookup("#alarmTable");

		// Alarm anlegen
		final ObservableList<Alarm> tableData = alarmTable.getItems();
		final Alarm alarm1 = new Alarm(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00"), "Test");
		tableData.add(alarm1);

		/*
		 * Sicherstellen, dass Zellen die korrekten Objekte enthalten.
		 * Wäre z. B. nicht der Fall, wenn der Name des Alarm-Attributs nicht mit dem Namen der Spalte übereinstimmt.
		 */
		final Object dateTimeCellData = alarmTable.getColumns().get(0).getCellData(0);
		assertNotNull(dateTimeCellData);
		assertEquals(alarm1.getDateTime().getTime(), ((Calendar) dateTimeCellData).getTime());

		final Object descriptionCellData = alarmTable.getColumns().get(1).getCellData(0);
		assertNotNull(descriptionCellData);
		assertEquals(alarm1.getDescription(), (String) descriptionCellData);
	}

}
