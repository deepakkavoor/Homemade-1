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

public class SnacksActivity extends AppCompatActivity {
    Button snacks_upload;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int menuID;
    int flag=0;
    private ArrayList<String> menu_items = new ArrayList<String>();
    public boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            // s is not numeric
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);

        //PLEASE PASS provider ID to this activity from login page
        final int myproviderID = 13;
        //PLEASE PASS provider ID to this activity from login page


        final Spinner spinner = (Spinner) findViewById(R.id.snacks_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.food_array,  android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText spinner_quantity = (EditText) findViewById(R.id.snacks_spinner_quantity);


        final Spinner spinner1 = (Spinner) findViewById(R.id.snacks_drinks_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.food_array,  android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        final EditText spinner1_quantity = (EditText) findViewById(R.id.snacks_drinks_spinner_quantity);


        final EditText additional_menu = (EditText) findViewById(R.id.additional_snacks);

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


        snacks_upload = findViewById(R.id.snacks_upload);

        snacks_upload.setOnClickListener(new View.OnClickListener() {
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


                if (!item.equals("None") && !item_price.equals("0")  && isNumeric(item1_cost))
                    menu_items.add(item + "( " + item_cost + " )");
                else if ((!item1.equals("None") && item1_price.equals("0")) || !isNumeric(item1_cost)){
                    text = "Price not valid";
                    flag = 1;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(SnacksActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(item.equals("None")&& !item_price.equals("0")){
                    text = "Menu not entered with respective price";
                    flag = 1;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(SnacksActivity.this, MainActivity.class);
                    startActivity(intent);
                }


                if (!item1.equals("None") && !item1_price.equals("0") && isNumeric(item1_cost))
                    menu_items.add(item1 + "( " + item1_cost + " )");
                else if ((!item1.equals("None") && item1_price.equals("0")) || !isNumeric(item1_cost)){
                    text = "Price not valid";
                    flag = 1;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(SnacksActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(item1.equals("None") && !item1_price.equals("0")){
                    text = "Menu not entered with respective price";
                    flag = 1;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(SnacksActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                Log.d("ACTUAL MENU ID ", "" + menuID);

                Log.d("Array : ", menu_items.toString());

                Map<String, Object> menu_item = new HashMap<>();
                menu_item.put("content", menu_items);
                menu_item.put("menuID", menuID + 1);
                menu_item.put("provider", myproviderID);
                menu_item.put("menu_type", "Snacks");

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

                text = "Menu uploaded to database";

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent = new Intent(SnacksActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

