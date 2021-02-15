package com.base.mvvmproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.mvvmproject.R
import com.base.mvvmproject.data.api.ApiHelper
import com.base.mvvmproject.data.api.RetrofitBuilder
import com.base.mvvmproject.data.model.User
import com.base.mvvmproject.databinding.ActivityMainBinding.inflate
import com.base.mvvmproject.ui.base.ViewModelFactory
import com.base.mvvmproject.ui.main.adapter.MainAdapter
import com.base.mvvmproject.ui.main.viewmodle.MainViewModel
import com.base.mvvmproject.utils.Status
import com.base.mvvmproject.utils.autoCleared
import kotlinx.android.synthetic.main.fragment_list_user.*


class ListUserFragment : Fragment(),MainAdapter.UserItemListener   {

   // private var binding: ListUserFragment by autoCleared()
  //  private lateinit var viewModel: MainViewModel
   private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setupViewModel()
        setupRecyclerView()
        setupObservers()
    }
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

 /*   private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }*/

    private fun setupRecyclerView() {
        adapter = MainAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        if (it.data != null) {
                            Log.d("bbbb",it.data.size.toString())
                            val l: ArrayList<User> = ArrayList()
                            l.addAll(it.data)
                            adapter.setItems(l)
                        }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                       // Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    override fun onClickedCharacter(userId: String) {
        findNavController().navigate(
            R.id.action_listUserFragment_to_detailFragment,
            bundleOf("id" to userId)
        )
    }


}
