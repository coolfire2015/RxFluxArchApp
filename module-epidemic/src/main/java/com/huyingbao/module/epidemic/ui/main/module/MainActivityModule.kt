package com.huyingbao.module.epidemic.ui.main.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.epidemic.ui.main.view.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectMainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectProvinceFragment(): ProvinceFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectCountryFragment(): CountryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectCityFragment(): CityFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectEntriesFragment():EntriesFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectGoodsFragment():GoodsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectRecommendFragment():RecommendFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectRumorFragment():RumorFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectTimelinFragment():TimelinFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectWikiFragment():WikiFragment
}

