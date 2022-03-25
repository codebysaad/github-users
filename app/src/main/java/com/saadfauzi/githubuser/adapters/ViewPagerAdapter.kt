package com.saadfauzi.githubuser.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saadfauzi.githubuser.ui.fragments.FollowersFollowingFragment

class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    var data: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = FollowersFollowingFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowersFollowingFragment.ARG_USERNAME_FOLLOWERS, data)
                    putString(FollowersFollowingFragment.TAB_NAME, "Following")
                }
                return fragment
            }
            1 -> {
                fragment = FollowersFollowingFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowersFollowingFragment.ARG_USERNAME_FOLLOWERS, data)
                    putString(FollowersFollowingFragment.TAB_NAME, "Followers")
                }
                return fragment
            }
        }
        return fragment as Fragment
    }
}