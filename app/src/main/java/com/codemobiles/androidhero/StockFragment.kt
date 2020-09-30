package com.codemobiles.androidhero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codemobiles.androidhero.databinding.CustomStockListBinding
import com.codemobiles.androidhero.databinding.FragmentStockBinding
import com.codemobiles.androidhero.models.ProductAllResponse
import com.codemobiles.androidhero.services.APIClient
import com.codemobiles.androidhero.services.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockFragment : Fragment() {
    private lateinit var binding: FragmentStockBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStockBinding.inflate(layoutInflater)

        // important
        binding.recyclerview.adapter = StockAdapter(listOf())
        binding.recyclerview.layoutManager = GridLayoutManager(context, 2)
        // binding.recyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL ))
        binding.recyclerview.addItemDecoration(GridSpacingItemDecoration(2, 20, true))

        binding.swipeRefresh.setOnRefreshListener {
            feedNetwork()
        }

        feedNetwork()

        return binding.root
    }

    private fun feedNetwork(){
        APIClient.getClient().create(APIService::class.java).getProducts().let { call ->
            call.enqueue(object : Callback<List<ProductAllResponse>> {
                override fun onFailure(call: Call<List<ProductAllResponse>>, t: Throwable) {
                    binding.swipeRefresh.isRefreshing = false
                }

                override fun onResponse(
                    call: Call<List<ProductAllResponse>>,
                    response: Response<List<ProductAllResponse>>
                ) {
                    if(response.isSuccessful){
                        val result: List<ProductAllResponse> = response.body()!!
                        binding.recyclerview.adapter = StockAdapter(result)
                    }

                    binding.swipeRefresh.isRefreshing = false
                }
            })
        }
    }

    // primary class
    class StockAdapter(var productList: List<ProductAllResponse>) : RecyclerView.Adapter<CustomViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val binding = CustomStockListBinding.inflate(LayoutInflater.from(parent.context))
            return CustomViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val product = productList[position]

            val binding = holder.binding
            binding.textviewName.text = product.name
            binding.textviewDetail.text = "codemobiles android dev"
            binding.textviewPrice.text = product.price.toString()
            binding.textviewStock.text = product.stock.toString()

            val image = APIClient.getImageURL() + product.image
            Glide.with(holder.binding.root).load(image).into(binding.imageviewProduct);
        }

        override fun getItemCount() = productList.size

    }

    class CustomViewHolder(val binding: CustomStockListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

