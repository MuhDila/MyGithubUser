package com.muhdila.mygithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhdila.mygithubuser.R
import com.muhdila.mygithubuser.data.remote.response.UserGithubItems
import com.muhdila.mygithubuser.databinding.ActivityUserGithubBinding
import com.muhdila.mygithubuser.ui.NavBarColor
import com.muhdila.mygithubuser.ui.detail.DetailGithubActivity
import com.muhdila.mygithubuser.ui.detail.DetailGithubViewModel
import com.muhdila.mygithubuser.ui.favourite.FavouriteActivity
import com.muhdila.mygithubuser.ui.setting.SettingActivity
import com.muhdila.mygithubuser.ui.setting.SettingPreferences
import com.muhdila.mygithubuser.ui.setting.SettingViewModel
import com.muhdila.mygithubuser.ui.setting.ViewModelFactory
import com.muhdila.mygithubuser.ui.setting.dataStore

class UserGithubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserGithubBinding
    private lateinit var userGithubViewModel: UserGithubViewModel
    private lateinit var adapter: UserGithubAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting color status bar and navigator bar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        NavBarColor.setColor(this, toolbar)

        // Hide action bar
        supportActionBar?.hide()

        // Image button clicked
        binding.layoutSearch.imgSetting.setOnClickListener{
            val intent = Intent(this@UserGithubActivity, SettingActivity::class.java)
            startActivity(intent)
        }
        binding.layoutSearch.imgFav.setOnClickListener {
            val intent = Intent(this@UserGithubActivity, FavouriteActivity::class.java)
            startActivity(intent)
        }

        userGithubViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserGithubViewModel::class.java]

        setupRecyclerView()
        setupSearchBar()
        observeViewModel()
        // Theme
        settingTheme()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        // Initialize the adapter
        adapter = UserGithubAdapter()
        binding.rvUser.adapter = adapter
    }

    private fun setupSearchBar() {
        val searchBar = binding.layoutSearch.searchBar
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val textSearchBar = searchView.text
                    userGithubViewModel.userGithubSearch(textSearchBar.toString())
                    searchView.hide()
                    true
                }
        }
    }

    private fun observeViewModel() {
        userGithubViewModel.userGithubList()
        userGithubViewModel.userGithubList.observe(this) { setUserData(it) }
        userGithubViewModel.userGithubSearch.observe(this) { dataUser -> setUserData(dataUser) }
        userGithubViewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun setUserData(githubUser: List<UserGithubItems>) {
        // Submit data to the adapter
        adapter.submitList(githubUser)

        adapter.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserGithubItems) {
                putDetailUser(data)
            }
        })

        // Handle empty data case
        if (githubUser.isEmpty()) {
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun settingTheme(){
        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun putDetailUser(data: UserGithubItems) {
        val intent = Intent(this, DetailGithubActivity::class.java)
        intent.putExtra(DetailGithubViewModel.USERNAME, data.login)
        intent.putExtra(DetailGithubViewModel.HOME_URL, data.homeUrl)
        intent.putExtra(DetailGithubViewModel.AVATAR_URL, data.avatarUrl)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
