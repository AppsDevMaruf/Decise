package com.example.decise.ui.profile.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.decise.data.networks.profile.DropDownModel
import com.example.decise.databinding.ItemSearchBinding
import com.example.decise.interfaces.DropDownInteractionListener
import com.example.decise.utils.DropDownType

class DropDownAdapter (private var dropDownInteractionListener: DropDownInteractionListener, dropDownType: DropDownType) :
    ListAdapter<DropDownModel, DropDownAdapter.DropDownModelViewHolder>(comparator) {

    private var originalList: List<DropDownModel>? = null
    private var _dropDownType = dropDownType // Store the original unfiltered list

    fun submitInitialList(list: List<DropDownModel>) {
        originalList = list
        submitList(list)
    }

    // Update the adapter's data with the filtered list
    fun filterList(filteredList: List<DropDownModel>) {
        submitList(filteredList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropDownModelViewHolder {
        // below line is to inflate our layout.
        return DropDownModelViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DropDownModelViewHolder, position: Int) {
        getItem(position)?.let { item ->

            holder.binding.run {

                dropDownItemTv.text = item.name

                holder.itemView.setOnClickListener {
                    dropDownInteractionListener.selectedDropDownItem(item, _dropDownType)
                }
            }
        }
    }

    companion object {
        private val comparator =
            object : DiffUtil.ItemCallback<DropDownModel>() {
                override fun areItemsTheSame(
                    oldItem: DropDownModel,
                    newItem: DropDownModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: DropDownModel,
                    newItem: DropDownModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }


    inner class DropDownModelViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

}
