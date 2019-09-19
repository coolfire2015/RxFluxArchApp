package com.huyingbao.module.common.app

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.huyingbao.core.arch.RxFlux
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.arch.model.RxRetry
import com.huyingbao.core.arch.store.RxAppStore
import com.huyingbao.module.common.ui.update.action.AppAction
import com.huyingbao.module.common.ui.update.model.AppBean
import com.huyingbao.module.common.ui.update.model.AppUpdateState
import com.huyingbao.module.common.ui.update.model.getAppState
import com.huyingbao.module.common.ui.update.view.CommonUpdateDialog
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.common.utils.showCommonLoading
import com.huyingbao.module.common.utils.showCommonRetry
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 全局Store，可以接受全局的[RxError]、[RxRetry]、[RxLoading]。
 *
 * 通过[RxFlux]持有的Activity栈，来进行对应的操作。
 *
 * Created by liujunfeng on 2019/8/1.
 */
@Singleton
class CommonAppStore @Inject constructor(
        application: Application,
        rxDispatcher: RxDispatcher,
        private val rxFlux: RxFlux
) : RxAppStore(application, rxDispatcher) {
    /**
     * 接收[RxError]，粘性
     */
    @Subscribe(sticky = true)
    fun onRxError(rxError: RxError) {
        rxFlux.activityStack.peek()?.let { showCommonError(it, rxError) }
    }

    /**
     * 接收[RxRetry]，粘性
     */
    @Subscribe(sticky = true)
    fun onRxRetry(rxRetry: RxRetry<*>) {
        rxFlux.activityStack.peek()?.let { showCommonRetry(it, rxRetry) }
    }


    /**
     * 显示或隐藏进度对话框，接收[RxLoading]，粘性
     */
    @Subscribe(sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        rxFlux.activityStack.peek()?.let { showCommonLoading(it, rxLoading) }
    }

    /**
     * 显示更新弹框
     */
    @Subscribe(tags = [AppAction.GET_APP_LATEST])
    fun onGetAppLatest(rxAction: RxAction) {
        rxAction.getResponse<AppBean>()?.let {
            it.appUpdateState = getAppState(
                    build = it.build,
                    packageName = getApplication<Application>().packageName,
                    application = getApplication())
            val activity = rxFlux.activityStack.peek() ?: return
            if (it.appUpdateState == AppUpdateState.LATEST) {
                activity.toast("当前已是最新版本")
                return
            }
            if (activity is AppCompatActivity) {
                val fragmentByTag = activity.supportFragmentManager
                        .findFragmentByTag(CommonUpdateDialog::class.java.simpleName)
                if (fragmentByTag == null) {
                    CommonUpdateDialog
                            .newInstance(
                                    apkUrl = it.install_url,
                                    changelog = it.changelog,
                                    appUpdateState = it.appUpdateState)
                            .show(
                                    activity.supportFragmentManager,
                                    CommonUpdateDialog::class.java.simpleName)
                }
            }
        }
    }
}

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface CommonAppAction {
    companion object {
        /**
         * 滑动到顶部
         */
        const val SCROLL_TO_TOP = "scrollToTop"
        /**
         * 需要获取下一页数据
         */
        const val GET_NEXT_PAGE = "getNextPage"
    }
}
