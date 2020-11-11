package com.example.ankas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankas.Class.ProductOrder;
import com.example.ankas.Class.ProductOrderAdmin;
import com.example.ankas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductOrderAdminAdapter  extends BaseAdapter {
    private Context context;
    private int layout;
    public static ArrayList<ProductOrderAdmin> productOrderAdminArrayList;

    public ProductOrderAdminAdapter(Context context, int layout, ArrayList<ProductOrderAdmin> productOrderAdminArrayList) {
        this.context = context;
        this.layout = layout;
        this.productOrderAdminArrayList = productOrderAdminArrayList;
    }

    @Override
    public int getCount() {
        return productOrderAdminArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productOrderAdminArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView textTitle; // Название
        TextView textPrice; // Цена
        TextView textQuantity; // Кол-во
        TextView textUserName;  // Имя и фамилия
        TextView textTelephone; // Телефон
        TextView textEmail; // E-mail
        TextView textStatus; // Статус
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View row = view;
        ProductOrderAdminAdapter.ViewHolder holder = new ProductOrderAdminAdapter.ViewHolder(); // Присваивание компонентов
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textTitle = (TextView) row.findViewById(R.id.textTitle);
            holder.textPrice = (TextView) row.findViewById(R.id.textPrice);
            holder.textQuantity = (TextView) row.findViewById(R.id.textQuantity);
            holder.textUserName = (TextView) row.findViewById(R.id.textUserName);
            holder.textTelephone = (TextView) row.findViewById(R.id.textTelephone);
            holder.textEmail = (TextView) row.findViewById(R.id.textEmail);
            holder.textStatus = (TextView) row.findViewById(R.id.textStatus);

            row.setTag(holder);
        } else {
            holder = (ProductOrderAdminAdapter.ViewHolder) row.getTag();
        }
        final ProductOrderAdmin productOrderAdmin = productOrderAdminArrayList.get(position);

        holder.textTitle.setText(productOrderAdmin.getTitle_product());
        holder.textPrice.setText("Цена товара: " + productOrderAdmin.getPrice_product() + " ₽");
        holder.textQuantity.setText("Кол-во: " + productOrderAdmin.getQuantity());
        holder.textUserName.setText(productOrderAdmin.getName() + " " + productOrderAdmin.getSurname());
        holder.textTelephone.setText(productOrderAdmin.getUser_tel());
        holder.textEmail.setText(productOrderAdmin.getUser_email());
        holder.textStatus.setText("Статус заказа: " + productOrderAdmin.getStatus());

        return row;
    }
}
