package com.example.aabhashgod.ecanteen;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aabhashgod.ecanteen.adapter.ClickListenerEvents;
import com.example.aabhashgod.ecanteen.adapter.FoodAdapter;
import com.example.aabhashgod.ecanteen.model.MenuModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView foodList;
    private int counter = 0;
    private FoodAdapter foodAdapter;
    List<MenuModel> list = initializeData();
    private String userDisplayName,userDisplayEmail,userPhotoURI;
    private TextView navdisplayname,navEmail;
    private CircleImageView navAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeView();
        buildRecyclerView();
    }

    public void changeItemQuantity(int position, String quantity) {
        list.get(position).addQuantity(quantity);
        foodAdapter.notifyItemChanged(position);

    }

    public void initializeView() {

        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth!=null){
            userDisplayName = firebaseAuth.getCurrentUser().getDisplayName();
            userDisplayEmail = firebaseAuth.getCurrentUser().getEmail();
            userPhotoURI = String.valueOf(firebaseAuth.getCurrentUser().getPhotoUrl());
        }
        //Initializing View Elements
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OnClick Method For FAB
            }
        });
        //Initializing Navigation Drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //On menuItemClick of NAV bar
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_menu);
        View header=navigationView.getHeaderView(0);
        navdisplayname = header.findViewById(R.id.nav_display_name);
        navEmail = header.findViewById(R.id.nav_email);
        navAvatar =header.findViewById(R.id.nav_image_avatar);
        navEmail.setText(userDisplayEmail);
        navdisplayname.setText(userDisplayName);
        Picasso.with(this)
                .load(userPhotoURI)
                .into(navAvatar);
    }

    public void buildRecyclerView() {
        //Initializing RecyclerView
        foodList = findViewById(R.id.foodContainer);
        //Initializing Adapter
        foodAdapter = new FoodAdapter(list, this);
        //Setting HasFixedSize property to RecyclerView
        foodList.setHasFixedSize(true);
        //Declaring and Initializing LinearLayout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //Setting LinearLayout on RecyclerView
        foodList.setLayoutManager(linearLayoutManager);
        //Setting Adapter on RecyclerView
        foodList.setAdapter(foodAdapter);
        //Handling click events on RecyclerView
        foodAdapter.setOnItemClickListener(new ClickListenerEvents() {

            @Override
            public void onAddClick(int position) {
                counter++;
                changeItemQuantity(position, "Quantity : " + counter);
            }
        });
        //Notifying DataSetChanged on RecyclerView
        foodAdapter.notifyDataSetChanged();

    }

    public List<MenuModel> initializeData() {
        List<MenuModel> menuModelClasses = new ArrayList<>();
        menuModelClasses.add(new MenuModel("MoMo", "", "RS : 95", R.drawable.momo));
        menuModelClasses.add(new MenuModel("Chowmein", "", "RS : 35", R.drawable.chowmin));
        menuModelClasses.add(new MenuModel("Coke", "", "RS : 50", R.drawable.coke));
        menuModelClasses.add(new MenuModel("Boiled Egg", "", "RS : 25", R.drawable.egg));
        return menuModelClasses;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        }
        if (id == R.id.nav_signOut) {
            final DrawerLayout mainLayout;
            mainLayout = findViewById(R.id.drawer_layout);

            Snackbar snackbar = Snackbar
                    .make(mainLayout, "Logging Out ?", Snackbar.LENGTH_LONG)
                    .setAction("Yes!", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseAuth.getInstance().signOut();
                            Intent startMainActivity = new Intent(MenuActivity.this, MainActivity.class);
                            Snackbar mSnackbar = Snackbar.make(mainLayout, "Logged Out successfully", Snackbar.LENGTH_LONG);
                            mSnackbar.setActionTextColor(Color.RED);
                            mSnackbar.show();
                            startActivity(startMainActivity);

                        }
                    });

            snackbar.show();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
