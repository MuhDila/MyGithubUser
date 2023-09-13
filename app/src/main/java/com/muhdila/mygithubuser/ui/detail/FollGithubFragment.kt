package com.muhdila.mygithubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhdila.mygithubuser.data.response.UserGithubItems
import com.muhdila.mygithubuser.databinding.FragmentFollGithubBinding

class FollGithubFragment : Fragment() {

    private var _binding: FragmentFollGithubBinding? = null
    private val binding get() = _binding!!
    private lateinit var follGithubViewModel: FollGithubViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollGithubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        follGithubViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollGithubViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    private fun observeViewModel() {
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        arguments?.getString(ARG_NAME)

        follGithubViewModel.userGithubFollower(
            activity?.intent?.getStringExtra(DetailGithubViewModel.USERNAME).toString()
        )
        follGithubViewModel.userGithubFollowing(
            activity?.intent?.getStringExtra(DetailGithubViewModel.USERNAME).toString()
        )
        follGithubViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        when (index) {
            1 -> follGithubViewModel.listUserGithubFollower.observe(viewLifecycleOwner)
            {setUserGithubFoll(it) }
            2 -> follGithubViewModel.listUserGithubFollowing.observe(viewLifecycleOwner)
            { setUserGithubFoll(it) }
        }

    }

    private fun setUserGithubFoll(userFoll: List<UserGithubItems>) {
        val adapter = FollGithubAdapter(userFoll)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }
}