package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.userprofile.Database.DatabaseHelper;

public class LogIn extends AppCompatActivity {
EditText id,pass;
TextView new_user;
Button login;
DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        id=findViewById(R.id.user_id);
        pass=findViewById(R.id.user_pass);
        login=findViewById(R.id.user_login);
        new_user=findViewById(R.id.new_account);
        helper=new DatabaseHelper(LogIn.this);
         final SharedPreferences pref= getApplicationContext().getSharedPreferences("MyPref",0);
       final SharedPreferences.Editor editor=pref.edit();
        editor.clear();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id=id.getText().toString().trim();
                String user_password=pass.getText().toString().trim();
                editor.putString("id",user_id);
                editor.putString("pass",user_password);
                editor.commit();
                if(user_id.isEmpty() || user_password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter valid Creditiantials",Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean res = helper.check_user_data(user_id, user_password);
                    if(res==true){
                        //startActivity(new Intent(LogIn.this,MainActivity.class));
                        Intent intent=new Intent(LogIn.this,MainActivity.class);
                        intent.putExtra("id",user_id);
                        intent.putExtra("pass",user_password);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"User Name And Password Incorrect",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}