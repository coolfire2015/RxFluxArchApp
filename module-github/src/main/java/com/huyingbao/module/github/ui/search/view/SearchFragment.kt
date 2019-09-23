package com.huyingbao.module.github.ui.search.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.search.store.SearchStore

/**
 * 搜索模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class SearchFragment : BaseFluxFragment<SearchStore>() {
    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun getLayoutId() = R.layout.github_fragment_search

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
