package rmblworx.tools.timey.gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
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

/**
 * Controller für die Alarm-GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class AlarmController extends Controller {

	/**
	 * Formatiert Zeitstempel als Datum/Zeit-Werte.
	 */
	private SimpleDateFormat dateTimeFormatter;

	@FXML
	private ResourceBundle resources;

	@FXML
	private TableView<Alarm> alarmTable;

	@FXML
	private TableColumn<Alarm, Calendar> alarmDateTimeColumn;

	@FXML
	private TableColumn<Alarm, String> alarmDescriptionColumn;

	@FXML
	private Button alarmEditButton;

	@FXML
	private Button alarmDeleteButton;

	@FXML
	private void initialize() {
		assert alarmTable != null : "fx:id='alarmTable' was not injected";
		assert alarmDateTimeColumn != null : "fx:id='alarmDateTimeColumn' was not injected";
		assert alarmDescriptionColumn != null : "fx:id='alarmDescriptionColumn' was not injected";
		assert alarmEditButton != null : "fx:id='alarmEditButton' was not injected";
		assert alarmDeleteButton != null : "fx:id='alarmDeleteButton' was not injected";

		if (alarmDateTimeColumn != null) {
			alarmDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Alarm, Calendar>("dateTime"));

			alarmDateTimeColumn.setCellFactory(new Callback<TableColumn<Alarm, Calendar>, TableCell<Alarm, Calendar>>() {
				public TableCell<Alarm, Calendar> call(final TableColumn<Alarm, Calendar> param) {
					return new TableCell<Alarm, Calendar>() {
						protected void updateItem(final Calendar item, final boolean empty) {
							super.updateItem(item, empty);
							setText(empty ? null : dateTimeFormatter.format(item.getTimeInMillis()));
						}
					};
				}
			});
		}

		if (alarmDescriptionColumn != null) {
			alarmDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Alarm, String>("description"));
		}

		if (alarmTable != null) {
			// CSS-Klasse für inaktive Alarme setzen
			alarmTable.setRowFactory(new Callback<TableView<Alarm>, TableRow<Alarm>>() {
				public TableRow<Alarm> call(final TableView<Alarm> tableView) {
					final TableRow<Alarm> row = new TableRow<Alarm>() {
						protected void updateItem(final Alarm alarm, final boolean empty) {
							super.updateItem(alarm, empty);
							if (alarm != null) {
								if (!alarm.isEnabled()) {
									getStyleClass().add("alarm-disabled");
								}
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
					if (alarmEditButton != null) {
						alarmEditButton.setDisable(!isItemSelected);
					}
					if (alarmDeleteButton != null) {
						alarmDeleteButton.setDisable(!isItemSelected);
					}
				}
			});

			// standardmäßig nach Datum/Zeit sortieren, älteste zuerst
			if (alarmDateTimeColumn != null) {
				alarmDateTimeColumn.setSortType(SortType.ASCENDING);
				alarmTable.getSortOrder().add(alarmDateTimeColumn);
			}

			addSampleData();
		}

		if (alarmEditButton != null) {
			alarmEditButton.setDisable(true);
		}

		if (alarmDeleteButton != null) {
			alarmDeleteButton.setDisable(true);
		}

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
					alarmTable.getItems().add(alarm);
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
					alarmTable.getSelectionModel().clearSelection(); // Auswahl aufheben
					alarmTable.getItems().remove(alarm);
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
			if (showAlarmEditDialog(alarm, resources.getString("alarmEdit.title.edit"))) {
				refreshTable();
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
		final Alarm selectedItem = alarmTable.getSelectionModel().getSelectedItem();
		final ObservableList<Alarm> tableData = alarmTable.getItems();
		alarmTable.setItems(null);
		alarmTable.layout();
		alarmTable.setItems(tableData);
		alarmTable.getSelectionModel().select(selectedItem); // wichtig (@see http://javafx-jira.kenai.com/browse/RT-26291)
	}

	/**
	 * Fügt der Tabelle Beispieldaten hinzu.
	 */
	private void addSampleData() {
		final Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.SECOND, 5);
		final Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.SECOND, 10);
		final Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.SECOND, 15);
		final ObservableList<Alarm> tableData = alarmTable.getItems();
		tableData.add(new Alarm(cal1, "noch wird hier ..."));
		tableData.add(new Alarm(cal2, "... nichts persistiert ..."));
		tableData.add(new Alarm(cal3, "... oder ausgelöst", false));
	}

	/**
	 * Initialisiert den Datum/Zeit-Formatierer.
	 */
	private void setupDateTimeFormatter() {
		if (dateTimeFormatter == null) {
			dateTimeFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		}
	}

}
