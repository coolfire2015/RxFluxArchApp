package com.huyingbao.module.github.ui.star.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.github.BuildConfig
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * 点赞模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@ActivityScope
class StarActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), StarAction {
}
