package com.example.cleanarchitecture.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.cleanarchitecture.model.CarItem
import java.util.*
import java.util.concurrent.Executors

abstract class BaseRecyclerAdapter<T>(
    callBack: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<ViewDataBinding>>(
        AsyncDifferConfig.Builder<T>(callBack)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build()
) {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var posts: List<CarItem?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        if (viewType == VIEW_TYPE_ITEM){
            return BaseViewHolder(createBinding(parent = parent, viewType = viewType))
        }else{

            return BaseViewHolder(createBindingLoading(parent = parent, viewType = viewType))

        }

    }

    override fun getItemCount(): Int {
        return posts?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (posts[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    abstract fun createBindingLoading(parent: ViewGroup, viewType: Int? = 1): ViewDataBinding

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        posts.get(position)?.let { bind(holder.binding, it) }
        holder.binding.executePendingBindings()
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int? = 0): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, item: CarItem)

    open fun setPosts(posts: List<CarItem?>) {
        this.posts = posts
        notifyDataSetChanged()
    }


}
