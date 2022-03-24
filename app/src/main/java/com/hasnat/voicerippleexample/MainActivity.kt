package com.hasnat.voicerippleexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import java.lang.IllegalStateException
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private var playButton: Button? = null
    var ripple2: RippleView? = null
    lateinit var cycleRunnable:Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeControlPanel()
        ripple2 = findViewById<View>(R.id.ripple2) as RippleView

        cycleRunnable = Runnable {
            Handler(mainLooper).post { //DO YOUR THINGS
                ripple2!!.newRipple()
                taskHandler.postDelayed(cycleRunnable, 300)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        try {
        } catch (e: IllegalStateException) {
            Log.e(TAG, "onStop(): ", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //voiceRipple.onDestroy();
    }

    var taskHandler: Handler = NonLeakyHandler(this)


    private fun initializeControlPanel() {
        playButton = findViewById<View>(R.id.voice_play_button) as Button
        val handler = Handler(Looper.myLooper()!!)
        val run = Runnable {
            Handler(Looper.myLooper()!!).post {
                taskHandler.removeCallbacks(cycleRunnable)
            }
        }
        playButton!!.setOnClickListener {
            // if (directory != null && audioFile != null) {
            // player = new MediaPlayer();
            //if(!handler.post(run)){

            handler.postDelayed(run, 3000)
            //}
            taskHandler.postDelayed(cycleRunnable, 0)
            //  }
        }
    }

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private class NonLeakyHandler(activity: MainActivity) : Handler() {
        private val mActivity: WeakReference<MainActivity>
        override fun handleMessage(msg: Message) {
            val activity = mActivity.get()
            if (activity != null) {
                // ...
            }
        }

        init {
            mActivity = WeakReference(activity)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}