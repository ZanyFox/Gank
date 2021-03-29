package com.zf.gank.view.fragment

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.zf.gank.R
import com.zf.gank.databinding.BannerViewBinding
import com.zf.gank.databinding.FragmentRecommendLayoutBinding
import com.zf.gank.ext.observe
import com.zf.gank.ext.renderList
import com.zf.gank.ext.renderState
import com.zf.gank.pojo.Article
import com.zf.gank.pojo.Banner
import com.zf.gank.state.ListViewState
import com.zf.gank.state.ViewState
import com.zf.gank.view.adapter.ArticleAdapter
import com.zf.gank.vm.RecommendViewModel
import com.zf.jetpackmvvm.base.fragment.BaseVMFragment
import com.zf.jetpackmvvm.util.errorLog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecommendFragment :
    BaseVMFragment<FragmentRecommendLayoutBinding, RecommendViewModel>() {

    private val mAdapter: ArticleAdapter by lazy { ArticleAdapter() }

    private lateinit var bannerBinding: BannerViewBinding
    private lateinit var bannerAdapter: BannerImageAdapter<Banner>


    override fun initializeView() {

        mViewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
            bannerBinding = BannerViewBinding.inflate(layoutInflater, this, false)
            addHeaderView(bannerBinding.root)

            addItemDivider(R.drawable.divider)
        }

        bannerAdapter = object : BannerImageAdapter<Banner>(arrayListOf()) {
            override fun onBindView(
                holder: BannerImageHolder?,
                data: Banner?,
                position: Int,
                size: Int
            ) {
                holder?.imageView?.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(activity!!).load(data?.imagePath)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                    .into(holder?.imageView!!)
            }
        }

        bannerBinding.banner.apply {
            adapter = bannerAdapter
            indicator = CircleIndicator(context)
        }


//        mAdapter.addData(arrayListOf<String>().apply { for (i in 0 until 50) add("第${i + 1}条数据") })

    }

    override fun preloadData() {
        super.preloadData()
        mViewModel.getBanner()
        mViewModel.getRecommendArticle(ListViewState.Action.INITIALIZE)
    }

    companion object {

        const val RECOMMEND_FRAGMENT = 0
        fun newInstance(): Fragment {

            val fragment = RecommendFragment()

            return fragment
        }
    }

    override fun viewModel(): RecommendViewModel =


        ViewModelProvider(this).get(RecommendViewModel::class.java)


    override fun bindView() {

        observe(mViewModel.bannerLiveData, this::renderBanner)
        observe(mViewModel.recommendLiveData, this::renderArticleList)
    }

    private fun renderBanner(state: ViewState<List<Banner>>) {
        renderState(state, {
            bannerAdapter.setDatas(it)
            bannerAdapter.notifyDataSetChanged()
        }, {
            errorLog(msg = it)
        })
    }

    private fun renderArticleList(state: ListViewState<Article>) {

        renderList(state, {}, mViewBinding.recyclerView, mAdapter, mViewBinding.swipeRefreshLayout)
    }

}