package com.example.ankas.Adapter;

import android.content.Context;
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
import com.example.ankas.Class.Category;
import com.example.ankas.Class.Product;
import com.example.ankas.Class.User;
import com.example.ankas.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static ArrayList<Product> productArrayList;

    public ProductAdapter(Context context, int layout, ArrayList<Product> productArrayList) {
        this.context = context;
        this.layout = layout;
        this.productArrayList = productArrayList;
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView image;
        TextView textTitle;
        TextView textPrice;
        TextView textDescription;
        TextView textManufacturer;
        TextView textAvailability;

         ImageView imageFavorite;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View row = view;
        ProductAdapter.ViewHolder holder = new ProductAdapter.ViewHolder(); // Присваивание компонентов
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.textTitle = (TextView) row.findViewById(R.id.textTitle);
            holder.textPrice = (TextView) row.findViewById(R.id.textPrice);
            holder.textDescription = (TextView) row.findViewById(R.id.textDescription);
            holder.textManufacturer = (TextView) row.findViewById(R.id.textManufacturer);
            holder.textAvailability = (TextView) row.findViewById(R.id.textAvailability);
            holder.imageFavorite = (ImageView) row.findViewById(R.id.imageFavorite);
            row.setTag(holder);
        } else {
            holder = (ProductAdapter.ViewHolder) row.getTag();
        }
        Product product = productArrayList.get(position);
        holder.textTitle.setText(product.getTitle());
        holder.textPrice.setText(product.getPrice() + " ₽");
        holder.textDescription.setText("    Описание: " + product.getDescription());
        holder.textManufacturer.setText(product.getBrand());
        holder.textAvailability.setText(product.getAvailability());
        Picasso.with(context)
                .load("http://anndroidankas.h1n.ru/imageAnkas/imageProduct/" + product.getImage_url())
                .placeholder(R.drawable.logo_ico)
                .error(R.drawable.error)
                .into(holder.image);
        return row;
    }
}