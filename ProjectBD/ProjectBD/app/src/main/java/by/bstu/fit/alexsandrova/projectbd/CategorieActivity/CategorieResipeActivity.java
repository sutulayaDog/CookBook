package by.bstu.fit.alexsandrova.projectbd.CategorieActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import by.bstu.fit.alexsandrova.projectbd.Help.ArrayBuilder;
import by.bstu.fit.alexsandrova.projectbd.Help.UriBuilder;
import by.bstu.fit.alexsandrova.projectbd.Database.DbHelper;
import by.bstu.fit.alexsandrova.projectbd.Models.Categorie;
import by.bstu.fit.alexsandrova.projectbd.R;

public class CategorieResipeActivity extends AppCompatActivity {

    private ListView categorielistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_categorie_resipe);
        categorielistView = findViewById(R.id.CategorielistView);

        setTitle("Категории");
        categorielistView.setOnItemClickListener(defaultItemClickOfList);
        loadLists();
    }

    AdapterView.OnItemClickListener defaultItemClickOfList = (parent, view, position, id) -> {
        int idCategorie = ((Categorie)parent.getItemAtPosition(position)).getIdCategorie();

        Intent intent = new Intent(CategorieResipeActivity.this, CategoryActivity.class);
        intent.putExtra("idCategorie", String.valueOf(idCategorie));
        startActivity(intent);
    };

    private void loadLists(){
        Cursor cursor= getContentResolver().query(UriBuilder.getUri(UriBuilder.CATEGORIE),
                null,"Status is null or Status not like 'R'", null, null);
        ArrayList<Categorie> list = new ArrayList<>();

        if(cursor.moveToFirst()){
            list = ArrayBuilder.getCategories(cursor);
        }
        categorielistView.setAdapter(new ArrayAdapter<Categorie>(this, android.R.layout.simple_list_item_1, list));
        cursor.close();
    }
    protected void onPostResume() {
        super.onPostResume();
        loadLists();
    }
}
