package com.mehroz.valet1_task.presentation


import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import com.mehroz.valet1_task.R
import com.mehroz.valet1_task.base.BaseFragment
import com.mehroz.valet1_task.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_settings

    override fun initialize() {
        binding.fragmentSettingsDisplayModeToggle.isChecked = viewModel.isDarkModeOn()
        binding.fragmentSettingsDisplayModeToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.setDarkMode(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                viewModel.setDarkMode(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}