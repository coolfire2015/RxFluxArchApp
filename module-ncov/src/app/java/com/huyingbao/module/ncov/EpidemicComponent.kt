package com.huyingbao.module.ncov

import android.app.Application
import com.huyingbao.module.ncov.app.NcovAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2020/1/29.
 */
@Singleton
@Component(modules = [NcovAppModule::class])
interface NcovComponent : AndroidInjector<NcovApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): NcovComponent
    }
}
