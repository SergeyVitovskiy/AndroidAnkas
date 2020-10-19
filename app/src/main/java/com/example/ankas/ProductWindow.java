package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ankas.Adapter.ProductAdapter;
import com.example.ankas.Class.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductWindow extends AppCompatActivity {

    GridView gridProduct;

    static int idSelectSubcategory;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_windows);

        productArrayList = new ArrayList<>(); // Создаем лист для подкатегорий
        productAdapter = new ProductAdapter(this, R.layout.item_product, productArrayList); // Создаем адаптер
        requestQueue = Volley.newRequestQueue(this);
        //Список категорий
        jsonParseProduct(idSelectSubcategory); // Парсинг
        gridProduct = (GridView) findViewById(R.id.gridProduct); // Обьявление GridView
        gridProduct.setAdapter(productAdapter); // Присваиваем адаптер
        menuNavigation(); // Меню
        gridOnClick(); // Обработка нажатий
        
    }

    private void gridOnClick() {
        gridProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ProductDetailWindow.idSelectProductDetail = ProductAdapter.productArrayList.get(position).getArticle();
                Intent intent = new Intent(ProductWindow.this, ProductDetailWindow.class);
                startActivity(intent);
            }
        });
    }

    // Меню навигации
    private void menuNavigation() {
        LinearLayout layoutShop, layoutMenuBrash, layoutBasket,layoutHuman;
        layoutShop = (LinearLayout) findViewById(R.id.layoutShop); // Категории
        layoutBasket = (LinearLayout) findViewById(R.id.layoutBasket); // Корзина
        layoutHuman = (LinearLayout) findViewById(R.id.layoutHuman); // Пользователь
        layoutMenuBrash = (LinearLayout) findViewById(R.id.layoutMenuBrash); // Меню

        layoutShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductWindow.this, SubcategoryWindow.class);
                startActivity(intent);
            }
        });
        layoutBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductWindow.this, BasketWindow.class);
                startActivity(intent);
            }
        });
        layoutHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductWindow.this, UserAuthorizationWindow.class);
                startActivity(intent);
            }
        });
        layoutMenuBrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductWindow.this, MenuBrashWindow.class);
                startActivity(intent);
            }
        });
    }

    private void jsonParseProduct(int position) {
        String url = "http://anndroidankas.h1n.ru/php/product.php?subcategory="+position;

        productArrayList.clear(); // Отчищаем лист с категориями
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("PRODUCT");
                            for (int i = 0; i < jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                int article = object.getInt("article");
                                String title = object.getString("title_product");
                                String price = object.getString("price");
                                String brand = object.getString("country") + ", " + object.getString("title_brand");
                                String image_url= object.getString("image_url"); // Ссылка на картинку
                                String description= object.getString("description"); // Описание
                                String availability= object.getString("availability");
                                productArrayList.add(new Product(article, title, price, brand, image_url, description, availability)); // Добавляем товар
                            }
                            productAdapter.notifyDataSetChanged(); // Отправка в адаптер для добавление категорий товара
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}
