package by.bstu.fit.alexsandrova.projectbd.Models;

import android.database.Cursor;

public class ProductInResipe {
    private int idProductInResipe;
    private int idProduct;
    private int idResipe;
    private String quantity;

    private int dateLastChange;
    private int timeLastChange;
    private String status;

    public ProductInResipe(int id, int idProduct, int idResipe, String quantity,
                           int dateLastChange, int timeLastChange, String status){
        this.idProductInResipe = id;
        this.idProduct = idProduct;
        this.idResipe = idResipe;
        this.quantity = quantity;
        this.dateLastChange = dateLastChange;
        this.timeLastChange = timeLastChange;
        this.status = status;
    }
    public ProductInResipe(Cursor cursor){
        this.idProductInResipe = cursor.getInt(0);
        this.idProduct = cursor.getInt(1);
        this.idResipe = cursor.getInt(2);
        this.quantity = cursor.getString(3);
        this.dateLastChange = cursor.getInt(4);
        this.timeLastChange = cursor.getInt(5);
        this.status = cursor.getString(6);
    }

    public int getIdProductInResipe() {
        return idProductInResipe;
    }

    public void setIdProductInResipe(int idProductInResipe) {
        this.idProductInResipe = idProductInResipe;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdResipe() {
        return idResipe;
    }

    public void setIdResipe(int idResipe) {
        this.idResipe = idResipe;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
}
