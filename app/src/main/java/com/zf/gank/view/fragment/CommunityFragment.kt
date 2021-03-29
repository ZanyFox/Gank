package com.zf.gank.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zf.gank.databinding.FragmentCommunityLayoutBinding
import com.zf.jetpackmvvm.base.fragment.BaseVBFragment


/**
 * 社区
 */
class CommunityFragment : BaseVBFragment<FragmentCommunityLayoutBinding>() {

    companion object {

        const val COMMUNITY_FRAGMENT = 1

        fun newInstance(): Fragment {
            return CommunityFragment()
        }
    }


}