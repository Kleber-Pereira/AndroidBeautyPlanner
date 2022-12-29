package com.kleber.beautyplanner

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    private val mHandler = Handler(Looper.getMainLooper())
    private val mLauncher: Launcher = Launcher()
    override fun onStart() {
        super.onStart()
        mHandler.postDelayed(mLauncher, SPLASH_DELAY.toLong())
    }

    override fun onStop() {
        mHandler.removeCallbacks(mLauncher)
        super.onStop()
    }

    private fun launch() {
        if (!isFinishing) {
            startActivity(Intent(baseContext, MainActivity::class.java))
            finish()
        }
    }


    private inner class Launcher : Runnable {
        override fun run() {
            launch()
        }
    }

    companion object {
        private const val SPLASH_DELAY = 2000
        private const val TAG = "SplashActivity"
    }
}




