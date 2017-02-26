package com.wust.map_picture;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMapLongClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.entity.Picture;
import com.example.mysocial.R;
import com.example.utils.ActivityUtil;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class map extends Fragment implements OnMapClickListener,
		OnMarkerClickListener, InfoWindowAdapter {

	private MapView mapView;
	private AMap aMap;
	private View mapLayout;
	private Marker temp;
	private List<Picture> list=new ArrayList<Picture>();
	private Picture pic;
	private int ScreenSize;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mapLayout == null) {
			mapLayout = inflater.inflate(R.layout.map, null);
			mapView = (MapView) mapLayout.findViewById(R.id.map);
			mapView.onCreate(savedInstanceState);
			if (aMap == null) {
				aMap = mapView.getMap();
				setupMap();
			}
		} else {
			if (mapLayout.getParent() != null) {
				((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
			}
		}
		return mapLayout;
	}

	public void setupMap() {
		aMap.setOnMapClickListener(this);
		aMap.setOnMarkerClickListener(this);
		aMap.setInfoWindowAdapter(this);
		aMap.moveCamera(CameraUpdateFactory.zoomTo(5.0f));
		getdata();
		loadMark();
		ScreenSize=ActivityUtil.getScreenSize()[0];
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onPause() {

		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	public void onMapClick(LatLng arg0) {
		temp.hideInfoWindow();
	}

	@Override
	public View getInfoContents(Marker arg0) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		 View infoWindow =getActivity().getLayoutInflater().inflate(R.layout.marker, null);
		 ImageView imageView = (ImageView)infoWindow.findViewById(R.id.picture);
		 infoWindow.findViewById(R.id.button1).setVisibility(View.GONE);
		 pic.getPic().loadImageThumbnail(getActivity(), imageView, ScreenSize*2/3, ScreenSize*2/3);
		 return infoWindow;
	}

	@Override
	public boolean onMarkerClick(Marker m) {
		temp = m;
		pic=list.get(Integer.parseInt(m.getTitle()));
		m.showInfoWindow();
		return false;
	}

	public void loadMark() {
		Picture p;
		for (int i = 0; i < list.size(); i++) {
			p = list.get(i);
			addMark(p.getLatitude(), p.getLongitude(),i);
		}
	}

	public void addMark(double lat, double lon,int id) {
		LatLng L = new LatLng(lat, lon);
		aMap.addMarker(new MarkerOptions().position(L).title(""+id).visible(true));
	}
	
	public void getdata(){
		BmobQuery<Picture> query=new BmobQuery<Picture>();
		query.findObjects(getActivity(), new FindListener<Picture>() {		
			@Override
			public void onSuccess(List<Picture> data) {
				Toast.makeText(getActivity(), "Ë¢ÐÂ³É¹¦", Toast.LENGTH_LONG).show();
				list=data;
				loadMark();		
			} 		
			@Override
			public void onError(int arg0, String arg1) {			
			}
		});

	}

}