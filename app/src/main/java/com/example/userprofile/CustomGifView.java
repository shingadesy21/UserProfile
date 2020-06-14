package com.example.userprofile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class CustomGifView extends Thread
{
    Activity activity;
    ImageView image_view;
    boolean is_running=false;
    int pause_time;
    int[] drawables;

    public CustomGifView(Activity activity,ImageView img_view,int[] drawable)
    {
        this.activity=activity;
        this.image_view=img_view;
        this.is_running=true;
        pause_time=25;
        this.drawables=drawable;
    }

    public void set_pause_time(int interval)
    {
        this.pause_time=interval;
    }
    public void stop_playing()
    {
        this.is_running=false;
    }

    public void run()
    {
        Log.d("Gif Player","Gif Player Stopped");

        int pointer=0;
        while (this.is_running)
        {
            if(drawables.length>0)
            {
                if((drawables.length-1)==pointer)
                {
                    pointer=0;
                }


                try
                {
                    activity.runOnUiThread(new Run(pointer));
                    Thread.sleep(pause_time);
                }
                catch (Exception e)
                {
                    Log.d("GifPlayer","Exception: "+e.getMessage());
                    is_running=false;
                }
                pointer++;
            }
        }
        Log.d("Gif Player","Gif Player Stopped");
    }

    class Run implements Runnable
    {
        int pointer;
        public Run(int pointer)
        {
            this.pointer=pointer;
        }
        public void run()
        {
            image_view.setImageResource(drawables[pointer]);
        }
    }
}