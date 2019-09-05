package com.huyingbao.module.gan.action

import android.app.Application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.module.gan.GanEventBusIndex

import org.greenrobot.eventbus.EventBus

/**
 * Application生命周期方法分发类
 *
 * Created by liujunfeng on 2019/8/1.
 */
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
