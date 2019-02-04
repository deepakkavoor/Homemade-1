package com.example.student.homemade;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

public class CancelAdapter extends RecyclerView.Adapter<CancelAdapter.ExampleViewHolder> {
    private ArrayList<CancelItem> mExampleList;
    private OnItemClickListner mListner;
    public interface OnItemClickListner{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listener){
        mListner=listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mDeleteImage;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListner listener) {
            super(itemView);
            mTextView1=itemView.findViewById(R.id.tvitem);
            mTextView2=itemView.findViewById(R.id.tvprice);
            mTextView3=itemView.findViewById(R.id.tvqty);
            mDeleteImage=itemView.findViewById(R.id.image_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }

                }
            });
        }
    }
    public CancelAdapter(ArrayList<CancelItem>exampleList){
        mExampleList=exampleList;

    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cancel_item,parent,false);
        ExampleViewHolder evh=new ExampleViewHolder(v,mListner);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        CancelItem currentItem=mExampleList.get(position);

        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());
        holder.mTextView3.setText(currentItem.getmText3());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
