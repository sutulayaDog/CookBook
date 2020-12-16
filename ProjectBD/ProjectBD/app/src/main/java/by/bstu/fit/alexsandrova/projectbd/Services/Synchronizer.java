package by.bstu.fit.alexsandrova.projectbd.Services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Date;

import by.bstu.fit.alexsandrova.projectbd.Help.*;
import by.bstu.fit.alexsandrova.projectbd.Models.*;

public class Synchronizer extends IntentService {
    private OkHttpClient client;

    private int lastUpdateDate;
    private int lastUpdateTime;

    public Synchronizer()
    {
        super("Synchronizer");
    }

    public void onCreate() {
        super.onCreate();

        client = new OkHttpClient();

        lastUpdateDate = Global.settings.getValue("lastUpdateDate", 0);
        lastUpdateTime = Global.settings.getValue("lastUpdateTime", 0);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // чекаем соедиенние с интернетом
        if(Internet.checkInternetConenction(this)) {
            // проверка доступен ли сервер
            if (!Internet.serverIsReachable())
                return;
        }
        else
            return;

        // обновляемся (от независимых к зависимым)
        SyncProductTable();
        SyncCategorieTable();
        SyncResipeTable();
        SyncProductInResipeTable();

        lastUpdateDate = DateTime.getDateSpecialFormat(new Date());
        Date dateWithoutSomeMinutes = DateTime.addMinutes(new Date(), -2);
        lastUpdateTime = DateTime.getTimeSpecialFormat(dateWithoutSomeMinutes);

        // сохраняем время последнего ообновления
        Global.settings.setValue("lastUpdateDate", lastUpdateDate);
        Global.settings.setValue("lastUpdateTime", lastUpdateTime);
    }

    private void SyncCategorieTable(){
        SyncSomeTable<Categorie> syncHelper =
                new SyncSomeTable<>(client, new TypeToken<ArrayList<Categorie>>(){});

        ArrayList<Categorie> list = syncHelper.update(lastUpdateDate, lastUpdateTime);
        ArrayList<Categorie> collectionToUpdate = new ArrayList();
        ArrayList<Categorie> collectionToInsert = new ArrayList();

        for(Categorie item: list){
            Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.CATEGORIE),
                    null, "IdCategorie = ?" , new String[]{String.valueOf(item.getIdCategorie())}, null, null);

