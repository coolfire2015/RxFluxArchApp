package com.huyingbao.module.epidemic.ui.news.action

interface NewsAction {
    companion object {
        const val GET_NEWS = "getNews"
    }

    fun getNews(page: Int)
}