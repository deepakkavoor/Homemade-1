package com.example.student.homemade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Order {
    String provider;
    String consumer;
    boolean completed;
    boolean delivered;
    boolean paid;
    String orderTime;
    double orderTotal;
    ArrayList<FoodItem> itemsOrdered;
    String deliveryPerson;

    public Order(String provider, String consumer, boolean completed, boolean delivered, boolean paid, String orderTime, double orderTotal, ArrayList<FoodItem> itemsOrdered, String deliveryPerson) {
        this.provider = provider;
        this.consumer = consumer;
        this.completed = completed;
        this.delivered = delivered;
        this.paid = paid;
        this.orderTime = orderTime;
        this.orderTotal = orderTotal;
        this.itemsOrdered = itemsOrdered;
        this.deliveryPerson = deliveryPerson;
    }
}

public class CustomerConfirmActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String consumerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//////////////////////////////////////////////////////////////////////////////////////////////////////
    double orderTotal = 456.05;
    final String providerID = "vMR09oO90SbUtCapURrudg5QMlw2";
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String TAG = "CUSTOMERCONFIRMACTIVITY";
    final DocumentReference provider = db.collection("Provider").document(providerID);

    final boolean isMassOrder = true;
    final double discountMassOrder = 23.4;
    double discountLongTerm = 46.7;
    final double deliveryCharges = 75;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_confirm);
        FirebaseApp.initializeApp(this);

        provider.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String,Object> data = documentSnapshot.getData();
                discountLongTerm = Double.parseDouble(data.get("longTermSubscriptionDiscount").toString());
//                        .toString();
            }
        });

        db.collection("Consumer").whereEqualTo("id", consumerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Button lastButton = findViewById(R.id.btnlast);
                TextView walletCost = findViewById(R.id.walletCost);
                TextView totalCost = findViewById(R.id.totalCost);
                TextView statusInfo = findViewById(R.id.statusInfo);


                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot document : task.getResult()) {

                        final HashMap<String, Object> map = (HashMap<String, Object>) document.getData();
                        final String docID = document.getId();
                        final boolean isSubscriber = (Boolean)map.get("isSubscriber");
                        walletCost.setText(map.get("wallet").toString());
                        orderTotal = orderTotal - (isSubscriber?discountLongTerm*orderTotal*0.01:0) - (isMassOrder?orderTotal*0.01*discountMassOrder:0) + deliveryCharges ;
                        totalCost.setText(orderTotal + "");
                        statusInfo.setText("Please proceed to make the payment");
                        if(Double.parseDouble(map.get("wallet").toString()) > orderTotal){
                            lastButton.setText("Pay");
                            lastButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.d(TAG,"working till here for more case");
//                                    db.collection("Orders").add(new );
                                    db.collection("Consumer").document(docID).update("wallet", Double.parseDouble(map.get("wallet").toString()) - orderTotal);
                                    db.collection("Provider").whereEqualTo("id", providerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for(QueryDocumentSnapshot documentP : task.getResult()){
                                                db.collection("Provider").document(documentP.getId()).update("wallet",Double.parseDouble(documentP.getData().get("wallet").toString()) + orderTotal);
                                                Log.d(TAG,"provider updated successfully");
                                            }
                                        }
                                    });

                                }
                            });
                        }else{
                            statusInfo.setText("OOPS!! You don't have enough balance to make the payment");
                            lastButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.d(TAG, "Working properly for the less amount case add the code later");
                                }
                            });
                        }
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
