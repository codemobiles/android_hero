package com.codemobiles.androidhero

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.androidhero.databinding.ActivityHomeBinding
import com.codemobiles.androidhero.databinding.CustomTabBinding
import com.codemobiles.androidhero.ui.main.SectionsPagerAdapter
import com.codemobiles.androidhero.ui.main.TAB_ICONS
import com.codemobiles.androidhero.ui.main.TAB_TITLES
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, lifecycle)

        setupViewPager()
        setupEventWidget()
    }

    private fun setupEventWidget() {
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action") {
                    startActivity(Intent(applicationContext, FormActivity::class.java))
                }.show()
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = sectionsPagerAdapter

        binding.viewPager.setPageTransformer(HorizontalFlipTransformation())
//        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // mark: the pager should be set before the tab layout.
        TabLayoutMediator(binding.tabs, binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
//                 tab.text = TAB_TITLES[position]
//                 tab.setIcon(TAB_ICONS[position])
                val inflater =
                    applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val binding: CustomTabBinding = CustomTabBinding.inflate(inflater)
                binding.iconTab.setImageResource(TAB_ICONS[position])
                binding.textTab.text = TAB_TITLES[position]
                tab.customView = binding.root
            }
        ).attach()
    }
}