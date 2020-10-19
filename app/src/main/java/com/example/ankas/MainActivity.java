package com.example.ankas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ankas.Adapter.CategoryAdapter;
import com.example.ankas.Class.Category;
import com.example.ankas.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ExpandableHeightGridView gridCategory;

    ArrayList<Category> categoryArrayList;
    CategoryAdapter categoryAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_window);

        categoryArrayList = new ArrayList<>(); // Создаем лист для подкатегорий
        categoryAdapter = new CategoryAdapter(this, R.layout.item_category, categoryArrayList); // Создаем адаптер
        requestQueue = Volley.newRequestQueue(this);
        //Список категорий
        jsonParseCategory(); // Получить категории товара и вывести
        gridCategory = (ExpandableHeightGridView) findViewById(R.id.gridCategory); // Обьявление GridView
        gridCategory.setExpanded(true);
        gridCategory.setAdapter(categoryAdapter); // Присваиваем адаптер
        gridOnClick(); // Обработка нажатий
        menuNavigation();
    }
    // Меню навигации
    private void menuNavigation() {
        LinearLayout layoutShop, layoutMenuBrash, layoutBasket,layoutHuman;
        layoutShop = (LinearLayout) findViewById(R.id.layoutShop); // Категории
        layoutBasket = (LinearLayout) findViewById(R.id.layoutBasket); // Корзина
        layoutHuman = (LinearLayout) findViewById(R.id.layoutHuman); // Пользователь
        layoutMenuBrash = (LinearLayout) findViewById(R.id.layoutMenuBrash); // Меню

        layoutBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BasketWindow.class);
                startActivity(intent);
            }
        });
        // Переход в профиль или авторизацию
        layoutHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((User.login == "Null") && User.password == "Null"){
                    Intent intent = new Intent(MainActivity.this, UserAuthorizationWindow.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, UserAuthorizedWindow.class);
                    startActivity(intent);
                }
            }
        });
        layoutMenuBrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuBrashWindow.class);
                startActivity(intent);
            }
        });
    }

    private void gridOnClick() {
        gridCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SubcategoryWindow.idSelectCategory = CategoryAdapter.categoryArrayList.get(position).getId();
                Intent intent = new Intent(MainActivity.this, SubcategoryWindow.class);
                startActivity(intent);
            }
        });
    }

    private void jsonParseCategory() {
        String url = "http://anndroidankas.h1n.ru/php/category.php";

        categoryArrayList.clear(); // Отчищаем лист с категориями
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("CATEGORY");
                            for (int i = 0; i < jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                int id = object.getInt("id");
                                String title = object.getString("title");
                                String image_url = object.getString("image_url"); // Получаем URL картики
                                categoryArrayList.add(new Category(id,title,image_url)); // Добавляем категорию
                            }
                            categoryAdapter.notifyDataSetChanged(); // Отправка в адаптер для добавление категорий товара
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


//Toast.makeText(getApplicationContext(), "id " + CategoryAdapter.categoryArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();