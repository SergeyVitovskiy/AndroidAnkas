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

public class UserAuthorizationWindow extends AppCompatActivity {

    public static User user;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_authorization_windows);

        requestQueue = Volley.newRequestQueue(this);

        Entrance();
        menuNavigation(); // Меню навигации
    }

    private void Entrance() {
        final TextView textLogin = (TextView) findViewById(R.id.textLogin); // Логин
        final TextView textPassword = (TextView) findViewById(R.id.textPassword); // Пароль
        Button buttonEnter = (Button) findViewById(R.id.buttonEnter); // Кнопка войти

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                    Intent intent = new Intent(UserAuthorizationWindow.this, UserAuthorizedWindow.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
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
        });

        TextView textRegistration = (TextView) findViewById(R.id.textRegistration); // Кнопка перехода в регистрацию
        textRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizationWindow.this, UserRegistrationWindow.class);
                startActivity(intent);
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
                Intent intent = new Intent(UserAuthorizationWindow.this, MainActivity.class);
                startActivity(intent);
            }
        });
        layoutFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizationWindow.this, FavoriteWindow.class);
                startActivity(intent);
            }
        });
        layoutBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizationWindow.this, BasketWindow.class);
                startActivity(intent);
            }
        });
    }
}
