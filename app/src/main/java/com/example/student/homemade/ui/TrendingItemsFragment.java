package com.example.student.homemade.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student.homemade.R;

public class TrendingItemsFragment extends Fragment {
    View v;
    int[] IMAGES = {R.drawable.alooparatha,R.drawable.biriyani,R.drawable.chicken_kabab,R.drawable.dosaidli,R.drawable.muturpaneer,R.drawable.poori_bhaji,R.drawable.samosa,};
    String[] NAMES={"Aloo-Paratha","Biriyani","Chicken kabab","Dosa-Idli","Paneer","Poori-Bhaji","Samosa"};
    String[] DESCRIPTION = {"SHARMA HOUSE FOOD","KUMAR FOOD HOUSE FOOD","MAHARAJA KI DUKAN","JAIN HOUSE FOOD","KRISHNA FOOD CENTER","GOPAL CENTER OF FOOD","TEMP HOUSE"};


    public TrendingItemsFragment() {
        // Required empty public constructor
    }

    public static TrendingItemsFragment newInstance() {

        return new TrendingItemsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_trending_items, container, false);

        ListView listView = (ListView) v.findViewById(R.id.listview);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        return v;

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.trending_items_custom_view,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);
            TextView textView_name = (TextView) view.findViewById(R.id.textView_names);
            TextView textView_description = (TextView) view.findViewById(R.id.textView_description);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_description.setText(DESCRIPTION[i]);
            return view;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
