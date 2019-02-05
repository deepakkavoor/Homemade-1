package com.example.student.homemade;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.student.homemade.ui.ConsumerDetailsLayout;
import com.example.student.homemade.ui.ConsumerUIFragment;
import com.example.student.homemade.ui.DeliveryAndTrackingFragment;
import com.example.student.homemade.ui.HistoricalOrdersFragment;
import com.example.student.homemade.ui.MassOrderFragment;
import com.example.student.homemade.ui.RestaurantFragment;
import com.example.student.homemade.ui.TrendingItemsFragment;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements ProviderUIFragment.OnFragmentInteractionListener, ConsumerUIFragment.OnFragmentInteractionListener{
    private static int c;
    private DrawerLayout mDrawer;

    private Toolbar toolbar;
    private Context context;

    private NavigationView navigationView;


    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Dashboard");
        //logout = findViewById(R.id.main_btn_logout);
        setSupportActionBar(toolbar);

        SharedPreferences settings = getSharedPreferences("ProviderOrConsumerPreference", 0);
        c = settings.getInt("ProviderOrConsumerFlag", 0);
        Class fragmentClass;
        Fragment fragment = null;
        //Intent loginIntent = getIntent();
        //c = loginIntent.getIntExtra("ProviderOrConsumer", 0);
        if(c == 1) {
            fragmentClass = ProviderUIFragment.class;
            FragmentManager fragmentManager = getSupportFragmentManager();
            try {

                fragment = (Fragment) fragmentClass.newInstance();

            } catch (Exception e) {

                e.printStackTrace();

            }
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
        else if(c == 2) {
            fragmentClass = ConsumerUIFragment.class;
            FragmentManager fragmentManager = getSupportFragmentManager();
            try {

                fragment = (Fragment) fragmentClass.newInstance();

            } catch (Exception e) {

                e.printStackTrace();

            }
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
        else
            fragmentClass = null;


        mDrawer = findViewById(R.id.drawer_view);
        navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null) {
            Log.d("Main", user.getUid());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight

                        selectDrawerItem(menuItem);

                        return true;

                    }

                });


    }

    public void selectDrawerItem(MenuItem menuItem) {

        // Create a new fragment and specify the fragment to show based on nav item clicked

        Fragment fragment = null;

        Class fragmentClass;

        switch (menuItem.getItemId()) {
            case R.id.dashboard:
                if(c == 1)
                    fragmentClass = ProviderUIFragment.class;
                else if(c == 2)
                    fragmentClass = ConsumerUIFragment.class;
                else
                    fragmentClass = null;
                break;
            case R.id.rest_list:
                fragmentClass = RestaurantFragment.class;
                //toolbar.setTitle("Resturant List");
                break;
            case R.id.historical_orders:
                fragmentClass = HistoricalOrdersFragment.class;
               // toolbar.setTitle("Resturant List");
                break;
            case R.id.mass_orders:
                fragmentClass = MassOrderFragment.class;
               // toolbar.setTitle("Mass Order");
                break;
            case R.id.trending_items:
               fragmentClass = TrendingItemsFragment.class;
               // toolbar.setTitle("Trending Items");
                break;
            case R.id.delivery_and_tracking:
                fragmentClass = DeliveryAndTrackingFragment.class;
               // toolbar.setTitle("Delivery and Tracking");
                break;
            case R.id.user_button:
                Intent intent1 = new Intent(context, ConsumerDetailsLayout.class);
                startActivity(intent1);
                fragmentClass = MassOrderFragment.class;
                break;
            case R.id.cancel_order:
                Intent intent = new Intent(context,CancelOrder.class);
                startActivity(intent);
                fragmentClass = MassOrderFragment.class;
                break;
            case R.id.main_btn_logout:

                //Logic for logout


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context);//, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder//.setTitle("Logout")
                        .setMessage("Are you sure you want to logout from your account?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestIdToken("853704140115-a7gjo5s8uvvsfig7ij7s1t6cg9ejh1ld.apps.googleusercontent.com")
                                        .requestEmail()
                                        .build();
                                FirebaseAuth.getInstance().signOut();
                                GoogleSignIn.getClient(getApplicationContext(), gso).signOut();
                                LoginManager.getInstance().logOut();

                                //intent to login page
                                Intent newLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(newLoginIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                fragmentClass = MassOrderFragment.class;


//                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken("853704140115-a7gjo5s8uvvsfig7ij7s1t6cg9ejh1ld.apps.googleusercontent.com")
//                        .requestEmail()
//                        .build();
//                FirebaseAuth.getInstance().signOut();
//                GoogleSignIn.getClient(getApplicationContext(), gso).signOut();
//                LoginManager.getInstance().logOut();
//
//                //intent to login page
//                Intent newLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(newLoginIntent);
//                finish();


                break;



            default:
                fragmentClass = ProviderUIFragment.class;

        }


        try {

            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {

            e.printStackTrace();

        }


        // Insert the fragment by replacing any existing fragment

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();


        // Highlight the selected item has been done by NavigationView

        menuItem.setChecked(true);

        // Set action bar title

        setTitle(menuItem.getTitle());

        // Close the navigation drawer

        mDrawer.closeDrawers();

    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

}

