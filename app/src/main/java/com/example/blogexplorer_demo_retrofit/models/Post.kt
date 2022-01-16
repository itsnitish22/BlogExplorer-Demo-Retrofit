package com.example.blogexplorer_demo_retrofit.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    @field:Json(name = "body") val content: String
) : Serializable