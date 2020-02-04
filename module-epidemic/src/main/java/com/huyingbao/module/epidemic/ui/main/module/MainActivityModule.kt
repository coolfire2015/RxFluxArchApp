package com.huyingbao.module.epidemic.ui.main.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.epidemic.ui.main.view.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectMainFragment(): MainFragment
}

