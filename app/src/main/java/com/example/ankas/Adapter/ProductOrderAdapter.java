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
        TextView textQuantity; // Кол-во
        TextView textComment;  // Коментарий
        TextView textStatus; // Статус
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
            holder.textQuantity = (TextView) row.findViewById(R.id.textQuantity);
            holder.textComment = (TextView) row.findViewById(R.id.textComment);
            holder.textStatus = (TextView) row.findViewById(R.id.textStatus);

            row.setTag(holder);
        } else {
            holder = (ProductOrderAdapter.ViewHolder) row.getTag();
        }
        final ProductOrder productOrder = productOrderArrayList.get(position);

        holder.textTitle.setText(productOrder.getTitle_product());
        holder.textPrice.setText(productOrder.getPrice_product() + " ₽");
        holder.textQuantity.setText("Кол-во: " + productOrder.getQuantity());
        holder.textComment.setText( "Комментарий к заказу: " + productOrder.getComment());
        holder.textStatus.setText(productOrder.getStatus());

        Picasso.with(context)
                .load("http://anndroidankas.h1n.ru/imageAnkas/imageProduct/" + productOrder.getImage_url())
                .placeholder(R.drawable.logo_ico)
                .error(R.drawable.error)
                .into(holder.image);

        return row;
    }
}
