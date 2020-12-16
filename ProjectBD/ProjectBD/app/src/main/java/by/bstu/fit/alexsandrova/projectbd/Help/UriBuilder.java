package by.bstu.fit.alexsandrova.projectbd.Help;

import android.net.Uri;

public class UriBuilder {
    public static final String AUTHORITY = "by.bstu.fit.alexsandrova.bdproject.providers.ContentnProvider";

    public static final String CATEGORIE = "CategoriesList";
    public static final String RESIPE = "ResipesList";
    public static final String PRODUCT = "ProductList";
    public static final String PRODUCTINRESIPE = "ProductInResipeList";

    public static Uri getUri(String table) {
        return Uri.parse("content://" + AUTHORITY + "/" + table);
    }

    public static Uri getUri(String table, String arg1) {
        return Uri.parse("content://" + AUTHORITY + "/" + table + "/" + arg1);
    }

    public static Uri getUri(String table, String arg1, String arg2) {
        return Uri.parse("content://" + AUTHORITY + "/" + table + "/" + arg1 + "/" + arg2);
    }

    public static Uri getUri(String table, int arg1) {
        return Uri.parse("content://" + AUTHORITY + "/" + table + "/" + arg1);
    }

    public static Uri getUri(String table, int arg1, int arg2) {
        return Uri.parse("content://" + AUTHORITY + "/" + table + "/" + arg1 + "/" + arg2);
    }
}
