package com.amebaownd.pikohan_nwiatori.colorcombinationtester

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.View.CircleSelector
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var circleSelector:CircleSelector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        circleSelector = findViewById<CircleSelector>(R.id.circle_selector)
        val rand = Random()

        findViewById<Button>(R.id.button2).setOnClickListener{
            circleSelector.update(rand.nextInt(100).toFloat())
        }
    }
}
