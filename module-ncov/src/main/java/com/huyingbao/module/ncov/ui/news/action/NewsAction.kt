package com.huyingbao.module.ncov.ui.news.action

interface NewsAction {
    companion object {
        const val GET_NEWS = "getNews"
    }

    fun getNews(page: Int)
}