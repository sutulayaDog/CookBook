package by.bstu.fit.alexsandrova.projectbd.Database;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;

import by.bstu.fit.alexsandrova.projectbd.Help.DateTime;

public class DbProvider extends ContentProvider {
        static final String AUTHORITY = "by.bstu.fit.alexsandrova.bdproject.providers.ContentnProvider";

        static final String CATEGORIE = "CategoriesList";
        static final String RESIPE = "ResipesList";
        static final String PRODUCT = "ProductList";
        static final String PRODUCTINRESIPE = "ProductInResipeList";


        //id  специфические строки
        static final int URI_CATEGORIES = 1;
        static final int URI_CATEGORIE = 2;
        static final int URI_RESIPES = 3;
        static final int URI_RESIPE = 4;
        static final int URI_PRODUCT = 5;
        static final int URI_PRODUCTS = 6;
        static final int URI_PRODUCTINRESIPE = 7;
        static final int URI_PRODUCTSINRESIPES= 8;



        private static final UriMatcher uriMatcher;
        DbHelper dbHelper;
        SQLiteDatabase db;

        //помощь в управлении URI в провайдере
        static {
                uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
                uriMatcher.addURI(AUTHORITY, CATEGORIE, URI_CATEGORIES);
                uriMatcher.addURI(AUTHORITY, CATEGORIE + "/#", URI_CATEGORIE );

                uriMatcher.addURI(AUTHORITY, RESIPE , URI_RESIPES);
                uriMatcher.addURI(AUTHORITY, RESIPE + "/#", URI_RESIPE);

                uriMatcher.addURI(AUTHORITY, PRODUCT , URI_PRODUCTS);
                uriMatcher.addURI(AUTHORITY, PRODUCT + "/#", URI_PRODUCT);

                uriMatcher.addURI(AUTHORITY, PRODUCTINRESIPE , URI_PRODUCTSINRESIPES);
                uriMatcher.addURI(AUTHORITY, PRODUCTINRESIPE + "/#", URI_PRODUCTINRESIPE);
        }

        @Override
        public boolean onCreate() {
                dbHelper = new DbHelper(getContext());
                return true;
        }

        @Nullable
        @Override
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
                Cursor cursor;
                db = dbHelper.getReadableDatabase();

