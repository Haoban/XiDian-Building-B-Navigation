package com.example.navigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private LatLng ll;
    private RoutePlanSearch mSearch;
    private RouteLine route;
    private PlanNode stNode;
    private PlanNode enNode;
    private double start1;
    private double start2;
    private int doorNumber;
    private double end1;
    private double end2;
    private TextView text;


    private void search(int doorNumber){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        switch (doorNumber){
            case 0:
                if(start2 < 108.845){
                    end1 = 34.13244680470119;
                    end2 = 108.83794997553575;
                    builder.setMessage("请从B楼辅楼西侧入口进入");
                }
                else{
                    end1 = 34.132095696252044;
                    end2 = 108.83868658605394;
                    builder.setMessage("请从B楼辅楼东侧入口进入");
                }
                break;
            case 1:
                if(start1 > 34.133 && start2 < 108.845){
                    end1 = 34.13191640626344;
                    end2 = 108.83924353547012;
                    builder.setMessage("请从B楼东侧入口进入");
                }
                else{
                    end1 = 34.13149806146398;
                    end2 = 108.83946811184762;
                    builder.setMessage("请从B楼最东侧入口进入");
                }
                //textOne.setText("请从B楼最东侧入口进入");
                break;
            case 2:
                end1 = 34.13191640626344;
                end2 = 108.83924353547012;
                builder.setMessage("请从B楼东侧入口进入");
                //textOne.setText("请从B楼东侧入口进入");
                break;
            case 3:
                end1 = 34.132215222697866;
                end2 = 108.83847099273154;
                builder.setMessage("请先走到B楼中间大门处");
                //textOne.setText("请从B楼中间大门处进入");
                break;
            case 4:
                end1 = 34.132484156577235;
                end2 = 108.83760861944197;
                builder.setMessage("请先走到B楼西侧入口");
                //textOne.setText("请从B楼西侧门口进入");
                break;
            case 5:
                if(start1 > 34.133){
                    end1 = 34.13288008543904;
                    end2 = 108.83691692419926;
                }
                else{
                    end1 = 34.13276803029072;
                    end2 = 108.83674624615239;
                }
                builder.setMessage("请从B楼最西侧入口进入");
                //textOne.setText("请从B楼最西侧入口进入");
                break;
        }
        builder.setCancelable(true);
        builder.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mapView.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        setTitle("校园导航");
        Intent intent = getIntent();
        start1 = intent.getDoubleExtra("start1",34);
        start2 = intent.getDoubleExtra("start2",108);
        doorNumber = intent.getIntExtra("end",3);
        //textOne = (TextView) findViewById(R.id.text_one);
        search(doorNumber);

        //text = findViewById(R.id.text);
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        //baiduMap.setMyLocationEnabled(true);
        /*
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                enNode = PlanNode.withLocation(latLng);
                stNode = PlanNode.withLocation(ll);
                mSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(enNode));
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
*/
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult result) {
                if (result == null ) {
                    Toast.makeText(MainActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR){
                    // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo();
                    Toast.makeText(MainActivity.this,"有歧义",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();
                    route = result.getRouteLines().get(0);
                    WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });


        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        }else {
            requestLocation();
        }
    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setCoorType("bd09ll");
        mLocationClient.setLocOption(option);
    }

    private void navigateTo(BDLocation location){
        ll = new LatLng(location.getLatitude(),location.getLongitude());
        if (isFirstLocate){
            LatLng start = new LatLng(start1,start2);

            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;


            LatLng end = new LatLng(end1,end2);
            stNode = PlanNode.withLocation(start);
            enNode = PlanNode.withLocation(end);

            mSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(enNode));

        }
        /*
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data = builder.build();
        baiduMap.setMyLocationData(data);
        */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        mSearch.destroy();
        //baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {

            /*if (location.getLocType() == BDLocation.TypeGpsLocation ||
                    location.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(location);
            }*/
            navigateTo(location);
        }
    }
}
