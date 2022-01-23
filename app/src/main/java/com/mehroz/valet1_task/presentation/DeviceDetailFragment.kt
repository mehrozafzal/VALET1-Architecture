package com.mehroz.valet1_task.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.mehroz.valet1_task.R
import com.mehroz.valet1_task.base.BaseFragment
import com.mehroz.valet1_task.core.Status
import com.mehroz.valet1_task.data.local.Device
import com.mehroz.valet1_task.databinding.FragmentDeviceDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeviceDetailFragment : BaseFragment<FragmentDeviceDetailBinding>() {

    private val viewModel: MainViewModel by activityViewModels()
    private var addFavoriteItem: MenuItem? = null
    private var excludeFavoriteItem: MenuItem? = null
    private var addToFavorite: Boolean = false
    private var device: Device? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_device_detail

    override fun initialize() {
        viewModel.deviceDetailObserver.observe(this, {
            binding.apply {
                device = it
                deviceDetail = it
                fragmentDeviceDetailBanner.load(it.imageUrl) {
                    crossfade(true)
                    error(R.drawable.placeholder_image)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        addFavoriteItem = menu.findItem(R.id.addFavorite)
        excludeFavoriteItem = menu.findItem(R.id.excludeFavorite)
        device?.let { viewModel.getDeviceFromDb(it.id) }
        observeDeviceFromDb()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addFavorite -> {
                viewModel.removeDeviceInDb(device?.id)
                addToFavorite = false
                excludeFavoriteItem?.isVisible = true
                addFavoriteItem?.isVisible = false
                Snackbar.make(
                    binding.root,
                    getString(R.string.device_removed_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                true
            }
            R.id.excludeFavorite -> {
                viewModel.insertDeviceInDb(device)
                addToFavorite = true
                excludeFavoriteItem?.isVisible = false
                addFavoriteItem?.isVisible = true
                Snackbar.make(
                    binding.root,
                    getString(R.string.device_added_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun observeDeviceFromDb() {
        lifecycleScope.launch {
            viewModel.deviceStateFlow.collectLatest {
                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.data?.id == device?.id) {
                            excludeFavoriteItem?.isVisible = false
                            addFavoriteItem?.isVisible = true
                        } else {
                            excludeFavoriteItem?.isVisible = true
                            addFavoriteItem?.isVisible = false
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                        excludeFavoriteItem?.isVisible = true
                        addFavoriteItem?.isVisible = false
                        if (!it.message.isNullOrEmpty())
                            Snackbar.make(
                                binding.root,
                                it.message,
                                Snackbar.LENGTH_SHORT
                            ).show()
                    }
                }
            }
        }
    }
}