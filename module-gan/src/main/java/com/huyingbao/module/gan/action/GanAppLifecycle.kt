package com.huyingbao.module.gan.action

import android.app.Application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.module.gan.GanEventBusIndex

import org.greenrobot.eventbus.EventBus

@RxAppObserver
class GanAppLifecycle(application: Application) : RxAppLifecycle(application) {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        EventBus.builder()
                .addIndex(GanEventBusIndex())
                .eventInheritance(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {

    }
}
