package com.huyingbao.module.epidemic.ui.news.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.news.store.NewsStore

class NewsFragment : BaseFluxFragment<NewsStore>() {
    companion object {
        fun newInstance() = NewsFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_news

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
