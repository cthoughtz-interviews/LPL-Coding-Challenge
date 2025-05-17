package com.simple.lplcodingchallenge.domain.model


import com.google.gson.annotations.SerializedName

class PostResponse : ArrayList<PostResponse.PostResponseItem>() {
    data class PostResponseItem(
        @SerializedName("body")
        var body: String?,
        @SerializedName("email")
        var email: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("postId")
        var postId: Int?
    )
}