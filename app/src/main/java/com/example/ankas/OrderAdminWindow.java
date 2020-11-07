package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ankas.Adapter.ProductOrderAdapter;
import com.example.ankas.Adapter.ProductOrderAdminAdapter;
import com.example.ankas.Class.ProductOrderAdmin;
import com.example.ankas.Class.ProductOrder;
import com.example.ankas.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderAdminWindow  extends AppCompatActivity {

    ExpandableHeightGridView gridOrderAdmin;

    ArrayList<ProductOrderAdmin> orderAdminArrayList; // Лист с продуктами
    ProductOrderAdminAdapter productOrderAdminAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_admin_window);

        orderAdminArrayList = new ArrayList<>(); // Создаем лист для подкатегорий
        productOrderAdminAdapter = new ProductOrderAdminAdapter(this, R.layout.item_order_admin, orderAdminArrayList); // Создаем адаптер
        requestQueue = Volley.newRequestQueue(this);
        //Список категорий
        jsonParseMyOrder(); // Получить категории товара и вывести
        gridOrderAdmin = (ExpandableHeightGridView) findViewById(R.id.gridOrderAdmin); // Обьявление GridView
        gridOrderAdmin.setExpanded(true);
        gridOrderAdmin.setAdapter(productOrderAdminAdapter); // Присваиваем адаптер
        menuNavigation(); // Навигация нижней панели
    }

    // Парсинг корзины
    private void jsonParseMyOrder() {
        String url = "http://anndroidankas.h1n.ru/php/admin_orders.php";
        orderAdminArrayList.clear(); // Отчищаем лист с категориями
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("ORDERS");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String title_product = object.getString("title_product");
                                int price_product = object.getInt("price_product");
                                int quantity = object.getInt("quantity");
                                String name = object.getString("name");
                                String surname = object.getString("surname");
                                String user_tel = object.getString("user_tel");
                                String user_email = object.getString("user_email");
                                String status = object.getString("status");

                                orderAdminArrayList.add(new ProductOrderAdmin(title_product, price_product, quantity, name, surname, user_tel, user_email, status)); // Добавляем товары в лист
                            }
                            productOrderAdminAdapter.notifyDataSetChanged(); // Отправка в адаптер для добавление категорий товара
                            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
                            scrollView.smoothScrollTo(0,0);
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
                Intent intent = new Intent(OrderAdminWindow.this, BasketWindow.class);
                startActivity(intent);
            }
        });

        layoutShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderAdminWindow.this, MainActivity.class);
                startActivity(intent);
            }
        });
        layoutHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((User.login == "Null" || User.login == "") && (User.password == "Null" || User.login == "")){
                    Intent intent = new Intent(OrderAdminWindow.this, UserAuthorizationWindow.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OrderAdminWindow.this, UserAuthorizedWindow.class);
                    startActivity(intent);
                }
            }
        });
        layoutMenuBrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderAdminWindow.this, MenuBrashWindow.class);
                startActivity(intent);
            }
        });
    }
}