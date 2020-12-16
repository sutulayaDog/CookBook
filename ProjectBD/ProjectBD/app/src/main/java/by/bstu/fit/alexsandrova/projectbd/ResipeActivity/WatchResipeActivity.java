package by.bstu.fit.alexsandrova.projectbd.ResipeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import by.bstu.fit.alexsandrova.projectbd.R;
import by.bstu.fit.alexsandrova.projectbd.CategorieActivity.CategorieResipeActivity;

public class WatchResipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_resipe);
        setTitle("Выбор");
    }

    public void allResipe_onClick(View view){
        startActivity(new Intent(WatchResipeActivity.this, AllResipeActivity.class));
    }
    public void categorie_onClick(View view){
        startActivity(new Intent(WatchResipeActivity.this, CategorieResipeActivity.class));
    }
}
