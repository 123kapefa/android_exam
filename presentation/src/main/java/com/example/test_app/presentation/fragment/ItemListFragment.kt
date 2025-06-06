package com.example.test_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_app.presentation.adapter.ItemAdapter
import com.example.test_app.presentation.viewmodel.ItemListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.test_app.presentation.R

class ItemListFragment : Fragment(R.layout.fragment_item_list) {

    private val viewModel: ItemListViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.itemRecyclerView)
        adapter = ItemAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.items.collect { items ->
                adapter.submitList(items)
            }
        }
    }
}