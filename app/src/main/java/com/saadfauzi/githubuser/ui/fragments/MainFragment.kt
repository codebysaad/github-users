package com.saadfauzi.githubuser.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.saadfauzi.githubuser.R
import com.saadfauzi.githubuser.adapters.RecyclerUserAdapter
import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity
import com.saadfauzi.githubuser.databinding.FragmentMainBinding
import com.saadfauzi.githubuser.helpers.Result
import com.saadfauzi.githubuser.viewmodels.MainViewModel
import com.saadfauzi.githubuser.viewmodels.MainViewModelFactory
import kotlinx.coroutines.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private lateinit var mViewModel: MainViewModel
    private lateinit var adapterUser: RecyclerUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lifecycle", "OnViewCreated")

        mViewModel = obtainViewModel()

        getAllGithubUser()

        adapterUser = RecyclerUserAdapter { users ->
            Log.d("MainFragment", users.username)
            if (users.isFavorite) {
                mViewModel.deleteUser(users)
            } else {
                mViewModel.saveUser(users)
            }
        }

        binding?.rvUser?.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(activity, 2)
                } else {
                    LinearLayoutManager(activity)
                }
            setHasFixedSize(true)
            adapter = adapterUser
        }

        adapterUser.setOnItemClickCallback(object : RecyclerUserAdapter.IOnItemClickCallback {
            override fun onItemClicked(data: GithubUserEntity) {
                gotoDetailUser(data)
            }
        })

        binding?.mainSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
            private var searchUser: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchUser?.cancel()
                searchUser = coroutineScope.launch {
                    newText?.let { query ->
                        delay(500)
                        if (query.isEmpty()) {
                            getAllGithubUser()
                        } else {
                            mViewModel.getSearchUser(query).observe(viewLifecycleOwner) { result ->
                                if (result != null) {
                                    when (result) {
                                        is Result.Loading -> {
                                            showLoading(true)
                                        }
                                        is Result.Success -> {
                                            showLoading(false)
                                            val data = result.data.filter {
                                                it.username.contains(query)
                                            }
                                            adapterUser.submitList(data)
                                        }
                                        is Result.Error -> {
                                            showLoading(false)
                                            activity?.window?.decorView?.let { view ->
                                                Snackbar.make(
                                                    view,
                                                    result.error,
                                                    Snackbar.LENGTH_LONG
                                                )
                                                    .setBackgroundTint(
                                                        ResourcesCompat.getColor(
                                                            resources,
                                                            R.color.red_500,
                                                            resources.newTheme()
                                                        )
                                                    )
                                                    .setAction("OK") {}
                                                    .setActionTextColor(
                                                        ResourcesCompat.getColor(
                                                            resources,
                                                            R.color.white,
                                                            resources.newTheme()
                                                        )
                                                    )
                                                    .show()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false
            }

        })
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "OnPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "OnResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d("Lifecycle", "OnDestroy")
    }

    private fun obtainViewModel(): MainViewModel {
        val factory = MainViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)
    }

    private fun getAllGithubUser() {
        mViewModel.getAllGithubUser().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val data = result.data
                        adapterUser.submitList(data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        activity?.window?.decorView?.let { view ->
                            Snackbar.make(
                                view,
                                result.error,
                                Snackbar.LENGTH_LONG
                            )
                                .setBackgroundTint(
                                    ResourcesCompat.getColor(
                                        resources,
                                        R.color.red_500,
                                        resources.newTheme()
                                    )
                                )
                                .setAction("OK") {}
                                .setActionTextColor(
                                    ResourcesCompat.getColor(
                                        resources,
                                        R.color.white,
                                        resources.newTheme()
                                    )
                                )
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun gotoDetailUser(dataUser: GithubUserEntity) {
        val toDetailUser =
            MainFragmentDirections.actionNavigationHomeToDetailActivity(
                dataUser
            )
        findNavController().navigate(toDetailUser)
    }

}