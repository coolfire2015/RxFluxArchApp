package com.huyingbao.module.epidemic.ui.main.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.epidemic.ui.main.view.ProvinceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectProvinceFragment(): ProvinceFragment
}

