package com.huyingbao.module.epidemic.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.model.Banner

/**
 * Created by liujunfeng on 2019/1/1.
 */
class EpidemicAdapter(
        data: MutableList<Banner>?
) : BaseQuickAdapter<Banner, BaseViewHolder>(
        R.layout.wan_recycle_item_banner,
        data) {
    override fun convert(helper: BaseViewHolder, item: Banner?) {
        helper.setText(R.id.tv_item_title, item?.title)
    }
}
