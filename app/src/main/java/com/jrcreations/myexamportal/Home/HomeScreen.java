package com.jrcreations.myexamportal.Home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.jrcreations.myexamportal.Home.Items.CurrentAffairs;
import com.jrcreations.myexamportal.Home.Items.PYQ;
import com.jrcreations.myexamportal.Home.Items.Quizes;
import com.jrcreations.myexamportal.Home.Items.TestSeries;
import com.jrcreations.myexamportal.R;


public class HomeScreen extends AppCompatActivity {

DrawerLayout drawerLayout;
NavigationView navigationView;
Toolbar  toolbar;
ActionBarDrawerToggle toggle;
BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navdrawer);
        drawerLayout=findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();





        getSupportFragmentManager().beginTransaction().
                replace(R.id.mainconstraint,new TestSeries()).commit();
        bottomNavigationView.setSelectedItemId(R.id.testseries);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.testseries:
                        fragment =new TestSeries();
                        break;
                    case R.id.quiz:
                        fragment =new Quizes();
                        break;
                    case R.id.currentaffairs:
                        fragment=new CurrentAffairs();
                        break;
                    case R.id.pyq:
                        fragment=new PYQ();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainconstraint,fragment).commit();
                return true;
            }
        });

    }
}