package com.huyingbao.module.ncov.app

//import com.huyingbao.module.ncov.NcovEventBusIndex

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.core.arch.utils.RxAndroidInjection
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Application生命周期方法分发类
 *
 * Created by liujunfeng on 2020/1/29.
 */
@RxAppObserver
class NcovAppLifecycle(application: Application) : RxAppLifecycle(application) {
    @Inject
    lateinit var mNcovAppStore: NcovAppStore

    init {
        if (application is HasAndroidInjector) {
            RxAndroidInjection.inject(this, application)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
//        EventBus.builder()
//                .addIndex(NcovEventBusIndex())
//                .eventInheritance(false)
        mNcovAppStore.subscribe()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
        mNcovAppStore.unsubscribe()
    }
}
