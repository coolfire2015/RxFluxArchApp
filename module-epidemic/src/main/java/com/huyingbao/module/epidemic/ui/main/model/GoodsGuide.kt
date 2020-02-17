package com.huyingbao.module.epidemic.ui.main.model

/**
 * 商品指南
 */
data class GoodsGuide(
        val categoryName: String,
        val contentImgUrls: List<String>,
        val id: Int,
        val recordStatus: Int,
        val sort: Int,
        val title: String
)