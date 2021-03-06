package com.huyingbao.module.gan

import com.huyingbao.core.annotations.RxAppOwner
import com.huyingbao.core.base.BaseApp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppOwner
class GanApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerGanComponent.builder().application(this).build()
    }
}
