package com.example.student.homemade;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreakfastActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button breakfast_upload;
    int menuID;
    int flag=0;
    //    public void pre_fill_form(){
//
//    }
    private ArrayList<String> menu_items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        //PLEASE PASS provider ID to this activity from login page
        final int myproviderID = 13;
        //PLEASE PASS provider ID to this activity from login page



        final Spinner spinner = (Spinner) findViewById(R.id.breakfast_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.breakfast_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText spinner_quantity = (EditText) findViewById(R.id.breakfast_spinner_quantity);


        final Spinner spinner1 = (Spinner) findViewById(R.id.breakfast_drinks_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.breakfast_drinks_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        final EditText spinner1_quantity = (EditText) findViewById(R.id.breakfast_drinks_spinner_quantity);

        final EditText additional_menu = (EditText) findViewById(R.id.additional_breakfast);



//        Intent intent = getIntent();
//        String value = intent.getStringExtra("type");
//        if(value.equals("breakfast_update")){
//            db.collection("Menu").orderBy("menuID", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    ArrayList<String> string_to_array = new ArrayList<>();
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Log.d("THIS IS TO BE UPDATED  ", document.getId() + " => " + document.getData());
//                            HashMap<String, Object> map = (HashMap<String, Object>) document.getData();
////                        Log.d("MENUID =  ", "" + map.get("menuID"));
//                            String[] strings = map.get("content").toString().split(",");
//                            List<String> fixlist = Arrays.asList(strings);
//
//                            string_to_array = new ArrayList<String>(fixlist);
////                        Log.d("Array is (reverse) ", string_to_array.toString());
//                            int n = string_to_array.size();
//                            String first_item = string_to_array.get(0).replace("[","");
//                            String last_but_one_item = string_to_array.get(n-2).replace("[","");
//                            String last_item = string_to_array.get(n-1).replace("]","");
//
//                            last_but_one_item = last_but_one_item.replaceAll("\\s+","");
//                            last_item = last_item.replaceAll("\\s+","");
//
//
//                            int startIndex = last_but_one_item.indexOf("(");
//                            int endIndex = last_but_one_item.indexOf(")");
//                            String replacement = "";
//                            StringBuilder sb = new StringBuilder(last_but_one_item);
//                            sb.delete(startIndex,endIndex+1);
//                            last_but_one_item = sb.toString();
//
//                            startIndex = last_item.indexOf("(");
//                            endIndex = last_item.indexOf(")");
//                            sb = new StringBuilder(last_item);
//                            sb.delete(startIndex,endIndex+1);
//                            last_item = sb.toString();
//
//
//                            Log.d("Last item is", "this is" + last_item);
//                            Log.d("Last but one item is", "this is" + last_but_one_item);
//                            ArrayAdapter Adap = (ArrayAdapter)spinner.getAdapter();
//                            int spinnerpos = Adap.getPosition(last_but_one_item);
//                            Log.d("Position", " " + spinnerpos);
//                            spinner.setSelection(spinnerpos);
//
//                            ArrayAdapter Adap1 = (ArrayAdapter)spinner1.getAdapter();
//                            int spinnerpos1 = Adap1.getPosition(last_item);
//                            Log.d("Position of Coffee ", "" + spinnerpos1);
//                            spinner1.setSelection(spinnerpos1);
//
//
//                        }
//                    } else {
//                        Log.d("R", "Error getting documents: ", task.getException());
//                    }
//                }
//            });
//        }


        db.collection("Menu").orderBy("menuID", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("THIS IS  ", document.getId() + " => " + document.getData());
                        HashMap<String, Object> map = (HashMap<String, Object>) document.getData();
                        Log.d("MENUID =  ", "" + map.get("menuID"));

                        menuID = Integer.parseInt(map.get("menuID").toString());
                    }
                } else {
                    Log.d("R", "Error getting documents: ", task.getException());
                }
            }
        });


        breakfast_upload = findViewById(R.id.breakfast_upload);

        breakfast_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text;

                String item = spinner.getSelectedItem().toString();
                String item_price = spinner_quantity.getText().toString().trim().length() + "";
                String item1 = spinner1.getSelectedItem().toString();
                String item1_price = spinner1_quantity.getText().toString().trim().length() + "";
                String item2 = additional_menu.getText().toString();
                Log.d("item", item);
                Log.d("item_price", item_price);
                Log.d("item1", item1);
                Log.d("item1_price", item1_price);
                Log.d("additional_menu", item2);
                String[] elements = item2.split(",");
                List<String> fixedLenghtList = Arrays.asList(elements);

                String item_cost = spinner_quantity.getText().toString();
                String item1_cost = spinner1_quantity.getText().toString();

                if (!item2.equals(""))
                    menu_items = new ArrayList<String>(fixedLenghtList);


                if (!item.equals("None") && !item_price.equals("0"))
                    menu_items.add(item + "( " + item_cost + " )");
                else if(item_price.equals("0")){
                    text = "Price not entered";
                    flag = 1;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(BreakfastActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(item.equals("None")){
                    text = "Menu not entered with respective price";
                    flag = 1;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(BreakfastActivity.this, MainActivity.class);
                    startActivity(intent);
                }


                if (!item1.equals("None") && !item1_price.equals("0"))
                    menu_items.add(item1 + "( " + item1_cost + " )");
                else if(item1_price.equals("0")){
                    text = "Price not entered";
                    flag = 1;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(BreakfastActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(item1.equals("None")){
                    text = "Menu not entered with respective price";
                    flag = 1;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(BreakfastActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                Log.d("ACTUAL MENU ID ", "" + menuID);

                Log.d("Array : ", menu_items.toString());

                Map<String, Object> menu_item = new HashMap<>();
                menu_item.put("content", menu_items);
                menu_item.put("menuID", menuID + 1);
                menu_item.put("provider", myproviderID);
                menu_item.put("menu_type", "Breakfast");
                if (flag != 1) {


                    db.collection("Menu").document("" + (menuID + 1)).set(menu_item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Su", "DocumentSnapshot successfully written!");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Fa", "Error writing document", e);
                                }
                            });

                    db.collection("Menu").document("" + menuID)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Delete", "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Error Delete", "Error deleting document", e);
                                }
                            });

//                context = getApplicationContext();
                    text = "Menu uploaded to database";
//                duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(BreakfastActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}