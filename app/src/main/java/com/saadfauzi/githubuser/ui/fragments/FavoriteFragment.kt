package com.saadfauzi.githubuser.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.saadfauzi.githubuser.adapters.RecyclerUserAdapter
import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity
import com.saadfauzi.githubuser.databinding.FragmentFavoriteBinding
import com.saadfauzi.githubuser.viewmodels.MainViewModel
import com.saadfauzi.githubuser.viewmodels.MainViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var mViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = obtainViewModel()

        mViewModel.getFavoriteUser().observe(viewLifecycleOwner){ result ->
            binding?.favProgressBar?.visibility = View.GONE
            if (result.isEmpty()){
                binding?.apply {
                    tvEmptyFav.visibility = View.VISIBLE
                    imgEmptyFav.visibility = View.VISIBLE
                    rvFavUser.visibility = View.GONE
                }
            } else {
                binding?.apply {
                    tvEmptyFav.visibility = View.GONE
                    imgEmptyFav.visibility = View.GONE
                }
                showRecyclerView(result)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(): MainViewModel {
        val factory = MainViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]
    }

    private fun showRecyclerView(list: List<GithubUserEntity>) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.rvFavUser?.layoutManager = GridLayoutManager(activity, 2)
        } else {
            binding?.rvFavUser?.layoutManager = LinearLayoutManager(activity)
        }

        val onFavorite: (GithubUserEntity) -> Unit = {
            if (it.isFavorite){
                mViewModel.deleteUser(it)
            } else {
                mViewModel.saveUser(it)
            }
        }

        val adapterUser = RecyclerUserAdapter(onFavorite)
        adapterUser.submitList(list)
        binding?.rvFavUser?.adapter = adapterUser
        adapterUser.setOnItemClickCallback(object : RecyclerUserAdapter.IOnItemClickCallback {
            override fun onItemClicked(data: GithubUserEntity) {
                gotoDetailUser(data)
            }
        })
    }

    private fun gotoDetailUser(dataUser: GithubUserEntity) {
        val toDetailUser =
            FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity(
                dataUser
            )
        findNavController().navigate(toDetailUser)
    }
}