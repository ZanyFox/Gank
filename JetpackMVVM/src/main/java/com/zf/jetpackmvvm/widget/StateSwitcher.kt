package com.zf.jetpackmvvm.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.zf.jetpackmvvm.R


/**
 *
 * 页面状态切换类
 */

class StateSwitcher @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val tag = StateSwitcher::class.simpleName

    private val layoutInflater by lazy { LayoutInflater.from(context) }

    private var loadingLayoutId: Int = 0
    private var errorLayoutId: Int = 0
    private var emptyLayoutId: Int = 0


    private var defaultLoadingLayoutId = R.layout.default_loading_state_layout
    private var defaultErrorLayoutId = R.layout.default_error_state_layout
    private var defaultEmptyLayoutId = R.layout.default_empty_layout

    private var loadingLayout: View? = null
    private var errorLayout: View? = null
    private var emptyLayout: View? = null

    private var currentView: View? = null
    private var contentView: View? = null

    private var initLoadingLayout: ((View?) -> Unit)? = null
    private var initErrorLayout: ((View?) -> Unit)? = null
    private var initEmptyLayout: ((View?) -> Unit)? = null

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.StateSwitcher)
        loadingLayoutId = typeArray.getResourceId(
            R.styleable.StateSwitcher_loading_layout,
            defaultLoadingLayoutId
        )
        errorLayoutId =
            typeArray.getResourceId(
                R.styleable.StateSwitcher_network_error_layout,
                defaultErrorLayoutId
            )
        emptyLayoutId =
            typeArray.getResourceId(R.styleable.StateSwitcher_empty_layout, defaultEmptyLayoutId)

//        loadingLayout = layoutInflater.inflate(loadingLayoutId, null)
//
//        errorLayout = layoutInflater.inflate(errorLayoutId, null)
//        emptyLayout = layoutInflater.inflate(emptyLayoutId, null)

        // 重用 TypedArray
        typeArray.recycle()
    }

    fun setLoadingLayout(loadingLayout: View) {
        this.loadingLayout = loadingLayout
    }


    fun setLoadingLayout(@LayoutRes loadingLayoutId: Int) {
        this.loadingLayout = layoutInflater.inflate(loadingLayoutId, null, false)
    }

    fun setErrorLayout(errorLayout: View) {
        this.errorLayout = errorLayout
    }

    fun setEmptyLayout(emptyLayout: View) {
        this.emptyLayout = emptyLayout
    }

    fun initLoadingLayout(@LayoutRes id: Int = -1, initBlock: (View) -> Unit) {

        val layoutId = if (-1 == id) loadingLayoutId else id
        loadingLayout = loadingLayout ?: layoutInflater.inflate(layoutId, null)
        initBlock(this.loadingLayout!!)
    }

    fun initErrorLayout(@LayoutRes id: Int = -1, initBlock: (View) -> Unit) {

        val layoutId = if (-1 == id) errorLayoutId else id
        errorLayout = errorLayout ?: layoutInflater.inflate(layoutId, null)
        initBlock(this.errorLayout!!)
    }

    fun initEmptyLayout(@LayoutRes id: Int = -1, initBlock: (View) -> Unit) {

        val layoutId = if (-1 == id) emptyLayoutId else id
        emptyLayout = layoutInflater.inflate(layoutId, null)
        initBlock(this.emptyLayout!!)
    }

    fun switchState(viewState: ViewState, params: ViewGroup.LayoutParams? = null) {

        when (viewState) {
            ViewState.LOADING -> replaceLoadingState(params)
            ViewState.EMPTY -> replaceEmptyState(params)
            ViewState.ERROR -> replaceErrorState(params)
            ViewState.SUCCESS -> replaceNormalState(params)
            else -> {
                Log.d(tag, "没有定义该状态")
            }
        }
    }

    private fun replaceNormalState(params: ViewGroup.LayoutParams? = null) {
//        contentView ?: let { throw Exception("没有原始视图") }
        contentView?.let { replaceLayout(it, params) }
    }

    private fun replaceLoadingState(params: ViewGroup.LayoutParams? = null) {
        loadingLayout = loadingLayout ?: layoutInflater.inflate(loadingLayoutId, null)
        replaceLayout(loadingLayout!!, params)
    }

    private fun replaceEmptyState(params: ViewGroup.LayoutParams? = null) {
        emptyLayout = emptyLayout ?: layoutInflater.inflate(emptyLayoutId, null)
        replaceLayout(emptyLayout!!, params)
    }

    private fun replaceErrorState(params: ViewGroup.LayoutParams? = null) {
        emptyLayout = errorLayout ?: layoutInflater.inflate(errorLayoutId, null)
        replaceLayout(emptyLayout!!, params)
    }

    private fun replaceLayout(view: View, params: ViewGroup.LayoutParams? = null) {
        if (view == currentView)
            return
        currentView = getChildAt(0)
        contentView = contentView ?: currentView
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        removeAllViews()
        addView(view, layoutParams)
        currentView = view
    }

    enum class ViewState {
        SUCCESS,
        ERROR,
        EMPTY,
        LOADING
    }
}
