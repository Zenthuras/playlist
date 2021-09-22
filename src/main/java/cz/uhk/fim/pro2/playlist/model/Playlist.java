package cz.uhk.fim.pro2.playlist.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlist {
    private ObservableList<Song> songs = FXCollections.observableArrayList();

    public Playlist() {
    }

    public ObservableList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ObservableList<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void removeSong(Song song) {
        this.songs.remove(song);
    }

}
