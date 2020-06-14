package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.userprofile.Database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class User_Registration extends AppCompatActivity {
EditText name,password,email;
RadioGroup gender;
Button register;
TextView login,browse;
String gender_value="";
DatabaseHelper helper;
ImageView imageView;
final int REQUEST_CODE_GALLERY = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__registration);


        name=findViewById(R.id.use_name);
        email=findViewById(R.id.user_email);
        password=findViewById(R.id.user_pass);
        gender=findViewById(R.id.user_gender);
        login=findViewById(R.id.user_login_page);
        register=findViewById(R.id.user_signup);
        imageView=findViewById(R.id.user_profile_photo);
        browse=findViewById(R.id.photo);
        helper=new DatabaseHelper(User_Registration.this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_Registration.this,LogIn.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u_name=name.getText().toString().trim();
                String u_email=email.getText().toString().trim();
                String u_password=password.getText().toString().trim();

                final SharedPreferences pref= getApplicationContext().getSharedPreferences("MyPref",0);
                SharedPreferences.Editor editor=pref.edit();
                editor.clear();

                editor.putString("id",u_name);
                editor.putString("pass",u_password);
                editor.commit();


                if(u_name.isEmpty() || u_email.isEmpty() || u_password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Valid Data",Toast.LENGTH_LONG).show();
                }
                else if(gender_value.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Valid Data",Toast.LENGTH_LONG).show();

                }
                else{
                    long val=helper.adduser(u_name,u_email,u_password,gender_value,imageViewToByte(imageView));//,gender_value
                    if(val > 0)
                    {
                        Toast.makeText(getApplicationContext(),"Register Successful",Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(getApplicationContext(),MainActivity.class );
                        intent.putExtra("id",u_name);
                        intent.putExtra("pass",u_password);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Register ERROR",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        User_Registration.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });




    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void onRadioButtonCLicked(View view){
        boolean cheked=((RadioButton)view).isChecked();

        switch (view.getId()){
            case R.id.rd_male:
                if(cheked)
                    gender_value="Male";
                break;
            case R.id.rd_female:
                if(cheked)
                    gender_value="Female";
                break;

        }
    }
}