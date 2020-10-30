package com.example.ankas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ankas.BasketWindow;
import com.example.ankas.Class.ProductBasket;
import com.example.ankas.Class.ProductOrder;
import com.example.ankas.Class.User;
import com.example.ankas.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductOrderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static ArrayList<ProductOrder> productOrderArrayList;

    public ProductOrderAdapter(Context context, int layout, ArrayList<ProductOrder> productOrderArrayList) {
        this.context = context;
        this.layout = layout;
        this.productOrderArrayList = productOrderArrayList;
    }

    @Override
    public int getCount() {
        return productOrderArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productOrderArrayList.get(position);
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
        ProductOrderAdapter.ViewHolder holder = new ProductOrderAdapter.ViewHolder(); // Присваивание компонентов
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
            holder = (ProductOrderAdapter.ViewHolder) row.getTag();
        }
        final ProductOrder productOrder = productOrderArrayList.get(position);

        return row;
    }
}
