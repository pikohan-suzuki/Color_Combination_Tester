package com.amebaownd.pikohan_nwiatori.colorcombinationtester.View

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.R
import java.util.*

interface SVSquareSelectorChangeListener : EventListener {
  fun onSVValueChanged(color: Int)
}

class SVSquareSelector : View {

  private var max = 255f
  private var currentColor = Color.RED
  private var paint = Paint()
  private var listener: SVSquareSelectorChangeListener? = null

  private var startX = 0f
  private var startY = 0f
  private var endX = 0f
  private var endY = 0f

  constructor(context: Context) : super(context, null)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    initView(attrs)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    initView(attrs)
  }

  private fun initView(attrs: AttributeSet?) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SVSquareSelector)
    setMax(typedArray.getFloat(R.styleable.SVSquareSelector_accuracy, 100f))
    this.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    this.setOnTouchListener(onTouchListener)
  }

  override fun onDraw(canvas: Canvas?) {
    startX = (width - height) / 2f
    endX = width.toFloat() - (width - height) / 2f
    val hsv = FloatArray(3)
    Color.RGBToHSV(Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor), hsv)
    var startColor = Color.HSVToColor(hsv.apply { this[1] = 0f;this[2] = 1f })
    var endColor = Color.HSVToColor(hsv.apply { this[1] = 1f;this[2] = 1f })
    val Sshader = LinearGradient(startX, 0f, endX, 0f, startColor, endColor, Shader.TileMode.CLAMP)
    startColor = Color.HSVToColor(hsv.apply { this[1] = 0f;this[2] = 0f })
    endColor = Color.HSVToColor(hsv.apply { this[1] = 0f;this[2] = 1f })
    val Vshader = LinearGradient(startX, height.toFloat(), startX, 0f, startColor, endColor, Shader.TileMode.CLAMP)
    val shader = ComposeShader(Sshader, Vshader, PorterDuff.Mode.MULTIPLY)
    paint.shader = shader
    canvas!!.drawRect(startX, 0f, endX, height.toFloat(), paint)
    Color.RGBToHSV(Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor), hsv)
    canvas.drawCircle(startX + height * hsv[1], height * (1 - hsv[2]), 15f, Paint().apply { this.color = Color.WHITE })
    canvas.drawCircle(startX + height * hsv[1], height * (1 - hsv[2]), 10f, Paint().apply { this.color = Color.BLACK })
  }

  private val onTouchListener = OnTouchListener { view: View, motionEvent: MotionEvent ->
    when (motionEvent.action) {
      MotionEvent.ACTION_DOWN -> {
        var x = motionEvent.x
        var y = motionEvent.y
        if (x < 0)
          x = 0f
        else if (x > width)
          x = width.toFloat()
        if(y<0)
          y=0f
        else if(y>height)
          y=height.toFloat()
        val hsv = FloatArray(3)
        Color.RGBToHSV(Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor), hsv)
        hsv[1] = (x - startX) / height
        hsv[2] = 1 - (y / height)
        updateFromMyself(Color.HSVToColor(hsv))

        return@OnTouchListener true
      }
      MotionEvent.ACTION_MOVE -> {
        var x = motionEvent.x
        var y = motionEvent.y

        Log.d("wwwwww",x.toString())
        if (x < 0)
          x = 0f
        else if (x > width)
          x=width.toFloat()
        if(y<0)
          y=0f
        else if(y>height)
          y=height.toFloat()
        Log.d("wwwwww",x.toString())
        val hsv = FloatArray(3)
        Color.RGBToHSV(Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor), hsv)
        hsv[1] = (x - startX) / height
        hsv[2] = 1 - (y / height)
        updateFromMyself(Color.HSVToColor(hsv))

        return@OnTouchListener true
      }
      else ->
        return@OnTouchListener false
    }
  }


  fun updateFromMyself(color: Int) {
    currentColor = color
    invalidate()
    if (listener != null)
      listener!!.onSVValueChanged(currentColor)
  }

  fun updateFromOther(color: Int) {
    currentColor = color
    invalidate()
  }

  fun setSVSquareSelectorChangeListener(listener: SVSquareSelectorChangeListener) {
    this.listener = listener
  }

  fun setMax(num: Float) {
    this.max = num
  }
}