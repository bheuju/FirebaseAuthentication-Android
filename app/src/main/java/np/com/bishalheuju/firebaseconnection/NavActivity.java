package np.com.bishalheuju.firebaseconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;

public class NavActivity extends AppCompatActivity {

    private static final String token = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, token);
        setContentView(R.layout.activity_nav);

        MapView mapView = (MapView) findViewById(R.id.mapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(27.721998, 85.357228))
                        .title("You")
                );
            }
        });

        MapboxNavigation navigation = new MapboxNavigation(this, token);

//        Point origin = Point.fromLngLat(27.697869, 85.323790);
//        Point destination = Point.fromLngLat(27.682820, 85.320019);
        Point origin = Point.fromLngLat(-77.03613, 38.90992);
        Point destination = Point.fromLngLat(-77.0365, 38.8977);

        boolean simulateRoute = true;

        // Create a NavigationViewOptions object to package everything together
        NavigationViewOptions options = NavigationViewOptions.builder()
                .origin(origin)
                .destination(destination)
                .shouldSimulateRoute(simulateRoute)
                .build();

        // Call this method with Context from within an Activity
        NavigationLauncher.startNavigation(this, options);

    }
}
