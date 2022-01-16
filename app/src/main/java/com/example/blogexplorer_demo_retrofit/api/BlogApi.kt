package com.example.blogexplorer_demo_retrofit.api

import com.example.blogexplorer_demo_retrofit.models.Post
import com.example.blogexplorer_demo_retrofit.models.User
import retrofit2.http.*

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("_page") page: Int = 1,
        @Query("_limit") limit: Int = 10
    ): List<Post>

    @Headers("Platform: Android")
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

    //it is the new version of body
    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") postId: Int, @Body post: Post): Post

    //request body only needs to contain the specific changes to the resource
    @PATCH("posts/{id}")
    suspend fun patchPost(@Path("id") postId: Int, @Body params: Map<String, String>): Post

    @DELETE("posts/{id}")
    suspend fun deletePost(@Header("Auth-Token") auth: String, @Path("id") postId: Int)

    @POST("posts/")
    suspend fun createPost(@Body post: Post): Post

    @FormUrlEncoded
    @POST("posts/")
    suspend fun createPostUrlEncode(
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Post
}

//just for remembering
//snake_case
//camelCasing
