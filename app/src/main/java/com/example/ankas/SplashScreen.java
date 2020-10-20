package com.example.ankas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ankas.Adapter.CategoryAdapter;
import com.example.ankas.Class.Category;
import com.example.ankas.MainActivity;
import com.example.ankas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    TextView textLoading;
    Timer timer;
    int tick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        textLoading = (TextView) findViewById(R.id.textLoading);

        tick = 0;
        timer = new Timer();
        timer.schedule(new UpdateTimeTask(), 0, 500);
    }

    private class UpdateTimeTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (tick) {
                        case 0:
                            textLoading.setText("Запуск лаунчера");

                            break;
                        case 1:
                            textLoading.setText("Запрос на сервер");
                            break;
                        case 2:
                            textLoading.setText("Получение ответа");
                            break;
                        case 3:
                            textLoading.setText("Обработка ответа");
                            break;
                        case 4:
                            textLoading.setText("Открытие.");
                            break;
                        case 5:
                            textLoading.setText("Открытие..");
                            break;
                        case 6:
                            textLoading.setText("Открытие...");
                            break;
                        case 7:
                            Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                            SplashScreen.this.startActivity(mainIntent);
                            SplashScreen.this.finish();
                            timer.cancel();
                            break;
                        default:
                            break;
                    }
                    tick++;
                }
            });
        }
    }
}
