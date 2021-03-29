package com.zf.gank.view.activity

import com.zf.gank.R
import com.zf.gank.databinding.ActivityMainLayoutBinding
import com.zf.gank.view.fragment.HolderFragment
import com.zf.jetpackmvvm.base.activity.BaseVBActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseVBActivity<ActivityMainLayoutBinding>() {


    override fun initializeView() {
        super.initializeView()

//        val controller = ViewCompat.getWindowInsetsController(mViewBinding.root)
//        controller?.isAppearanceLightStatusBars = true
//        window.statusBarColor = Color.TRANSPARENT
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        supportFragmentManager.beginTransaction()
            .add(R.id.single_frame_layout, HolderFragment.newInstance())
            .commit()
    }



    //    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(this, arrayListOf()) }

//    override fun initializeView() {
//        with(mViewBinding) {
//            homeDataRecyclerView.init(LinearLayoutManager(this@MainActivity), articleAdapter)
//            swipeRefreshLayout.setOnRefreshListener {
//                mViewModel.getHomeArticle(action = ListViewState.Action.REFRESH)
//            }
//        }
//
//
//    }
//
//
//    override fun viewModel(): MainPageViewModel =
//        ViewModelProvider(this).get(MainPageViewModel::class.java)
//
//    override fun bindView() {
//        observe(mViewModel.homeArticleLiveData, this::render2)
//        observe(mViewModel.bannerLiveData, this::renderBanner)
//    }
//
//    override fun initialize() {
//        mViewModel.getHomeArticle(ListViewState.Action.INITIALIZE)
//    }
//
//    private fun renderBanner(banner: ViewState<Banner>) {
//
//    }
//
//    private fun render2(data: ListViewState<Article>) {
//
//        renderList(
//            data,
//            { mViewBinding.swipeRefreshLayout.isRefreshing = false },
//            mViewBinding.homeDataRecyclerView,
//            articleAdapter
//        )
//    }
//
//    private fun showFailure(msg: String) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//    }

}

