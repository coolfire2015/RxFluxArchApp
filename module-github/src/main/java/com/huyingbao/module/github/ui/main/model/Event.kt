package com.huyingbao.module.github.ui.main.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.huyingbao.module.github.ui.login.model.User
import com.huyingbao.module.github.ui.repos.model.Repository
import java.util.*

/**
 * 事件类
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Entity(tableName = "event")
data class Event(
        @PrimaryKey
        var id: String = "",
        var type: String? = null,
        @Ignore
        var actor: User? = null,
        @Ignore
        var repo: Repository? = null,
        @Ignore
        var org: User? = null,
        @SerializedName("public")
        var isPublic: Boolean = false,
        @Ignore
        @SerializedName("created_at")
        var createdAt: Date? = null,
        @Ignore //不想持久的字段
        var payload: EventPayload? = null
)