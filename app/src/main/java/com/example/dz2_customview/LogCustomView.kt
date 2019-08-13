package com.example.dz2_customview

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button


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

    var leftLogo=0f
    var topLogo=0f
    var rightLogo=0f
    var bottomLogo=0f

    var midHeight = 0f
    var midWidth = 0f

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
        val propertyRotateName="rotate"
        val propertyRotate=PropertyValuesHolder.ofFloat(propertyRotateName,0f,360f)
        val valueAnimator= ValueAnimator.ofPropertyValuesHolder(propertyRotate).apply {
            duration=4000
            addUpdateListener {
                rotate=it.getAnimatedValue(propertyRotateName) as Float
                invalidate()
            }
            repeatMode=ValueAnimator.RESTART
            repeatCount=ValueAnimator.INFINITE
        }
        valueAnimator.start()
    }

    fun drawGear(canvas: Canvas, cx: Float, cy: Float) {
        val rectTooth2: RectF = RectF(cx-25,cy-125,cx+25,cy-70)

        canvas.drawCircle(cx, cy, 100f, blackPaint)
        canvas.drawRoundRect(rectTooth2,15f,15f,blackPaint)

        for (i in 1..7) {
            canvas.rotate(45f,cx,cy)
            canvas.drawRoundRect(rectTooth2,15f,15f,blackPaint)
        }

        canvas.drawCircle(cx, cy, 45f, hole) // Отверстие
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return

        val rectLogo: RectF = RectF(leftLogo,topLogo,rightLogo,bottomLogo)

        canvas.drawBitmap(bitmap,leftLogo+170f,topLogo-60f,blackPaint)

        path.addOval(rectLogo,Path.Direction.CCW)

        canvas.drawTextOnPath("Управляйте своими достижениями",path,1555f,70f,textPaint)

        canvas.rotate(rotate,rightLogo-370f, bottomLogo-230f)
        drawGear(canvas, rightLogo-370f, bottomLogo-230f) //130,115


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("myLogs","onMeasure")


        //val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        //val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val midHeight = heightSize / 2f
        val midWidth = widthSize / 2f



        //Центр
        //canvas.drawPoint(midWidth, midHeight, redPaint)
        // Прямоугольник с лого
        leftLogo=midWidth-530f
        topLogo=midHeight-400f
        rightLogo=midWidth+529f
        bottomLogo=midHeight+272f

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


}