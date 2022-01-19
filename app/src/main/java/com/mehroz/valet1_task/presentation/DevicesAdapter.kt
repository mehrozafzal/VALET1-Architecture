package com.mehroz.valet1_task.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filter.FilterResults
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mehroz.valet1_task.data.remote.response.DevicesResponseItem
import com.mehroz.valet1_task.databinding.ItemDeviceBinding
import java.util.*
import kotlin.collections.ArrayList

class DevicesAdapter(private var deviceList: MutableList<DevicesResponseItem>) :
    RecyclerView.Adapter<DevicesAdapter.PostViewHolder>(), Filterable {
    private lateinit var binding: ItemDeviceBinding
    val deviceListFull: List<DevicesResponseItem> = ArrayList<DevicesResponseItem>(deviceList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = ItemDeviceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PostViewHolder(binding, deviceList)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindViews()
    }

    override fun getItemCount(): Int = deviceList.size


    class PostViewHolder(
        private val binding: ItemDeviceBinding,
        private val deviceList: List<DevicesResponseItem>,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews() {
            binding.apply {
                itemDeviceStatus.text = deviceList[adapterPosition].status
                itemDeviceTitle.text = deviceList[adapterPosition].title
                itemDeviceImage.load(deviceList[adapterPosition].imageUrl) {
                    crossfade(true)
                }
            }
        }
    }


    override fun getFilter(): Filter = devicesFilter

    private val devicesFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<DevicesResponseItem> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(deviceListFull)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in deviceListFull) {
                    if (item.title?.lowercase(Locale.getDefault())
                            ?.contains(filterPattern) == true
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            deviceList.clear()
            deviceList.addAll(results.values as ArrayList<DevicesResponseItem>)
            notifyDataSetChanged()
        }
    }

}