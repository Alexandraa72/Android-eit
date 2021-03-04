package com.example.eitstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    private  Connection mycon = new Connection();
    EditText etStudentCode, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etStudentCode = findViewById(R.id.etStudentCode);
        etPassword = findViewById(R.id.etPassword);
    }

    public void clickLogin(View view) {
        checkLogin(etStudentCode.getText().toString().trim(), etPassword.getText().toString().trim());
    }

    public void checkLogin(String stCode, String password) {
        ResultSet resultSet = mycon.executeQuery(this, "select U25AA, U25BB from U25 where U25AA = '" + stCode + "' and U25BB = '" + password + "'");

        try {
            if (resultSet.next()) {
                //Toast.makeText(getApplicationContext(), resultSet.getString("U25AA"), Toast.LENGTH_LONG).show();
                Intent mainintent = new Intent(LoginActivity.this, MainActivity.class);
                mainintent.putExtra("stCode", resultSet.getString("U25AA"));
                startActivity(mainintent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Оюутны код эсвэл нууц үг буруу", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException throwables) {
            Toast.makeText(this,throwables.getMessage(), Toast.LENGTH_LONG).show();
            throwables.printStackTrace();
        }
        finally {
            mycon.connectionClose();
        }
    }
}