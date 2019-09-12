package com.huyingbao.module.gan.ui.random.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * 默认情况下，Room使用类名作为数据库表名，SQLite中的表名不区分大小写。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Entity(tableName = "article")
data class Article(
        /**
         * createdAt : 2018-06-08T01:36:11.740Z
         * desc : 2018-06-08
         * publishedAt : 2018-06-08T00:00:00.0Z
         * source : web
         * type : 福利
         * url : http://ww1.sinaimg.cn/large/0065oQSqly1fs34w0jx9jj30j60ootcn.jpg
         * used : true
         * who : lijinshanmx
         */
        @PrimaryKey
        var _id: String = "",
        var createdAt: String? = null,
        var desc: String? = null,
        var publishedAt: String? = null,
        var source: String? = null,
        var type: String? = null,
        var url: String? = null,
        var isUsed: Boolean = false,
        var who: String? = null,
        @Ignore //不想持久的字段
        var images: List<String>? = null
)