package by.bstu.fit.alexsandrova.projectbd.AddActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.bstu.fit.alexsandrova.projectbd.Help.ArrayBuilder;
import by.bstu.fit.alexsandrova.projectbd.Help.UriBuilder;
import by.bstu.fit.alexsandrova.projectbd.Models.Categorie;
import by.bstu.fit.alexsandrova.projectbd.Models.Product;
import by.bstu.fit.alexsandrova.projectbd.Models.ProductInResipe;
import by.bstu.fit.alexsandrova.projectbd.Models.Resipe;
import by.bstu.fit.alexsandrova.projectbd.R;

public class AddResipeActivity extends AppCompatActivity {
    EditText NameEditText;
    EditText DescriptionEditText;
    EditText TimeCookEditText;

    EditText Quantity1;
    EditText Quantity2;
    EditText Quantity3;
    EditText Quantity4;
    EditText Quantity5;

    Spinner IdCategorieSpinner;
    Spinner IdProduct1Spinner;
    Spinner IdProduct2Spinner;
    Spinner IdProduct3Spinner;
    Spinner IdProduct4Spinner;
    Spinner IdProduct5Spinner;

    Resipe resipe;
    List<ProductInResipe> listProductInResipes;
    int idResipe = 0;

    ArrayList<Categorie> listCategories = new ArrayList<>();
    ArrayList<Product> listProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resipe);
        NameEditText = findViewById(R.id.NameEditText);
        DescriptionEditText = findViewById(R.id.DescriptionEditText);
        TimeCookEditText = findViewById(R.id.TimeCookEditText);
        IdCategorieSpinner = findViewById(R.id.IdCategorieSpinner);
        IdProduct1Spinner = findViewById(R.id.IdProduct1Spinner);
        IdProduct2Spinner = findViewById(R.id.IdProduct2Spinner);
        IdProduct3Spinner = findViewById(R.id.IdProduct3Spinner);
        IdProduct4Spinner = findViewById(R.id.IdProduct4Spinner);
        IdProduct5Spinner = findViewById(R.id.IdProduct5Spinner);
        Quantity1 = findViewById(R.id.Quantity1);
        Quantity2 = findViewById(R.id.Quantity2);
        Quantity3 = findViewById(R.id.Quantity3);
        Quantity4 = findViewById(R.id.Quantity4);
        Quantity5 = findViewById(R.id.Quantity5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addproduct_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.add_product :
                startActivity(new Intent(AddResipeActivity.this, AddProductActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onPostResume() {
        super.onPostResume();
        fillSpinner();
        setData();
    }

    private void fillSpinner() {
        listCategories = new ArrayList<>();
        listCategories.add(new Categorie("(не выбрано)"));

        Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.CATEGORIE), null, "Status is null or Status != 'R'",
                null, null);

        if(cursor.moveToFirst()){
            listCategories.addAll(ArrayBuilder.getCategories(cursor));
        }
        cursor.close();

        listProducts = new ArrayList<>();
        listProducts.add(new Product("(не выбрано)"));

        cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.PRODUCT),null, "Status is null or Status != 'R'",
                null, null);

        if(cursor.moveToFirst()){
            listProducts.addAll(ArrayBuilder.getProducts(cursor));
        }

        IdProduct1Spinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, listProducts));

        IdProduct2Spinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, listProducts));

        IdProduct3Spinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, listProducts));

        IdProduct4Spinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, listProducts));

        IdProduct5Spinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, listProducts));

        IdCategorieSpinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, listCategories));
    }
    public void setData(){
        idResipe = getIntent().getIntExtra("id", 0);
        if(idResipe == 0)
            setTitle("Добавить рецепт");
        else{
            setTitle("Изменить рецепт");
            findViewById(R.id.updateResipeButton).setVisibility(View.VISIBLE);
            findViewById(R.id.addResipeButton).setVisibility(View.GONE);

            Cursor cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.RESIPE, idResipe), null,
                    null, null, null);

            if(cursor.moveToFirst())
                resipe = new Resipe(cursor);
            cursor.close();

            cursor = getContentResolver().query(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), null,
                    "IdResipe = ? and (Status is null or Status != 'R')", new String[] {String.valueOf(idResipe)}, null);

            listProductInResipes = new ArrayList<>();
            if(cursor.moveToFirst())
                listProductInResipes = ArrayBuilder.getProductsInResipes(cursor);

            // заполенние полей
            NameEditText.setText(resipe.getName());
            DescriptionEditText.setText(resipe.getDescription());
            TimeCookEditText.setText(resipe.getTimeCook());

            // установка занчение спиннера
            for (int i = 0; i < listCategories.size(); i++){
                if(listCategories.get(i).getIdCategorie() == resipe.getIdCategorie()) {
                    IdCategorieSpinner.setSelection(i);
                    break;
                }
            }

            // установка зачений для продуктов
            if(listProductInResipes.size() >= 1)
            {
                for (int i = 0; i < listProducts.size(); i++){
                    if(listProducts.get(i).getIdProduct() == listProductInResipes.get(0).getIdProduct()){
                        IdProduct1Spinner.setSelection(i);
                        Quantity1.setText(listProductInResipes.get(0).getQuantity());
                    }

                }
            }

            if(listProductInResipes.size() >= 2)
            {
                for (int i = 0; i < listProducts.size(); i++){
                    if(listProducts.get(i).getIdProduct() == listProductInResipes.get(1).getIdProduct()){
                        IdProduct2Spinner.setSelection(i);
                        Quantity2.setText(listProductInResipes.get(1).getQuantity());
                    }

                }
            }

            if(listProductInResipes.size() >= 3)
            {
                for (int i = 0; i < listProducts.size(); i++){
                    if(listProducts.get(i).getIdProduct() == listProductInResipes.get(2).getIdProduct()){
                        IdProduct3Spinner.setSelection(i);
                        Quantity3.setText(listProductInResipes.get(2).getQuantity());
                    }

                }
            }

            if(listProductInResipes.size() >= 4)
            {
                for (int i = 0; i < listProducts.size(); i++){
                    if(listProducts.get(i).getIdProduct() == listProductInResipes.get(3).getIdProduct()){
                        IdProduct4Spinner.setSelection(i);
                        Quantity4.setText(listProductInResipes.get(3).getQuantity());
                    }

                }
            }

            if(listProductInResipes.size() == 5)
            {
                for (int i = 0; i < listProducts.size(); i++){
                    if(listProducts.get(i).getIdProduct() == listProductInResipes.get(4).getIdProduct()){
                        IdProduct5Spinner.setSelection(i);
                        Quantity5.setText(listProductInResipes.get(4).getQuantity());
                    }

                }
            }
        }
    }

    public void addResipe_onClick(View view){
        // если ничего не выбрано из списка то мы не сможем ничего добавить
        if(IdCategorieSpinner.getSelectedItemId() == 0 || IdProduct1Spinner.getSelectedItemId() == 0){
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("Name", NameEditText.getText().toString());
        values.put("Description", DescriptionEditText.getText().toString());
        values.put("TimeCook", TimeCookEditText.getText().toString());
        values.put("IdCategorie", ((Categorie)IdCategorieSpinner.getSelectedItem()).getIdCategorie());

        Uri res = getContentResolver().insert(UriBuilder.getUri(UriBuilder.RESIPE), values);

        if (res == null){
            Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
            finish();
        }

        idResipe = Integer.parseInt(res.getLastPathSegment());
        if(IdProduct1Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct1Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity1.getText().toString());
            res = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);

            if (res == null){
                Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if(IdProduct2Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct2Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity2.getText().toString());
            res = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);

            if (res == null){
                Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if(IdProduct3Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct3Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity3.getText().toString());
            res = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);

            if (res == null){
                Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if(IdProduct4Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct4Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity4.getText().toString());
            res = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);

            if (res == null){
                Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if(IdProduct5Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct5Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity1.getText().toString());
            res = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);

            if (res == null){
                Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void updateResipe_onClick(View view){
        if(IdCategorieSpinner.getSelectedItemId() == 0 || IdProduct1Spinner.getSelectedItemId() == 0){
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("Name", NameEditText.getText().toString());
        values.put("Description", DescriptionEditText.getText().toString());
        values.put("TimeCook", TimeCookEditText.getText().toString());
        values.put("IdCategorie", ((Categorie)IdCategorieSpinner.getSelectedItem()).getIdCategorie());

        int res = getContentResolver().update(UriBuilder.getUri(UriBuilder.RESIPE, idResipe), values, null, null);

        if (res == 0){
            Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
            finish();
        }

        Uri uri;
        if(IdProduct1Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct1Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity1.getText().toString());

            if(listProductInResipes.size() == 1){
                res = getContentResolver().update(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE, listProductInResipes.get(0).getIdProductInResipe()), value, null, null);

                if (res == 0){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else {
                uri = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);
                if(uri == null){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }



        if(IdProduct2Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct2Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity2.getText().toString());
            if(listProductInResipes.size() == 2){
                res = getContentResolver().update(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE, listProductInResipes.get(1).getIdProductInResipe()), value, null, null);

                if (res == 0){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else {
                uri = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);
                if(uri == null){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        if(IdProduct3Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct3Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity3.getText().toString());
            if(listProductInResipes.size() == 3){
                res = getContentResolver().update(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE, listProductInResipes.get(2).getIdProductInResipe()), value, null, null);

                if (res == 0){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else {
                uri = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);
                if(uri == null){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        if(IdProduct4Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct4Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity4.getText().toString());
            if(listProductInResipes.size() == 4){
                res = getContentResolver().update(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE, listProductInResipes.get(3).getIdProductInResipe()), value, null, null);

                if (res == 0){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else {
                uri = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);
                if(uri == null){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        if(IdProduct5Spinner.getSelectedItemId() != 0 ) {
            ContentValues value = new ContentValues();
            value.put("IdProduct", ((Product) IdProduct5Spinner.getSelectedItem()).getIdProduct());
            value.put("IdResipe", idResipe);
            value.put("Quantity", Quantity1.getText().toString());
            if(listProductInResipes.size() == 5){
                res = getContentResolver().update(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE, listProductInResipes.get(4).getIdProductInResipe()), value, null, null);

                if (res == 0){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else {
                uri = getContentResolver().insert(UriBuilder.getUri(UriBuilder.PRODUCTINRESIPE), value);
                if(uri == null){
                    Toast.makeText(this, "Ошибка записи в базу данных", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        Toast.makeText(this, "Изменено", Toast.LENGTH_SHORT).show();
        finish();
    }
}
