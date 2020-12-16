package by.bstu.fit.alexsandrova.projectbd.Database;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String password = "password12345678";

    public DbHelper(Context context) {
        super(context, "CookBook_DB", null, 1);
        SQLiteDatabase.loadLibs(context);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table Product(" +
                "IdProduct integer primary key autoincrement," +
                "Name integer," +
                "DateLastChange integer," +
                "TimeLastChange integer,"+
                "Status char(1)" +");");

        db.execSQL("create table Categorie(" +
                "IdCategorie integer primary key autoincrement," +
                "Name integer," +
                "DateLastChange integer," +
                "TimeLastChange integer,"+
                "Status char(1)" +");");

        db.execSQL("create table Resipe(" +
                "IdResipe integer primary key autoincrement," +
                "Name text," +
                "Description text," +
                "TimeCook text," +
                "IdCategorie text," +
                "DateLastChange integer," +
                "TimeLastChange integer," +
                "Status char(1)," +
                "foreign key(IdCategorie) references Categorie(IdCategorie)" +
                ");");

        db.execSQL("create table Product_in_Resipe(" +
                "IdProduct integer," +
                "IdResipe integer," +
                "IdProductInResipe integer primary key autoincrement," +
                "Quantity text," +
                "DateLastChange integer," +
                "TimeLastChange integer,"+
                "Status char(1)," +
                "foreign key(IdProduct) references Product(IdProduct)," +
                "foreign key(IdResipe) references Resipe(IdResipe)" +
                ");");


        db.execSQL("create TRIGGER afterSetRemovedStatus_Remove " +
                "after UPDATE on Resipe " +
                "when new.Status = 'R' " +
                "BEGIN " +
                "update Product_in_Resipe set Status = 'R', " +
                "DateLastChange = cast(substr(strftime('%Y',date('now','+3 hour')),3,2) || strftime('%m%d',date('now','+3 hour')) as INTEGER), " +
                "TimeLastChange = cast(strftime('%H%M%S',time('now','+3 hour')) as INTEGER) " +
                "where IdResipe = new.IdResipe; " +
                "end;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    public SQLiteDatabase getReadableDatabase() {
        return(super.getReadableDatabase(password));
    }

    public SQLiteDatabase getWritableDatabase() {
        return(super.getWritableDatabase(password));
    }
}
