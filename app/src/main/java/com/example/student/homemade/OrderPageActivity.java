package com.example.student.homemade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class FoodItem {

    String itemName;
    float itemCost;
    int itemNumber;

    public FoodItem(String itemName, float itemCost, int itemNumber) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemNumber = itemNumber;
    }
}

class Provider {
    String userID;
    String menu;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}

//class Order {
//
//    FoodItem[] orderedItems;
//    float totalCost;
//
//    public Order(FoodItem[] orderedItems, float totalCost) {
//        this.orderedItems = orderedItems;
//        this.totalCost = totalCost;
//    }
//}

class Item {
    List<String> content;
    String menu_type;

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }
}



public class OrderPageActivity extends AppCompatActivity {

    private static final String TAG = OrderPageActivity.class.getName();
    ArrayList<FoodItem> foodItems = new ArrayList<>();
    ListAdapter itemAdapter;
    ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        Button orderButton = findViewById(R.id.orderButton);

        itemListView = (ListView) findViewById(R.id.itemList);
//        FirebaseApp.initializeApp(this);
        final String providerID = "789";
        final String type = "Breakfast";
        final String consumerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Provider")
                .whereEqualTo("id", providerID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                db.collection("Provider").document(document.getId())
                                        .collection("Menu").document(type).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot documentM = task.getResult();
                                            if(documentM.exists()) {
                                                HashMap<String, Object> map = (HashMap<String, Object>) documentM.getData().get("items");
                                                for(String key : map.keySet()){
                                                    foodItems.add(new FoodItem(key, Float.parseFloat(map.get(key).toString()), 0));
                                                    itemAdapter =
                                                            new CustomAdapter(getApplicationContext(),
                                                                    foodItems, OrderPageActivity.this);
                                                    itemListView.setAdapter(itemAdapter);
                                                }
                                            }else{
                                                Log.d("fuck","the documentM doesn't exist");
                                            }
                                        }
                                    }
                                });
                            }



                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView totalCost = findViewById(R.id.totalCost);
                double orderTotal = Double.parseDouble(totalCost.getText().toString());
                HashMap<String,Object> intentHash = new HashMap<String,Object>();
                intentHash.put("foodItems",foodItems);
                intentHash.put("providerID",providerID);
                intentHash.put("type",type);
                intentHash.put("consumerID",consumerID);
                intentHash.put("orderTotal",orderTotal);
                Intent intent = new Intent(OrderPageActivity.this,CustomerConfirmActivity.class);
                intent.putExtra("intentHash",intentHash);
                startActivity(intent);
            }
        });
    }
}