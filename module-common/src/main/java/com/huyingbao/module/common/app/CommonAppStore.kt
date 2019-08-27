package com.huyingbao.module.common.app

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.huyingbao.core.arch.RxFlux
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.arch.model.RxRetry
import com.huyingbao.core.arch.store.RxAppStore
import com.huyingbao.core.base.flux.activity.BaseFluxActivity
import com.huyingbao.module.common.R
import com.huyingbao.module.common.ui.dialog.CommonLoadingDialog
import com.huyingbao.module.common.ui.dialog.CommonLoadingDialogClickListener
import com.huyingbao.module.common.ui.update.action.CommonAction
import com.huyingbao.module.common.ui.update.model.AppBean
import com.huyingbao.module.common.ui.update.model.AppUpdateState
import com.huyingbao.module.common.ui.update.model.getAppState
import com.huyingbao.module.common.ui.update.view.UpdateDialog
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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
        val activity = rxFlux.activityStack.peek() ?: return
        when (val throwable = rxError.throwable) {
            is HttpException -> activity.toast("${throwable.code()}:${throwable.message()}")
            is SocketException -> activity.toast("网络异常!")
            is UnknownHostException -> activity.toast("网络异常!")
            is SocketTimeoutException -> activity.toast("连接超时!")
            else -> activity.toast(throwable.toString())
        }
    }

    /**
     * 接收[RxRetry]，粘性
     */
    @Subscribe(sticky = true)
    fun onRxRetry(rxRetry: RxRetry<*>) {
        val activity = rxFlux.activityStack.peek() ?: return
        val coordinatorLayout = activity.findViewById<CoordinatorLayout>(R.id.cdl_content) ?: return
        if (activity is BaseFluxActivity<*>) {
            Snackbar.make(coordinatorLayout, rxRetry.tag, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry") { activity.baseActionCreator.postRetryAction(rxRetry) }
                    .show()
        }
    }

    /**
     * 显示或隐藏进度对话框，接收[RxLoading]，粘性
     */
    @Subscribe(sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        val activity = rxFlux.activityStack.peek() ?: return
        if (activity is AppCompatActivity) {
            val fragmentByTag = activity.supportFragmentManager.findFragmentByTag(rxLoading.tag)
            if (fragmentByTag == null && rxLoading.isLoading) {
                //显示进度框
                val commonLoadingDialog = CommonLoadingDialog.newInstance()
                commonLoadingDialog.clickListener = object : CommonLoadingDialogClickListener {
                    override fun onCancel() {
                        if (activity is BaseFluxActivity<*>) {
                            activity.baseActionCreator.removeRxAction(tag = rxLoading.tag)
                            activity.toast("取消操作${rxLoading.tag}")
                        }
                    }
                }
                commonLoadingDialog.show(activity.supportFragmentManager, rxLoading.tag)
                return
            }
            if (fragmentByTag is CommonLoadingDialog && !rxLoading.isLoading) {
                //隐藏进度框
                fragmentByTag.dismiss()
            }
        }
    }

    /**
     * 显示更新弹框
     */
    @Subscribe(tags = [CommonAction.GET_APP_LATEST])
    fun onGetAppLatest(rxAction: RxAction) {
        rxAction.getResponse<AppBean>()?.let {
            it.appUpdateState = getAppState(
                    build = it.build,
                    packageName = getApplication<Application>().packageName,
                    application = getApplication<Application>())
            val activity = rxFlux.activityStack.peek() ?: return
            if (it.appUpdateState == AppUpdateState.LATEST) {
                activity.toast("当前已是最新版本")
                return
            }
            if (activity is AppCompatActivity) {
                val fragmentByTag = activity.supportFragmentManager
                        .findFragmentByTag(UpdateDialog::class.java.simpleName)
                if (fragmentByTag == null) {
                    UpdateDialog
                            .newInstance(
                                    apkUrl = it.install_url,
                                    changelog = it.changelog,
                                    appUpdateState = it.appUpdateState)
                            .show(
                                    activity.supportFragmentManager,
                                    UpdateDialog::class.java.simpleName)
                }
            }
        }
    }
}
