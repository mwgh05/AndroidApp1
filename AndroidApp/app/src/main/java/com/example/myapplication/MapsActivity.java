package com.example.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Producto.Producto;
import com.example.myapplication.archivos.Controlador;
import com.google.android.gms.maps.model.LatLngBounds;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import android.Manifest;

import com.example.myapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import android.util.Log;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Location currentLocation = null;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String TAG = "MapsActivity";
    private PlacesClient placesClient;
    private ArrayList<String> productos=new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();
    TextInputEditText texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDckEpSeNCLbBMdkGpCwIGX5o3eHJ21sMU");
        }
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtener la ubicación actual solo si se tienen permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            // Solicitar permisos si no están concedidos
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        ImageButton botonMenu = findViewById(R.id.regresar);
        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Botón de menú clickeado");
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        texto=findViewById(R.id.texto);
        ImageButton botonBuscar = findViewById(R.id.buscar);
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarProducto();
            }
        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLocation = location;
                        // Llamar al método onMapReady para mostrar el mapa con la ubicación actual
                        onMapReady(mMap);
                    } else {
                        // Manejar el caso en que currentLocation sea null
                        // Por ejemplo, mostrar un mensaje de error o centrar el mapa en una ubicación predeterminada
                        Toast.makeText(MapsActivity.this, "Ubicación no disponible", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // Si no tienes permiso, solicitarlo al usuario
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, FINE_PERMISSION_CODE);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //9.856402696232688, -83.91262816484455
        if (currentLocation != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Mi Ubicación").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13)); // Ajustar el zoom según sea necesario
            findNearbyHardwareStores();
            //verificarProductosAsignados();
        }
    }

    // Método para realizar la búsqueda de ferreterías cercanas
    private void findNearbyHardwareStores() {
        int radiusMeters = 5000;
        String keyword = "ferretería";
        String apiKey = "AIzaSyDckEpSeNCLbBMdkGpCwIGX5o3eHJ21sMU";
        String urlString = String.format(Locale.US,
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=%d&keyword=%s&key=%s",
                currentLocation.getLatitude(), currentLocation.getLongitude(), radiusMeters, keyword, apiKey);

        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                String response = readStream(in);

                JSONObject jsonObject = new JSONObject(response);
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    result.put("productos", asignarProductos());
                    String placeName = result.getString("name");
                    JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");

                    runOnUiThread(() -> {
                        LatLng latLng = new LatLng(lat, lng);
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(placeName));
                        assert marker != null;
                        marker.setTag(result);
                        markers.add(marker);
                        marker.showInfoWindow();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    private String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String nextLine = "";
        while ((nextLine = reader.readLine()) != null) {
            sb.append(nextLine);
        }
        return sb.toString();
    }


    private JSONArray asignarProductos(){
        Controlador controlador = new Controlador(this);
        productos = controlador.leerProductos();
        if(!productos.isEmpty()) {
            ArrayList<String> seleccionados = new ArrayList<>();
            for (int i = 0; i < 20 && !productos.isEmpty(); i++) {
                Random x = new Random();
                int num = x.nextInt(productos.size());
                seleccionados.add(productos.remove(num));
            }
            return new JSONArray(seleccionados);
        }else{
            return new JSONArray();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permiso denegado. Por favor acepte el permiso.", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void buscarProducto(){
        try {
            Controlador controlador = new Controlador(this);
            productos = controlador.leerProductos();
            String producto = texto.getText().toString().trim();
            if(!productos.isEmpty()) {
                Toast.makeText(this, "Buscando producto...", Toast.LENGTH_SHORT).show();
            }
            if(productos.contains(producto)) {
                for (Marker marker : markers) {
                    JSONObject ferreteria = (JSONObject) marker.getTag();
                    if (ferreteria != null) {
                        JSONArray productosFerreteria = ferreteria.optJSONArray("productos");
                        if (productosFerreteria != null) {
                            for (int i = 0; i < productosFerreteria.length(); i++) {
                                if (productosFerreteria.optString(i).equals(producto)) {
                                    marker.setVisible(true);
                                    break;
                                } else {
                                    marker.setVisible(false);
                                }
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(this, "No se encuentra el producto.", Toast.LENGTH_SHORT).show();
                for (Marker marker : markers) {
                    marker.setVisible(true);
                }
            }
        }catch(Exception e){
            Toast.makeText(this, "No se encuentra el producto!", Toast.LENGTH_SHORT).show();
            for (Marker marker : markers) {
                marker.setVisible(true);
            }
        }
    }
}
