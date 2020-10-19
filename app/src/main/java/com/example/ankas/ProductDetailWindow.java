package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.ankas.Class.Product;
import com.example.ankas.Class.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProductDetailWindow extends AppCompatActivity {

    static int idSelectProductDetail;


    Button buttonBuy;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_window);

        buttonBuy = (Button) findViewById(R.id.buttonBuy); // Кнопка добавить в корзину
        requestQueue = Volley.newRequestQueue(this);

        jsonParseProductDetail(idSelectProductDetail); // Парсинг
        checkBasket(); // Проверка коризны
        AddBasket(); // Добавление в корзину
        menuNavigation(); // Навигация
    }

    private void checkBasket() {
        String url = "http://anndroidankas.h1n.ru/php/basket_product_info.php?user_login=" + User.login + "&product_id=" + idSelectProductDetail;
        Toast.makeText(getApplicationContext(), "url  " + url, Toast.LENGTH_SHORT).show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("BASKET_PRODUCT"); // Массив данных
                            JSONObject object = jsonArray.getJSONObject(0); // Получение первого массива
                            int quantity = object.getInt("quantity");
                            Toast.makeText(getApplicationContext(), "quantity " + quantity, Toast.LENGTH_SHORT).show();
                            if (quantity >= 1){
                                buttonBuy.setText("В корзине");
                            } else {
                                buttonBuy.setText("Добавить в корзину");
                            }
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

    //Добавление в корзину
    private void AddBasket() {
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.login != "" && User.login != "Null") {
                    // Добавление в корзину
                    if (buttonBuy.getText().toString() == "Добавить в корзину") {
                        String url = "http://anndroidankas.h1n.ru/php/basket_product_add.php?user_login=" + User.login + "&product_id=" + idSelectProductDetail;
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        JSONArray jsonArray = null;
                                        try {
                                            jsonArray = response.getJSONArray("ANSWER"); // Массив данных
                                            JSONObject object = jsonArray.getJSONObject(0); // Получение первого массива
                                            String answer = object.getString("answer");
                                            Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show(); // Сообщение полученное с сервера
                                            buttonBuy.setText("В корзине");
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
                    } else if (buttonBuy.getText().toString() == "В корзине"){ // Если уже в карзине
                        Intent intent = new Intent(ProductDetailWindow.this, BasketWindow.class);
                        startActivity(intent);
                    }
                } else { // Если пользователь не авторизировался
                    Toast.makeText(getApplicationContext(), "Вы не авторизированы!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Меню навигации
    private void menuNavigation() {
        LinearLayout layoutShop, layoutFavorites, layoutBasket,layoutHuman;
        layoutShop = (LinearLayout) findViewById(R.id.layoutShop); // Категории
        layoutFavorites = (LinearLayout) findViewById(R.id.layoutFavorites); // Избранное
        layoutBasket = (LinearLayout) findViewById(R.id.layoutBasket); // Корзина
        layoutHuman = (LinearLayout) findViewById(R.id.layoutHuman); // Пользователь

        layoutShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailWindow.this, SubcategoryWindow.class);
                startActivity(intent);
            }
        });
        layoutBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailWindow.this, BasketWindow.class);
                startActivity(intent);
            }
        });
        layoutFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailWindow.this, FavoriteWindow.class);
                startActivity(intent);
            }
        });
        layoutHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((User.login == "Null" || User.login == "") && (User.password == "Null" || User.login == "")){
                    Intent intent = new Intent(ProductDetailWindow.this, UserAuthorizationWindow.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ProductDetailWindow.this, UserAuthorizedWindow.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void jsonParseProductDetail(int position) {
        final String url = "http://anndroidankas.h1n.ru/php/product_detail.php?product=" + position;
        final TextView textArticle = (TextView) findViewById(R.id.textArticle); // Артикул
        final TextView textBrand = (TextView) findViewById(R.id.textBrand); // Бренд
        final ImageView imageProduct = (ImageView) findViewById(R.id.imageProduct); // Изображение
        final TextView textTitle = (TextView) findViewById(R.id.textTitle); // Название
        final TextView textPrice = (TextView) findViewById(R.id.textPrice); // Цена
        final TextView textDescription = (TextView) findViewById(R.id.textDescription); // Описание
        final TextView textSpecifications = (TextView) findViewById(R.id.textSpecifications); // Характеристики

        final TextView textExpandDescription = (TextView) findViewById(R.id.textExpandDescription); // Развернуть описание
        final TextView textExpandFeatures = (TextView) findViewById(R.id.textExpandFeatures); // Развернуть характеристики

        textExpandDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textExpandDescription.getText() == "Развернуть описание...")
                {
                    textExpandDescription.setText("Свернуть описание...");
                    textDescription.setMaxLines(9999);
                }
                else
                {
                    textExpandDescription.setText("Развернуть описание...");
                    textDescription.setMaxLines(7);
                }
            }
        });

        textExpandFeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textExpandFeatures.getText() == "Развернуть характеристики...")
                {
                    textExpandFeatures.setText("Свернуть характеристики...");
                    textSpecifications.setMaxLines(9999);
                }
                else
                {
                    textExpandFeatures.setText("Развернуть характеристики...");
                    textSpecifications.setMaxLines(7);
                }
            }
        });

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("PRODUCT");
                            for (int i = 0; i < jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(0);
                                textArticle.setText("Артикул: FRG" + object.getInt("article"));
                                textBrand.setText("Производитель: " + object.getString("country") + ", " + object.getString("title_brand"));
                                Picasso.with(ProductDetailWindow.this)
                                        .load("http://anndroidankas.h1n.ru/imageAnkas/imageProduct/" + object.getString("image_url"))
                                        .placeholder(R.drawable.logo_ico)
                                        .error(R.drawable.error)
                                        .into(imageProduct);

                                textTitle.setText(object.getString("title_product"));
                                textPrice.setText(object.getString("price") + " ₽");
                                textDescription.setText(object.getString("description"));
                                textSpecifications.setText(object.getString("specifications"));
                            }
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
