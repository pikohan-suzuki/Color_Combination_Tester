package com.amebaownd.pikohan_nwiatori.colorcombinationtester.View

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.R
import java.lang.Math.pow
import java.lang.Math.sqrt
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class CircleSelector : View {
    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    //円の中心座標(%)
    private var centerX = 0f
    private var centerY = 0f
    //円の半径
    private var radius = 10f
    //円のラインの太さ
    private var strokeWidth = 5f
    //円とセレクタとテキストの色
    private var circleColor = Color.BLACK
    private var selectorColor = Color.WHITE
    private var textColor = Color.BLACK
    private var circlePaint = Paint()
    private var selectorPaint = Paint()
    private var textPaint = Paint()
    //360°当たりのデータ量
    private var max = 100f
    //データ０の位置(x/360°)
    private var startAngle = 0f
    //表示するデータ
    private var data = 50f

    private fun initView(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleSelector)
        setRadius(typedArray.getFloat(R.styleable.CircleSelector_radius, 30f))
        setStrokeWidth(typedArray.getFloat(R.styleable.CircleSelector_strokeWidth, 3f))
        setMax(typedArray.getFloat(R.styleable.CircleSelector_max, 100f))
        setStartAngle(typedArray.getFloat(R.styleable.CircleSelector_startAngle, 0f))
        setCircleColor(typedArray.getColor(R.styleable.CircleSelector_circleColor, Color.BLACK))
        setSelectorColor(typedArray.getColor(R.styleable.CircleSelector_selectorColor, Color.RED))
        setTextColor(typedArray.getColor(R.styleable.CircleSelector_textColor, Color.GREEN))
        circlePaint.style = Paint.Style.STROKE
        this.setOnTouchListener(onTouchListener)
    }

    override fun onDraw(canvas: Canvas?) {
//        var rect = rect

        canvas!!.drawArc(
            0f + strokeWidth,
            (height - width) / 2f + strokeWidth,
            width.toFloat() - strokeWidth,
            width.toFloat() + (height - width) / 2f - strokeWidth,
            startAngle,
            data * 360 / max,
            true,
            circlePaint
        ) //円の描画
        canvas.drawArc(
            0f + strokeWidth,
            (height - width) / 2f + strokeWidth,
            width.toFloat() - strokeWidth,
            width.toFloat() + (height - width) / 2f - strokeWidth,
            startAngle,
            360f,
            true,
            Paint().apply { this.color = Color.WHITE })
        canvas.drawCircle(
            (width / 2f + (width - strokeWidth) / 2f * sin(data / max * 2 * PI)).toFloat(),
            height / 2f - (width - strokeWidth) / 2f * cos(data / max * 2 * PI).toFloat(),
            strokeWidth * 2 / 3,
            selectorPaint
        )
    }

    fun update(data: Float) {
        if (data % max == 0f)
            setData(max)
        else
            setData(data % max)
        setCenterRange()
        invalidate()
    }

    fun setCenterRange() {
        centerX = width / 2f
        centerY = height / 2f
    }

    fun setRadius(r: Float) {
        radius = r
    }

    fun setStrokeWidth(width: Float) {
        strokeWidth = width
        circlePaint.strokeWidth = width
    }

    fun setCircleColor(color: Int) {
        circleColor = color
        circlePaint.color = color
    }

    fun setSelectorColor(color: Int) {
        selectorColor = color
        selectorPaint.color = color
    }

    fun setTextColor(color: Int) {
        textColor = color
        textPaint.color = color
    }

    fun setMax(num: Float) {
        max = num
    }

    fun setStartAngle(angle: Float) {
        startAngle = angle
    }

    fun setData(data: Float) {
        this.data = data
    }

    private val onTouchListener = View.OnTouchListener { view: View, motionEvent: MotionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN->{
                val x =motionEvent.x-width/2f
                val y = motionEvent.y-height/2f
                val touchWidth=strokeWidth*4
                Log.d("aaaaaaaaa","x:"+ x +"  y:"+y+"  "+sqrt(pow((width.toDouble()-strokeWidth)/2f,2.toDouble())-pow(x.toDouble(),2.toDouble()))+"    "+sqrt(pow(width.toDouble()/2f,2.toDouble())-pow(x.toDouble(),2.toDouble())))
                if(y>=sqrt(pow((width.toDouble()-touchWidth)/2f,2.toDouble())-pow(x.toDouble(),2.toDouble())) && y<= sqrt(pow(width.toDouble()/2f,2.toDouble())-pow(x.toDouble(),2.toDouble())) ||
                    y<=-sqrt(pow((width.toDouble()-touchWidth)/2f,2.toDouble())-pow(x.toDouble(),2.toDouble())) && y>= -sqrt(pow(width.toDouble()/2f,2.toDouble())-pow(x.toDouble(),2.toDouble()))){
                    update(0f)
                }
                return@OnTouchListener true
            }
            else->{
                return@OnTouchListener false
            }

        }

    }

}