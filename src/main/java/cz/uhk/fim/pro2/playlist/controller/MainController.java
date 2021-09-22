package cz.uhk.fim.pro2.playlist.controller;

import cz.uhk.fim.pro2.playlist.model.Playlist;
import cz.uhk.fim.pro2.playlist.model.Song;
import cz.uhk.fim.pro2.playlist.utils.CSVParser;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // --- UI komponenty ---
    @FXML
    private TableView<Song> tblPlaylist;
    @FXML
    private TableColumn<Song, Integer> colOrder;
    @FXML
    private TableColumn<Song, String> colName;
    @FXML
    private TableColumn<Song, String> colInterpret;
    @FXML
    private TableColumn<Song, String> colTime;

    @FXML
    private Label lblTotalTime;

    @FXML
    private TextField addName;
    @FXML
    private TextField addInterpret;
    @FXML
    private TextField addMinutes;
    @FXML
    private TextField addSeconds;

    @FXML
    private Button btnDeleteSong;
    @FXML
    private Button btnSaveSongs;
    @FXML
    private Button btnAddSong;
    @FXML
    private Button btnMoveUp;
    @FXML
    private Button btnMoveDown;
    // --- konec UI komponent ---

    private final Playlist playlist = new Playlist();
    private Song currentSong;
    private final ObservableList<Song> songs = this.playlist.getSongs();

    public MainController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        initComponents();
        Song song = new Song("Numb", "Linkin Park", 3, 6, "03:06", 1);
        this.playlist.addSong(song);
        song = new Song("One More Light", "Linkin Park", 4, 31, "04:31", 2);
        this.playlist.addSong(song);
        song = new Song("Heavy", "Linkin Park", 2, 49, "02:49", 3);
        this.playlist.addSong(song);
    }

    private void initTable() {
        colOrder.setCellValueFactory(cellData -> cellData.getValue().getOrderProperty().asObject());
        colName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        colInterpret.setCellValueFactory(cellData -> cellData.getValue().getInterpretProperty());
        colTime.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());

        tblPlaylist.setItems(this.playlist.getSongs());
        tblPlaylist.setPlaceholder(new Label("Žádná hudba k zobrazení"));


        tblPlaylist.getSelectionModel().getSelectedCells().addListener(new ListChangeListener<TablePosition>() {
            @Override
            public void onChanged(Change<? extends TablePosition> change) {

                TableView.TableViewSelectionModel selectionModel = tblPlaylist.getSelectionModel();
                ObservableList selectedCells = selectionModel.getSelectedCells();

                TablePosition tablePosition;
                if (tblPlaylist.getItems().size() != 0) {
                    tablePosition = (TablePosition) selectedCells.get(0);
                    currentSong = playlist.getSongs().get(tablePosition.getRow());
                }
            }

        });

    }

    private void initComponents() {
        btnAddSong.setOnAction(e -> addSong());
        btnDeleteSong.setOnAction(e -> deleteSong());
        btnMoveUp.setOnAction(e -> moveSongUp());
        btnMoveDown.setOnAction(e -> moveSongDown());
        btnSaveSongs.setOnAction(e -> saveSongs());
        songs.addListener(new ListChangeListener<Song>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Song> c) {
                if (playlist.getSongs() != null) {
                    lblTotalTime.setText(calculateTotalTime());
                } else {
                    lblTotalTime.setText("0");
                }
            }
        });
    }

    private void addSong() {
        String name = addName.getText();
        String interpret = addInterpret.getText();

        String minutes = addMinutes.getText();
        String seconds = addSeconds.getText();

        boolean isNumericMinutes = isNumeric(minutes);
        boolean isNumericSeconds = isNumeric(seconds);

        String time = minutes + ":" + seconds;

        int order;
        if (this.playlist.getSongs().size() == 0) {
            order = 1;
        } else {
            order = this.playlist.getSongs().size() + 1;
        }

        if (!name.equals("") && !interpret.equals("") && !minutes.equals("") && !seconds.equals("") && isNumericMinutes && isNumericSeconds && Integer.parseInt(seconds) < 60) {

            Song song = new Song(name, interpret, Integer.parseInt(minutes), Integer.parseInt(seconds), time, order);
            this.playlist.addSong(song);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potvrzení");
            alert.setHeaderText("Informace");
            String s = "Skladba přidána!";
            alert.setContentText(s);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText("Informace");
            String s = "Zadejte správné hodnoty!";
            alert.setContentText(s);
            alert.show();
        }

    }

    private void deleteSong() {
        this.playlist.removeSong(this.currentSong);
        updateOrder();
    }

    private String calculateTotalTime() {
        int totalMinutes = 0;
        int totalSeconds = 0;

        for (Song song : this.playlist.getSongs()) {
            totalMinutes += song.getMinutes();
            totalSeconds += song.getSeconds();

        }

        if (totalSeconds >= 60) {
            totalMinutes += totalSeconds / 60;
            totalSeconds = totalSeconds % 60;
        }

        return totalMinutes + ":" + totalSeconds;

    }

    private void moveSongUp() {
        if (tblPlaylist.getItems().size() != 0) {
            int index = tblPlaylist.getSelectionModel().getSelectedIndex();
            if (index != 0) {
                tblPlaylist.getItems().add(index - 1, tblPlaylist.getItems().remove(index));
                tblPlaylist.getSelectionModel().clearAndSelect(index - 1);
                updateOrder();
            }
        }
    }

    private void moveSongDown() {
        if (tblPlaylist.getItems().size() != 0) {
            int index = tblPlaylist.getSelectionModel().getSelectedIndex();
            if (index != tblPlaylist.getItems().size() - 1) {
                tblPlaylist.getItems().add(index + 1, tblPlaylist.getItems().remove(index));
                tblPlaylist.getSelectionModel().clearAndSelect(index + 1);
                updateOrder();
            }
        }
    }

    private void updateOrder() {
        for (int i = 0; i < tblPlaylist.getItems().size(); i++) {
            tblPlaylist.getItems().get(i).setOrder(i + 1);
            tblPlaylist.refresh();
        }
    }

    private boolean isNumeric(String number) {

        if (number == null) {
            return false;
        }
        try {
            int x = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void saveSongs() {
        if (tblPlaylist.getItems().size() != 0) {
            CSVParser.generateCsv("skladby", tblPlaylist.getItems());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potvrzení uložení");
            alert.setHeaderText("Informace");
            String s = "Skladby uloženy!";
            alert.setContentText(s);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chyba");
            alert.setHeaderText("Informace");
            String s = "Prázdný playlist!";
            alert.setContentText(s);
            alert.show();
        }
    }
}
