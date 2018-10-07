package com.example.aabhashgod.ecanteen;

import android.content.Intent;
import android.graphics.Color;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import com.example.aabhashgod.ecanteen.adapter.ClickListenerEvents;
import com.example.aabhashgod.ecanteen.adapter.FoodAdapter;
import com.example.aabhashgod.ecanteen.model.MenuModel;
import com.example.aabhashgod.ecanteen.model.PlateModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView foodList;
    private int foodCount = 0;
    private FoodAdapter foodAdapter;
    List<MenuModel> list = initializeData();
    List<PlateModel> plate = initializePlate();
    private String itemName;
    private int itemQuantity, itemPrice;

    private String userDisplayName, userDisplayEmail, userPhotoURI;
    private TextView navdisplayname, navEmail;
    private CircleImageView navAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeView();
        buildRecyclerView();
    }

    public void changeItemQuantity(int position, int quantity) {
        list.get(position).addQuantity(quantity);
        foodAdapter.notifyItemChanged(position);

    }

    public void initializeView() {

        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth != null) {
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
                Intent startPlateActivity = new Intent(MenuActivity.this, Plate.class);
                startPlateActivity.putExtra("key", (Serializable) list);
                startActivity(startPlateActivity);
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
        View header = navigationView.getHeaderView(0);
        navdisplayname = header.findViewById(R.id.nav_display_name);
        navEmail = header.findViewById(R.id.nav_email);
        navAvatar = header.findViewById(R.id.nav_image_avatar);
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
                foodCount++;
                startFinalOrderActivity(position);
                changeItemQuantity(position, foodCount);
            }
        });
        //Notifying DataSetChanged on RecyclerView
        foodAdapter.notifyDataSetChanged();

    }

    private void startFinalOrderActivity(int position) {

        itemName = list.get(position).getName();
        itemQuantity = list.get(position).getShortDetail();
        itemPrice = list.get(position).getPrice();
        initializePlate();

    }

    private List<PlateModel> initializePlate() {
        List<PlateModel> plateModels = new ArrayList<>();
        plateModels.add(new PlateModel(itemName, itemQuantity, itemPrice));
        return plateModels;
    }

    public List<MenuModel> initializeData() {
        List<MenuModel> menuModelClasses = new ArrayList<>();
        menuModelClasses.add(new MenuModel("MoMo", 0, 95, R.drawable.momo));
        menuModelClasses.add(new MenuModel("Chowmein", 0, 35, R.drawable.chowmin));
        menuModelClasses.add(new MenuModel("Coke", 0, 56, R.drawable.coke));
        menuModelClasses.add(new MenuModel("Boiled Egg", 0, 54, R.drawable.egg));
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
