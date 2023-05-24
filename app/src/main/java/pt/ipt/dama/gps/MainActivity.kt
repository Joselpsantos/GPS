package pt.ipt.dama.gps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.CarrierConfigManager.Gps
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), LocationListener{

    private lateinit var locationManager: LocationManager
    private lateinit var textViewGPSPosition: TextView
    private lateinit var bt: Button
    private  var locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt = findViewById(R.id.button)
        bt.setOnClickListener{
            getLocation()
        }
    }

    /**
     * read the user's location
     */
    private fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        //see if the user adds permission to use the gps
        if(
            (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ){
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }

        //reads the GPS position
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    /**
     * This method will receive the gps location and write it to the textview
     */
    override fun onLocationChanged(location: Location){
        textViewGPSPosition= findViewById(R.id.textView)
        textViewGPSPosition.text = "Latitude: \n${location.latitude} \n\nLongitude:\n ${location.longitude}"

    }

    /**
     * Inform the user the result of permission
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == locationPermissionCode){

            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //the user allowed the app to user the GPS
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else{
                //the user does not allow the use of GPS
                Toast.makeText(this, "You have not allowed the GPS", Toast.LENGTH_SHORT).show()
            }
            }
        }

    }

}