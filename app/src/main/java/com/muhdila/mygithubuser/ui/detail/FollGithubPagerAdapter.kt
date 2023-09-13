package com.muhdila.mygithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FollGithubPagerAdapter(activity: DetailGithubActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = FollGithubFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollGithubFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int = 2
}
