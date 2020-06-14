package com.example.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userprofile.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
TextView user_name,user_email,user_sex;
Button update,delete;
DatabaseHelper helper;
String name,password,email,gender;
ImageView imageView;
byte[] img;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_name=findViewById(R.id.set_name);
        user_email=findViewById(R.id.set_email);
        user_sex=findViewById(R.id.set_gender);
        update=findViewById(R.id.user_update);
        delete=findViewById(R.id.user_delete);
        imageView=findViewById(R.id.user_profile);
        helper=new DatabaseHelper(this);



        //retriving user name and password
        if (getIntent().hasExtra("id") && getIntent().hasExtra("pass")){
                name=getIntent().getStringExtra("id");
                password=getIntent().getStringExtra("pass");
        }

        //fetching details from database
        Cursor cursor=helper.getdata(name,password);
        while(cursor.moveToNext()){
            //int id=cursor.getInt(0);
             name=cursor.getString(1);
             email=cursor.getString(2);
             gender=cursor.getString(3);
             //img=cursor.getBlob(4);
        }
//set values to textview n imageview
        user_name.setText("Name:- "+name);
        user_email.setText("Email:- "+email);
        user_sex.setText("SEX:- "+gender);
        //imageView.setImageBitmap();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setting:
                Toast.makeText(getApplicationContext(),"Working Om Progress",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.logout:
                startActivity(new Intent(this,LogIn.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}