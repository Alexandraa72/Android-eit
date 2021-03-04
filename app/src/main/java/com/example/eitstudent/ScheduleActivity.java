package com.example.eitstudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ScheduleActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        drawerLayout = findViewById(R.id.drawer_layout);


    }
    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        Uri uri = Uri.parse("http://www.erdenetis.edu.mn");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        MainActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        this.finish();
    }
    public void ClickSettings(View view){
        recreate();
    }
    public void ClickLogout(){
        MainActivity.logout(this);

    }
    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

}