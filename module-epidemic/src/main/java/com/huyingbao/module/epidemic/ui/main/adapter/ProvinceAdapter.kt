package com.huyingbao.module.epidemic.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.model.AreaProvinceStat

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ProvinceAdapter(
        data: MutableList<AreaProvinceStat>?
) : BaseQuickAdapter<AreaProvinceStat, BaseViewHolder>(
        R.layout.epidemic_recycle_item,
        data) {
    override fun convert(helper: BaseViewHolder, item: AreaProvinceStat?) {
        helper.setText(R.id.tv_item_title, item?.provinceName)
    }
}
