package com.muhdila.mygithubuser.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhdila.mygithubuser.data.response.UserGithubItems
import com.muhdila.mygithubuser.databinding.ActivityUserGithubBinding
import com.muhdila.mygithubuser.ui.NavBarColor
import com.muhdila.mygithubuser.ui.detail.DetailGithubActivity
import com.muhdila.mygithubuser.ui.detail.DetailGithubViewModel

class UserGithubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserGithubBinding
    private lateinit var userGithubViewModel: UserGithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting color status bar and navigator bar
        NavBarColor.setStatusBarAndNavBarColors(this)

        // Hide action bar
        supportActionBar?.hide()

        userGithubViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserGithubViewModel::class.java]

        setupRecyclerView()
        setupSearchBar()
        observeViewModel()
    }
    
    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val textSearchBar = searchView.text
                searchView.hide()
                userGithubViewModel.userGithubSearch(textSearchBar.toString())
                false
            }
        }
    }

    private fun observeViewModel() {
        userGithubViewModel.userGithubList()
        userGithubViewModel.userGithubList.observe(this) { setUserData(it) }
        userGithubViewModel.userGithubSearch.observe(this) { dataUser -> setUserData(dataUser) }
        userGithubViewModel.isLoading.observe(this) { showLoading(it) }
        // Observe data from ViewModel and populate your RecyclerView
        userGithubViewModel.userGithubList.observe(this) { data ->
            if (data != null) {
                // Populate RecyclerView with data
                setUserData(data)
            } else {
                // Data not available, fetch it
                userGithubViewModel.userGithubList()
            }
        }
    }

    private fun setUserData(githubUser: List<UserGithubItems>) {
        val adapter = UserGithubAdapter(githubUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserGithubItems) {
                putDetailUser(data)
            }
        })

        if (adapter.itemCount < 1) Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show().also { userGithubViewModel.userGithubList.observe(this) { setUserData(it) } }
    }

    private fun putDetailUser(data: UserGithubItems) {
        val intent = Intent(this@UserGithubActivity, DetailGithubActivity::class.java)
        intent.putExtra(DetailGithubViewModel.USERNAME, data.login)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}