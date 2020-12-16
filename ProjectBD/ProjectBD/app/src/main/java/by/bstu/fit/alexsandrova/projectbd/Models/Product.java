package by.bstu.fit.alexsandrova.projectbd.Models;

import android.database.Cursor;

public class Product {
    private int idProduct;
    private String name;

    private int dateLastChange;
    private int timeLastChange;
    private String status;

    public Product(String name){
        this.idProduct = 0;
        this.name = name;
    }
    public Product(int id, String name, int dateLastChange, int timeLastChange, String status){
        this.idProduct = id;
        this.name = name;
        this.dateLastChange = dateLastChange;
        this.timeLastChange = timeLastChange;
        this.status = status;
    }
    public Product(Cursor cursor){
        this.idProduct = cursor.getInt(0);
        this.name = cursor.getString(1);
        this.dateLastChange = cursor.getInt(2);
        this.timeLastChange = cursor.getInt(3);
        this.status = cursor.getString(4);
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
