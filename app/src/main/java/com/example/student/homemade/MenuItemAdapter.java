package com.example.student.homemade;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MyViewHolder> {
//    int[] drawables = {android.R.drawable.alert_dark_frame};
//    HashMap<String, int> map
    private Context context;
    private ArrayList<MenuItem> items;
    private Integer type;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CircleImageView photo;
        public EditText price;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.item_name);
            photo = view.findViewById(R.id.item_image);
            price = view.findViewById(R.id.item_price);

        }
    }


    public MenuItemAdapter(Context context, ArrayList<MenuItem> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.menu_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MenuItemAdapter.MyViewHolder holder, final int position) {

        final MenuItem item = items.get(position);
        holder.name.setText(item.getName());
//        int id = context.getResources().getIdentifier("com.example.student.homemade:drawable/"+"alooparatha.jpg",null,null);
//        String name= "R.drawable.alooparatha.jpg";
//        Glide.with(context).load(name).into(holder.photo);
//        holder.photo.setImageResource(id);
//        holder.photo.setImageResource(android.R.drawable.alert_light_frame);

        holder.price.setText(item.getPrice().toString());

        if(item.getPrice() == 0){
            Log.d("bind", item.getName());
            holder.price.setError("enter");
        }

        holder.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // Log.d("before",s.toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d("ontextchanged",s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >0) {
                    item.setPrice(Long.valueOf(s.toString()));
                }
                else if (s.length() == 0){
                    item.setPrice(0L);
                }
               // Log.d("after",s.toString());

            }
        });

    }

    @Override
    public int getItemCount() {

        return items.size();

    }

    public void added(MenuItem c) {
        Log.d("added @ adapter", items.size() + "s");
        items.add(c);
        notifyItemInserted(items.indexOf(c));
    }

    public void remove(MenuItem c) {

        int pos = items.indexOf(c);
        items.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, items.size());
    }

    public ArrayList<MenuItem> getItems(){
        return items;
    }

}