package com.example.ankas.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ankas.BasketWindow;
import com.example.ankas.Class.Product;
import com.example.ankas.Class.ProductBasket;
import com.example.ankas.Class.User;
import com.example.ankas.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductBasketAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static ArrayList<ProductBasket> productBasketArrayList;

    public ProductBasketAdapter(Context context, int layout, ArrayList<ProductBasket> productBasketArrayList) {
        this.context = context;
        this.layout = layout;
        this.productBasketArrayList = productBasketArrayList;
    }

    @Override
    public int getCount() {
        return productBasketArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productBasketArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView image;
        TextView textTitle; // Название
        TextView textPrice; // Цена
        TextView textDescription;  // Описание
        TextView textManufacturer; // Бренд
        TextView textQuantityBasket; // Кол-во заказываемого товара
        TextView textAvailability; // Товар в надичии или нет

        LinearLayout layoutMinus;
        LinearLayout layoutPlus;
        ImageView imageTrash;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View row = view;
        ProductBasketAdapter.ViewHolder holder = new ProductBasketAdapter.ViewHolder(); // Присваивание компонентов
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.textTitle = (TextView) row.findViewById(R.id.textTitle);
            holder.textPrice = (TextView) row.findViewById(R.id.textPrice);
            holder.textDescription = (TextView) row.findViewById(R.id.textDescription);
            holder.textManufacturer = (TextView) row.findViewById(R.id.textManufacturer);
            holder.textQuantityBasket = (TextView) row.findViewById(R.id.textQuantityBasket);
            holder.textAvailability = (TextView) row.findViewById(R.id.textAvailability);

            holder.layoutMinus = (LinearLayout) row.findViewById(R.id.layoutMinus);
            holder.layoutPlus = (LinearLayout) row.findViewById(R.id.layoutPlus);
            holder.imageTrash = (ImageView) row.findViewById(R.id.imageTrash);
            row.setTag(holder);
        } else {
            holder = (ProductBasketAdapter.ViewHolder) row.getTag();
        }
        final ProductBasket productBasket = productBasketArrayList.get(position);
        holder.textTitle.setText(productBasket.getTitle());
        holder.textPrice.setText(productBasket.getPrice());
        holder.textDescription.setText(productBasket.getDescription());
        holder.textManufacturer.setText(productBasket.getBrand());
        holder.textQuantityBasket.setText("" + productBasket.getQuantity_basket()); // Кол-во заказываемых товаров
        holder.textAvailability.setText("В наличии: " + productBasket.getQuantity() + " шт.");
        Picasso.with(context)
                .load("http://anndroidankas.h1n.ru/imageAnkas/imageProduct/" + productBasket.getImage_url())
                .placeholder(R.drawable.logo_ico)
                .error(R.drawable.error)
                .into(holder.image);
        OrderAmount();
        final ViewHolder finalHolder = holder;
        //Убрать обну единицу товара
        holder.layoutMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(finalHolder.textQuantityBasket.getText().toString()) > 1) {
                    int quantity = ProductBasketAdapter.productBasketArrayList.get(position).getQuantity_basket() - 1;
                    ProductBasketAdapter.productBasketArrayList.get(position).setQuantity_basket(quantity);
                    finalHolder.textQuantityBasket.setText("" + quantity);
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    String url = "http://anndroidankas.h1n.ru/php/basket_product_update_quantity.php?user_login=" + User.login + "&product_article=" + ProductBasketAdapter.productBasketArrayList.get(position).getArticle() + "&quantity=" + quantity;
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
                    OrderAmount();
                }
            }
        });
        //Добавить одну единицу товара
        holder.layoutPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(finalHolder.textQuantityBasket.getText().toString()) < ProductBasketAdapter.productBasketArrayList.get(position).getQuantity()){
                    int quantity = ProductBasketAdapter.productBasketArrayList.get(position).getQuantity_basket() + 1;
                    ProductBasketAdapter.productBasketArrayList.get(position).setQuantity_basket(quantity);
                    finalHolder.textQuantityBasket.setText("" + quantity);
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    String url = "http://anndroidankas.h1n.ru/php/basket_product_update_quantity.php?user_login=" + User.login + "&product_article=" + ProductBasketAdapter.productBasketArrayList.get(position).getArticle() + "&quantity=" + quantity;
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
                    OrderAmount();
                }
            }
        });
        // Убрать из корзины
        holder.imageTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                String url = "http://anndroidankas.h1n.ru/php/basket_product_delete.php?user_login=" + User.login + "&product_article=" + ProductBasketAdapter.productBasketArrayList.get(position).getArticle();
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
                productBasketArrayList.remove(position);
                BasketWindow.productBasketAdapter.notifyDataSetChanged();
                if (productBasketArrayList.size() == 0) {
                    BasketWindow.textMessage.setVisibility(View.VISIBLE);
                    BasketWindow.textMessage.setText("У вас нет товаров в корзине");
                }
                OrderAmount();
            }
        });
        return row;
    }

    private void OrderAmount() {
        BasketWindow.orderAmount = 0;
        if (productBasketArrayList.size() != 0) {
            for (int i = 0; i < productBasketArrayList.size(); i++) {
                BasketWindow.orderAmount += productBasketArrayList.get(i).getQuantity_basket() * Double.parseDouble(productBasketArrayList.get(i).getPrice()); // Сумма заказа
            }
            BasketWindow.textOrderAmount.setText("Сумма заказа: " + BasketWindow.orderAmount + " ₽");
        }
    }
}
