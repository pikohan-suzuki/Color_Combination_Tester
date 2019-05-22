package com.amebaownd.pikohan_nwiatori.colorcombinationtester.View

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.R

class ColorSeekBar : View {
    private var max=100f
    private var uniqueColor= SeekBarColor.RED
    private var currentColor = Color.rgb(0,0,0)

    enum class SeekBarColor(val color:Int){
        RED(0),
        GREEN(1),
        BLUE(2)
    }

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }


    private fun initView(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorSeekBar)
        setMax(typedArray.getFloat(R.styleable.ColorSeekBar_max, 100f))
        this.setOnTouchListener(onTouchListener)
    }

    override fun onDraw(canvas: Canvas?) {

    }

    fun setMax(num:Float){max=num}

    private val onTouchListener= OnTouchListener{view:View,motionEvent:MotionEvent->

        when(motionEvent.action){
            MotionEvent.ACTION_DOWN->{
                return@OnTouchListener true
            }
            MotionEvent.ACTION_MOVE->{
                return@OnTouchListener true
            }
            MotionEvent.ACTION_UP->{
                return@OnTouchListener false
            }
        }
    }

    fun updateColor(color:Color){

    }

}