package com.huyingbao.module.wan.app

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.module.wan.ui.article.model.WanResponse
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * 模块自定义的ActionCreator，对所有的接口添加返回结果检查
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class WanActionCreator(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager
) : RxActionCreator(rxDispatcher, rxActionManager) {

    override fun <T> postHttpAction(rxAction: RxAction, httpObservable: Observable<T>) {
        super.postHttpAction(rxAction, httpObservable.flatMap(verifyResponse()))
    }

    override fun <T> postHttpLoadingAction(rxAction: RxAction, httpObservable: Observable<T>) {
        super.postHttpLoadingAction(rxAction, httpObservable.flatMap(verifyResponse()))
    }

    /**
     * 验证接口返回数据是正常
     *
     * 1.没有数据,返回未知异常
     *
     * 2.有数据,返回code不是成功码,返回自定义异常
     */
    fun <T> verifyResponse(): Function<T, Observable<T>> {
        return Function { response ->
            if (response !is WanResponse<*>) {
                return@Function Observable.error(ClassCastException())
            }
            if ((response as WanResponse<*>).errorCode != 0) {
                return@Function Observable.error(IllegalStateException((response as WanResponse<*>).errorMsg))
            }
            Observable.just(response)
        }
    }

    /**
     * 操作重试
     *
     * @param retryNub       失败重试次数
     * @param retryDelayTime 每次重新调用间隔
     */
    fun retryAction(retryNub: Int, retryDelayTime: Long): Function<Observable<out Throwable>, Observable<*>> {
        return Function { observable ->
            observable
                    .zipWith(Observable.range(1, retryNub), BiFunction<Throwable, Int, Int> { _, t2 -> t2 })
                    .flatMap { Observable.timer(retryDelayTime, TimeUnit.SECONDS) }
        }
    }
}
