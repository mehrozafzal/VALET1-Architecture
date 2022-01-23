package com.mehroz.valet1_task.presentation

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mehroz.valet1_task.R
import com.mehroz.valet1_task.base.BaseFragment
import com.mehroz.valet1_task.core.Status
import com.mehroz.valet1_task.data.local.Device
import com.mehroz.valet1_task.databinding.FragmentMyDevicesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyDevicesFragment : BaseFragment<FragmentMyDevicesBinding>(), DevicesAdapter.OnClickListener {

    private val viewModel: MainViewModel by activityViewModels()
    private var adapter: DevicesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        val searchItem = menu.findItem(R.id.appSearchBar)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val queryTextListener: SearchView.OnQueryTextListener
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_my_devices

    override fun initialize() {
        viewModel.getAllDeviceFromDb()
        observeDevicesFromDb()
        binding.fragmentMyDevicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.fragmentMyDevicesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                (binding.fragmentMyDevicesRecyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun observeDevicesFromDb() {
        lifecycleScope.launchWhenStarted {
            viewModel.dbDevicesStateFlow.collectLatest {
                when (it.status) {
                    Status.SUCCESS -> {
                        (activity as MainActivity).hideProgress()
                        it.data?.let { devices -> renderList(devices as MutableList<Device>) }
                        binding.fragmentMyDevicesRecyclerView.visibility = View.VISIBLE
                        binding.fragmentMyDevicesNoDeviceMessage.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        (activity as MainActivity).showProgress()
                        binding.fragmentMyDevicesRecyclerView.visibility = View.GONE
                        binding.fragmentMyDevicesNoDeviceMessage.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        (activity as MainActivity).hideProgress()
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

    private fun renderList(deviceList: MutableList<Device>) {
        adapter = DevicesAdapter(deviceList, this)
        binding.fragmentMyDevicesRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(deviceItem: Device) {
        viewModel.deviceDetailObserver.value = deviceItem
        findNavController().navigate(R.id.deviceDetailFragment)
    }
}