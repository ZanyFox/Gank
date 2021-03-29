package com.zf.jetpackmvvm.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.zf.jetpackmvvm.R


import kotlin.math.min

class ShapeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val mShaderMatrix = Matrix()
    private var isInitialized: Boolean = false
    private val mDrawableRect = RectF()
    private val mPaint = Paint()
    private lateinit var mBitmapShader: BitmapShader
    private var mBitmap: Bitmap? = null

    private lateinit var mBorderRectF: RectF
    private var mRadius: Float = 0f
    private var mBitmapWidth = 0
    private var mBitmapHeight = 0

    private var cornerRadius = 0
    private var shapeId: Int = 0

    private var mWidth = 0
    private var mHeight = 0

    private val TAG = "ShapeImageView"

    companion object {

        const val RECT_SHAPE = 0
        const val CIRCLE_SHAPE = 1
    }


    init {
//        super.setScaleType(ScaleType.CENTER_CROP)
        isInitialized = true
        // 父类构造函数执行完，子类构造函数执行之前调用
        mPaint.isAntiAlias = true

        val styleAttr =
            context.obtainStyledAttributes(attrs, R.styleable.ShapeImageView, defStyleAttr, 0)

        shapeId = styleAttr.getInt(R.styleable.ShapeImageView_shape, 0)
        cornerRadius =
            styleAttr.getDimension(R.styleable.ShapeImageView_cornerRadius, 0F).toInt()

        styleAttr.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        setup()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        setup()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        setup()
    }


    override fun onDraw(canvas: Canvas) {

        mBitmap ?: let { return }

        when (shapeId) {

            RECT_SHAPE -> {
                canvas.drawRoundRect(
                    mDrawableRect,
                    cornerRadius.toFloat(),
                    cornerRadius.toFloat(),
                    mPaint
                )

            }

            CIRCLE_SHAPE -> {
                canvas.drawCircle(
                    mDrawableRect.centerX(),
                    mDrawableRect.centerY(),
                    mRadius,
                    mPaint
                )
            }

        }

    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }


    private fun initializeBitmap() {

        mBitmap = getBitmapFromDrawable(drawable)

        setup()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {

        val bitmap: Bitmap = when (drawable) {
            null -> return null
            is BitmapDrawable -> return drawable.bitmap
            is ColorDrawable ->
                Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)
            else -> Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }

        return try {
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun calCircleBorder(): RectF {

        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingBottom - paddingTop

        val sideLength = min(availableWidth, availableHeight)

        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f
        return RectF(left, top, sideLength + left, sideLength + top)
    }

    private fun calRectBorder(): RectF {

        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingBottom - paddingTop

        val sideLength = min(availableWidth, availableHeight)

        val left = paddingLeft
        val top = paddingTop
        return RectF(
            left.toFloat(), top.toFloat(),
            (availableWidth + left).toFloat(), (availableHeight + top).toFloat()
        )

    }


    private fun setup() {

        // 由于父类的构造方法中会调用setImageDrawable
        // 子类的setImageDrawable方法会调用setup
        // 但是此时自定义View的属性还没有初始化
        // 所以不能让它执行下去
        if (!isInitialized)
            return

        if (width == 0 && height == 0) return

        mBitmap ?: let {
            invalidate()
            return
        }

        // 我这里犯了个错误，当自定义的View初始化时没有执行完父类构造函数，
        // 自定义View的属性没有初始化
        // 所以TAG并没有初始化，就会出现Log打印不出东西的情况
        // Log.d(TAG, "mPaint is null")

        mBitmapWidth = mBitmap!!.width
        mBitmapHeight = mBitmap!!.height
        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mPaint.shader = mBitmapShader

        when (shapeId) {

            CIRCLE_SHAPE -> {
                mBorderRectF = calCircleBorder()
                mRadius = min(mBorderRectF.width() / 2, mBorderRectF.height() / 2)
            }

            RECT_SHAPE -> {
                mBorderRectF = calRectBorder()
            }
        }
        mDrawableRect.set(mBorderRectF)
        updateShaderMatrix()
        invalidate()
    }


    // 图片缩放、移动算法
    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f
        mShaderMatrix.set(null)
        if (mBitmapWidth * mDrawableRect.height() > mBitmapHeight * mDrawableRect.width()) {
            scale = mDrawableRect.height() / mBitmapHeight
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / mBitmapWidth
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }

        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + mDrawableRect.left,
            (dy + 0.5).toInt() + mDrawableRect.top
        )
        mBitmapShader.setLocalMatrix(mShaderMatrix)

    }

    fun setShapeId(id: Int) {
        shapeId = id
    }

    fun setCornorRadius(radius: Int) {
        cornerRadius = radius
    }

    override fun getScaleType(): ScaleType {
        return ScaleType.FIT_CENTER
    }
}