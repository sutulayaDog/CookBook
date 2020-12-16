package by.bstu.fit.alexsandrova.projectbd.Help;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Settings(Context context){
        preferences = context.getSharedPreferences("Settings", 0);
        editor = preferences.edit();
    }

    public void setValue(String key, Integer value){
        editor.putInt(key, value);
        editor.commit();
    }

    public Integer getValue(String key, Integer defaultValue){
        return preferences.getInt(key, defaultValue);
    }
}
