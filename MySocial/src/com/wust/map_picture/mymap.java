package com.wust.map_picture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMarkerDragListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMapLongClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.entity.Picture;
import com.example.mysocial.R;
import com.example.ui.Editctivity;
import com.example.ui.MainActivity;
import com.example.utils.ActivityUtil;
import com.example.utils.DownLoadUtils;
import com.example.utils.UploadUtils;
import com.wust.map_picture.db.picUser;
import com.wust.map_picture.db.pictureDAO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class mymap extends Fragment implements LocationSource,
		AMapLocationListener, OnMapLongClickListener, OnMapClickListener,
		OnMarkerClickListener, InfoWindowAdapter, OnInfoWindowClickListener {

	private MapView mapView;
	private AMap aMap;
	private View mapLayout;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Marker temp;
	private pictureDAO localDB;
	private List<picUser> list_loc;
	private float ScreenSize;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mapLayout == null) {
			mapLayout = inflater.inflate(R.layout.mymap, null);
			mapView = (MapView) mapLayout.findViewById(R.id.mymap);
			mapView.onCreate(savedInstanceState);
			if (aMap == null) {
				aMap = mapView.getMap();
				setUpMap();
			}
		} else {
			if (mapLayout.getParent() != null) {
				((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
			}
		}
		return mapLayout;
	}

	private void setUpMap() {
		aMap.setOnMapClickListener(this);
		aMap.setOnMapLongClickListener(this);
		aMap.setOnMarkerClickListener(this);
		aMap.setInfoWindowAdapter(this);
		aMap.setOnInfoWindowClickListener(this);

		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.loction));
		myLocationStyle.strokeColor(Color.BLACK);
		myLocationStyle.radiusFillColor(Color.argb(85, 0, 180, 0));
		myLocationStyle.strokeWidth(1.0f);
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);
		aMap.getUiSettings().setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		aMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
		localDB=new pictureDAO(getActivity());
		list_loc=localDB.queryAll();
		load_db();
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
		deactivate();
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

	@Override
	public void onLocationChanged(Location aLocation) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		// TODO 自动生成的方法存根
		if (mListener != null && aLocation != null) {
			mListener.onLocationChanged(aLocation);
		}
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO 自动生成的方法存根
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy
					.getInstance(getActivity());
			mAMapLocationManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		// TODO 自动生成的方法存根
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

	@Override
	public void onInfoWindowClick(final Marker m) {
		 String[] items=new String[]{"查看","分享","上传","删除"};
		 new AlertDialog.Builder(getActivity()) .setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int i) {
					switch (i) {
					case 0:
						 if(m.getTitle()!=""){ File file = new File(m.getTitle()); Intent
						  intent = new Intent();
						  intent.setAction(android.content.Intent.ACTION_VIEW);
						  intent.setDataAndType(Uri.fromFile(file), "image/*");
						 startActivity(intent); }
						break;
					case 1:
						Intent intent = new Intent(getActivity(), Editctivity.class);
						startActivity(intent);
						break;
					case 2:
						  UploadUtils uploaduitls = new UploadUtils(
								  m.getPosition().longitude, m.getPosition().latitude,
									getActivity(), m.getTitle());
								uploaduitls.upload();
						break;
					case 3:
						localDB.delete(m.getTitle());
						m.destroy();
						break;
					default:
						break;
					}
				
			}
		}).setNegativeButton("取消",null).show();		 
	}

	@Override
	public View getInfoContents(Marker arg0) {
		return null;
	}

	@Override
	public View getInfoWindow(final Marker m) {
		View infoWindow = getActivity().getLayoutInflater().inflate(
				R.layout.marker, null);

		Button b = (Button) infoWindow.findViewById(R.id.button1);
		ImageView imageView = (ImageView) infoWindow.findViewById(R.id.picture);

		temp = m;
		if (m.getTitle() == "") {
			b.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 1);
				}
			});
		} else {
			imageView.setAdjustViewBounds(true);
			imageView.setImageBitmap(compressImageFromFile(m.getTitle()));
			b.setVisibility(View.GONE);
		}

		return infoWindow;
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		arg0.showInfoWindow();
		return false;
	}

	public void onMapClick(LatLng arg0) {
		if (temp != null)
			temp.hideInfoWindow();
	}

	@Override
	public void onMapLongClick(LatLng click) {
		Marker a = aMap.addMarker(new MarkerOptions().position(click)
				.visible(true).title(""));
		a.showInfoWindow();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == -1 && data != null) {
			Uri selectedImage = data.getData();
			String[] filepathColum = { MediaColumns.DATA };
			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filepathColum, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filepathColum[0]);
			String PicPath = cursor.getString(columnIndex);
			cursor.close();
			temp.setTitle(PicPath);
			temp.showInfoWindow();
			write_db(temp);
		}
	}
	
	public void load_db() {
		if(list_loc!=null) {
			picUser p;
			for(int i=0;i<list_loc.size();i++) {
				 p=list_loc.get(i);
				 addMark(new LatLng(p.getLat(), p.getLon()), p.getPic_path());
			}
		}
	}
	public void write_db(Marker m) {
		picUser p=new picUser(m.getTitle(), m.getPosition().latitude, m.getPosition().longitude);
		localDB.insert(p);
	}
	
	public void addMark(LatLng L, String PicPath) {
		aMap.addMarker(new MarkerOptions().position(L).visible(true)
				.title(PicPath));
	}
	private Bitmap compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = ScreenSize/2;
		float ww = hh;
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;
		newOpts.inPreferredConfig = Config.ARGB_8888;
		newOpts.inPurgeable = true;
		newOpts.inInputShareable = true;
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;
	}
}
