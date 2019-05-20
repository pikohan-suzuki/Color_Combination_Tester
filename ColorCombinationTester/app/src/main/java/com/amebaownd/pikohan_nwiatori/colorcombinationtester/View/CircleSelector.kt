package com.amebaownd.pikohan_nwiatori.colorcombinationtester.View

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.R

class CircleSelector: View {
    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    //円の中心座標(%)
    private var centerX= 0f
    private var centerY =0f
    //円の半径
    private var radius = 10f
    //円のラインの太さ
    private var strokeWidth=5f
    //円とセレクタとテキストの色
    private var circleColor = Color.BLACK
    private var selectorColor = Color.WHITE
    private var textColor = Color.BLACK
    private var circlePaint = Paint()
    private var selectorPaint= Paint()
    private var textPaint= Paint()
    //360°当たりのデータ量
    private var max =100f
    //データ０の位置(x/360°)
    private var startAngle =0f
    //表示するデータ
    private var data =0f

    private fun initView(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleSelector)
        setRadius(typedArray.getFloat(R.styleable.CircleSelector_radius,30f))
        setStrokeWidth(typedArray.getFloat(R.styleable.CircleSelector_strokeWidth,3f))
        setMax(typedArray.getFloat(R.styleable.CircleSelector_max,100f))
        setStartAngle(typedArray.getFloat(R.styleable.CircleSelector_startAngle,0f))
        setCircleColor(typedArray.getColor(R.styleable.CircleSelector_circleColor, Color.BLACK))
        setSelectorColor(typedArray.getColor(R.styleable.CircleSelector_selectorColor, Color.RED))
        setTextColor(typedArray.getColor(R.styleable.CircleSelector_textColor, Color.GREEN))
        circlePaint.style= Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
//        var rect = rect
        canvas!!.drawArc(0f+strokeWidth,(height-width)/2f+strokeWidth,width.toFloat()-strokeWidth,width.toFloat()+(height-width)/2f-strokeWidth,startAngle,data*360/max,true,circlePaint) //円の描画
        canvas.drawArc(0f+strokeWidth,(height-width)/2f+strokeWidth,width.toFloat()-strokeWidth,width.toFloat()+(height-width)/2f-strokeWidth,startAngle,360f,true,Paint().apply { this.color=Color.WHITE})
//        canvas.drawRect()
    }

    fun update(data:Float){
        setData(data%max)
        setCenterRange(0f,0f)
        invalidate()
    }




    fun setCenterRange(x:Float,y:Float){
        centerX=width/2f
        centerY=height/2f
    }
    fun setRadius(r:Float){ radius=r }
    fun setStrokeWidth(width:Float){
        strokeWidth=width
        circlePaint.strokeWidth=width
    }
    fun setCircleColor(color:Int){
        circleColor=color
        circlePaint.color=color
    }
    fun setSelectorColor(color:Int){
        selectorColor=color
        selectorPaint.color=color
    }
    fun setTextColor(color:Int){
        textColor=color
        textPaint.color=color
    }
    fun setMax(num:Float){max=num}
    fun setStartAngle(angle:Float){startAngle=angle}
    fun setData(data:Float){this.data=data}
}