                switch (uriMatcher.match(uri)){
                case URI_CATEGORIES:
                        // ВОЗВРАЩАЕТ СПИСОК КАТЕГОРИЙ
                        cursor = db.query("Categorie", new String[] {"IdCategorie", "Name", "DateLastChange", "TimeLastChange", "Status"}, selection,
                        selectionArgs, null, null, null);
                        break;
                case URI_CATEGORIE:{
                        //возвращает список рецептов относящиеся к этой категории т.е categories/1 значит то мы получим списко всех рецептов в категории с id=1
                        String id = uri.getLastPathSegment();

                        cursor = db.query("Categorie", new String[] {"IdCategorie", "Name", "DateLastChange", "TimeLastChange", "Status"},  "IdCategorie = ?",
                        new String[]{id}, null, null,null);
                        break;
                }
                case URI_RESIPES:
                        // получить все рецепты
                        cursor = db.query("Resipe", new String[] {"IdResipe", "IdCategorie", "Name", "Description", "TimeCook", "DateLastChange", "TimeLastChange", "Status"}, selection,
                                selectionArgs, null, null, null);
                        break;
                case URI_RESIPE: {
                        //получить инфу об определенном рецепте
                        String id = uri.getLastPathSegment();
                        cursor = db.query("Resipe", new String[]{"IdResipe", "IdCategorie", "Name", "Description", "TimeCook", "DateLastChange", "TimeLastChange", "Status"}, "IdResipe = ?",
                        new String[]{id}, null, null, null);
                        break;
                }
                case URI_PRODUCTS:
                        // получить все продукты
                        cursor = db.query("Product", new String[] {"IdProduct", "Name", "DateLastChange", "TimeLastChange", "Status"}, selection,
                                selectionArgs, null, null, null);
                        break;
                case URI_PRODUCT:{
                        //получить инфу об определенном продукте
                        String id = uri.getLastPathSegment();
                        cursor = db.query("Product", new String[]{"IdProduct", "Name", "DateLastChange", "TimeLastChange", "Status"}, "IdProduct = ?",
                        new String[]{id}, null, null, null);
                        break;
                }
                case URI_PRODUCTSINRESIPES:
                        // получить все продукты в рецепте
                        cursor = db.query("Product_in_Resipe", new String[] {"IdProductInResipe", "IdProduct", "IdResipe", "Quantity", "DateLastChange", "TimeLastChange", "Status"}, selection,
                                selectionArgs, null, null, null);
                        break;
                case URI_PRODUCTINRESIPE:
                        //получить инфу об определенном продукте
                        String id = uri.getLastPathSegment();
                        cursor = db.query("Product_in_Resipe", new String[]{"IdProductInResipe", "IdProduct", "IdResipe", "Quantity", "DateLastChange", "TimeLastChange", "Status"}, "IdProductInResipe = ?",
                        new String[]{id}, null, null, null);
                        break;
                default:
                        throw new IllegalArgumentException("Wrong URI: " + uri);
                }

                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
        }

        @Nullable
        @Override
        public String getType(@NonNull Uri uri) { return null; }

        @Nullable
        @Override
        public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
                db = dbHelper.getWritableDatabase();
                long id = -1;

                if(!values.containsKey("DateLastChange"))
                        values.put("DateLastChange", DateTime.getDateSpecialFormat(new Date()));
                if(!values.containsKey("TimeLastChange"))
                        values.put("TimeLastChange", DateTime.getTimeSpecialFormat(new Date()));

                switch (uriMatcher.match(uri)){
                        case URI_RESIPES:
                                id = db.insert("Resipe", null, values);
                                break;
                        case URI_PRODUCTS:
                                id = db.insert("Product", null, values);
                                break;
                        case URI_CATEGORIES:
                                id = db.insert("Categorie", null, values);
                                break;
                        case URI_PRODUCTSINRESIPES:
                                id = db.insert("Product_in_Resipe", null, values);
                                break;
                        default:
                                throw  new IllegalArgumentException("Wrong uri: " + uri.toString());
                }

                Uri result = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(result, null);
                return result;
        }

        @Override
        public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
                int res = -1;
                db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("Status", "R");
                values.put("DateLastChange", DateTime.getDateSpecialFormat(new Date()));
                values.put("TimeLastChange", DateTime.getTimeSpecialFormat(new Date()));

                switch (uriMatcher.match(uri)){
                        case URI_RESIPE:{
                                res = db.update("Resipe", values, "IdResipe = ?", new String[]{uri.getLastPathSegment()});
                                break;
                        }
                        case URI_CATEGORIE:{
                                res = db.update("Categorie", values, "IdCategorie = ?", new String[]{uri.getLastPathSegment()});
                                break;
                        }
                        case URI_PRODUCT:{
                                res = db.update("Product", values,"IdProduct = ?", new String[]{uri.getLastPathSegment()});
                                break;
                        }
                        case URI_PRODUCTINRESIPE:{
                                res = db.update("Product_in_Resipe", values,"IdProductInResipe = ?", new String[]{uri.getLastPathSegment()});
                                break;
                        }
                        default:
                                throw new IllegalArgumentException("Wrong URI: " + uri);
                }

                getContext().getContentResolver().notifyChange(uri, null);
                return res;
        }

        @Override
        public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
                db = dbHelper.getWritableDatabase();
                int count = 0;

                if(!values.containsKey("DateLastChange"))
                        values.put("DateLastChange", DateTime.getDateSpecialFormat(new Date()));
                if(!values.containsKey("TimeLastChange"))
                        values.put("TimeLastChange", DateTime.getTimeSpecialFormat(new Date()));

                switch (uriMatcher.match(uri)){
                        case URI_RESIPE:{
                                count = db.update("Resipe", values, "IdResipe = ?", new String[]{uri.getLastPathSegment()});
                                break;
                        }
                        case URI_CATEGORIE:{
                                count = db.update("Categorie", values, "IdCategorie = ?", new String[]{uri.getLastPathSegment()});
                                break;
                        }
                        case URI_PRODUCT:{
                                count = db.update("Product", values,"IdProduct = ?", new String[]{uri.getLastPathSegment()});
                                break;
                        }
                        case URI_PRODUCTINRESIPE:{
                                count = db.update("Product_in_Resipe", values,"IdProductInResipe = ?", new String[]{uri.getLastPathSegment()});
                                break;
                        }
                        default:
                                throw new IllegalArgumentException("Wrong URI: " + uri);
                }
                return count;
        }
}