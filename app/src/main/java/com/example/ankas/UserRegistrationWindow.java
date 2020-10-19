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
    TextView textLogin, textEmail,textPassword,textPasswordTuo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_window);

        requestQueue = Volley.newRequestQueue(this);

        textLogin = (TextView) findViewById(R.id.textLogin);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textPassword = (TextView) findViewById(R.id.textPassword);
        textPasswordTuo = (TextView) findViewById(R.id.textPasswordTuo);
        registration();
        menuNavigation(); // Меню навигации
    }

    private void registration() {


        final Button buttonEnter = (Button) findViewById(R.id.buttonEnter);

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL url = null;
                try {
                    url = new URL("http://anndroidankas.h1n.ru/php/user_registration.php?email=" + textEmail.getText() + "&login=" + textLogin.getText() + "&password=" + textPassword.getText());
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // Успешно
                    } else {
                        // Ошибка в отправлении
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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
        layoutFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegistrationWindow.this, FavoriteWindow.class);
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



                /* if (textEmail.getText() != ""){
                    if (textLogin.getText() != ""){
                        if (textPassword.getText() != "" && textPasswordTuo.getText() != ""){
                            if (textPassword.getText() == textPasswordTuo.getText()){
                                       } else {
                                Toast.makeText(getApplicationContext(), "Пароли не совпадают" + textPassword.getText() +",,," + textPasswordTuo.getText(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Поле с паролем пустое", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Поле логин пустое", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Поле с Email пустое", Toast.LENGTH_SHORT).show();
                }*/