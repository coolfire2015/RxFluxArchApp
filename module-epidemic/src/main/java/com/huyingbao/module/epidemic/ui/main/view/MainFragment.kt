package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import androidx.navigation.Navigation
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import kotlinx.android.synthetic.main.epidemic_fragment_main.*

/**
 * 主页面
 */
class MainFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = MainFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle("肺炎疫情", false)
        bt_province.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_provinceFragment)
        }
        bt_country.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_countryFragment) }
        bt_entries.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_entriesFragment) }
        bt_recommend.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_recommendFragment) }
        bt_rumor.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_rumorFragment) }
        bt_goods.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_goodsFragment) }
        bt_timeline.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_timelinFragment) }
        bt_wiki.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_wikiFragment) }
    }
}