package com.example.dz2_customview

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
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
    val redPaint: Paint
    val textPaint: Paint

    val hole : Paint
    val porterDuffMode: PorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

    var rotate=0f

    val bitmapSource = BitmapFactory.decodeResource(resources,R.drawable.partnerlog)
    val bitmap : Bitmap = Bitmap.createBitmap(bitmapSource)

    val path = Path()

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
            //color = Color.BLACK
            color = Color.parseColor("#666666")
            strokeWidth = 6f
        }

        redPaint = Paint().apply {
            color = Color.RED
            strokeWidth = 6f
        }

        hole = Paint().apply {
            color = Color.BLUE
            strokeWidth = 6f
            xfermode=porterDuffMode
        }

        textPaint = Paint().apply {
            color = Color.parseColor("#666666") // 3366FF
            textSize=60f
            typeface= Typeface.DEFAULT_BOLD
        }

    }

    fun startAnimation() {
        Log.d("myLogs","startAnimation")
        val propertyRotateName="rotate"
        val propertyRotate=PropertyValuesHolder.ofFloat(propertyRotateName,0f,360f)
        val valueAnimator= ValueAnimator.ofPropertyValuesHolder(propertyRotate).apply {
            duration=4000
            addUpdateListener {
                rotate=it.getAnimatedValue(propertyRotateName) as Float
                invalidate()
                Log.d("myLogs","invalidate")
            }
            repeatMode=ValueAnimator.RESTART
            repeatCount=ValueAnimator.INFINITE
        }
        valueAnimator.start()
    }

    fun drawGear(canvas: Canvas, midWidth: Float, midHeight: Float) {
        val rectTooth2: RectF = RectF(midWidth-25,midHeight-125,midWidth+25,midHeight-70)

        canvas.drawCircle(midWidth, midHeight, 100f, blackPaint)
        canvas.drawRoundRect(rectTooth2,15f,15f,blackPaint)

        for (i in 1..7) {
            canvas.rotate(45f,midWidth,midHeight)
            canvas.drawRoundRect(rectTooth2,15f,15f,blackPaint)
        }

        canvas.drawCircle(midWidth, midHeight, 45f, hole) // Отверстие
    }

    override fun onDraw(canvas: Canvas?) {
        Log.d("myLogs","Рисуем Лого")

        if (canvas == null) return

        canvas.drawBitmap(bitmap,170f,200f,blackPaint)

        val midHeight = height / 2f
        val midWidth = width / 2f

        val rectOval: RectF = RectF(0f,0f,midWidth,300f)

        path.addOval(10f,midHeight-400f, width.toFloat()-30f,midHeight,Path.Direction.CCW)

        //canvas.drawPath(path,blackPaintStroke)
        canvas.drawTextOnPath("Управляйте своими достижениями",path,1250f,70f,textPaint)

        //canvas.drawPoint(midWidth, midHeight,redPaint) // Центр

        Log.d("myLogs",rotate.toString())
        canvas.rotate(rotate,midWidth+130, midHeight-115)

        drawGear(canvas, midWidth+130, midHeight-115)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

}