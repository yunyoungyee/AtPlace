package com.example.atplace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.atplace.databinding.ActivityMidpointBinding
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class midActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "http://4055-175-126-15-53.ngrok.io"
    }

    private lateinit var binding: ActivityMidpointBinding
    private lateinit var mapView: MapView
    private lateinit var button: Button
    private val eventListener = MarkerEventListener(this)


    var mapViewContainer: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initMapView();
        var x = intent.getStringExtra("x");
        var y = intent.getStringExtra("y");
        var address = intent.getStringExtra("address");
        println(x);
        println(y);

        binding = ActivityMidpointBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)


        mapView = binding.wideMapView
        mapView.removeAllPOIItems()

        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))
        mapView.setPOIItemEventListener(eventListener)



        val marker = MapPOIItem()
        marker.apply {
            itemName = address
            mapPoint = MapPoint.mapPointWithGeoCoord(y.toDouble(), x.toDouble())
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.before
            selectedMarkerType = MapPOIItem.MarkerType.CustomImage
            customSelectedImageResourceId = R.drawable.after
            isCustomImageAutoscale = false

            setCustomImageAnchor(0.5f, 1.0f)
        }

        mapView.addPOIItem(marker)
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(y.toDouble(), x.toDouble()), true);

        button = binding.button2
        button.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            intent.apply {
                this.putExtra("x",x )
                this.putExtra("y",y )
                this.putExtra("category","데이트" )
            }
            startActivity(intent)
        }
    }

    // 커스텀 말풍선 클래스
    class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.balloon_layout, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)
        val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            address.text = "getPressedCalloutBalloon"
            return mCalloutBalloon
        }
    }

    // 마커 클릭 이벤트 리스너
    class MarkerEventListener(val context: Context): MapView.POIItemEventListener {
        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            // 마커 클릭 시
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
            // 말풍선 클릭 시 (Deprecated)
            // 이 함수도 작동하지만 그냥 아래 있는 함수에 작성하자

        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
            // 말풍선 클릭 시
        }

        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {
            // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
        }
    }


    override fun finish() {
        // 종료할 때 맵뷰 제거 (맵뷰 2개 이상 동시에 불가)
        binding.wideMapView
        super.finish()
    }
    private fun initMapView() {
        // 맵뷰 초기화 및 컨테이너 레이아웃에 추가
        mapView = MapView(this).also {
            mapViewContainer = RelativeLayout(this)
            mapViewContainer?.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            binding.root.addView(mapViewContainer)
            mapViewContainer?.addView(it)
        }
    }
}