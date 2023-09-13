package com.muhdila.mygithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
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
            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Do nothing before text changes
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Update the user list as the text changes
                    userGithubViewModel.userGithubSearch(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    // Do nothing after text changes
                }
            })

            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val textSearchBar = searchView.text
                    userGithubViewModel.userGithubSearch(textSearchBar.toString())

                    // Hide the search view
                    searchView.hide()

                    return@setOnEditorActionListener true
                }
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