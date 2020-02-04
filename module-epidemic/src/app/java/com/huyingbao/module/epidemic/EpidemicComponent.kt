package com.huyingbao.module.epidemic

import android.app.Application
import com.huyingbao.module.epidemic.app.EpidemicAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2020/1/29.
 */
@Singleton
@Component(modules = [EpidemicAppModule::class])
interface EpidemicComponent : AndroidInjector<EpidemicApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): EpidemicComponent
    }
}
