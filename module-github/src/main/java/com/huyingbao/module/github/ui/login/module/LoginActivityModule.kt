package com.huyingbao.module.github.ui.login.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.github.ui.login.view.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectLoginFragment(): LoginFragment
}