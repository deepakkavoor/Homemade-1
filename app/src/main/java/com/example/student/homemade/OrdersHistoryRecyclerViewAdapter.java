package com.example.student.homemade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class  OrdersHistoryRecyclerViewAdapter extends RecyclerView.Adapter<OrdersHistoryRecyclerViewAdapter.ViewHolder> {
    private ArrayList<OrderInfo> info;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView time_and_date;
        TextView no_order_items;
        TextView order_id;
        TextView things_ordered;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time_and_date = itemView.findViewById(R.id.time_and_date);
            no_order_items = itemView.findViewById(R.id.no_order_items);
            order_id = itemView.findViewById(R.id.order_id);
            things_ordered = itemView.findViewById(R.id.things_ordered);
            layout = itemView.findViewById(R.id.orders_layout_rl);


        }
    }

    public OrdersHistoryRecyclerViewAdapter(Context context, ArrayList<OrderInfo> info) {
        this.info = info;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_recycler_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final OrderInfo orderInfo = info.get(i);
        viewHolder.time_and_date.append(orderInfo.getTime_and_date());
        viewHolder.no_order_items.append("" + orderInfo.getNoOrders());
        viewHolder.order_id.append("" + orderInfo.getOrderID());
        viewHolder.things_ordered.append("" + orderInfo.getThings_ordered());


//        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, orderInfo.getOrderID(), Toast.LENGTH_LONG).show();
//                Log.d("SELECTION", v.toString());
//                Log.d("SELECTION", orderInfo.toString());
//                int order_id = orderInfo.getOrderID();
//                Intent newintent = new Intent(context, EachOrderDisplayByID.class);
//                newintent.putExtra("order_id", order_id);
//                context.startActivity(newintent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public void added(OrderInfo orderInfo){
        info.add(orderInfo);
        notifyItemInserted(info.indexOf(orderInfo));

    }


}