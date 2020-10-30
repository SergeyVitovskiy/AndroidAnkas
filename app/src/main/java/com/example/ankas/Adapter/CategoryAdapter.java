package com.example.ankas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankas.Class.Category;
import com.example.ankas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static ArrayList<Category> categoryArrayList;

    public CategoryAdapter(Context context, int layout, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.layout = layout;
        this.categoryArrayList = categoryArrayList;
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView image;
        TextView textTitle;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textTitle = (TextView) row.findViewById(R.id.textTitle);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Category category = categoryArrayList.get(position);
        holder.textTitle.setText(category.getTitle());
        //Получение картинки с сервера
        Picasso.with(context)
                .load("http://anndroidankas.h1n.ru/imageAnkas/imageCategory/" + category.getImage_url())
                .placeholder(R.drawable.logo_ico)
                .error(R.drawable.error)
                .into(holder.image);
        return row;
    }
}
