package by.bstu.fit.alexsandrova.projectbd.Help;

import android.database.Cursor;

import java.util.ArrayList;

import by.bstu.fit.alexsandrova.projectbd.Models.*;

public class ArrayBuilder {
    public static ArrayList<Categorie> getCategories(Cursor cursor){
        ArrayList<Categorie> list = new ArrayList<>();

        do {
            Categorie item = new Categorie(cursor);
            list.add(item);
        } while (cursor.moveToNext());

        return list;
    }
    public static ArrayList<Product> getProducts(Cursor cursor){
        ArrayList<Product> list = new ArrayList<>();

        do {
            list.add(new Product(cursor));
        } while (cursor.moveToNext());

        return list;
    }
    
    public static ArrayList<Resipe> getResipes(Cursor cursor){
        ArrayList<Resipe> list = new ArrayList<>();

        do {
            Resipe item = new Resipe(cursor);
            list.add(item);
        } while (cursor.moveToNext());

        return list;
    }
    public static ArrayList<ProductInResipe> getProductsInResipes(Cursor cursor){
        ArrayList<ProductInResipe> list = new ArrayList<>();

        do {
            ProductInResipe item = new ProductInResipe(cursor);
            list.add(item);
        } while (cursor.moveToNext());

        return list;
    }
}
