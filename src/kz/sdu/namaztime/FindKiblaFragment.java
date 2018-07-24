package kz.sdu.namaztime;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FindKiblaFragment extends Fragment {

	private GoogleMap map;
	private static final LatLng kiblaLL = new LatLng(21.422510, 39.826168);
	private View v;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		v = inflater.inflate(R.layout.find_kibla_fragment, container, false);

		try {
			if (map == null) {
				map = ((SupportMapFragment) MainFragmentActivity.fragmentManager
						.findFragmentById(R.id.kibla_map)).getMap();
			}
		} catch (Exception e) {
		}

		if (map != null) {
			setupMap();
		}
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if (map != null) {
			setupMap();
		}
		if (map == null) {
			map = ((SupportMapFragment) MainFragmentActivity.fragmentManager
					.findFragmentById(R.id.kibla_map)).getMap();
			if (map != null) {
				setupMap();
			}
		}
	}

	private double latitude;
	private double longitude;

	private void setupMap() {
		LocationManager lm = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = lm.getBestProvider(criteria, false);
		Location location = lm.getLastKnownLocation(provider);
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
		} else {
			latitude = 43.20785422;
			longitude = 76.66996434;
		}
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				new LocationListener() {
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
					}

					public void onProviderEnabled(String provider) {
					}

					public void onProviderDisabled(String provider) {
					}

					public void onLocationChanged(Location location) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						map.clear();
						map.addMarker(new MarkerOptions().position(new LatLng(
								latitude, longitude)));
						map.addPolyline(new PolylineOptions()
								.add(new LatLng(latitude, latitude), kiblaLL)
								.width(5).color(Color.RED).geodesic(true));
					}
				});

		map.addMarker(new MarkerOptions().position(new LatLng(latitude,
				longitude)));
		map.addPolyline(new PolylineOptions()
				.add(new LatLng(latitude, longitude), kiblaLL).width(5)
				.color(Color.RED).geodesic(true));

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(18).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		map.setOnMapClickListener(new OnMapClickListener() {
			public void onMapClick(LatLng ll) {
				map.clear();
				map.addMarker(new MarkerOptions().position(new LatLng(
						ll.latitude, ll.longitude)));
				map.addPolyline(new PolylineOptions()
						.add(new LatLng(ll.latitude, ll.longitude), kiblaLL)
						.width(5).color(Color.RED).geodesic(true));
			}
		});
	}

	public void onDestroyView() {
		super.onDestroyView();
		map = null;
		v = null;
	}
}
