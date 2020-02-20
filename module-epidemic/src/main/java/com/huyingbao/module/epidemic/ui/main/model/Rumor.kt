package com.huyingbao.module.epidemic.ui.main.model

/**
 * 谣言
 */
data class Rumor(
        val _id: Int,
        val body: String,
        val mainSummary: String,
        val rumorType: Int,
        val sourceUrl: String,
        val title: String
)