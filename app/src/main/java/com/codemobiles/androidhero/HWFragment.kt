package com.codemobiles.androidhero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codemobiles.androidhero.databinding.CustomStockListBinding
import com.codemobiles.androidhero.databinding.FragmentStockBinding

class HWFragment : Fragment() {
    private lateinit var binding: FragmentStockBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStockBinding.inflate(layoutInflater)

        // important
        binding.recyclerview.adapter = StockAdapter()
        binding.recyclerview.layoutManager = GridLayoutManager(context, 2)

        return binding.root
    }

    class StockAdapter : RecyclerView.Adapter<CustomViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val binding = CustomStockListBinding.inflate(LayoutInflater.from(parent.context))
            return CustomViewHolder(binding.root)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        }

        override fun getItemCount() = 100

    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}