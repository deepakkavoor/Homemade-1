package com.example.student.homemade;

import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;

        import java.util.ArrayList;

public class CancelOrder extends AppCompatActivity {
    private ArrayList<CancelItem> mExampleList;
    private RecyclerView mRecyclerview;
    private CancelAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        createExampleList();
        buildRecyclerView();
    }
    public void removeItem(int position){
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }


    public void changeItem(int position,String text){
        mExampleList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }
    public void createExampleList(){
        mExampleList=new ArrayList<>();
        mExampleList.add(new CancelItem("Line1","Line2","Line3"));
        mExampleList.add(new CancelItem("Line12","Line2","Line3"));
        mExampleList.add(new CancelItem("Line1","Line2","Line3"));
        mExampleList.add(new CancelItem("Line25","Line2","Line3"));
        mExampleList.add(new CancelItem("Line1","Line2","Line3"));
        mExampleList.add(new CancelItem("Line12","Line2","Line3"));
        mExampleList.add(new CancelItem("Line1","Line2","Line3"));
        mExampleList.add(new CancelItem("Line25","Line2","Line3"));


    }
    public void buildRecyclerView(){
        mRecyclerview=findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new CancelAdapter(mExampleList);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListner(new CancelAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                changeItem(position," ");
            }


            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
}
