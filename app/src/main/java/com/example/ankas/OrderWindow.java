package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ankas.Adapter.ProductBasketAdapter;
import com.example.ankas.Class.ProductBasket;
import com.example.ankas.Class.Review;
import com.example.ankas.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderWindow extends AppCompatActivity {
    RequestQueue requestQueue;

    LinearLayout layoutPickup;
    LinearLayout layoutDelivery;

    LinearLayout layoutOnline; // Купить онлайн
    LinearLayout layoutUponReceipt; // Купить при получении

    EditText textTel, textEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_window);

        requestQueue = Volley.newRequestQueue(this);

        TextView textOrderAmount = (TextView) findViewById(R.id.textOrderAmount); // Сумма заказа
        textOrderAmount.setText("Общая сумма заказа: " + BasketWindow.orderAmount + " ₽"); // Вывод общей суммы заказа

        textTel = (EditText) findViewById(R.id.textTel);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textTel.setText(User.telephone);
        textEmail.setText(User.email);

        // Лайауты которые нужно открыть или скрыть
        layoutPickup = (LinearLayout) findViewById(R.id.layoutPickup);
        layoutDelivery = (LinearLayout) findViewById(R.id.layoutDelivery);
        layoutOnline = (LinearLayout) findViewById(R.id.layoutOnline);
        layoutUponReceipt = (LinearLayout) findViewById(R.id.layoutUponReceipt);

        ButtonDeliveryMethod(); // Кнопки доставки
        ButtonPaymentMethod(); // Кнопки оплаты
        menuNavigation();
        buttonEnter();
    }
    //Обработка заказа
    private void buttonEnter() {
        Button buttonEnter = (Button) findViewById(R.id.buttonEnter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textAddress = (EditText) findViewById(R.id.textAddress);

                EditText textCode = (EditText) findViewById(R.id.textCode);
                EditText textDate = (EditText) findViewById(R.id.textDate);
                EditText textPIN = (EditText) findViewById(R.id.textPIN);

                if (textTel.getText().toString().length() == 11) {
                    int soback=0, toch = 0;
                    boolean prov = false;
                    for (char element : textEmail.getText().toString().toCharArray()) {
                        if (element == '@') soback++;
                        if (element == '.') toch++;
                        if (soback == 0 && toch == 1) prov = true;
                    }
                    if (!textEmail.getText().toString().equals("") && soback == 1 && toch == 1 && prov == false) {
                        for (int i = 0; i < ProductBasketAdapter.productBasketArrayList.size(); i++) {
                            String url = "http://anndroidankas.h1n.ru/php/orders_add.php?" +
                                    "product_id=" + ProductBasketAdapter.productBasketArrayList.get(i).getArticle() +
                                    "&user_login=" + User.login +
                                    "&user_tel=" + textTel.getText().toString() +
                                    "&user_email=" + textEmail.getText().toString() +
                                    "&quantity=" + ProductBasketAdapter.productBasketArrayList.get(i).getQuantity_basket() +
                                    "&price_product=" + ProductBasketAdapter.productBasketArrayList.get(i).getPrice();
                            if (layoutPickup.getVisibility() == View.VISIBLE) {
                                url += "&delivery_method=Самовывоз" +
                                        "&delivery_address=NULL";
                            } else if (layoutDelivery.getVisibility() == View.VISIBLE) {
                                url += "&delivery_method=Доставка" +
                                        "&delivery_address=" + textAddress.getText().toString();
                            }
                            if (layoutOnline.getVisibility() == View.VISIBLE) {
                                url += "&payment_method=Онлайн" +
                                        "&payment_code=" + textCode.getText().toString() +
                                        "&payment_date=" + textDate.getText().toString() +
                                        "&payment_pin=" + textPIN.getText().toString();
                            } else if (layoutUponReceipt.getVisibility() == View.VISIBLE) {
                                url += "&payment_method=При получении" +
                                        "&payment_code=NULL" +
                                        "&payment_date=NULL" +
                                        "&payment_pin=NULL";
                            }
                            EditText textComment = (EditText) findViewById(R.id.textComment);
                            url += "&status=В рассмотрении" +
                                    "&comment=" + textComment.getText().toString();
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            JSONArray jsonArray = null;
                                            try {
                                                jsonArray = response.getJSONArray("ANSWER"); // Массив данных
                                                JSONObject object = jsonArray.getJSONObject(0); // Получение первого массива
                                                String answer = object.getString("answer");
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
                        for (int i = 0; i < ProductBasketAdapter.productBasketArrayList.size(); i++) {
                            String url = "http://anndroidankas.h1n.ru/php/basket_product_delete.php?user_login=" + User.login +
                                    "&product_article=" + ProductBasketAdapter.productBasketArrayList.get(i).getArticle();
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            JSONArray jsonArray = null;
                                            try {
                                                jsonArray = response.getJSONArray("ANSWER"); // Массив данных
                                                JSONObject object = jsonArray.getJSONObject(0); // Получение первого массива
                                                String answer = object.getString("answer");
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
                        Toast.makeText(getApplicationContext(), "Заказ оформлен. В течении 15 минут с вами свяжется сотрудник компании", Toast.LENGTH_SHORT).show(); // Сообщение полученное с сервера
                        ProductBasketAdapter.productBasketArrayList.clear();
                        Intent intent = new Intent(OrderWindow.this, BasketWindow.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Email указан неверно", Toast.LENGTH_SHORT).show(); // Сообщение полученное с сервера
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Телефон указан неверно", Toast.LENGTH_SHORT).show(); // Сообщение полученное с сервера
                }
            }
        });
    }
    // Способ оплаты
    private void ButtonPaymentMethod(){
        final Button buttonUponReceipt = (Button) findViewById(R.id.buttonUponReceipt); // При получении
        final Button buttonOnline = (Button) findViewById(R.id.buttonOnline); // Онлайн
        buttonUponReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutOnline.setVisibility(View.GONE);
                layoutUponReceipt.setVisibility(View.VISIBLE);

                buttonOnline.setBackgroundColor(ContextCompat.getColor(OrderWindow.this, R.color.colorGreen));
                buttonUponReceipt.setBackgroundColor(ContextCompat.getColor(OrderWindow.this, R.color.colorLightGreen));
            }
        });

        buttonOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutOnline.setVisibility(View.VISIBLE);
                layoutUponReceipt.setVisibility(View.GONE);
                buttonOnline.setBackgroundColor(ContextCompat.getColor(OrderWindow.this, R.color.colorLightGreen));
                buttonUponReceipt.setBackgroundColor(ContextCompat.getColor(OrderWindow.this, R.color.colorGreen));
            }
        });
    }
    // Способ доставки
    private void ButtonDeliveryMethod(){
        final Button buttonPickup = (Button) findViewById(R.id.buttonPickup); // Самовывоз
        final Button buttonDelivery = (Button) findViewById(R.id.buttonDelivery); // Доставка
        buttonPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutPickup.setVisibility(View.VISIBLE);
                layoutDelivery.setVisibility(View.GONE);
                buttonPickup.setBackgroundColor(ContextCompat.getColor(OrderWindow.this, R.color.colorLightGreen));
                buttonDelivery.setBackgroundColor(ContextCompat.getColor(OrderWindow.this, R.color.colorGreen));
            }
        });

        buttonDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutPickup.setVisibility(View.GONE);
                layoutDelivery.setVisibility(View.VISIBLE);
                buttonDelivery.setBackgroundColor(ContextCompat.getColor(OrderWindow.this, R.color.colorLightGreen));
                buttonPickup.setBackgroundColor(ContextCompat.getColor(OrderWindow.this, R.color.colorGreen));
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
                Intent intent = new Intent(OrderWindow.this, MainActivity.class);
                startActivity(intent);
            }
        });
        layoutHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((User.login == "Null" || User.login == "") && (User.password == "Null" || User.login == "")){
                    Intent intent = new Intent(OrderWindow.this, UserAuthorizationWindow.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OrderWindow.this, UserAuthorizedWindow.class);
                    startActivity(intent);
                }
            }
        });
        layoutMenuBrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderWindow.this, MenuBrashWindow.class);
                startActivity(intent);
            }
        });
    }
}
