package com.codemobiles.androidhero.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codemobiles.androidhero.ChartFragment
import com.codemobiles.androidhero.HWFragment
import com.codemobiles.androidhero.R
import com.codemobiles.androidhero.StockFragment

val TAB_TITLES = arrayOf<String>(
    "STOCK",
    "CHART",
    "HW",
)

val TAB_ICONS = arrayOf<Int>(
    R.drawable.ic_stock,
    R.drawable.ic_chart,
    R.drawable.ic_product,
)

class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount() = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                StockFragment()
            }
            1 -> {
                ChartFragment()
            }
            2 -> {
                HWFragment()
            }
            else -> {
                StockFragment()
            }
        }
    }
}