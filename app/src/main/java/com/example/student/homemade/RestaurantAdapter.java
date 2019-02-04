package com.example.student.homemade;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.student.homemade.ui.RestaurantDialogFragment;

import java.util.List;


//Adapter that displays restaurant cards
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private Context mContext;
    private List<RestaurantModel> myList;
    private String TAG = "RestaurantAdapter";


    public RestaurantAdapter(@NonNull Context context, List<RestaurantModel> list) {
        this.mContext = context;
        this.myList = list;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final RestaurantModel restaurantModel = myList.get(i);
        String dis = mContext.getString(R.string.dist_from_curr_loc) +": " + restaurantModel.getDistance();
        myViewHolder.distanceFromCurrLoc.setText(dis + " km");
        // myViewHolder.rating.setText("Rating:" + String.valueOf(5));
        myViewHolder.restaurantName.setText(restaurantModel.getRestaurantName());
        myViewHolder.rating.setRating((float)restaurantModel.getRating());

//Glide is a library used to put images in image view
        Glide.with(mContext).load(R.drawable.default_rest).into(myViewHolder.thumbnail);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantDialogFragment dialogFragment = new RestaurantDialogFragment();
                Bundle args = new Bundle();
                args.putString("stars",String.valueOf(restaurantModel.getRating()));
                args.putString("title",restaurantModel.getRestaurantName());
                args.putStringArrayList("reviews",restaurantModel.getReview());
                args.putString("description",restaurantModel.getDescription());

                Log.d(TAG,String.valueOf(restaurantModel.getRating()));
                Log.d(TAG,restaurantModel.getRestaurantName());
                Log.d(TAG,restaurantModel.getReview().toString());

                dialogFragment.setArguments(args);
                dialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "Simple Dialog");
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_items, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName, distanceFromCurrLoc;
        RatingBar rating;
        ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurantname);
            distanceFromCurrLoc = view.findViewById(R.id.distance);
            thumbnail = view.findViewById(R.id.coverImageView);
            rating=view.findViewById(R.id.rating_bar);


        }
    }

}