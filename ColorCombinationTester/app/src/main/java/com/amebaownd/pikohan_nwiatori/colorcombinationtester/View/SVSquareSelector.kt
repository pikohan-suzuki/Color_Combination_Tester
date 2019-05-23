package com.amebaownd.pikohan_nwiatori.colorcombinationtester.View

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.amebaownd.pikohan_nwiatori.colorcombinationtester.R

class SVSquareSelector : View {

    private var max=255f
    private var currentColor=0
    private var paint= Paint()
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
        this.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
//        this.setOnTouchListener(onTouchListener)
    }

    override fun onDraw(canvas: Canvas?) {
        var paint2 = Paint()
        var hsv = FloatArray(3)
        Color.RGBToHSV(Color.red(currentColor),Color.green(currentColor),Color.blue(currentColor),hsv)
        var startColor=Color.HSVToColor(hsv.apply { this[1]=0f;this[2]=1f })
        var endColor=Color.HSVToColor(hsv.apply { this[1]=1f;this[2]=1f })
        val Sshader = LinearGradient((width-height)/2f, 0f, width.toFloat()-(width-height)/2f, 0f, startColor, endColor, Shader.TileMode.CLAMP)
        startColor=Color.HSVToColor(hsv.apply { this[1]=0f;this[2]=0f })
        endColor=Color.HSVToColor(hsv.apply { this[1]=0f;this[2]=1f })
        val Vshader = LinearGradient((width-height)/2f, height.toFloat(), (width-height)/2f, 0f, startColor, endColor, Shader.TileMode.CLAMP
        )
        val shader = ComposeShader(Sshader,Vshader,PorterDuff.Mode.MULTIPLY)
        paint.shader=shader
        canvas!!.drawRect((width-height)/2f, 0f, width.toFloat()-(width-height)/2f, height.toFloat(), paint)
//        canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint2)
    }

    fun updateFromMyself(){

    }

    fun updateFromOther(color:Int){
        currentColor=color
        invalidate()
    }

    fun setMax(num:Float){this.max=num}
}