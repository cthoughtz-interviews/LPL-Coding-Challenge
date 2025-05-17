package com.simple.lplcodingchallenge.domain.model


import com.google.gson.annotations.SerializedName

class PostResponse : ArrayList<PostResponse.PostResponseItem>() {
    data class PostResponseItem(
        @SerializedName("body")
        var body: String? = null,
        @SerializedName("email")
        var email: String? = null,
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("postId")
        var postId: Int? = null
    )
}