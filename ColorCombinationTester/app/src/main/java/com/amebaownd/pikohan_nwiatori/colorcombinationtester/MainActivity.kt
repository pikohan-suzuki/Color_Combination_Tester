package com.amebaownd.pikohan_nwiatori.colorcombinationtester

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.View.CircleSelector
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.View.RgbSeekBar
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.View.ColorSeekBarChangeListener
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.View.SVSquareSelector
import java.util.*

class MainActivity : AppCompatActivity() , ColorSeekBarChangeListener {
    override fun onValueChanged(color:Int) {
        redRgbSeekBar.updateColorFromOther(color)
        greenRgbSeekBar.updateColorFromOther(color)
        blueRgbSeekBar.updateColorFromOther(color)
        svSelector.updateFromOther(color)
        rgbTextView.text="R: "+ Color.red(color)+" G: "+Color.green(color) + " B: "+Color.blue(color)
    }

    lateinit var circleSelector:CircleSelector
    lateinit var redRgbSeekBar: RgbSeekBar
    lateinit var greenRgbSeekBar: RgbSeekBar
    lateinit var blueRgbSeekBar: RgbSeekBar
    lateinit var svSelector:SVSquareSelector
    lateinit var rgbTextView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        circleSelector = findViewById<CircleSelector>(R.id.circle_selector)
        redRgbSeekBar=findViewById(R.id.red_colorSeekBar)
        greenRgbSeekBar=findViewById(R.id.green_colorSeekBar)
        blueRgbSeekBar=findViewById(R.id.blue_colorSeekBar)
        rgbTextView=findViewById(R.id.rgb)
        svSelector=findViewById(R.id.hsv_selector)

        redRgbSeekBar.setColorSeekBarChangeListener(this)
        greenRgbSeekBar.setColorSeekBarChangeListener(this)
        blueRgbSeekBar.setColorSeekBarChangeListener(this)

        val rand = Random()

        findViewById<Button>(R.id.button2).setOnClickListener{
            circleSelector.update(rand.nextInt(100).toFloat())
            val aaa =rand.nextInt(16581375)
            redRgbSeekBar.updateColorFromMyself(aaa)
            greenRgbSeekBar.updateColorFromMyself(aaa)
            blueRgbSeekBar.updateColorFromMyself(aaa)
            rgbTextView.text="R: "+ Color.red(aaa)+" G: "+Color.green(aaa) + " B: "+Color.blue(aaa)
        }

        findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(seekBarChangeListener(circleSelector))
    }

    private class seekBarChangeListener(private val circleSelector: CircleSelector):SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            circleSelector.update(progress.toFloat())
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }
}
