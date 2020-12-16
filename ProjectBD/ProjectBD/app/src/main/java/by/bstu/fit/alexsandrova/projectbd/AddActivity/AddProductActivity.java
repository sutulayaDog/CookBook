package by.bstu.fit.alexsandrova.projectbd.AddActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import by.bstu.fit.alexsandrova.projectbd.Help.UriBuilder;
import by.bstu.fit.alexsandrova.projectbd.Database.DbHelper;
import by.bstu.fit.alexsandrova.projectbd.R;

public class AddProductActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText NameProductEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        NameProductEditText = findViewById(R.id.NameProductEditText);
        setTitle("Добавить продукт");
    }

    public void addProduct_onClick(View view){
        if(NameProductEditText.getText().toString().length() == 0){
            Toast.makeText(this, "Поле не заполнено", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("Name", NameProductEditText.getText().toString());

        Uri res = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCT), values);

        if (res == null){
            Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
            finish();
        }

        Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show();
        finish();
    }
}
