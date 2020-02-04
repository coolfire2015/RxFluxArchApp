package com.huyingbao.module.epidemic.ui.main.model

/**
 * 防疫谣言
 */
data class IndexRumor(
    val body: String,
    val id: Int,
    val mainSummary: String,
    val rumorType: Int,
    val score: Int,
    val sourceUrl: String,
    val summary: String,
    val title: String
)