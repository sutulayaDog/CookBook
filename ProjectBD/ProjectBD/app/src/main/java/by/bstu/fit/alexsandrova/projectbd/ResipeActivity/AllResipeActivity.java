package by.bstu.fit.alexsandrova.projectbd.ResipeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.bstu.fit.alexsandrova.projectbd.AddActivity.AddResipeActivity;
import by.bstu.fit.alexsandrova.projectbd.Help.ArrayBuilder;
import by.bstu.fit.alexsandrova.projectbd.Help.UriBuilder;
import by.bstu.fit.alexsandrova.projectbd.Models.Resipe;
import by.bstu.fit.alexsandrova.projectbd.R;

public class AllResipeActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Resipe> mAdapter;
    List<Resipe> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_all_resipe);
        setTitle("Все рецепты");

        listView = findViewById(R.id.listView);
        list = new ArrayList<>();

        listView.setOnItemClickListener(defaultItemClickOfList);
        listView.setOnItemLongClickListener(defaultLongClickListener);

        loadList();
    }

    AdapterView.OnItemClickListener defaultItemClickOfList = (parent, view, position, id) -> {
        int idResipe = ((Resipe)parent.getItemAtPosition(position)).getIdResipe();

        Intent intent = new Intent(AllResipeActivity.this, ResipeActivity.class);
        intent.putExtra("id", idResipe);
        startActivity(intent);
    };
    AdapterView.OnItemLongClickListener defaultLongClickListener = (parent, view, position, id) ->{
        int idResipe = ((Resipe)parent.getItemAtPosition(position)).getIdResipe();

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menulayout);
        popupMenu.setOnMenuItemClickListener((menu) -> {
            switch (menu.getItemId()) {
                case R.id.action_remove:
                    AlertDialog.Builder ad = new AlertDialog.Builder(this);
                    ad.setTitle("Уверены?");  // заголовок
                    ad.setPositiveButton("Да", (dialog, arg1) -> {

                        int res = getContentResolver().delete(UriBuilder.getUri(UriBuilder.RESIPE, idResipe),
                                null, null);

                        if(res > 0)
                            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Не удалось выполнить действие", Toast.LENGTH_SHORT).show();

                        loadList();
                    });
                    ad.setNegativeButton("Нет", (dialog, which) -> {});
                    ad.show();
                    return true;
                case R.id.action_updateResipe:
                    Intent intent4 = new Intent(AllResipeActivity.this, AddResipeActivity.class);
                    intent4.putExtra("id", idResipe);
                    startActivity(intent4);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();

        return true;
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate menu with items using MenuInflator
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        // Initialise menu item search bar
        // with id and take its object
        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        SearchView searchView
                = (SearchView) MenuItemCompat
                .getActionView(searchViewItem);


        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        if (list.contains(query)) {
                            mAdapter.getFilter().filter(query);
                        }
                        else {
                            // Search query not found in List View
                            Toast.makeText(AllResipeActivity.this,
                                    "Not found",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        mAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

        return super.onCreateOptionsMenu(menu);
    }
    private void loadList(){
        Cursor cursor= getContentResolver().query(UriBuilder.getUri(UriBuilder.RESIPE),
                null, "Status is null or Status not like 'R'",null, null);

        if(cursor.moveToFirst())
            list = ArrayBuilder.getResipes(cursor);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(mAdapter);
        cursor.close();
    }
    protected void onPostResume() {
        super.onPostResume();
        loadList();
    }
}
