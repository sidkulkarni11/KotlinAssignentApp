package com.sid.kotlinassignentapp.ui.agentlist

import GetAgentsUseCase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sid.kotlinassignentapp.R
import com.sid.kotlinassignentapp.data.local.appdatabase.AppDatabase
import com.sid.kotlinassignentapp.data.local.preferences.SettingsPreferences
import com.sid.kotlinassignentapp.data.remote.RetrofitClient
import com.sid.kotlinassignentapp.data.repository.AgentRepositoryImpl
import com.sid.kotlinassignentapp.databinding.FragmentAgentListBinding
import com.sid.kotlinassignentapp.ui.agentprofile.AgentProfileFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AgentListFragment : Fragment() {

    private var _binding: FragmentAgentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AgentListViewModel
    private lateinit var adapter: AgentPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupSearch()
        setupPullToRefresh()
        observePagingData()
        observeLoadState()
    }

    private fun setupViewModel() {
        val api = RetrofitClient.agentApi
        val database = AppDatabase.getInstance(requireContext())

        val repository = AgentRepositoryImpl(
            api = api,
            database = database
        )

        val useCase = GetAgentsUseCase(repository)
        val factory = AgentListViewModelFactory(useCase)

        viewModel = ViewModelProvider(this, factory)[AgentListViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = AgentPagingAdapter { agent ->
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    AgentProfileFragment().apply {
                        arguments = Bundle().apply {
                            putInt(AgentProfileFragment.ARG_USER_ID, agent.id)
                            putString(AgentProfileFragment.ARG_USER_NAME, agent.name)
                        }
                    }
                )
                .addToBackStack(null)
                .commit()
        }

        binding.rvAgents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAgents.adapter = adapter
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener { text ->
            viewModel.onSearchQueryChanged(text?.toString().orEmpty())
        }
    }

    private fun setupPullToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun observePagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.agents.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing =
                    loadStates.refresh is LoadState.Loading

                if (loadStates.refresh is LoadState.NotLoading) {
                    SettingsPreferences(requireContext())
                        .updateLastRefresh(System.currentTimeMillis())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
