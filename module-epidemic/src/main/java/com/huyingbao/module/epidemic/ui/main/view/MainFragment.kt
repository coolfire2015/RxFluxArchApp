package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.action.MainAction
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import kotlinx.android.synthetic.main.epidemic_fragment_main.*

/**
 * 主页面
 */
class MainFragment : BaseFluxFragment<MainStore>() {
    override fun getLayoutId() = R.layout.epidemic_fragment_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle("肺炎疫情",false)
        bt_province.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_PROVINCE) }
        bt_city.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_CITY) }
        bt_country.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_COUNTRY) }
        bt_entries.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_ENTRIES) }
        bt_recommend.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_RECOMMEND) }
        bt_rumor.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_RUMOR) }
        bt_goods.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_GOODS) }
        bt_statistics.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_STATISTICS) }
        bt_timeline.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_TIMELINE) }
        bt_wiki.setOnClickListener { baseActionCreator.postLocalChange(MainAction.TO_WIKI) }
    }
}