package com.huyingbao.module.ncov.ui.main.model

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