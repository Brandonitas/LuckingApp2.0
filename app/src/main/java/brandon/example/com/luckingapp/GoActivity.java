package brandon.example.com.luckingapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GoActivity extends AppCompatActivity {

    DatosDestino datosDestino;
    private TextView address;
    private RequestQueue mQueue;
    private String Client_ID = "QMNIRNPLOC000NM1FTM12MMCKLVQXP2FYK1C2SY0VKJBNFBG";
    private String Client_Secret = "CROO0CF21VITHDRVWPSKZS4CNQQJX5KXEXEXGZLIFNS2GQPG";
    private String latitudActual = "19.283746";
    private String longitudActual = "-99.135789";
    private String categoria;
    private String precio;
    private String distancia;
    private String url;

    private String name;
    private Double latitudLugar, longitudLugar;

    private Location location;
    private Marker marker;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;

    private Button go;

    private final int REQUEST_LOCATION_CODE = 99; //cualquier numero

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        go = (Button) findViewById(R.id.gobutton);
        address = (TextView) findViewById(R.id.address);
        datosDestino = (DatosDestino) getIntent().getSerializableExtra("datos");

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        categoria = datosDestino.categorias.get(0);
        precio = datosDestino.price;
        distancia = datosDestino.radio;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){//validamos la version
            checkLocationPermission();
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            //Toast.makeText(GoActivity.this, location.toString(), Toast.LENGTH_SHORT).show();
                            latitudActual = String.valueOf(location.getLatitude());
                            longitudActual  = String.valueOf(location.getLongitude());
                            Log.e("LAT", latitudActual);
                            Log.e("LON",longitudActual);

                            url = makeURL("https://api.foursquare.com/v2/venues/explore?client_id="+Client_ID+"&client_secret="+Client_Secret+"&v=20130815%20&ll="+latitudActual+","+longitudActual,
                                    categoria,
                                    precio,
                                    distancia);
                        }
                    }
                });




        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoActivity.this,MapsActivity.class);
                intent.putExtra("lat", latitudLugar);
                intent.putExtra("lon", longitudLugar);
                startActivity(intent);
            }
        });




    }



    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }

    private String makeURL(String url, String categoria, String precio, String distancia){
        String newUrl = url + "&query="+categoria;
        //String newUrl = url + "&query="+categoria+"&price="+precio+"&radius="+distancia;
        Log.e("MYLOG",newUrl);
        executeJSON(newUrl);

        return url;
    }

    private void executeJSON(String url){
        //adapter.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject data = response.getJSONObject("response");
                    JSONArray groups = data.getJSONArray("groups");
                    JSONObject jsonObject = groups.getJSONObject(0);
                    JSONArray items = jsonObject.getJSONArray("items");
                    int random = (int) (Math.random() * items.length());
                    Log.e("MYLOG",String.valueOf(random));
                    JSONObject place = items.getJSONObject(random);
                    JSONObject placedetails = place.getJSONObject("venue");
                    name = placedetails.getString("name");
                    Log.e("NAME",name);
                    JSONObject location = placedetails.getJSONObject("location");
                    latitudLugar = location.getDouble("lat");
                    longitudLugar = location.getDouble("lng");

                    if(name.isEmpty()){
                        address.setText("Lo sentimos, no encontramos un lugar con esas características");
                    }else{
                        address.setText(name);
                        go.setVisibility(View.VISIBLE);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }


}
