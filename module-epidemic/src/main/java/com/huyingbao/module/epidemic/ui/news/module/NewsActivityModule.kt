package com.huyingbao.module.epidemic.ui.news.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.epidemic.ui.news.view.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewsActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectNewsFragment(): NewsFragment
}
