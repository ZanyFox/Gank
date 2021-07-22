package com.zf.jetpackmvvm.widget.easyrecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class EasyRecyclerView : RecyclerView {


    private val mHeaderViewList = arrayListOf<View>()
    private val mFooterViewList = arrayListOf<View>()


    internal var mAdapterWrapper: AdapterWrapper? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )


    fun addItemDivider(@DrawableRes id: Int) {
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(context, id)?.let { divider.setDrawable(it) }
        super.addItemDecoration(divider)
    }

    override fun addItemDecoration(decor: ItemDecoration, index: Int) {
        super.addItemDecoration(decor, index)

    }

    /*
        * 没有添加监听
        * */
    override fun setAdapter(adapter: Adapter<*>?) {


        mAdapterWrapper?.getOriginAdapter()?.unregisterAdapterDataObserver(mAdapterDataObserver)
        adapter ?: let { mAdapterWrapper = null }
        adapter?.let {
            adapter.registerAdapterDataObserver(mAdapterDataObserver)
            mAdapterWrapper = AdapterWrapper(context, adapter as Adapter<ViewHolder>)

            if (mHeaderViewList.size > 0)
                mHeaderViewList.forEach { mAdapterWrapper!!.addHeaderView(it) }
            if (mFooterViewList.size > 0)
                mFooterViewList.forEach { mAdapterWrapper!!.addFooterView(it) }

            super.setAdapter(mAdapterWrapper)
        }
    }


    /*
    * 观察者
    *
    * adapter 通知  mAdapterDataObserver
    * 最后再由mAdapterWrapper 通知更新
    * */
    private val mAdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            mAdapterWrapper?.notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mAdapterWrapper?.notifyItemRangeRemoved(
                positionStart + (mAdapterWrapper?.getHeaderCount() ?: 0), itemCount
            )
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mAdapterWrapper?.notifyItemMoved(
                fromPosition + (mAdapterWrapper?.getHeaderCount() ?: 0),
                toPosition + (mAdapterWrapper?.getHeaderCount() ?: 0)
            )
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mAdapterWrapper?.notifyItemRangeInserted(
                positionStart + (mAdapterWrapper?.getHeaderCount() ?: 0),
                itemCount
            )
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mAdapterWrapper?.notifyItemRangeChanged(
                positionStart + (mAdapterWrapper?.getHeaderCount() ?: 0), itemCount
            )
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mAdapterWrapper?.notifyItemRangeChanged(
                positionStart + (mAdapterWrapper?.getHeaderCount() ?: 0),
                itemCount,
                payload
            )
        }
    }


    fun addHeaderView(view: View) {

        mHeaderViewList.add(view)
        mAdapterWrapper?.addHeaderAndNotify(view)
    }

    fun removeHeaderView(view: View?) {

        mHeaderViewList.remove(view)
        mAdapterWrapper?.removeHeaderViewAndNotify(view)
    }

    fun addFooterView(view: View) {
        mFooterViewList.add(view)
        mAdapterWrapper?.addFooterViewAndNotify(view)
    }

    fun removeFooterView(view: View?) {
        mFooterViewList.remove(view)
        mAdapterWrapper?.removeFooterViewAndNotify(view!!)
    }

    fun getHeaderViewCount() = mAdapterWrapper?.getHeaderCount() ?: 0
    fun getItemViewType(position: Int) = mAdapterWrapper?.getItemViewType(position)


    private var mScrollState = -1
    private var isLoadMore = false
    private var isAutoLoadMore = true
    private var loadMoreView: View? = null
    private var loadMoreListener: LoadMoreListener? = null


    fun setLoadMoreListener(listener: LoadMoreListener) {
        this.loadMoreListener = listener
    }

    override fun onScrollStateChanged(state: Int) {
        mScrollState = state
    }


    override fun onScrolled(dx: Int, dy: Int) {

        if (layoutManager is LinearLayoutManager?) {
            val manager = layoutManager as LinearLayoutManager
            val itemCount = manager.itemCount
            val lastVisiblePosition = manager.findLastVisibleItemPosition()

            // 当前页面可见的最后一项是全部数据的最后一项  并且  当前状态为用户拖动或者惯性滑动时
            if (lastVisiblePosition + 1 == itemCount && (mScrollState == SCROLL_STATE_DRAGGING || mScrollState == SCROLL_STATE_SETTLING)) {
                dispatchLoadMore()
            }
        }
    }


    private fun dispatchLoadMore() {


        if (isLoadMore) return
        isLoadMore = true
        loadMoreListener?.preLoading()
        loadMoreListener?.onLoading()

    }

/*
* 当加载完成时需要调用这个方法
*
* */
    fun loadMoreFinish() {

        isLoadMore = false
        loadMoreListener?.onLoadFinish()
    }

    public interface LoadMoreListener {

        fun preLoading(){}
        fun onLoading(){}
        fun onLoadFinish(){}
    }

}