package com.zf.test


import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zf.gank.R
import com.zf.gank.databinding.FragmentMainLayoutBinding
import com.zf.gank.view.fragment.RecommendFragment
import com.zf.jetpackmvvm.base.activity.BaseVBActivity


class TestActivity : BaseVBActivity<FragmentMainLayoutBinding>() {


    val fragments = arrayListOf<Fragment>()


    override fun initializeView() {
        super.initializeView()
//        val content = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
//
//        content.fitsSystemWindows = true

//        window.statusBarColor = Color.TRANSPARENT
//        val controller = ViewCompat.getWindowInsetsController(mViewBinding.root)
//        controller?.isAppearanceLightStatusBars = true

//        for (i in 0 until 10) {
//            fragments.add(RecommendFragment())
//        }


        val mAdapter = ViewPagerAdapter(this, fragments)
        mViewBinding.viewPager.adapter = mAdapter
        mViewBinding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        mViewBinding.viewPager.offscreenPageLimit = 2

        val tabLayoutMediator =
            TabLayoutMediator(
                mViewBinding.mainPageTableLayout,
                mViewBinding.viewPager
            ) { tab: TabLayout.Tab, i: Int ->
                tab.text = "第${i + 1}个"
            }
        tabLayoutMediator.attach()
    }

}