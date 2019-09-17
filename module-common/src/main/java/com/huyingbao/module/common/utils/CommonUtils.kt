package com.huyingbao.module.common.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.arch.model.RxRetry
import com.huyingbao.core.base.flux.activity.BaseFluxActivity
import com.huyingbao.module.common.R
import com.huyingbao.module.common.ui.loading.CommonLoadingDialog
import com.huyingbao.module.common.ui.loading.CommonLoadingDialogClickListener
import org.jetbrains.anko.toast
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 公用显示错误信息
 */
fun showCommonError(activity: Activity, rxError: RxError) {
    when (val throwable = rxError.throwable) {
        is HttpException -> activity.toast("${throwable.code()}:${throwable.message()}")
        is SocketException -> activity.toast("网络异常!")
        is UnknownHostException -> activity.toast("网络异常!")
        is SocketTimeoutException -> activity.toast("连接超时!")
        else -> activity.toast(throwable.toString())
    }
}

/**
 * 公用显示错误重试
 */
fun showCommonRetry(activity: Activity, rxRetry: RxRetry<*>) {
    val coordinatorLayout = activity.findViewById<CoordinatorLayout>(R.id.cdl_content) ?: return
    if (activity is BaseFluxActivity<*>) {
        Snackbar.make(coordinatorLayout, rxRetry.tag, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") { activity.baseActionCreator.postRetryAction(rxRetry) }
                .show()
    }
}

/**
 * 公用显示操作进度
 */
fun showCommonLoading(activity: Activity, rxLoading: RxLoading) {
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