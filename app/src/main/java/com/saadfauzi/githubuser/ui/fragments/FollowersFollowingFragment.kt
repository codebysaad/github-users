package com.saadfauzi.githubuser.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.saadfauzi.githubuser.adapters.FollowersFollowingAdapter
import com.saadfauzi.githubuser.data.remote.models.GithubUserItem
import com.saadfauzi.githubuser.databinding.FollowersFollowingFragmentBinding
import com.saadfauzi.githubuser.viewmodels.FollowersFollowingViewModel

class FollowersFollowingFragment : Fragment() {

    private var _binding: FollowersFollowingFragmentBinding? = null
    private val binding get() = _binding
    private val mViewModel by viewModels<FollowersFollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FollowersFollowingFragmentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollowersUser?.setHasFixedSize(true)

        mViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        val userName = arguments?.getString(ARG_USERNAME_FOLLOWERS)
        val tabName = arguments?.getString(TAB_NAME)

        if (tabName == "Followers") {
            mViewModel.getListFollowers(userName)
            mViewModel.listFollowers.observe(viewLifecycleOwner) { listFollowers ->
                if (listFollowers == null) {
                    binding?.apply {
                        tvEmpty.visibility = View.VISIBLE
                        imgEmpty.visibility = View.VISIBLE
                        rvFollowersUser.visibility = View.GONE
                    }
                } else {
                    binding?.apply {
                        tvEmpty.visibility = View.GONE
                        imgEmpty.visibility = View.GONE
                        rvFollowersUser.visibility = View.VISIBLE
                    }
                    showRecyclerView(listFollowers)
                }
            }
        } else {
            mViewModel.getListFollowing(userName)
            mViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                if (listFollowing == null) {
                    binding?.apply {
                        tvEmpty.visibility = View.VISIBLE
                        imgEmpty.visibility = View.VISIBLE
                        rvFollowersUser.visibility = View.GONE
                    }
                } else {
                    binding?.apply {
                        tvEmpty.visibility = View.GONE
                        imgEmpty.visibility = View.GONE
                        rvFollowersUser.visibility = View.VISIBLE
                    }
                    showRecyclerView(listFollowing)
                }
            }
        }
    }

    private fun showRecyclerView(list: ArrayList<GithubUserItem>) {
        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.rvFollowersUser?.layoutManager = GridLayoutManager(activity, 2)
        } else {
            binding?.rvFollowersUser?.layoutManager = LinearLayoutManager(activity)
        }

        val adapterUser = FollowersFollowingAdapter(list)
        binding?.rvFollowersUser?.adapter = adapterUser
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.followersProgressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_USERNAME_FOLLOWERS = "username_followers"
        const val TAB_NAME = "tab_name"
    }
}