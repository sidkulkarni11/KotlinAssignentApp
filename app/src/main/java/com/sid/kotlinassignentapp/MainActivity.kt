package com.sid.kotlinassignentapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sid.kotlinassignentapp.data.local.preferences.SettingsPreferences
import com.sid.kotlinassignentapp.databinding.ActivityMainBinding
import com.sid.kotlinassignentapp.ui.agentlist.AgentListFragment
import com.sid.kotlinassignentapp.ui.settings.SettingsFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Update last refresh timestamp safely
        lifecycleScope.launch {
            SettingsPreferences(this@MainActivity)
                .updateLastRefresh(System.currentTimeMillis())
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, AgentListFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                openSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openSettings() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SettingsFragment())
            .addToBackStack(null)
            .commit()
    }
}
