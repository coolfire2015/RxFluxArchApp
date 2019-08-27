package com.huyingbao.module.common.ui.update.view

import android.os.Bundle
import com.huyingbao.core.arch.view.RxSubscriberView
import com.huyingbao.core.base.common.dialog.BaseCommonDialog
import com.huyingbao.module.common.R
import com.huyingbao.module.common.app.CommonConstants
import com.huyingbao.module.common.ui.update.model.AppUpdateState
import kotlinx.android.synthetic.main.common_dialog_update.*
import org.jetbrains.anko.toast

/**
 * 通用更新页面
 *
 * Created by liujunfeng on 2019/8/27.
 */
class UpdateDialog : BaseCommonDialog(), RxSubscriberView {
    var appUpdateState: AppUpdateState = AppUpdateState.LATEST
    var apkUrl: String? = null
    var updateLog: String? = null
    var archiveFilepath: String? = null

    companion object {
        /**
         * @param apkUrl            下载的地址
         * @param archiveFilepath   已经下载在本机的存储路径
         * @param changelog         更新日志
         * @param appUpdateState    需要操作的App状态
         */
        fun newInstance(apkUrl: String? = null,
                        changelog: String? = null,
                        archiveFilepath: String? = null,
                        appUpdateState: AppUpdateState
        ): UpdateDialog {
            return UpdateDialog().apply {
                arguments = Bundle().apply {
                    //枚举的序数作为参数传入
                    putInt(CommonConstants.Key.INDEX, appUpdateState.ordinal)
                    putString(CommonConstants.Key.URL, apkUrl)
                    putString(CommonConstants.Key.CONTENT, changelog)
                    putString(CommonConstants.Key.FILE_PATH, archiveFilepath)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_dialog_update
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        initView()
        setOnClickCancel()
        setOnClickOk()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        arguments?.let {
            appUpdateState = AppUpdateState.values()[it.getInt(CommonConstants.Key.INDEX, 0)]
            apkUrl = it.getString(CommonConstants.Key.URL)
            updateLog = it.getString(CommonConstants.Key.CONTENT)
            archiveFilepath = it.getString(CommonConstants.Key.FILE_PATH)
        }
        when (appUpdateState) {
            AppUpdateState.INSTALL -> tv_update_title.text = "安装"
            AppUpdateState.UPDATE -> tv_update_title.text = "更新"
            AppUpdateState.DOWNLOAD -> tv_update_title.text = "下载"
            AppUpdateState.LATEST -> tv_update_title.text = "最新"
        }
        tv_update_content.text = updateLog
    }

    /**
     * 点击取消按钮
     */
    private fun setOnClickCancel() {
        tv_update_cancel.setOnClickListener { dismiss() }
    }

    /**
     * 点击确定按钮
     */
    private fun setOnClickOk() {
        tv_update_ok.setOnClickListener {
            when (appUpdateState) {
                AppUpdateState.DOWNLOAD -> context?.toast("下载")
                AppUpdateState.INSTALL -> context?.toast("安装")
                AppUpdateState.UPDATE -> context?.toast("更新")
                AppUpdateState.LATEST -> context?.toast("最新")
            }
        }
    }
}