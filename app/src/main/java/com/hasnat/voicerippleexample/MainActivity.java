package com.hasnat.voicerippleexample;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private Button playButton;



  RippleView ripple2;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initializeControlPanel();

    ripple2 = (RippleView) findViewById(R.id.ripple2);

  }


  @Override
  protected void onStop() {
    super.onStop();
    try {
    } catch (IllegalStateException e) {
      Log.e(TAG, "onStop(): ", e);
    }



  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    //voiceRipple.onDestroy();
  }


  Handler taskHandler = new NonLeakyHandler(this);

  private Runnable repeatativeTaskRunnable = new Runnable() {
    public void run() {
      new Handler(getMainLooper()).post(new Runnable() {
        @Override
        public void run() {

          //DO YOUR THINGS
          ripple2.newRipple();

          taskHandler.postDelayed(repeatativeTaskRunnable,300);


        }
      });
    }
  };

  private void initializeControlPanel() {
    playButton = (Button) findViewById(R.id.voice_play_button);

    Handler handler = new Handler(Looper.myLooper());
    Runnable run = new Runnable() {
      @Override
      public void run() {
        new Handler(Looper.myLooper()).post(new Runnable() {
          @Override
          public void run() {
            taskHandler.removeCallbacks(repeatativeTaskRunnable);
          }
        });
      }
    };



    playButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
       // if (directory != null && audioFile != null) {
         // player = new MediaPlayer();
          //if(!handler.post(run)){

            handler.postDelayed(run,3000);
          //}

          taskHandler.postDelayed(repeatativeTaskRunnable,0);
      //  }
      }
    });

  }


  /**
   * Instances of static inner classes do not hold an implicit
   * reference to their outer class.
   */
  private static class NonLeakyHandler extends Handler {
    private final WeakReference<MainActivity> mActivity;

    public NonLeakyHandler(MainActivity activity) {
      mActivity = new WeakReference<MainActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
      MainActivity activity = mActivity.get();
      if (activity != null) {
        // ...
      }
    }
  }
}
