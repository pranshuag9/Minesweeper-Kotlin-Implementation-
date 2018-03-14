package com.example.vasudev.minesweeper.SplashActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vasudev.minesweeper.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashAnimationView.playAnimation()
        atTextView.visibility= View.VISIBLE
        logoImageView.visibility=View.VISIBLE
        quoteTextView.visibility=View.VISIBLE

    }
}
