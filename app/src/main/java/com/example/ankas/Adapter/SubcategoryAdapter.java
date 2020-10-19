package com.example.ankas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankas.Class.Category;
import com.example.ankas.Class.Subcategory;
import com.example.ankas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubcategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static ArrayList<Subcategory> subcategoryArrayList;

    public SubcategoryAdapter(Context context, int layout, ArrayList<Subcategory> subcategoryArrayList) {
        this.context = context;
        this.layout = layout;
        this.subcategoryArrayList = subcategoryArrayList;
    }

    @Override
    public int getCount() {
        return subcategoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return subcategoryArrayList.get(position);
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
        SubcategoryAdapter.ViewHolder holder = new SubcategoryAdapter.ViewHolder();
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textTitle = (TextView) row.findViewById(R.id.textTitle);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (SubcategoryAdapter.ViewHolder) row.getTag();
        }
        Subcategory subcategory = subcategoryArrayList.get(position);
        holder.textTitle.setText(subcategory.getTitle());

        Picasso.with(context)
                .load("http://anndroidankas.h1n.ru/imageAnkas/imageSubcategory/" + subcategory.getImage_url())
                .placeholder(R.drawable.logo_ico)
                .error(R.drawable.error)
                .into(holder.image);
        return row;
    }
}