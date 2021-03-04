package com.example.eitstudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private Connection mycon = new Connection();

    CardView calendar;
    CardView schedule;
    CardView Gpa;
    CardView notification;
    CardView reminder;
    CardView todos;
    CardView fee;
    CardView exam;

    DrawerLayout drawerLayout;
    private TextView stname, gpa, stcode, mergejil, credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String code = intent.getStringExtra("stCode");
        drawerLayout = findViewById(R.id.drawer_layout);

        calendar = findViewById(R.id.calendar);
        schedule = findViewById(R.id.schedule);
        Gpa = findViewById(R.id.Gpa);
        notification = findViewById(R.id.noti);
        reminder = findViewById(R.id.reminder);
        todos = findViewById(R.id.todo);
        fee = findViewById(R.id.fee);
        exam = findViewById(R.id.exam);

        gpa = findViewById(R.id.gpa);
        stname = findViewById(R.id.stname);
        stcode = findViewById(R.id.stcode);
        mergejil = findViewById(R.id.mergejil);
        credit = findViewById(R.id.credit);
        showStudentInfo(code);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }
        });
        Gpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GpaActivity.class));
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            }
        });
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReminderActivity.class));
            }
        });
        todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TodoActivity.class));
            }
        });
        fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeeActivity.class));
            }
        });
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExamActivity.class));
            }
        });
    }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer((GravityCompat.START));
    }
    public void ClickLogo(View view){
        Uri uri = Uri.parse("http://www.erdenetis.edu.mn");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }
    public void ClickSettings(View view){

        redirectActivity(this,Settings.class);
    }
    public void ClickLogout(){
        logout(this);
        
    }

    public  static void logout(final Activity mainActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                redirectActivity(mainActivity,LoginActivity.class);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    protected void showStudentInfo(String code) {
        ResultSet resultSet = mycon.executeQuery(this, "select UPPER(LEFT(U021GG,1)+'.'+U021HH) as ner,U021EE as code, dbo.func_split_get_first_letters(MMM4) as mer,  dbo.func_CRED_all(U021EE) AS credit, dbo.func_GPA_all(U021EE) AS gpa from U021\n" +
                "left join MER_L on MMM3=U021DD and MMM2 = U021AE\n" +
                "where U021EE='" + code + "'");
        String result = "";
        try {
            if (resultSet.next()) {
//                Toast.makeText(getApplicationContext(), resultSet.getString("U25AA"), Toast.LENGTH_LONG).show();
                stname.setText(resultSet.getString("ner").trim());
                stcode.append(code);
                mergejil.append(resultSet.getString("mer"));
                gpa.append(" ");
                gpa.append(resultSet.getString("gpa"));
                credit.append(resultSet.getString("credit"));

            }
            //tvResult.setText(result);
        } catch (SQLException throwables) {
            Toast.makeText(this, throwables.getMessage(), Toast.LENGTH_LONG).show();
            throwables.printStackTrace();
        } finally {
            mycon.connectionClose();

        }
    }

    private void showStudentName(String code) {
    }
}