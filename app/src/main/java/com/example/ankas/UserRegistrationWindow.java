package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.ankas.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserRegistrationWindow extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView textLogin, textEmail,textPassword,textPasswordTuo,textName,textSurname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_window);

        requestQueue = Volley.newRequestQueue(this);

        textLogin = (TextView) findViewById(R.id.textLogin);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textPassword = (TextView) findViewById(R.id.textPassword);
        textPasswordTuo = (TextView) findViewById(R.id.textPasswordTuo);
        textName = (TextView) findViewById(R.id.textName);
        textSurname = (TextView) findViewById(R.id.textSurname);
        registration();
        menuNavigation(); // Меню навигации
    }
    // Обработка кнопки регистрация
    private void registration() {
        final Button buttonEnter = (Button) findViewById(R.id.buttonEnter);

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!textLogin.getText().toString().equals("")) {
                    if (!textEmail.getText().toString().equals("")) {
                        if (!textPassword.getText().toString().equals("") && !textPasswordTuo.getText().toString().equals("")) {
                            if (textPassword.getText().toString().equals(textPasswordTuo.getText().toString())) {
                                String url = "http://anndroidankas.h1n.ru/php/user_registration.php?login=" + textLogin.getText().toString() + "&password=" + textPassword.getText().toString() +
                                        "&email=" + textEmail.getText().toString() + "&name=" + textName.getText().toString() + "&surname=" + textSurname.getText().toString(); // GET запрос
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    JSONArray jsonArray = response.getJSONArray("ANSWER"); // Массив данных
                                                    JSONObject object = jsonArray.getJSONObject(0); // Получение первого массива
                                                    String answer = object.getString("answer");
                                                    Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show(); // Сообщение полученное с сервера
                                                    if (!answer.equals("Пользователь с таким логином уже есть!")) {
                                                        //Получение данных о пользоватле
                                                        String url = "http://anndroidankas.h1n.ru/php/user.php?login=" + textLogin.getText() + "&password=" + textPassword.getText();
                                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                                                new Response.Listener<JSONObject>() {
                                                                    @Override
                                                                    public void onResponse(JSONObject response) {
                                                                        try {
                                                                            JSONArray jsonArray = response.getJSONArray("USER");
                                                                            JSONObject object = jsonArray.getJSONObject(0);
                                                                            User.login = object.getString("login");
                                                                            User.password = object.getString("password");
                                                                            User.group = object.getString("groups");
                                                                            User.image_url = object.getString("image_url");
                                                                            User.surname = object.getString("surname");
                                                                            User.name = object.getString("name");
                                                                            User.email = object.getString("email");
                                                                            User.telephone = object.getString("telephone");
                                                                            //После получения
                                                                            Intent intent = new Intent(UserRegistrationWindow.this, UserAuthorizedWindow.class);
                                                                            startActivity(intent);
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
                            } else {
                                Toast.makeText(getApplicationContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Вы не заполнили поле с паролем!", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "Вы не заполнили поле E-mail!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Вы не заполнили поле Логин!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(UserRegistrationWindow.this, MainActivity.class);
                startActivity(intent);
            }
        });
        layoutBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegistrationWindow.this, BasketWindow.class);
                startActivity(intent);
            }
        });
    }
}