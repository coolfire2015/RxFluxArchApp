package com.huyingbao.module.epidemic

import com.huyingbao.core.annotations.RxAppOwner
import com.huyingbao.core.base.BaseApp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by liujunfeng on 2020/1/29.
 */
@RxAppOwner
class EpidemicApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerEpidemicComponent.builder().application(this).build()
    }
}
