package com.huyingbao.module.github

import android.app.Application
import com.huyingbao.module.github.app.GithubAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [GithubAppModule::class])
interface GithubComponent : AndroidInjector<GithubApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): GithubComponent
    }
}
