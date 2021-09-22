package cz.uhk.fim.pro2.playlist.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {
    private String name;
    private String interpret;
    private int minutes;
    private int seconds;
    private String time;
    private int order;

    public Song(String name, String interpret, int minutes, int seconds, String time, int order) {
        this.name = name;
        this.interpret = interpret;
        this.minutes = minutes;
        this.seconds = seconds;
        this.time = time;
        this.order = order;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getTime() {
        return time;
    }

    public StringProperty getTimeProperty() {
        return new SimpleStringProperty(time);
    }

    public void setTime() {
        this.time = time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterpret() {
        return interpret;
    }

    public StringProperty getInterpretProperty() {
        return new SimpleStringProperty(interpret);
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public int getOrder() {
        return order;
    }

    public IntegerProperty getOrderProperty() {
        return new SimpleIntegerProperty(order);
    }

    public void setOrder(int order) {
        this.order = order;
    }


}
