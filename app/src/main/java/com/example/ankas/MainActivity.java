package com.example.ankas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ExpandableHeightGridView gridCategory;
    TextView textLoading;

    Timer timer;
    int tick;

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

        timerLoading(); // Таймер
    }
    // Меню навигации
    private void menuNavigation() {
        LinearLayout layoutShop, layoutMenuBrash, layoutBasket,layoutHuman; // Обьявление Layout
        layoutBasket = (LinearLayout) findViewById(R.id.layoutBasket); // Корзина
        layoutHuman = (LinearLayout) findViewById(R.id.layoutHuman); // Пользователь
        layoutMenuBrash = (LinearLayout) findViewById(R.id.layoutMenuBrash); // Меню
        // Переход в корзину
        layoutBasket.setOnClickListener(new View.OnClickListener() { // Сканер нажатия
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BasketWindow.class);
                startActivity(intent);
            }
        });
        // Переход в профиль или авторизацию
        layoutHuman.setOnClickListener(new View.OnClickListener() { // Сканер нажатия
            @Override
            public void onClick(View view) {
                if ((User.login == "Null") && User.password == "Null"){ // Если пользователь не авторизован то переход на форму авторизации
                    Intent intent = new Intent(MainActivity.this, UserAuthorizationWindow.class);
                    startActivity(intent);
                } else { // Если пользователь авторизирован, то переход в профль
                    Intent intent = new Intent(MainActivity.this, UserAuthorizedWindow.class);
                    startActivity(intent);
                }
            }
        });
        // Переход в подробность о организации
        layoutMenuBrash.setOnClickListener(new View.OnClickListener() { // Сканер нажатия
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
                SubcategoryWindow.titleSelectCategory = CategoryAdapter.categoryArrayList.get(position).getTitle();
                Intent intent = new Intent(MainActivity.this, SubcategoryWindow.class);
                startActivity(intent);
            }
        });
    }

    private void jsonParseCategory() {
        String url = "http://anndroidankas.h1n.ru/php/category.php"; // Ссылка

        categoryArrayList.clear(); // Отчищаем лист с категориями
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("CATEGORY"); // Название массива
                            for (int i = 0; i < jsonArray.length();i++){ // Цикл по обходу каждой строки
                                JSONObject object = jsonArray.getJSONObject(i); // Плучение одной строки
                                int id = object.getInt("id"); // Идентификатор
                                String title = object.getString("title"); // Название
                                String image_url = object.getString("image_url"); // Получаем URL картики
                                categoryArrayList.add(new Category(id,title,image_url)); // Добавляем категорию
                            }
                            categoryAdapter.notifyDataSetChanged(); // Отправка в адаптер для добавление категорий товара
                            timer.cancel();
                            textLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                timer.cancel();
                // Вывод ошибки об отсутсивие интернета
                textLoading.setVisibility(View.VISIBLE);
                textLoading.setText("Нет подключения к Интернету!");
            }
        });
        requestQueue.add(request);
    }

    private void timerLoading(){
        tick = 0;
        timer = new Timer();
        timer.schedule(new MainActivity.UpdateTimeTask(), 0, 300);
        textLoading = (TextView) findViewById(R.id.textLoading);
    }
    //Timer
    private class UpdateTimeTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (tick) {
                        case 0:
                            textLoading.setText("Загрузка");
                            break;
                        case 1:
                            textLoading.setText("Загрузка.");
                            break;
                        case 2:
                            textLoading.setText("Загрузка..");
                            break;
                        case 3:
                            textLoading.setText("Загрузка...");
                            tick = -1;
                            break;
                    }
                    tick++;
                }
            });
        }
    }
}


//Toast.makeText(getApplicationContext(), "id " + CategoryAdapter.categoryArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();