package com.example.userprofile.Intero_Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.userprofile.MainActivity;
import com.example.userprofile.R;
import com.example.userprofile.User_Registration;

import java.util.ArrayList;
import java.util.List;

public class Welcome_Intro extends AppCompatActivity {
    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter ;
    int position = 0 ;
    Button btnGetStarted;
    Button btnNext;
    TextView tvSkip;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final SharedPreferences pref= getApplicationContext().getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=pref.edit();

        String name,password;
        name=pref.getString("id",null);
        password=pref.getString("pass",null);
        if (restorePrefData()) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class );


            intent.putExtra("id",name);
            intent.putExtra("pass",password);
            startActivity(intent);
            finish();


        }
        setContentView(R.layout.activity_welcome__intro);


        // ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tvSkip = findViewById(R.id.tv_skip);
        screenPager =findViewById(R.id.screen_viewpager);

        final List<ScreenItem> mList = new ArrayList<>();

        mList.add(new ScreenItem("WELCOME","Find and compare Master degrees from top universities worldwide: search all MBA, MSc, MA, LLM, MPhil and more postgraduate programmes to study abroad ",R.drawable.welcome));
        mList.add(new ScreenItem("JOIN US","Start your online learning journey today!\nExplore the benefits of online learning and discover how to successfully learn online. Join course for free. ",R.drawable.book));
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);


                }

                if (position == mList.size()-1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();


                }



            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //open main activity

                Intent mainActivity = new Intent(getApplicationContext(), User_Registration.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();



            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });



    }
    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;



    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();


    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);

    }
}

