package com.huyingbao.module.ncov.ui.main.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.ncov.ui.main.view.*
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
    abstract fun injectEntriesFragment(): EntriesFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectGoodsFragment(): GoodsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectRecommendFragment(): RecommendFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectRumorFragment(): RumorFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectTimelinFragment(): TimelineFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectWikiFragment(): WikiFragment
}
