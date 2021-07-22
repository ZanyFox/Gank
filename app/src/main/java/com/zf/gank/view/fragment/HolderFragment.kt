package com.zf.gank.view.fragment


import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zf.gank.R
import com.zf.gank.databinding.FragmentHolderLayoutBinding
import com.zf.jetpackmvvm.base.fragment.BaseVBFragment

class HolderFragment : BaseVBFragment<FragmentHolderLayoutBinding>(false) {


    private val fragments = hashMapOf<Int, Fragment>()
    private var currentFragment: Fragment? = null


    override fun initializeView() {
        super.initializeView()


        mViewBinding.bottomNav.labelVisibilityMode = BottomNavigationView.LABEL_VISIBILITY_LABELED
        mViewBinding.bottomNav.selectedItemId = R.id.main_page_menu_item
        switchFragment(MainFragment.MAIN_FRAGMENT)
        // 初始化底部导航栏
        mViewBinding.bottomNav.setOnNavigationItemSelectedListener {
            val childFragmentId = when (it.itemId) {
                R.id.main_page_menu_item -> MainFragment.MAIN_FRAGMENT
                R.id.community_menu_item -> CommunityFragment.COMMUNITY_FRAGMENT
                R.id.question_menu_item -> return@setOnNavigationItemSelectedListener true
                R.id.message_menu_item -> return@setOnNavigationItemSelectedListener true
                R.id.me_menu_item -> return@setOnNavigationItemSelectedListener true

                else -> throw IllegalArgumentException("没有该选项")
            }
            switchFragment(childFragmentId)
            return@setOnNavigationItemSelectedListener true
        }
    }


    private fun switchFragment(id: Int) {

        val transaction = childFragmentManager.beginTransaction()
        val fragment: Fragment
        if (!fragments.containsKey(id)) {
            fragment = createFragment(id)
            fragments[id] = fragment
            transaction.add(R.id.main_fragment_container, fragment)
        } else {
            fragment = fragments[id]!!
        }

        currentFragment?.let { transaction.hide(it) }
        currentFragment = fragment
        transaction.show(fragment).commit()
    }

    private fun createFragment(id: Int) =
        when (id) {
            MainFragment.MAIN_FRAGMENT -> MainFragment.newInstance()
            CommunityFragment.COMMUNITY_FRAGMENT -> CommunityFragment.newInstance()
            else -> throw IllegalArgumentException()
        }

    companion object {

        fun newInstance() = HolderFragment()
    }


}