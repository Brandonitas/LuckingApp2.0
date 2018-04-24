package brandon.example.com.luckingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
    private String latitu = "19.283746";
    private String longitu = "-99.135789";
    private String categoria;
    private String precio;
    private String distancia;
    private String url;

    private String name;
    private String latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        address = (TextView) findViewById(R.id.address);
        datosDestino = (DatosDestino) getIntent().getSerializableExtra("datos");

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        categoria = datosDestino.categorias.get(0);
        precio = datosDestino.price;
        distancia = datosDestino.radio;
        url = makeURL("https://api.foursquare.com/v2/venues/explore?client_id="+Client_ID+"&client_secret="+Client_Secret+"&v=20130815%20&ll="+latitu+","+longitu,
                categoria,
                precio,
                distancia);





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
                    latitud = location.getString("lat");
                    longitud = location.getString("lng");
                    if(name.isEmpty()){
                        address.setText("Lo sentimos, no encontramos un lugar con esas características");
                    }else{
                        address.setText(name);
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
