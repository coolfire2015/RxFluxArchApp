package com.huyingbao.module.epidemic.app

//import com.huyingbao.module.epidemic.EpidemicEventBusIndex

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
class EpidemicAppLifecycle(application: Application) : RxAppLifecycle(application) {
    @Inject
    lateinit var mEpidemicAppStore: EpidemicAppStore

    init {
        if (application is HasAndroidInjector) {
            RxAndroidInjection.inject(this, application)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
//        EventBus.builder()
//                .addIndex(EpidemicEventBusIndex())
//                .eventInheritance(false)
        mEpidemicAppStore.subscribe()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
        mEpidemicAppStore.unsubscribe()
    }
}
