package by.bstu.fit.alexsandrova.projectbd;

import android.app.Application;

import by.bstu.fit.alexsandrova.projectbd.Help.Settings;
import by.bstu.fit.alexsandrova.projectbd.Help.Global;

public class CookBookApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Global.settings = new Settings(getApplicationContext());
    }
}
