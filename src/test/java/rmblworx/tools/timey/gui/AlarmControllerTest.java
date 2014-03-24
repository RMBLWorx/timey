package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;
import org.mockito.ArgumentMatcher;

import rmblworx.tools.timey.vo.AlarmDescriptor;

/**
 * GUI-Tests für die Alarm-Funktionalität.
 *
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
@Category(TestFX.class)
public class AlarmControllerTest extends FxmlGuiControllerTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	// GUI-Elemente
	private TableView<Alarm> alarmTable;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "Alarm.fxml";
	}

	@Before
	@SuppressWarnings("unchecked")
	public final void setUp() {
		scene = stage.getScene();

		alarmTable = (TableView<Alarm>) scene.lookup("#alarmTable");

		// Tabelle leeren
		alarmTable.getItems().clear();
	}

	/**
	 * Testet das Löschen von Alarmen.
	 */
	@Test
	public final void testDeleteAlarm() {
		// zwei Alarme anlegen
		final ObservableList<Alarm> tableData = alarmTable.getItems();
		final LocalDateTime now = LocalDateTime.now().millisOfSecond().setCopy(0);
		final Alarm alarm1 = new Alarm(now.secondOfMinute().addToCopy(5), "alarm1");
		final Alarm alarm2 = new Alarm(now.secondOfMinute().addToCopy(10), "alarm2");
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
		click(alarmDeleteButton);
		verify(getController().getGuiHelper().getFacade()).removeAlarm(argThat(new ArgumentMatcher<AlarmDescriptor>() {
			public boolean matches(final Object argument) {
				return ((AlarmDescriptor) argument).getAlarmtime().getMilliSeconds() == alarm2.getDateTimeInMillis();
			}
		}));

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
		click(alarmDeleteButton);

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
		// Alarm anlegen
		final ObservableList<Alarm> tableData = alarmTable.getItems();
		final Alarm alarm1 = new Alarm(DateTimeUtil.getLocalDateTimeForString("24.12.2014 12:00:00"), "Test");
		tableData.add(alarm1);

		/*
		 * Sicherstellen, dass Zellen die korrekten Objekte enthalten.
		 * Wäre z. B. nicht der Fall, wenn der Name des Alarm-Attributs nicht mit dem Namen der Spalte übereinstimmt.
		 */
		final LocalDateTime dateTimeCellData = (LocalDateTime) alarmTable.getColumns().get(0).getCellData(0);
		assertNotNull(dateTimeCellData);
		assertEquals(alarm1.getDateTime(), dateTimeCellData);

		final String descriptionCellData = (String) alarmTable.getColumns().get(1).getCellData(0);
		assertNotNull(descriptionCellData);
		assertEquals(alarm1.getDescription(), descriptionCellData);
	}

}
