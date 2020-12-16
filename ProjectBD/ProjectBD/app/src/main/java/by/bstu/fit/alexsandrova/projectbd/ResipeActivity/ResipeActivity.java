package by.bstu.fit.alexsandrova.projectbd.ResipeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import by.bstu.fit.alexsandrova.projectbd.Help.UriBuilder;
import by.bstu.fit.alexsandrova.projectbd.Database.DbHelper;
import by.bstu.fit.alexsandrova.projectbd.Models.Resipe;
import by.bstu.fit.alexsandrova.projectbd.R;

public class ResipeActivity extends AppCompatActivity {

    TextView nameResipeTextView;
    TextView descriptionTextView;
    TextView timeCookTextView;
    TextView productTextView;

    Resipe resipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_resipe);

        nameResipeTextView = findViewById(R.id.nameResipeTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        timeCookTextView = findViewById(R.id.timeCookTextView);
        productTextView = findViewById(R.id.productTextView);
        setTitle("Рецепт");

        String id = String.valueOf(getIntent().getIntExtra("id", 0));

        Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.RESIPE, id), null, null, null);
        if (cursor.moveToFirst()) {
            resipe = new Resipe(cursor);
        }

        nameResipeTextView.setText(resipe.getName());
        descriptionTextView.setText(resipe.getDescription());
        timeCookTextView.setText(resipe.getTimeCook());

        cursor.close();

        cursor = new DbHelper(this).getReadableDatabase().rawQuery("select t2.name, t1.Quantity from Product_in_Resipe t1\n" +
                "inner join Product t2\n" +
                "on t1.IdProduct = t2.IdProduct\n" +
                "where t1.IdResipe = ?", new String[]{id});

        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()) {
            do{
                sb.append(cursor.getString(0) + " - " + cursor.getString(1) + "\n");
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        productTextView.setText(sb.toString());
    }}