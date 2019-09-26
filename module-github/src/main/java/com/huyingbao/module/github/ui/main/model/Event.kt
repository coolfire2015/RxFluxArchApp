package com.huyingbao.module.github.ui.main.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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
        @Embedded
        var actor: Actor? = null,
        @Embedded
        var repo: Repo? = null,
        @Embedded
        var payload: Payload? = null,
        @SerializedName("public")
        var isPublic: Boolean = false,
        var created_at: String? = null,
        @Embedded
        var org: Org? = null
)

data class Actor(
        @ColumnInfo(name = "actor_avatar")
        var avatar_url: String,
        @ColumnInfo(name = "actor_gravatar")
        var gravatar_id: String,
        @ColumnInfo(name = "actor_login")
        var login: String,
        @ColumnInfo(name = "actor_url")
        var url: String
)

data class Org(
        @ColumnInfo(name = "org_avatar")
        var avatar_url: String,
        @ColumnInfo(name = "org_gravatar")
        var gravatar_id: String,
        @ColumnInfo(name = "org_login")
        var login: String,
        @ColumnInfo(name = "org_url")
        var url: String
)

data class Payload(
        var action: String
)

data class Repo(
        @ColumnInfo(name = "repo_name")
        var name: String,
        @ColumnInfo(name = "repo_url")
        var url: String
)