package com.huyingbao.module.ncov.ui.news.model

data class NewsResponse(
        val code: Int,
        val fail: Boolean,
        val message: String,
        val result: NewsResult,
        val success: Boolean
)