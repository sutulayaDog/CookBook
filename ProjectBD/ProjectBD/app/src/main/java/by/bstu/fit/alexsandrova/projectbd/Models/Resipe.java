package by.bstu.fit.alexsandrova.projectbd.Models;

import android.database.Cursor;

public class Resipe {
    private int idResipe;
    private int idCategorie;
    private String name;
    private String description;
    private String timeCook;

    private int dateLastChange;
    private int timeLastChange;
    private String status;

    public Resipe(int id, int idCategorie, String name, String description, String timeCook,
                  int dateLastChange, int timeLastChange, String status){
        this.idResipe = id;
        this.idCategorie = idCategorie;
        this.name = name;
        this.description = description;
        this.timeCook = timeCook;
        this.dateLastChange = dateLastChange;
        this.timeLastChange = timeLastChange;
        this.status = status;
    }
    public Resipe(Cursor cursor){
        this.idResipe = cursor.getInt(0);
        this.idCategorie = cursor.getInt(1);
        this.name = cursor.getString(2);
        this.description = cursor.getString(3);
        this.timeCook = cursor.getString(4);
        this.dateLastChange = cursor.getInt(5);
        this.timeLastChange = cursor.getInt(6);
        this.status = cursor.getString(7);
    }

    public int getIdResipe() {
        return idResipe;
    }

    public void setIdResipe(int idResipe) {
        this.idResipe = idResipe;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeCook() {
        return timeCook;
    }

    public void setTimeCook(String timeCook) {
        this.timeCook = timeCook;
    }

    public int getDateLastChange() {
        return dateLastChange;
    }

    public void setDateLastChange(int dateLastChange) {
        this.dateLastChange = dateLastChange;
    }

    public int getTimeLastChange() {
        return timeLastChange;
    }

    public void setTimeLastChange(int timeLastChange) {
        this.timeLastChange = timeLastChange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString(){
        return name;
    }
}
