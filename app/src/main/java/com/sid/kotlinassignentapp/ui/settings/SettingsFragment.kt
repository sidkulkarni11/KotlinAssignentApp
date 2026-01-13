package com.sid.kotlinassignentapp.ui.settings

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sid.kotlinassignentapp.data.local.preferences.SettingsPreferences
import com.sid.kotlinassignentapp.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = SettingsPreferences(requireContext())
        viewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(prefs)
        )[SettingsViewModel::class.java]

        observeSettings()
        setupListeners()
    }

    private fun observeSettings() {
        lifecycleScope.launch {
            viewModel.offlineOnly.collectLatest {
                binding.switchOffline.isChecked = it
            }
        }

        lifecycleScope.launch {
            viewModel.autoRefresh.collectLatest {
                binding.switchRefresh.isChecked = it
            }
        }

        lifecycleScope.launch {
            viewModel.lastRefresh.collectLatest {
                binding.tvLastRefresh.text =
                    if (it == 0L) "Never"
                    else java.text.DateFormat.getDateTimeInstance().format(it)
            }
        }
    }

    private fun setupListeners() {
        binding.switchOffline.setOnCheckedChangeListener { _, checked ->
            viewModel.toggleOfflineOnly(checked)
        }

        binding.switchRefresh.setOnCheckedChangeListener { _, checked ->
            viewModel.toggleAutoRefresh(checked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
