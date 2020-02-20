package com.huyingbao.module.epidemic.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.model.AreaProvince

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ProvinceAdapter(
        data: MutableList<AreaProvince>?
) : BaseQuickAdapter<AreaProvince, BaseViewHolder>(
        R.layout.epidemic_recycle_item,
        data) {
    override fun convert(helper: BaseViewHolder, item: AreaProvince?) {
        helper
                .setText(R.id.tv_item_name, item?.provinceShortName)
                .setText(R.id.tv_item_confirmed, item?.confirmedCount?.toString())
                .setText(R.id.tv_item_current, item?.currentConfirmedCount?.toString())
                .setText(R.id.tv_item_cured, item?.curedCount?.toString())
                .setText(R.id.tv_item_dead, item?.deadCount?.toString())
                .setText(R.id.tv_item_suspected, item?.suspectedCount?.toString())
    }
}
