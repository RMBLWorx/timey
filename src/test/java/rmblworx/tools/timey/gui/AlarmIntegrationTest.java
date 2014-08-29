package rmblworx.tools.timey.gui;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.awt.TrayIcon;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.loadui.testfx.categories.TestFX;
import org.loadui.testfx.utils.FXTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.ITimey;
import rmblworx.tools.timey.TimeyFacade;
import rmblworx.tools.timey.gui.component.TimePicker;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Integrationstests für die Alarm-Funktionalität.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@Category(TestFX.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml", "/alarm-spring-timey-context.xml" })
public class AlarmIntegrationTest extends FxmlGuiTest {

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "Alarm.fxml";
	}

	/**
	 * {@inheritDoc}
	 */
	protected final ITimey setUpFacade() {
		return new TimeyFacade();
	}

	/**
	 * Testet
	 * <ol>
	 * <li>Anlegen eines Alarms per GUI,</li>
	 * <li>Senden des Ereignisses zum Auslösen des Alarms per Backend und</li>
	 * <li>Reaktion der GUI auf das Ereignis.</li>
	 * </ol>
	 */
	@Test
	@Ignore("schlägt derzeit fehl") // TODO
	public final void testAlarmCreatingAndExpiring() {
		/*
		 * Zeitdifferenz, die der Alarm in der Zukunft ausgelöst werden soll.
		 * Wert möglichst klein halten, um Test nich unnötig lange dauern zu lassen, aber dennoch groß genug, um sicherzustellen, dass
		 * Alarm bis zu diesem Zeitpunkt vollständig angelegt werden kann.
		 */
		final int bufferInSeconds = 2;

		final AlarmController controller = (AlarmController) getController();
		final GuiHelper guiHelper = controller.getGuiHelper();
		final ITimey facade = guiHelper.getFacade();

		// zwei unwichtige Alarme per Fassade hinzufügen
		final LocalDateTime now = LocalDateTime.now().withNano(0);
		facade.setAlarm(AlarmDescriptorConverter.getAsAlarmDescriptor(new Alarm(now.minusYears(1), "alter Alarm", "", false)));
		facade.setAlarm(AlarmDescriptorConverter.getAsAlarmDescriptor(new Alarm(now.plusYears(1), "Zukunftsalarm")));

		// relevanten Alarm per GUI hinzufügen
		final Button alarmAddButton = (Button) stage.getScene().lookup("#alarmAddButton");
		click(alarmAddButton);
		waitForThreads();

		final Scene dialogScene = controller.getDialogStage().getScene();
		final TimePicker alarmTimePicker = (TimePicker) dialogScene.lookup("#alarmTimePicker");
		final Button alarmSaveButton = (Button) dialogScene.lookup("#alarmSaveButton");

		Platform.runLater(new Runnable() {
			public void run() {
				alarmTimePicker.setValue(alarmTimePicker.getValue().plusSeconds(bufferInSeconds));
			}
		});
		FXTestUtils.awaitEvents();

		final MessageHelper messageHelper = mock(MessageHelper.class);
		guiHelper.setMessageHelper(messageHelper);

		// Speichern-Schaltfläche betätigen
		click(alarmSaveButton);
		waitForThreads();

		// sicherstellen, dass Ereignis verarbeitet wird
		verify(messageHelper, timeout(WAIT_FOR_EVENT)).showTrayMessageWithFallbackToDialog(anyString(), anyString(),
				isNull(TrayIcon.class), isA(ResourceBundle.class));
	}

}
