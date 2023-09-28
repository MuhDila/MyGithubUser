package com.muhdila.mygithubuser.ui.favourite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhdila.mygithubuser.R
import com.muhdila.mygithubuser.data.helper.ViewModelFactory
import com.muhdila.mygithubuser.data.remote.response.UserGithubItems
import com.muhdila.mygithubuser.databinding.ActivityFavouriteBinding
import com.muhdila.mygithubuser.ui.NavBarColor
import com.muhdila.mygithubuser.ui.detail.DetailGithubActivity
import com.muhdila.mygithubuser.ui.detail.DetailGithubViewModel
import com.muhdila.mygithubuser.ui.main.UserGithubAdapter

class FavouriteActivity : AppCompatActivity() {

    private var binding: ActivityFavouriteBinding? = null
    private lateinit var adapter: UserGithubAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Setting color status bar and navigator bar
        NavBarColor.setColor(this, null)

        // Action Bar
        supportActionBar?.title = getString(R.string.favourite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize RecyclerView
        setupRecyclerView()

        // Set up data
        setUserData()
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

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvUser?.addItemDecoration(itemDecoration)

        // Initialize the adapter
        adapter = UserGithubAdapter()
        binding?.rvUser?.adapter = adapter
    }

    private fun setUserData() {
        val favoriteViewModel by viewModels<FavouriteViewModel> {
            ViewModelFactory.getInstance(application)
        }

        favoriteViewModel.getAllFavourite().observe(this) { user ->
            val items = arrayListOf<UserGithubItems>()
            user.map {
                val item = UserGithubItems(login = it.username, homeUrl = it.homeUrl ,avatarUrl = it.avatarUrl!!)
                items.add(item)
            }

            // Submit data to the adapter
            adapter.submitList(items)
        }

        adapter.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserGithubItems) {
                putDetailUser(data)
            }
        })
    }

    private fun putDetailUser(data: UserGithubItems) {
        val intent = Intent(this, DetailGithubActivity::class.java)
        intent.putExtra(DetailGithubViewModel.USERNAME, data.login)
        intent.putExtra(DetailGithubViewModel.HOME_URL, data.homeUrl)
        intent.putExtra(DetailGithubViewModel.AVATAR_URL, data.avatarUrl)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
