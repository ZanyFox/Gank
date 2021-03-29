package com.zf.gank.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zf.gank.databinding.FragmentCommunityLayoutBinding
import com.zf.gank.databinding.FragmentMainLayoutBinding
import com.zf.gank.view.adapter.MainFragmentViewPagerAdapter
import com.zf.jetpackmvvm.base.fragment.BaseVBFragment


/**
 * 首页Fragment
 */
class MainFragment : BaseVBFragment<FragmentMainLayoutBinding>() {


    private val fragments = hashMapOf<Int, Fragment>()
    private val viewPagerAdapter: MainFragmentViewPagerAdapter by lazy {
        MainFragmentViewPagerAdapter(
            this,
            fragments
        )
    }

    override fun initializeView() {
        super.initializeView()
        initFragments()
        mViewBinding.viewPager.adapter = viewPagerAdapter

        mViewBinding.mainPageTableLayout.tabIndicatorAnimationMode =
            TabLayout.INDICATOR_ANIMATION_MODE_ELASTIC

        val tabLayoutMediator =
            TabLayoutMediator(
                mViewBinding.mainPageTableLayout,
                mViewBinding.viewPager, true, false
            ) { tab: TabLayout.Tab, i: Int ->
                tab.text = "第${i + 1}个"
            }
        tabLayoutMediator.attach()
    }

    private fun initFragments() {

        fragments[0] = RecommendFragment.newInstance()
        fragments[1] = RecommendFragment.newInstance()
        fragments[2] = RecommendFragment.newInstance()
        fragments[3] = RecommendFragment.newInstance()
        fragments[4] = RecommendFragment.newInstance()
        fragments[5] = RecommendFragment.newInstance()

    }


    companion object {

        const val MAIN_FRAGMENT = 0

        fun newInstance(): Fragment {

            return MainFragment()
        }
    }


}