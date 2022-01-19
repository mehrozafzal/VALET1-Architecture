package com.mehroz.valet1_task.presentation

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehroz.valet1_task.R
import com.mehroz.valet1_task.base.BaseFragment
import com.mehroz.valet1_task.core.Status
import com.mehroz.valet1_task.data.remote.response.DevicesResponseItem
import com.mehroz.valet1_task.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.view.MenuInflater
import android.view.inputmethod.EditorInfo

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: MainViewModel by activityViewModels()
    private var adapter: DevicesAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.appSearchBar)
        val searchManager = requireActivity().getSystemService(SEARCH_SERVICE) as SearchManager
        val queryTextListener: SearchView.OnQueryTextListener
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText);
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initialize() {
        viewModel.fetchDevices()
        setupDevicesObserver()
    }

    private fun setupDevicesObserver() {
        lifecycleScope.launch {
            val value = viewModel.getDevicesStateFlow()
            value.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        (activity as MainActivity).hideProgress()
                        it.data?.let { devices -> renderList(devices) }
                        binding.fragmentHomeRecyclerView.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        (activity as MainActivity).showProgress()
                        binding.fragmentHomeRecyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        (activity as MainActivity).hideProgress()
                        Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun renderList(deviceList: MutableList<DevicesResponseItem>) {
        adapter = DevicesAdapter(deviceList)
        binding.fragmentHomeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.fragmentHomeRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                (binding.fragmentHomeRecyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.fragmentHomeRecyclerView.adapter = adapter
    }
}