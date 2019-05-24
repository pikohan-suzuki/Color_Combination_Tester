package com.amebaownd.pikohan_nwiatori.colorcombinationtester.View

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.R
import java.lang.Math.pow
import java.util.*
import kotlin.math.*
interface CircleSelectorChangeListener : EventListener {
    fun onHueValueChanged(color: Int)
}
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
    private var max = 360f
    //データ０の位置(x/360°)
    private var startAngle = 0f
    //表示するデータ
    private var data = 50f
    //ホールド中か否か
    private var isHold = false

    private var listener : CircleSelectorChangeListener?=null

    private var currentColor=Color.RED

    private var longSide=0
    private var shortSide=0
    private fun initView(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleSelector)
        setRadius(typedArray.getFloat(R.styleable.CircleSelector_radius, 30f))
        setStrokeWidth(typedArray.getFloat(R.styleable.CircleSelector_strokeWidth, 3f))
        setMax(typedArray.getFloat(R.styleable.CircleSelector_max_circle, 100f))
        setStartAngle(typedArray.getFloat(R.styleable.CircleSelector_startAngle, 0f))
        setCircleColor(typedArray.getColor(R.styleable.CircleSelector_circleColor, Color.BLACK))
        setSelectorColor(typedArray.getColor(R.styleable.CircleSelector_selectorColor, Color.RED))
        setTextColor(typedArray.getColor(R.styleable.CircleSelector_textColor, Color.GREEN))
        circlePaint.style = Paint.Style.STROKE
        textPaint.textSize=100f
        this.setOnTouchListener(onTouchListener)
    }

    override fun onDraw(canvas: Canvas?) {
        var rectF = RectF()
        if(width<=height){
            rectF.left=0f+strokeWidth
            rectF.top=(height-width)/2f+strokeWidth
            rectF.right=width.toFloat()-strokeWidth
            rectF.bottom=width.toFloat()+(height-width)/2f-strokeWidth

        }else{
            rectF.left=(width-height)/2f+strokeWidth
            rectF.top=0f+strokeWidth
            rectF.right=(width+height)/2f-strokeWidth
            rectF.bottom=height-strokeWidth
        }
        val gradientColors = IntArray(31)
        for(i in 0..360 step 12)
            gradientColors[i/12]=Color.HSVToColor(floatArrayOf(((i+360-startAngle)%360).toFloat(),1f,1f))
        val sweepGradient = SweepGradient(width/2f,height/2f, gradientColors,null)
        circlePaint.shader=sweepGradient
        canvas!!.drawArc(rectF, startAngle,360f, false, circlePaint) //円の描画
        canvas.drawArc(rectF.left + strokeWidth, rectF.top + strokeWidth, rectF.right - strokeWidth,
            rectF.bottom - strokeWidth, startAngle, 360f, true, Paint().apply { this.color = Color.WHITE })
        canvas.drawCircle((width / 2f + (rectF.right-width/2f) * cos(2*PI-(data/max*2*PI)+(360-startAngle)*PI/180)).toFloat(), height / 2f - (rectF.right-width/2f)  * sin(2*PI-(data/max*2*PI)+(360-startAngle)*PI/180).toFloat(),
            strokeWidth * 2 / 3, selectorPaint)

        val touchWidth = strokeWidth * 1.5f
        for (i in 0 until width) {
            val x = i - width / 2f
            val y1 = sqrt(pow(((rectF.right - (width)/2f-touchWidth)).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble()))
            val y2 = sqrt(pow(((rectF.right+touchWidth) -width/2f).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble()))
            val y3 =  -sqrt(pow(((rectF.right - (width)/2f-touchWidth) ).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble()))
            val y4 = -sqrt(pow(((rectF.right+touchWidth) -width/2f).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble()))
            Log.d("aaaaaaaaaa",""+y1+"     "+y2+"        "+y3+"       "+y4)
            canvas.drawPoint(x + width / 2f, y1.toFloat() + height / 2f, Paint().apply { this.color = Color.BLUE })
            canvas.drawPoint(x + width / 2f, y2.toFloat() + height / 2f, Paint().apply { this.color = Color.BLUE })
            canvas.drawPoint(x + width / 2f, y3.toFloat() + height / 2f, Paint().apply { this.color = Color.BLUE })
            canvas.drawPoint(x + width / 2f, y4.toFloat() + height / 2f, Paint().apply { this.color = Color.BLUE })
            canvas.drawText(data.toInt().toString(),width/2f-50*data.toInt().toString().length/2f,height/2f+50,textPaint)
        }
    }

    private fun updateFromMyself(data: Float) {
        var hsv = FloatArray(3)
        Color.RGBToHSV(Color.red(currentColor),Color.green(currentColor),Color.blue(currentColor),hsv)
        if (data % max == 0f) {
            setData(max)
            currentColor=Color.HSVToColor(hsv.apply { this[0]=max })
        }
        else {
            setData(data % max)
            currentColor = Color.HSVToColor(hsv.apply { this[0] = data % max })
        }
        setCenterRange()
        invalidate()
        if(listener!=null)
            listener!!.onHueValueChanged(currentColor)
    }

    fun updateFromOther(color:Int){
        currentColor=color
        var hsv = FloatArray(3)
        Color.RGBToHSV(Color.red(currentColor),Color.green(currentColor),Color.blue(currentColor),hsv)
        data=hsv[0]
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

    fun setCircleSelectorChangeListener(listener: CircleSelectorChangeListener){
        this.listener=listener
    }

    private val onTouchListener = View.OnTouchListener { view: View, motionEvent: MotionEvent ->
        var rectF = RectF()
        if(width<=height){
            rectF.left=0f+strokeWidth
            rectF.top=(height-width)/2f+strokeWidth
            rectF.right=width.toFloat()-strokeWidth
            rectF.bottom=width.toFloat()+(height-width)/2f-strokeWidth

        }else{
            rectF.left=(width-height)/2f+strokeWidth
            rectF.top=0f+strokeWidth
            rectF.right=(width+height)/2f-strokeWidth
            rectF.bottom=height-strokeWidth
        }
        when (motionEvent.action) {

            MotionEvent.ACTION_DOWN -> {
                Log.d("MOTION_EVENT","ACTION_DOWN")
                val x = motionEvent.x - width / 2f
                val y = motionEvent.y - height / 2f
                val touchWidth = strokeWidth * 1.5f
//                Log.d("ccccccc","x:"+x+"  y:"+y)
//                Log.d("ccccccc",""+sqrt(pow(((rectF.right - width/2f)).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble()))+"       "+sqrt(pow(((rectF.right+strokeWidth) -width/2f).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble())))
//                Log.d("ccccccc",""+-sqrt(pow(((rectF.right - width/2f) ).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble()))+"       "+-sqrt(pow(((rectF.right+strokeWidth) -width/2f).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble())))
                if (!(y < sqrt(pow(((rectF.right - (width)/2f-touchWidth)).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble())))
                    && y <= sqrt(pow(((rectF.right+touchWidth) -width/2f).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble()))
                    || y <= -sqrt(pow(((rectF.right - (width)/2f-touchWidth) ).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble()))
                    && !(y < -sqrt(pow(((rectF.right+touchWidth) -width/2f).toDouble(), 2.toDouble()) - pow(x.toDouble(), 2.toDouble())))) {
                    var rad:Double=0.toDouble()
                    if(x>=0)
                        rad = atan((-y / x).toDouble())
                    else
                        rad= PI+atan((-y / x).toDouble())
                    var angle = 0.toDouble()
                    if (x >= 0 && y < 0)
                        angle = 90 - rad * 360 / PI / 2
                    else if (x >= 0 && y >= 0)
                        angle = 90 - rad * 360 / PI / 2
                    else if (x <= 0 && y > 0)
                        angle = 180 + 90 - rad * 360 / PI / 2
                    else if (x < 0f && y <= 0)
                        angle = 180 + 90 - rad * 360 / PI / 2
//                    update((angle * max / 180-(startAngle)*PI/180).toFloat())
                    updateFromMyself((((2*PI-rad+(360-startAngle)*PI/180))*max/2/PI).toFloat())
                    isHold = true
                }
                return@OnTouchListener true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("MOTION_EVENT","ACTION_MOVE")
                val x = motionEvent.x - width / 2f
                val y = motionEvent.y - height / 2f
                var rad = 0f
                if (isHold) {
                    if (y <= 0) {
                        rad =acos(x/sqrt(pow(x.toDouble(),2.toDouble())+pow(y.toDouble(),2.toDouble()))).toFloat()
                    } else {
                        rad = (2* PI - acos(x / sqrt(pow(x.toDouble(), 2.toDouble()) + pow(y.toDouble(), 2.toDouble())))).toFloat()
                    }
                    updateFromMyself((((2*PI-rad+(360-startAngle)*PI/180))*max/2/PI).toFloat())
                }

                return@OnTouchListener true
            }
            MotionEvent.ACTION_UP -> {
                Log.d("MOTION_EVENT","ACTION_UP")
                isHold = false
                return@OnTouchListener false
            }
            else -> {
                return@OnTouchListener true
            }
        }
    }

}