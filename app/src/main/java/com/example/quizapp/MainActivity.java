package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Adapter.ProgramingAdapter;
import com.example.quizapp.Model.ProgramingModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private DatabaseReference mRef;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);

        ArrayList<ProgramingModel> list = new ArrayList<>();

        list.add(new ProgramingModel(R.drawable.java,"Java"));
        list.add(new ProgramingModel(R.drawable.android,"Android"));
        list.add(new ProgramingModel(R.drawable.js,"JavaScript"));
        list.add(new ProgramingModel(R.drawable.html,"HTML"));
        list.add(new ProgramingModel(R.drawable.python,"Phyton"));
        list.add(new ProgramingModel(R.drawable.c,"C"));
        list.add(new ProgramingModel(R.drawable.css,"CSS"));
        list.add(new ProgramingModel(R.drawable.php,"PHP"));
        list.add(new ProgramingModel(R.drawable.mysql,"MySql"));
        list.add(new ProgramingModel(R.drawable.swift,"Swift"));
        list.add(new ProgramingModel(R.drawable.ckk,"C++"));
        list.add(new ProgramingModel(R.drawable.swift,"Swift"));


        ProgramingAdapter adapter = new ProgramingAdapter(list,this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);


        drawerLayout = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.Navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new HomeFragment());

        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userNameTV);
        TextView txtUserEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userEmailTV);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new HomeFragment());
            navigationView.setCheckedItem(R.id.Home);
        }

        mRef = FirebaseDatabase.getInstance().getReference().child("UserDetails");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String email = snapshot.child("email").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();

                   // Toast.makeText(MainActivity.this, "email: " + email + "username: " + username, Toast.LENGTH_SHORT).show();
                    txtProfileName.setText(username);
                    txtUserEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FailedException", "onCancelled:" + error.getMessage());
            }
        });




    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.Home: {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new HomeFragment()).commit();
                break;
            }

            case R.id.MyScore: {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new MyScoreFragment()).commit();
                break;
            }

            case R.id.logout: {
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignUpActivity.class));
                finishAffinity();
            }
                case R.id.about:{
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new AboutFragment()).commit();
                    break;
            }
            case R.id.exit:{
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new ExitFragment()).commit();
                break;
            }


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.side_navigation, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout: {
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        drawerLayout.isDrawerOpen(GravityCompat.START);
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        {
            super.onBackPressed();
        }
    }
}

