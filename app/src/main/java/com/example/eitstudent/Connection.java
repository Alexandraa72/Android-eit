package com.example.eitstudent;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    private String ip = "172.18.5.44";
    private String port = "1433";
    private String classes = "net.sourceforge.jtds.jdbc.Driver";
    private String database = "mustdb";
    private String username = "sa";
    private String password = "123";
    private String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
    public java.sql.Connection connection = null;

    public boolean connectionOpen(Activity activity)
    {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        //  txtResult = findViewById(R.id.txtResult);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(classes);
            connection = DriverManager.getConnection(url, username, password);
            //   txtResult.setText("SUCCESS");
            //Toast.makeText(activity, "SUCCESS", Toast.LENGTH_LONG).show();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //  txtResult.setText("ERROR");
            Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
            return false;
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            //  txtResult.setText("FAILURE");
            Toast.makeText(activity, "FAILURE", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public void connectionClose()
    {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet executeQuery(Activity activity, String sql)
    {
        ResultSet rs = null;
        Statement statement = null;
        try {

            if (connection == null || connection.isClosed()) connectionOpen(activity);
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
        }
        catch (SQLException throwables) {
            Toast.makeText(activity, throwables.getMessage(), Toast.LENGTH_LONG).show();
            throwables.printStackTrace();
        }
        return rs;
    }

    public Boolean execute(Activity activity, String sql)
    {
        Boolean ret = false;
        Statement statement = null;
        try {
            if (connection == null || connection.isClosed()) connectionOpen(activity);
            statement = connection.createStatement();
            ret = statement.execute(sql);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public int executeUpdate(Activity activity, String sql)
    {
        int ret = 0;
        Statement statement = null;
        try {
            if (connection == null || connection.isClosed()) connectionOpen(activity);
            statement = connection.createStatement();
            ret = statement.executeUpdate(sql);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }
}