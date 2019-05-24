package com.amebaownd.pikohan_nwiatori.colorcombinationtester.View

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.R
import java.util.*

interface ColorSeekBarChangeListener : EventListener {
  fun onRGBValueChanged(color: Int)
}

enum class SeekBarColor(val color: Int) {
  RED(0),
  GREEN(1),
  BLUE(2)
}

class RgbSeekBar : View {
  //データ上限
  private var max = 100f
  //管理する色R/G/B
  private var primaryColor = SeekBarColor.RED
  //現在の色
  private var currentColor = Color.RED
  //Bar描画用のPaint
  private var paint = Paint()
  //色変更時のイベントリスナー
  private var changeListener: ColorSeekBarChangeListener? = null

  constructor(context: Context) : super(context, null)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    initView(attrs)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    initView(attrs)
  }


  private fun initView(attrs: AttributeSet?) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RgbSeekBar)
    setMax(typedArray.getFloat(R.styleable.RgbSeekBar_max, 100f))
    setPrimaryColor(SeekBarColor.values()[typedArray.getInteger(R.styleable.RgbSeekBar_primary_color, 0)])
    this.setOnTouchListener(onTouchListener)
  }

  override fun onDraw(canvas: Canvas?) {
    var startColor = 0
    var endColor = 0
    var linePer = 0f
    when (primaryColor) {
      SeekBarColor.RED -> {
        startColor = Color.rgb(0, Color.green(currentColor), Color.blue(currentColor))
        endColor = Color.rgb(255, Color.green(currentColor), Color.blue(currentColor))
        linePer = Color.red(currentColor) / max
      }
      SeekBarColor.GREEN -> {
        startColor = Color.rgb(Color.red(currentColor), 0, Color.blue(currentColor))
        endColor = Color.rgb(Color.red(currentColor), 255, Color.blue(currentColor))
        linePer = Color.green(currentColor) / max
      }
      SeekBarColor.BLUE -> {
        startColor = Color.rgb(Color.red(currentColor), Color.green(currentColor), 0)
        endColor = Color.rgb(Color.red(currentColor), Color.green(currentColor), 255)
        linePer = Color.blue(currentColor) / max
      }
    }
    paint.shader =
      LinearGradient(0f, 0f, width.toFloat(), height.toFloat(), startColor, endColor, Shader.TileMode.CLAMP)
    canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    canvas.drawLine(
      width * linePer,
      0f,
      width * linePer,
      height / 2f,
      Paint().apply { this.color = Color.BLACK; this.strokeWidth = 10f })
    canvas.drawLine(
      width * linePer,
      height / 2f,
      width * linePer,
      height.toFloat(),
      Paint().apply { this.color = Color.WHITE; this.strokeWidth = 10f })
  }


  private val onTouchListener = OnTouchListener { view: View, motionEvent: MotionEvent ->
    when (motionEvent.action) {
      MotionEvent.ACTION_DOWN -> {
        var x = motionEvent.x
        if (x < 0)
          x = 0f
        else if (x > width)
          x = width.toFloat()
        when (primaryColor) {
          SeekBarColor.RED -> {
            currentColor = Color.rgb(
              (x * max / width).toInt(),
              Color.green(currentColor),
              Color.blue(currentColor)
            )
          }
          SeekBarColor.GREEN -> {
            currentColor = Color.rgb(
              Color.red(currentColor),
              (x * max / width).toInt(),
              Color.blue(currentColor)
            )
          }
          SeekBarColor.BLUE -> {
            currentColor = Color.rgb(
              Color.red(currentColor),
              Color.green(currentColor),
              (x * max / width).toInt()
            )
          }
        }
        updateColorFromMyself(currentColor)
        return@OnTouchListener true
      }
      MotionEvent.ACTION_MOVE -> {
        var x = motionEvent.x
        if (x < 0)
          x = 0f
        else if (x > width)
          x = width.toFloat()
        when (primaryColor) {
          SeekBarColor.RED -> {
            currentColor = Color.rgb(
              (x * max / width).toInt(),
              Color.green(currentColor),
              Color.blue(currentColor)
            )
          }
          SeekBarColor.GREEN -> {
            currentColor = Color.rgb(
              Color.red(currentColor),
              (x * max / width).toInt(),
              Color.blue(currentColor)
            )
          }
          SeekBarColor.BLUE -> {
            currentColor = Color.rgb(
              Color.red(currentColor),
              Color.green(currentColor),
              (x * max / width).toInt()
            )
          }
        }
        updateColorFromMyself(currentColor)
        return@OnTouchListener true
      }
      else ->
        return@OnTouchListener false
    }
  }


  private fun updateColorFromMyself(color: Int) {
    currentColor = color
    if (changeListener != null)
      changeListener!!.onRGBValueChanged(color)
    invalidate()
  }

  fun updateColorFromOther(color: Int) {
    currentColor = color
    invalidate()
  }

  fun setMax(num: Float) {
    max = num
  }

  fun setPrimaryColor(color: SeekBarColor) {
    primaryColor = color
  }

  fun setColorSeekBarChangeListener(listener: ColorSeekBarChangeListener) {
    this.changeListener = listener
  }

  fun getColor() = currentColor


}