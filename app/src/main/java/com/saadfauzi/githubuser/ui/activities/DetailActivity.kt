package com.saadfauzi.githubuser.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.saadfauzi.githubuser.R
import com.saadfauzi.githubuser.adapters.ViewPagerAdapter
import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity
import com.saadfauzi.githubuser.databinding.ActivityDetailBinding
import com.saadfauzi.githubuser.viewmodels.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var githubUser: GithubUserEntity
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding
    private val mViewModel by viewModels<DetailViewModel>()
    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        githubUser = args.githubUser

        mViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mViewModel.loadDetailsUser(githubUser.username)
        mViewModel.detailsGithubUser.observe(this) {

            val username = StringBuilder("@").append(githubUser.username)

            binding?.apply {
                detailName.text = it.name
                detailUsername.text = username
                detailCompany.text = it.company
                detailLocation.text = it.location
                detailValueFollowers.text = it.followers.toString()
                detailValueFollowing.text = it.following.toString()
                detailValueRepository.text = it.publicRepos.toString()

                Glide.with(this@DetailActivity)
                    .load(githubUser.avatarUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(detailCircleAvatar)
            }
        }

        showTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting_menu -> startActivity(Intent(this, SettingActivity::class.java))
            R.id.share_menu -> {
                val textShare =
                    "Hi, I'm ${githubUser.username}. Let's join with me, here my Github account ${githubUser.htmlUrl}. See you on top."
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, textShare)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showTabLayout() {
        val sectionPagerAdapter = ViewPagerAdapter(this)
        sectionPagerAdapter.data = githubUser.username
        binding?.detailViewPager?.adapter = sectionPagerAdapter
        binding?.detailTabsLayout?.let { detailTabsLayout ->
            binding?.detailViewPager?.let { detailViewPager ->
                TabLayoutMediator(detailTabsLayout, detailViewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }

        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.detailProgressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.title_tab_following,
            R.string.title_tab_followers
        )
    }
}