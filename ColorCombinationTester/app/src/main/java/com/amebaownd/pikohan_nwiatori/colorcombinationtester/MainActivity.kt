package com.amebaownd.pikohan_nwiatori.colorcombinationtester

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.View.*
import java.util.*

class MainActivity : AppCompatActivity() , ColorSeekBarChangeListener ,SVSquareSelectorChangeListener,CircleSelectorChangeListener{
    override fun onHueValueChanged(color: Int) {
        redRgbSeekBar.updateColorFromOther(color)
        greenRgbSeekBar.updateColorFromOther(color)
        blueRgbSeekBar.updateColorFromOther(color)
        svSelector.updateFromOther(color)
        rgbTextView.text="R: "+ Color.red(color)+" G: "+Color.green(color) + " B: "+Color.blue(color)
    }

    override fun onSVValueChanged(color: Int) {
        redRgbSeekBar.updateColorFromOther(color)
        greenRgbSeekBar.updateColorFromOther(color)
        blueRgbSeekBar.updateColorFromOther(color)
        circleSelector.updateFromOther(color)
        rgbTextView.text="R: "+ Color.red(color)+" G: "+Color.green(color) + " B: "+Color.blue(color)
    }

    override fun onRGBValueChanged(color:Int) {
        redRgbSeekBar.updateColorFromOther(color)
        greenRgbSeekBar.updateColorFromOther(color)
        blueRgbSeekBar.updateColorFromOther(color)
        svSelector.updateFromOther(color)
        circleSelector.updateFromOther(color)
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
        svSelector.setSVSquareSelectorChangeListener(this)
        circleSelector.setCircleSelectorChangeListener(this)
    }


}
