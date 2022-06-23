package com.example.atplace

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryAPI {
    @GET("/recommend")    // Keyword.json의 정보를 받아옴
    fun getSearchCategory(
            // 카카오 API 인증키 [필수]
            @Query("category") Category:String,
            @Query("x") X:String,
            @Query("Y") Y:String
            // 검색을 원하는 질의어 [필수]
            // 매개변수 추가 가능
            // @Query("category_group_code") category: String

    ): Call<ResultSearchKeyword>
}