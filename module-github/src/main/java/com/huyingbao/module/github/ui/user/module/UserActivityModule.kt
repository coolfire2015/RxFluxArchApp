package com.huyingbao.module.github.ui.user.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.github.ui.user.view.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectUserFragment(): UserFragment
}