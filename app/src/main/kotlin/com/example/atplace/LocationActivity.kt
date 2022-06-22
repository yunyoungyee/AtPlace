package com.example.atplace

import net.daum.mf.map.api.*;


import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atplace.databinding.ActivityLocationBinding
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest


class LocationActivity : AppCompatActivity() {
    companion object {
        const val BASE_URL = "http://33c9-175-126-15-53.ngrok.io"
    }

    private lateinit var binding : ActivityLocationBinding
    private val listItems = arrayListOf<ListLayout>()   // 리사이클러 뷰 아이템
    private val listAdapter = ListAdapter(listItems)    // 리사이클러 뷰 어댑터
    private var pageNumber = 1      // 검색 페이지 번호
    private var keyword = ""        // 검색 키워드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // 리사이클러 뷰
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = listAdapter

        fun getAppKeyHash() {
            try {
                val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                for (i in info.signatures) {
                    val md: MessageDigest = MessageDigest.getInstance("SHA")
                    md.update(i.toByteArray())

                    val something = String(Base64.encode(md.digest(), 0)!!)
                    Log.e("Debug key", something)
                }
            } catch (e: Exception) {
                Log.e("Not found", e.toString())
            }
        }
            getAppKeyHash()
                listAdapter.setItemClickListener(object: ListAdapter.OnItemClickListener {
            override fun onClick(v: View,d:Address, position: Int) {
                val mapPoint = MapPoint.mapPointWithGeoCoord(listItems[position].y, listItems[position].x)
                binding.mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true)
            }
        })


                // 검색 버튼
        binding.searchButton.setOnClickListener {
            keyword = binding.searchBarInputView.text.toString()
            pageNumber = 1
            searchKeyword(keyword)
        }
        // 클릭시 화면전환 및 값 전달
        listAdapter.setItemClickListener(object: ListAdapter.OnItemClickListener{
           override fun onClick(v: View, data: Address, position: Int) {
               val intent = Intent(this@LocationActivity, MainActivity::class.java)
               intent.putExtra("Address",data.address)
               intent.putExtra("name",data.name)
               intent.putExtra("road_address",data.road)
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
               startActivity(intent)
            }
        })

    }

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String) {
        val retrofit = Retrofit.Builder()          // Retrofit 구성
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(KakaoAPI::class.java)            // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(keyword)    // 검색 조건 입력
        // API 서버에 요청
        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(call: Call<ResultSearchKeyword>, response: Response<ResultSearchKeyword>) {
                // 통신 성공
                addItemsAndMarkers(response.body())
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("LocalSearch", "통신 실패: ${t.message}")
            }
        })
    }

    // 검색 결과 처리 함수
    private fun addItemsAndMarkers(searchResult: ResultSearchKeyword?) {
        var data = searchResult?.data;
        if (!data?.documents.isNullOrEmpty()) {
            // 검색 결과 있음
            listItems.clear()                   // 리스트 초기화
            binding.mapView.removeAllPOIItems()
            for (document in data!!.documents) {
                // 결과를 리사이클러 뷰에 추가
                val item = ListLayout(document.place_name,
                        document.road_address_name,
                        document.address_name,
                        document.x.toDouble(),
                        document.y.toDouble())
                listItems.add(item)
                // 지도에 마커 추가
                val point = MapPOIItem()
                point.apply {
                    itemName = document.place_name
                    mapPoint = MapPoint.mapPointWithGeoCoord(document.y.toDouble(),
                            document.x.toDouble())
                    markerType = MapPOIItem.MarkerType.BluePin
                    selectedMarkerType = MapPOIItem.MarkerType.RedPin
                }
                binding.mapView.addPOIItem(point)
            }
            listAdapter.notifyDataSetChanged()

        } else {
            // 검색 결과 없음
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

}