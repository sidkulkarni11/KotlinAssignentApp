package com.sid.kotlinassignentapp.ui.agentprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sid.kotlinassignentapp.data.local.appdatabase.AppDatabase
import com.sid.kotlinassignentapp.data.remote.RetrofitClient
import com.sid.kotlinassignentapp.data.repository.AgentRepositoryImpl
import com.sid.kotlinassignentapp.databinding.FragmentAgentProfileBinding
import com.sid.kotlinassignentapp.domain.usecase.GetPostsByUserUseCase
import kotlinx.coroutines.launch

class AgentProfileFragment : Fragment() {

    private var _binding: FragmentAgentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AgentProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = requireArguments().getInt(ARG_USER_ID)
        val userName = requireArguments().getString(ARG_USER_NAME).orEmpty()

        binding.tvName.text = userName

        setupViewModel(userId)
        observePosts()
    }

    private fun setupViewModel(userId: Int) {
        val api = RetrofitClient.agentApi
        val database = AppDatabase.getInstance(requireContext())
        val repository = AgentRepositoryImpl(api, database)
        val useCase = GetPostsByUserUseCase(repository)

        viewModel = ViewModelProvider(
            this,
            AgentProfileViewModelFactory(useCase, userId)
        )[AgentProfileViewModel::class.java]
    }

    private fun observePosts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.posts.collect { posts ->
                binding.tvPosts.text = posts.joinToString("\n\n") {
                    "• ${it.title}"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_USER_ID = "user_id"
        const val ARG_USER_NAME = "user_name"
    }
}
