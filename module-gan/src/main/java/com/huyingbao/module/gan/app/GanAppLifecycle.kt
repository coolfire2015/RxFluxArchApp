package com.huyingbao.module.gan.app

import android.app.Application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.core.arch.utils.RxAndroidInjection
import com.huyingbao.module.gan.GanEventBusIndex
import dagger.android.HasAndroidInjector

import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Application生命周期方法分发类
 *
 * Created by liujunfeng on 2019/8/1.
 */
@RxAppObserver
class GanAppLifecycle(application: Application) : RxAppLifecycle(application) {
    @Inject
    lateinit var ganAppStore: GanAppStore

    /**
     * 使用Dagger.Android完成依赖注入
     */
    init {
        if (application is HasAndroidInjector) {
            RxAndroidInjection.inject(this, application)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        //EventBus使用kapt编译生成的索引文件
        EventBus.builder()
                .addIndex(GanEventBusIndex())
                .eventInheritance(false)
        //全局数据维持AppStore，注册订阅
        ganAppStore.subscribe()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
        //全局数据维持AppStore，取消订阅
        ganAppStore.unsubscribe()
    }
}
