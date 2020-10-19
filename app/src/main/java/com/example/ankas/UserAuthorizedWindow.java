package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ankas.Class.User;
import com.squareup.picasso.Picasso;

public class UserAuthorizedWindow extends AppCompatActivity {

    public static User user;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_authorized_user);

        requestQueue = Volley.newRequestQueue(this);
        button();

        menuNavigation(); // Меню навигации
        TextView textNameSurname = (TextView) findViewById(R.id.textNameSurname); // Имя и фамилия
        ImageView imageUser = (ImageView) findViewById(R.id.imageUser); // Аватарка пользователя


        LinearLayout layoutAdmin = (LinearLayout) findViewById(R.id.layoutAdmin); // Панель администратора

        Picasso.with(UserAuthorizedWindow.this)
                .load("http://anndroidankas.h1n.ru/imageAnkas/imageUser/" + User.image_url)
                .placeholder(R.drawable.human)
                .error(R.drawable.error)
                .into(imageUser);
        textNameSurname.setText(User.surname + " " + User.name);

        if (User.group.equals("Администратор")) {
            layoutAdmin.setVisibility(View.VISIBLE);
        } else {
            layoutAdmin.setVisibility(View.GONE);
        }
    }

    private void button(){
        TextView textMyOrder = (TextView) findViewById(R.id.textMyOrder); // Мои заказы
        TextView textAccount = (TextView) findViewById(R.id.textAccount); // Аккаунт
        textAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizedWindow.this, UserEditAccount.class);
                startActivity(intent);
            }
        });
        TextView textExit = (TextView) findViewById(R.id.textExit); // Выход из профиля
        //Выход
        textExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.login = "Null";
                User.name = "Null";
                User.password = "Null";
                Intent intent = new Intent(UserAuthorizedWindow.this, UserAuthorizationWindow.class);
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
                Intent intent = new Intent(UserAuthorizedWindow.this, MainActivity.class);
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
        layoutMenuBrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizedWindow.this, MenuBrashWindow.class);
                startActivity(intent);
            }
        });
    }
}
