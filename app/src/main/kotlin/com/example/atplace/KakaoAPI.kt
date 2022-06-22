package com.example.atplace

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoAPI {
    @GET("/searching")    // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
            // 카카오 API 인증키 [필수]
            @Query("keyword") Keyword: String             // 검색을 원하는 질의어 [필수]
            // 매개변수 추가 가능
            // @Query("category_group_code") category: String

    ): Call<ResultSearchKeyword>    // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김
}