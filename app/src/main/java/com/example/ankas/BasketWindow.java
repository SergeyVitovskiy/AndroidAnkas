package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ankas.Adapter.ProductAdapter;
import com.example.ankas.Adapter.ProductBasketAdapter;
import com.example.ankas.Class.ProductBasket;
import com.example.ankas.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BasketWindow extends AppCompatActivity {

    ExpandableHeightGridView gridProductBasket;

    ArrayList<ProductBasket> productBasketArrayList; // Лист с продуктами
    public static ProductBasketAdapter productBasketAdapter; // Адаптер
    RequestQueue requestQueue;


    public static TextView textMessage;
    public static TextView textOrderAmount;

    public static double orderAmount; // Сумма заказа
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_window);

        gridProductBasket = (ExpandableHeightGridView) findViewById(R.id.gridProductBasket); // Обьявление GridView
        gridProductBasket.setExpanded(true);
        textMessage = (TextView) findViewById(R.id.textMessage); // У вас нет товара в корзине

        textOrderAmount = (TextView) findViewById(R.id.textOrderAmount); // Сумма заказа

        productBasketArrayList = new ArrayList<>(); // Создаем лист для подкатегорий
        productBasketAdapter = new ProductBasketAdapter(this, R.layout.item_basket, productBasketArrayList); // Создаем адаптер
        requestQueue = Volley.newRequestQueue(this);
        //Список категорий
        jsonParseProductBasket(); // Получить категории товара и вывести
        gridProductBasket.setAdapter(productBasketAdapter); // Присваиваем адаптер
        gridViewClick(); // Обработка нажатий в grid view
        menuNavigation();
        buttonEnter();
    }
    // Нажатие на заказ
    private void buttonEnter(){
        Button buttonEnter = (Button) findViewById(R.id.buttonEnter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productBasketArrayList.size() != 0) {
                    Intent intent = new Intent(BasketWindow.this, OrderWindow.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Корзина пуста", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Переход в товары
    private void gridViewClick() {
        gridProductBasket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long row) {
                ProductDetailWindow.idSelectProductDetail = ProductBasketAdapter.productBasketArrayList.get(position).getArticle();
                Intent intent = new Intent(BasketWindow.this, ProductDetailWindow.class);
                startActivity(intent);
            }
        });
    }
    // Парсинг корзины
    private void jsonParseProductBasket() {
        if (User.login != "" && User.login != "Null") {
            String url = "http://anndroidankas.h1n.ru/php/basket_product.php?user_login=" + User.login;

            productBasketArrayList.clear(); // Отчищаем лист с категориями
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("BASKET_PRODUCT");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                     int article = object.getInt("article"); // Индификатор
                                     String title = object.getString("title_product"); // Название
                                     String price = object.getString("price"); // Цена
                                     String brand = object.getString("country") + ", " + object.getString("title_brand"); // Бренд
                                     String image_url = object.getString("image_url"); // Изобраджение
                                     String description = object.getString("description"); // Описание
                                     int quantity = object.getInt("quantity"); // Кол-во товара
                                     int quantity_basket = object.getInt("quantity_basket"); // Кол-во товара в корзине
                                    productBasketArrayList.add(new ProductBasket(article, title, price, brand, image_url, description, quantity, quantity_basket)); // Добавляем продукты в лист
                                }
                                textMessage.setVisibility(View.GONE);
                                productBasketAdapter.notifyDataSetChanged(); // Отправка в адаптер для добавление категорий товара
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (productBasketArrayList.size() == 0) {
                                BasketWindow.textMessage.setVisibility(View.VISIBLE);
                                BasketWindow.textMessage.setText("У вас нет товаров в корзине!");
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(request);
        } else {
            textMessage.setText("Вы не авторизированы!");
            textMessage.setVisibility(View.VISIBLE);
        }
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
                Intent intent = new Intent(BasketWindow.this, MainActivity.class);
                startActivity(intent);
            }
        });
        layoutHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((User.login == "Null" || User.login == "") && (User.password == "Null" || User.login == "")){
                    Intent intent = new Intent(BasketWindow.this, UserAuthorizationWindow.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(BasketWindow.this, UserAuthorizedWindow.class);
                    startActivity(intent);
                }
            }
        });
        layoutMenuBrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasketWindow.this, MenuBrashWindow.class);
                startActivity(intent);
            }
        });
    }
}