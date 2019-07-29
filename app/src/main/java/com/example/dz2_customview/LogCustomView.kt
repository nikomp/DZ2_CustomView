package com.example.dz2_customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


class LogoCustomView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val blackPaintStroke : Paint
    val blackPaint : Paint
    val hole : Paint
    val pathTooth: Path = Path()
    val rectTooth: RectF = RectF(0f,0f,60f,60f)
    val peTooth: PathDashPathEffect
    val porterDuffMode: PorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

    init {

        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        blackPaintStroke = Paint().apply {
            color = Color.BLACK
            strokeWidth = 6f
            style = Paint.Style.STROKE
        }

        blackPaint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 6f
        }

        hole = Paint().apply {
            color = Color.BLUE
            strokeWidth = 6f
            xfermode=porterDuffMode
        }

        /*pathTooth.lineTo(0f,30f)
        pathTooth.lineTo(30f,30f)
        pathTooth.lineTo(30f,0f)*/

        pathTooth.addRect(rectTooth, Path.Direction.CW)

        peTooth=PathDashPathEffect(pathTooth,140f,0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas?) {
        Log.d("myLogs","Рисуем Лого")

        if (canvas == null) return

        val midHeight = height / 2f
        val midWidth = width / 2f

        blackPaintStroke.pathEffect=peTooth
        canvas.drawCircle(midWidth, midHeight, 200f, blackPaintStroke)
        canvas.drawCircle(midWidth, midHeight, 170f, blackPaint)
        canvas.drawCircle(midWidth, midHeight, 100f, hole)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)


    }

}