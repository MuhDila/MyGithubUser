package com.muhdila.mygithubuser.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.muhdila.mygithubuser.R
import com.muhdila.mygithubuser.data.database.Favourite
import com.muhdila.mygithubuser.data.helper.ViewModelFactory
import com.muhdila.mygithubuser.data.helper.loadImage
import com.muhdila.mygithubuser.data.remote.response.UserGithubResponseDetail
import com.muhdila.mygithubuser.databinding.ActivityDetailGithubBinding
import com.muhdila.mygithubuser.ui.NavBarColor
import com.muhdila.mygithubuser.ui.favourite.FavouriteViewModel
import com.muhdila.mygithubuser.ui.main.UserGithubActivity

class DetailGithubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGithubBinding
    private lateinit var detailGithubViewModel: DetailGithubViewModel
    private var getUsername: String? = null
    private var getHomeUrl: String? = null
    private var getAvatarUrl: String? = null
    private var isFavourite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting color status bar and navigator bar
        NavBarColor.setColor(this, null)

        // Action Bar Setting
        supportActionBar?.title = getString(R.string.detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailGithubViewModel = ViewModelProvider(this)[DetailGithubViewModel::class.java]

        setupDataFromIntent()
        setupViewPager()
        observeViewModel()

        // Favourite
        getFavourite()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu resource and add it to the options menu
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Action bar function
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish() // Close the current activity and go back
                return true
            }
            R.id.action_home -> {
                // Intent into about page
                val intent = Intent(this, UserGithubActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDataFromIntent() {
        getUsername = intent.getStringExtra(DetailGithubViewModel.USERNAME)
        getAvatarUrl = intent.getStringExtra(DetailGithubViewModel.AVATAR_URL)
        getHomeUrl = intent.getStringExtra(DetailGithubViewModel.HOME_URL)
        getUsername?.let { username ->
            detailGithubViewModel.userGithubDetail(username)
        }
    }

    private fun observeViewModel() {
        detailGithubViewModel.isLoading.observe(this) { isLoading -> showLoading(isLoading) }
        detailGithubViewModel.userGithubDetail.observe(this) { detailData -> githubUserDetail(detailData) }
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

    @SuppressLint("SetTextI18n")
    private fun githubUserDetail(detailData: UserGithubResponseDetail) {
        binding.imgDetailPhoto.loadImage(detailData.avatarUrl)
        binding.tvDetailName.text = detailData.name
        binding.tvDetailUsername.text = detailData.login
        binding.tvDetailFollowers.text = getString(R.string.followers_placeholder, detailData.followers)
        binding.tvDetailFollowing.text = getString(R.string.following_placeholder, detailData.following)
    }

    private fun getFavourite(){
        val favoriteViewModel by viewModels<FavouriteViewModel> {
            ViewModelFactory.getInstance(application)
        }

        binding.favoriteButton.setOnClickListener {
            val getUsername = intent.getStringExtra(DetailGithubViewModel.USERNAME)
            val getHomeUrl = intent.getStringExtra(DetailGithubViewModel.HOME_URL)
            val getAvatarUrl = intent.getStringExtra(DetailGithubViewModel.AVATAR_URL)
            val favourite = Favourite(getUsername!!, getHomeUrl!!, getAvatarUrl!!)
            if (isFavourite) {
                favoriteViewModel.delete(favourite)
                Toast.makeText(this, "$getUsername not your favourite", Toast.LENGTH_SHORT).show()
            } else {
                favoriteViewModel.insert(favourite)
                Toast.makeText(this, "$getUsername is your favourite", Toast.LENGTH_SHORT).show()
            }
        }

        favoriteViewModel.getFavouriteByUsername(getUsername.toString()).observe(this) {
            isFavourite = if (it?.username != null) {
                binding.favoriteButton.setImageResource(R.drawable.ic_favourite_fill)
                true
            } else {
                binding.favoriteButton.setImageResource(R.drawable.ic_favourite_border)
                false
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }
}