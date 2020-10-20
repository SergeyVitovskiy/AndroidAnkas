package com.example.ankas.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.ankas.Class.Review;
import com.example.ankas.Class.User;
import com.example.ankas.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static ArrayList<Review> reviewArrayList;

    public ReviewAdapter(Context context, int layout, ArrayList<Review> reviewArrayList) {
        this.context = context;
        this.layout = layout;
        this.reviewArrayList = reviewArrayList;
    }

    @Override
    public int getCount() {
        return reviewArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageReview1, imageReview2, imageReview3, imageReview4, imageReview5; // Оценка
        TextView textReview; // Описание
        TextView textReviewDetail; //Подробно
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View row = view;
        ReviewAdapter.ViewHolder holder = new ReviewAdapter.ViewHolder(); // Присваивание компонентов
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.imageReview1 = (ImageView) row.findViewById(R.id.imageReview1);
            holder.imageReview2 = (ImageView) row.findViewById(R.id.imageReview2);
            holder.imageReview3 = (ImageView) row.findViewById(R.id.imageReview3);
            holder.imageReview4 = (ImageView) row.findViewById(R.id.imageReview4);
            holder.imageReview5 = (ImageView) row.findViewById(R.id.imageReview5);
            holder.textReview = (TextView) row.findViewById(R.id.textReview);
            holder.textReviewDetail = (TextView) row.findViewById(R.id.textReviewDetail);
            row.setTag(holder);
        } else {
            holder = (ReviewAdapter.ViewHolder) row.getTag();
        }
        if (reviewArrayList.get(position).getEvalution() == 1){
            holder.imageReview1.setImageResource(R.drawable.favourites_true);}
        else if (reviewArrayList.get(position).getEvalution() == 2){
            holder.imageReview1.setImageResource(R.drawable.favourites_true);
            holder.imageReview2.setImageResource(R.drawable.favourites_true);
        } else if (reviewArrayList.get(position).getEvalution() == 3){
            holder.imageReview1.setImageResource(R.drawable.favourites_true);
            holder.imageReview2.setImageResource(R.drawable.favourites_true);
            holder.imageReview3.setImageResource(R.drawable.favourites_true);
        } else if (reviewArrayList.get(position).getEvalution() == 4){
            holder.imageReview1.setImageResource(R.drawable.favourites_true);
            holder.imageReview2.setImageResource(R.drawable.favourites_true);
            holder.imageReview3.setImageResource(R.drawable.favourites_true);
            holder.imageReview4.setImageResource(R.drawable.favourites_true);
        } else if (reviewArrayList.get(position).getEvalution() == 5){
            holder.imageReview1.setImageResource(R.drawable.favourites_true);
            holder.imageReview2.setImageResource(R.drawable.favourites_true);
            holder.imageReview3.setImageResource(R.drawable.favourites_true);
            holder.imageReview4.setImageResource(R.drawable.favourites_true);
            holder.imageReview5.setImageResource(R.drawable.favourites_true);
        }
        holder.textReview.setText(reviewArrayList.get(position).getReview());

        holder.textReviewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_review);
                ImageView imageReview1 = (ImageView) dialog.findViewById(R.id.imageReview1);
                ImageView imageReview2 = (ImageView) dialog.findViewById(R.id.imageReview2);
                ImageView imageReview3 = (ImageView) dialog.findViewById(R.id.imageReview3);
                ImageView imageReview4 = (ImageView) dialog.findViewById(R.id.imageReview4);
                ImageView imageReview5 = (ImageView) dialog.findViewById(R.id.imageReview5);
                TextView textReview = (TextView) dialog.findViewById(R.id.textReview);
                if (reviewArrayList.get(position).getEvalution() == 1){
                    imageReview1.setImageResource(R.drawable.favourites_true);}
                else if (reviewArrayList.get(position).getEvalution() == 2){
                    imageReview1.setImageResource(R.drawable.favourites_true);
                    imageReview2.setImageResource(R.drawable.favourites_true);
                } else if (reviewArrayList.get(position).getEvalution() == 3){
                    imageReview1.setImageResource(R.drawable.favourites_true);
                    imageReview2.setImageResource(R.drawable.favourites_true);
                    imageReview3.setImageResource(R.drawable.favourites_true);
                } else if (reviewArrayList.get(position).getEvalution() == 4){
                    imageReview1.setImageResource(R.drawable.favourites_true);
                    imageReview2.setImageResource(R.drawable.favourites_true);
                    imageReview3.setImageResource(R.drawable.favourites_true);
                    imageReview4.setImageResource(R.drawable.favourites_true);
                } else if (reviewArrayList.get(position).getEvalution() == 5){
                    imageReview1.setImageResource(R.drawable.favourites_true);
                    imageReview2.setImageResource(R.drawable.favourites_true);
                    imageReview3.setImageResource(R.drawable.favourites_true);
                    imageReview4.setImageResource(R.drawable.favourites_true);
                    imageReview5.setImageResource(R.drawable.favourites_true);
                }
                textReview.setText(reviewArrayList.get(position).getReview());
                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.99);
                int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.5);
                dialog.getWindow().setLayout(width,height);
                dialog.show(); // Открытие
            }
        });

        return row;
    }
}
