package com.example.cleanarchitecture.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.base.BaseRecyclerAdapter
import com.example.cleanarchitecture.databinding.CustomContributeItemBinding
import com.example.cleanarchitecture.databinding.ItemLoadingBinding
import com.example.cleanarchitecture.extension.circleUrl
import com.example.cleanarchitecture.model.CarItem
import kotlin.collections.ArrayList

class ContributorAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val callback: ((CarItem) -> Unit)?
) : BaseRecyclerAdapter<CarItem>(

    callBack = object : DiffUtil.ItemCallback<CarItem>() {
        override fun areItemsTheSame(oldItem: CarItem, newItem: CarItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CarItem, newItem: CarItem): Boolean {
            return oldItem.title == newItem.title && oldItem.url == newItem.url
        }
    }) {



    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding =
        DataBindingUtil.inflate<CustomContributeItemBinding>(
            LayoutInflater.from(parent.context), R.layout.custom_contribute_item,
            parent, false, dataBindingComponent
        ).apply {
            root.setOnClickListener {
                this.contributor?.let { item ->
                    callback?.invoke(item)
                }
            }
        }

    override fun bind(binding: ViewDataBinding, item: CarItem) {
        if (binding is CustomContributeItemBinding) {
            binding.contributor = item
            binding.avatar.circleUrl(item.url)
        }
    }

    override fun createBindingLoading(parent: ViewGroup, viewType: Int?): ViewDataBinding =
        DataBindingUtil.inflate<ItemLoadingBinding>(
            LayoutInflater.from(parent.context), R.layout.item_loading,
            parent, false, dataBindingComponent
        )
}
