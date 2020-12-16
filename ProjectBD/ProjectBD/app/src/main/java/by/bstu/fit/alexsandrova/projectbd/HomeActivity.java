package by.bstu.fit.alexsandrova.projectbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import by.bstu.fit.alexsandrova.projectbd.AddActivity.AddResipeActivity;
import by.bstu.fit.alexsandrova.projectbd.HelpfulActivity.LifeHackActivity;
import by.bstu.fit.alexsandrova.projectbd.HelpfulActivity.TimerActivity;
import by.bstu.fit.alexsandrova.projectbd.ResipeActivity.WatchResipeActivity;
import by.bstu.fit.alexsandrova.projectbd.Services.Synchronizer;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Cook Book");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.timer :
                startActivity(new Intent(HomeActivity.this, TimerActivity.class));
                return true;
            case R.id.lifehack:
                startActivity(new Intent(HomeActivity.this, LifeHackActivity.class));
                return true;
            case R.id.sync:
                Toast.makeText(this, "Синхронизация", Toast.LENGTH_SHORT).show();
                Intent intentService = new Intent(this, Synchronizer.class);
                startService(intentService);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void add_onClick(View view){
        startActivity(new Intent(HomeActivity.this, AddResipeActivity.class));
    }
    public void watch_onClick(View view){
        startActivity(new Intent(HomeActivity.this, WatchResipeActivity.class));
    }
}
