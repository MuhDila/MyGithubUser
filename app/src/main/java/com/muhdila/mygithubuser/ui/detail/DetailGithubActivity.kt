package com.muhdila.mygithubuser.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.muhdila.mygithubuser.R
import com.muhdila.mygithubuser.data.response.UserGithubResponseDetail
import com.muhdila.mygithubuser.databinding.ActivityDetailGithubBinding
import com.muhdila.mygithubuser.ui.NavBarColor

class DetailGithubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGithubBinding
    private lateinit var detailGithubViewModel: DetailGithubViewModel
    private var getUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting color status bar and navigator bar
        NavBarColor.setStatusBarAndNavBarColors(this)

        // Setting action bar
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailGithubViewModel = ViewModelProvider(this)[DetailGithubViewModel::class.java]

        setupDataFromIntent()
        setupViewPager()
        setupActionBar()
        observeViewModel()
    }

    private fun setupDataFromIntent() {
        getUsername = intent.getStringExtra(DetailGithubViewModel.USERNAME)
        getUsername?.let { username ->
            detailGithubViewModel.userGithubDetail(username)
        }
    }

    private fun observeViewModel() {
        detailGithubViewModel.isLoading.observe(this) { isLoading -> showLoading(isLoading) }
        detailGithubViewModel.userGithubDetail.observe(this) { detailData -> githubUserDetail(detailData) }
        // Observe data from ViewModel and populate your UI
        detailGithubViewModel.userGithubDetail.observe(this) { detailData ->
            if (detailData != null) {
                // Populate UI elements with data
                githubUserDetail(detailData)
            } else {
                // Data not available, fetch it
                setupDataFromIntent()
            }
        }
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = FollGithubPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabsLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setupActionBar() {
        supportActionBar?.elevation = 0f
    }

    @SuppressLint("SetTextI18n")
    private fun githubUserDetail(detailData: UserGithubResponseDetail) {
        Glide.with(binding.root.context).load(detailData.avatarUrl).into(binding.imgDetailPhoto)
        binding.tvDetailName.text = detailData.name
        binding.tvDetailUsername.text = detailData.login
        binding.tvDetailFollowers.text = "${detailData.followers} Followers"
        binding.tvDetailFollowing.text = "${detailData.following} Following"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Action bar function
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish() // Close the current activity and go back
                return true
            }
            // Handle other menu items if needed
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }
}