            if(cursor.moveToFirst())
            {
                do {
                    int date = cursor.getInt(2);
                    int time = cursor.getInt(3);

                    if(date < item.getDateLastChange() ||
                            (date == item.getDateLastChange() && time < item.getTimeLastChange())){
                        collectionToUpdate.add(item);
                    }
                } while (cursor.moveToNext());
            }
            else
                collectionToInsert.add(item);
        }

        // добавление
        for(Categorie item : collectionToInsert){
            ContentValues values = new ContentValues();
            values.put("IdCategorie", item.getIdCategorie());
            values.put("Name", item.getName());
            values.put("DateLastChange", item.getDateLastChange());
            values.put("TimeLastChange", item.getTimeLastChange());

            if(item.getStatus() != null)
                values.put("Status", String.valueOf(item.getStatus().equals("R")? "R" : null));

            Uri a = getContentResolver().insert(UriBuilder.getUri(UriBuilder.CATEGORIE), values);
        }
        // обновление
        for(Categorie item : collectionToUpdate){
            ContentValues values = new ContentValues();
            values.put("IdCategorie", item.getIdCategorie());
            values.put("Name", item.getName());
            values.put("DateLastChange", item.getDateLastChange());
            values.put("TimeLastChange", item.getTimeLastChange());

            if(item.getStatus() != null)
                values.put("Status", String.valueOf(item.getStatus().equals("R")? "R" : null));

            int a = getContentResolver().update(UriBuilder.getUri(UriBuilder.CATEGORIE, String.valueOf(item.getIdCategorie())), values,
                    null, null);
        }

        // смысл отрпавлять что либо если запускаемся в первый раз
        if(lastUpdateDate == 0 && lastUpdateTime == 0)
            return;

        // передача серверу обновленных данных со стороны телефона
        Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.CATEGORIE),
                null,
                "DateLastChange >= ? or (DateLastChange == ? and TimeLastChange > ?)",
                new String[]{String.valueOf(lastUpdateDate), String.valueOf(lastUpdateDate),
                        String.valueOf(lastUpdateTime)}, null, null);

        if(cursor.moveToFirst())
            syncHelper.upgrade(ArrayBuilder.getCategories(cursor));
    }
    private void SyncProductTable(){
        SyncSomeTable<Product> syncHelper =
                new SyncSomeTable<>(client, new TypeToken<ArrayList<Product>>(){});

        ArrayList<Product> list = syncHelper.update(lastUpdateDate, lastUpdateTime);
        ArrayList<Product> collectionToUpdate = new ArrayList();
        ArrayList<Product> collectionToInsert = new ArrayList();

        for(Product item: list){
            Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.PRODUCT),
                    null, "IdProduct = ?" , new String[]{String.valueOf(item.getIdProduct())}, null, null);

            if(cursor.moveToFirst())
            {
                do {
                    int date = cursor.getInt(2);
                    int time = cursor.getInt(3);

                    if(date < item.getDateLastChange() ||
                            (date == item.getDateLastChange() && time < item.getTimeLastChange())){
                        collectionToUpdate.add(item);
                    }
                } while (cursor.moveToNext());
            }
            else
                collectionToInsert.add(item);
        }

        // добавление
        for(Product item : collectionToInsert){
            ContentValues values = new ContentValues();
            values.put("IdProduct", item.getIdProduct());
            values.put("Name", item.getName());
            values.put("DateLastChange", item.getDateLastChange());
            values.put("TimeLastChange", item.getTimeLastChange());

            if(item.getStatus() != null)
                values.put("Status", String.valueOf(item.getStatus().equals("R")? "R" : null));

            Uri a = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCT), values);
        }
        // обновление
        for(Product item : collectionToUpdate){
            ContentValues values = new ContentValues();
            values.put("IdProduct", item.getIdProduct());
            values.put("Name", item.getName());
            values.put("DateLastChange", item.getDateLastChange());
            values.put("TimeLastChange", item.getTimeLastChange());

            if(item.getStatus() != null)
                values.put("Status", String.valueOf(item.getStatus().equals("R")? "R" : null));

            int a = getContentResolver().update(UriBuilder.getUri(UriBuilder.PRODUCT, String.valueOf(item.getIdProduct())), values,
                    null, null);
        }

        // смысл отрпавлять что либо если запускаемся в первый раз
        if(lastUpdateDate == 0 && lastUpdateTime == 0)
            return;

        // передача серверу обновленных данных со стороны телефона
        Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.PRODUCT),
                null,
                "DateLastChange >= ? or (DateLastChange == ? and TimeLastChange > ?)",
                new String[]{String.valueOf(lastUpdateDate), String.valueOf(lastUpdateDate),
                        String.valueOf(lastUpdateTime)}, null, null);

        if(cursor.moveToFirst())
            syncHelper.upgrade(ArrayBuilder.getProducts(cursor));
    }
    private void SyncResipeTable(){
        SyncSomeTable<Resipe> syncHelper =
                new SyncSomeTable<>(client, new TypeToken<ArrayList<Resipe>>(){});

        ArrayList<Resipe> list = syncHelper.update(lastUpdateDate, lastUpdateTime);
        ArrayList<Resipe> collectionToUpdate = new ArrayList();
        ArrayList<Resipe> collectionToInsert = new ArrayList();

        for(Resipe item: list){
            Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.RESIPE),
                    null, "IdResipe = ?" , new String[]{String.valueOf(item.getIdResipe())}, null, null);

            if(cursor.moveToFirst())
            {
                do {
                    int date = cursor.getInt(5);
                    int time = cursor.getInt(6);

                    if(date < item.getDateLastChange() ||
                            (date == item.getDateLastChange() && time < item.getTimeLastChange())){
                        collectionToUpdate.add(item);
                    }
                } while (cursor.moveToNext());
            }
            else
                collectionToInsert.add(item);
        }

        // добавление
        for(Resipe item : collectionToInsert){
            ContentValues values = new ContentValues();
            values.put("IdResipe", item.getIdResipe());
            values.put("IdCategorie", item.getIdCategorie());
            values.put("Name", item.getName());
            values.put("Description", item.getDescription());
            values.put("TimeCook", item.getTimeCook());
            values.put("DateLastChange", item.getDateLastChange());
            values.put("TimeLastChange", item.getTimeLastChange());

            if(item.getStatus() != null)
                values.put("Status", String.valueOf(item.getStatus().equals("R")? "R" : null));

            Uri a = getContentResolver().insert(UriBuilder.getUri(UriBuilder.RESIPE), values);
        }
        // обновление
        for(Resipe item : collectionToUpdate){
            ContentValues values = new ContentValues();
            values.put("IdResipe", item.getIdResipe());
            values.put("IdCategorie", item.getIdCategorie());
            values.put("Name", item.getName());
            values.put("Description", item.getDescription());
            values.put("TimeCook", item.getTimeCook());
            values.put("DateLastChange", item.getDateLastChange());
            values.put("TimeLastChange", item.getTimeLastChange());

            if(item.getStatus() != null)
                values.put("Status", String.valueOf(item.getStatus().equals("R")? "R" : null));

            int a = getContentResolver().update(UriBuilder.getUri(UriBuilder.RESIPE, String.valueOf(item.getIdResipe())), values,
                    null, null);
        }

        // смысл отрпавлять что либо если запускаемся в первый раз
        if(lastUpdateDate == 0 && lastUpdateTime == 0)
            return;

        // передача серверу обновленных данных со стороны телефона
        Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.RESIPE),
                null,
                "DateLastChange >= ? or (DateLastChange == ? and TimeLastChange > ?)",
                new String[]{String.valueOf(lastUpdateDate), String.valueOf(lastUpdateDate),
                        String.valueOf(lastUpdateTime)}, null, null);

        if(cursor.moveToFirst())
            syncHelper.upgrade(ArrayBuilder.getResipes(cursor));
    }
    private void SyncProductInResipeTable(){
        SyncSomeTable<ProductInResipe> syncHelper =
                new SyncSomeTable<>(client, new TypeToken<ArrayList<ProductInResipe>>(){});

        ArrayList<ProductInResipe> list = syncHelper.update(lastUpdateDate, lastUpdateTime);
        ArrayList<ProductInResipe> collectionToUpdate = new ArrayList();
        ArrayList<ProductInResipe> collectionToInsert = new ArrayList();

        for(ProductInResipe item: list){
            Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE),
                    null, "IdProductInResipe = ?" , new String[]{String.valueOf(item.getIdProductInResipe())}, null, null);

            if(cursor.moveToFirst())
            {
                do {
                    int date = cursor.getInt(4);
                    int time = cursor.getInt(5);

                    if(date < item.getDateLastChange() ||
                            (date == item.getDateLastChange() && time < item.getTimeLastChange())){
                        collectionToUpdate.add(item);
                    }
                } while (cursor.moveToNext());
            }
            else
                collectionToInsert.add(item);
        }

        // добавление
        for(ProductInResipe item : collectionToInsert){
            ContentValues values = new ContentValues();
            values.put("IdProductInResipe", item.getIdProductInResipe());
            values.put("IdProduct", item.getIdProduct());
            values.put("IdResipe", item.getIdResipe());
            values.put("Quantity", item.getQuantity());
            values.put("DateLastChange", item.getDateLastChange());
            values.put("TimeLastChange", item.getTimeLastChange());

            if(item.getStatus() != null)
                values.put("Status", String.valueOf(item.getStatus().equals("R")? "R" : null));

            Uri a = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), values);
        }
        // обновление
        for(ProductInResipe item : collectionToUpdate){
            ContentValues values = new ContentValues();
            values.put("IdProductInResipe", item.getIdProductInResipe());
            values.put("IdProduct", item.getIdProduct());
            values.put("IdResipe", item.getIdResipe());
            values.put("Quantity", item.getQuantity());
            values.put("DateLastChange", item.getDateLastChange());
            values.put("TimeLastChange", item.getTimeLastChange());

            if(item.getStatus() != null)
                values.put("Status", String.valueOf(item.getStatus().equals("R")? "R" : null));

            int a = getContentResolver().update(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE, String.valueOf(item.getIdProductInResipe())), values,
                    null, null);
        }

        // смысл отрпавлять что либо если запускаемся в первый раз
        if(lastUpdateDate == 0 && lastUpdateTime == 0)
            return;

        // передача серверу обновленных данных со стороны телефона
        Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE),
                null,
                "DateLastChange >= ? or (DateLastChange == ? and TimeLastChange > ?)",
                new String[]{String.valueOf(lastUpdateDate), String.valueOf(lastUpdateDate),
                        String.valueOf(lastUpdateTime)}, null, null);

        if(cursor.moveToFirst())
            syncHelper.upgrade(ArrayBuilder.getProductsInResipes(cursor));
    }
}
