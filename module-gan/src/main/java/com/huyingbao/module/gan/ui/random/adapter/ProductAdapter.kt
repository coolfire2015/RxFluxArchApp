package com.huyingbao.module.gan.ui.random.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.core.image.ImageLoader
import com.huyingbao.core.image.ImageLoaderUtils
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.model.Product

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ProductAdapter(data: List<Product>?) :
        BaseQuickAdapter<Product, BaseViewHolder>(R.layout.gan_recycle_item_product, data) {
    override fun convert(helper: BaseViewHolder, item: Product) {
        val imageLoader = ImageLoader.Builder<String>()
        imageLoader.isCircle = true
        if (item.images != null && item.images!!.isNotEmpty()) {
            imageLoader.resource = item.images!![0]
        } else {
            imageLoader.resource = item.url
        }
        imageLoader.errorHolder = android.R.drawable.ic_menu_camera
        imageLoader.imgView = helper.getView<ImageView>(R.id.iv_product_img)
        ImageLoaderUtils.loadImage(mContext, imageLoader.build())
        helper.setText(R.id.tv_product_name, item.desc)
                .setText(R.id.tv_product_description, item.createdAt)
                .setText(R.id.tv_product_id, "ProductId:" + item.who!!)
    }
}
