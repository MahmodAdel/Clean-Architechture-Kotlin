package com.example.cleanarchitecture.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecture.BR
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.base.BaseFragment
import com.example.cleanarchitecture.binding.FragmentDataBindingComponent
import com.example.cleanarchitecture.databinding.FragmentCarBinding
import com.example.cleanarchitecture.model.CarItem
import com.example.cleanarchitecture.model.CarItemMapper
import com.example.cleanarchitecture.util.autoCleared
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class CarFragment : BaseFragment<FragmentCarBinding, CarViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    private val carItemMapper: CarItemMapper= CarItemMapper()

    val mdataList: ArrayList<CarItem?> = ArrayList()
    var page: Int =1

    var isLoading = false
    override val viewModel: CarViewModel by viewModels { viewModelFactory }

    override val layoutId: Int
        get() = R.layout.fragment_car

    private var mainAdapter by autoCleared<ContributorAdapter>()

    private var bindingComponent = FragmentDataBindingComponent(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ContributorAdapter(bindingComponent) { item ->
        }
        this.mainAdapter = adapter

        with(viewDataBinding) {
            listContributor.adapter = mainAdapter
        }
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  viewModel.getCars(page,mdataList.size)
        subscribeUI()
        initScrollListener()

    }


    override fun onStart() {
        super.onStart()
        subscribe(viewModel.getCars(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("Received UIModel $it users.")

                showUsers(it.map {
                    carItemMapper.mapToPresentation(it)
                })
            }, {
                Timber.w(it)
                showError(it)
            })
        )

    }

    fun showUsers(it: List<CarItem>) {
        if (page == 1) {
            mdataList.addAll(it)
            mainAdapter.setPosts(mdataList)
        }else{
            mdataList.removeAt(mdataList.size - 1)
            val scrollPosition = mdataList.size
            mainAdapter.notifyItemRemoved(scrollPosition)
            mdataList.addAll(it)
            mainAdapter.setPosts(mdataList)
            mainAdapter.notifyDataSetChanged()
            isLoading = it.size != 50

        }
    }

    fun showError(it: Throwable) {
        Toast.makeText(context, "An error occurred :("+it.message, Toast.LENGTH_SHORT).show()
    }


    private fun subscribeUI() = with(viewModel) {
    /*    repoCars.observe(viewLifecycleOwner) {
            if (page == 1) {
                mdataList.addAll(it)
                mainAdapter.setPosts(mdataList)
            }else{
                mdataList.removeAt(mdataList.size - 1)
                val scrollPosition = mdataList.size
                mainAdapter.notifyItemRemoved(scrollPosition)
                mdataList.addAll(it)
                mainAdapter.setPosts(mdataList)
                mainAdapter.notifyDataSetChanged()
                isLoading = it.size != 10
            }
        }*/

        loading.observe(viewLifecycleOwner) { loading ->
            viewDataBinding.loading.visibility = if (loading) View.VISIBLE else View.GONE

        }
    }


    private fun initScrollListener() {
        viewDataBinding.listContributor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mdataList.size - 1) {
                        //bottom of list!
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        mdataList.add(null)
        mainAdapter.notifyItemInserted(mdataList.size-1)
        page=page+1
        Timer().schedule(1000){
            subscribe(viewModel.getCarsMore(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Received UIModel $it users.")

                    showUsers(it.map {
                        carItemMapper.mapToPresentation(it)
                    })
                }, {
                    Timber.w(it)
                    showError(it)
                })
            )

        }


    }


}


