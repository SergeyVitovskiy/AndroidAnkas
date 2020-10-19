package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ankas.Class.User;

public class UserAuthorizedWindow extends AppCompatActivity {

    public static User user;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_authorized_user);

        requestQueue = Volley.newRequestQueue(this);
        DataOutput();

        menuNavigation(); // Меню навигации
        TextView textNameSurname = (TextView) findViewById(R.id.textNameSurname);
        textNameSurname.setText(User.surname + " " + User.name);
    }
    private void DataOutput(){

    }
    private void button(){
        TextView textExit = (TextView) findViewById(R.id.textExit);
        textExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.name = "Null";
                User.password = "Null";
                Intent intent = new Intent(UserAuthorizedWindow.this, UserAuthorizationWindow.class);
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
                Intent intent = new Intent(UserAuthorizedWindow.this, MainActivity.class);
                startActivity(intent);
            }
        });
        layoutFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizedWindow.this, FavoriteWindow.class);
                startActivity(intent);
            }
        });
        layoutBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizedWindow.this, BasketWindow.class);
                startActivity(intent);
            }
        });
    }
}
