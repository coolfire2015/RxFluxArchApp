package com.huyingbao.module.epidemic.ui.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.epidemic.ui.news.store.NewsStore

class NewsActivity : BaseFluxFragActivity<NewsStore>() {
    override fun createFragment(): Fragment? {
        return NewsFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
