package com.huyingbao.module.ncov.ui.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.ncov.ui.news.store.NewsStore

class NewsActivity : BaseFluxFragActivity<NewsStore>() {
    override fun createFragment(): Fragment? {
        return NewsFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
