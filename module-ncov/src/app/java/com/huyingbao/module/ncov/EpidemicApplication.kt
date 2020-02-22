package com.huyingbao.module.ncov

import com.huyingbao.core.annotations.RxAppOwner
import com.huyingbao.core.base.BaseApp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by liujunfeng on 2020/1/29.
 */
@RxAppOwner
class NcovApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerNcovComponent.builder().application(this).build()
    }
}
