package com.example.demo.subscriptionbackgroundflow.myadslibrary

import com.google.gson.annotations.SerializedName

data class Response(

	@SerializedName("ResponseCode")
	val responseCode: String? = null,

	@SerializedName("data")
	val data: ArrayList<DataItem>? = null,

	@SerializedName("ResponseMessage")
	val responseMessage: String? = null
)

data class DataItem(

	@SerializedName("new_id")
	val newId: Int? = null,

	@SerializedName("images")
	val images: ArrayList<ImagesItem>? = null,

	@SerializedName("parent_id")
	val parentId: Int? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("icon")
	val icon: Any? = null,

	@SerializedName("all_childs")
	val allChilds: List<Any>? = null,

	@SerializedName("description")
	val description: Any? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("position")
	val position: Int? = null,

	@SerializedName("category_parameters")
	val categoryParameters: List<Any>? = null
)

data class ImagesItem(

	@SerializedName("zip")
	val zip: String? = null,

	@SerializedName("image")
	val image: String? = null,

	@SerializedName("keywords")
	val keywords: List<Any>? = null,

	@SerializedName("thumb_image")
	val thumbImage: String? = null,

	@SerializedName("coins")
	val coins: Int? = null,

	@SerializedName("video")
	val video: String? = null,

	@SerializedName("is_premium")
	val isPremium: Int? = null,

	@SerializedName("category_id")
	val categoryId: Int? = null,

	@SerializedName("size")
	val size: String? = null,

	@SerializedName("new_category_id")
	val newCategoryId: Int? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("audio")
	val audio: String? = null,

	@SerializedName("position")
	val position: Int? = null
)
