package com.huyingbao.module.ncov.ui.news.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.ncov.R
import com.huyingbao.module.ncov.ui.news.store.NewsStore

class NewsFragment : BaseFluxFragment<NewsStore>() {
    companion object {
        fun newInstance() = NewsFragment()
    }

    override fun getLayoutId() = R.layout.ncov_fragment_news

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
