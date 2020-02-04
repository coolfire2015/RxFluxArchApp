package com.huyingbao.module.epidemic.app

import android.app.Application

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxAppStore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpidemicAppStore @Inject constructor(
        application: Application,
        rxDispatcher: RxDispatcher
) : RxAppStore(application, rxDispatcher)
