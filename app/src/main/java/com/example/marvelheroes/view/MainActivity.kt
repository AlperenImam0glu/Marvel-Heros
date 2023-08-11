package com.example.marvelheroes.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.marvelheroes.R
import com.example.marvelheroes.paging.network.observeconnectivity.ConnectivityObserver
import com.example.marvelheroes.paging.network.observeconnectivity.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        networkObserver()
        setContentView(R.layout.activity_main)
    }

    private fun networkObserver(){
        connectivityObserver = NetworkConnectivityObserver(this)
        connectivityObserver.observe().onEach {
            when (it) {
                ConnectivityObserver.Status.Available -> showToast("Network is available")
                ConnectivityObserver.Status.Unavailable -> showToast("Network is Unavailable")
                ConnectivityObserver.Status.Losing -> showToast("Network is losing")
                ConnectivityObserver.Status.Lost -> showAlert()
            }
        }.launchIn(lifecycleScope)
    }

    private fun showAlert() {

        val alerDialogBuilder = AlertDialog.Builder(this)
        alerDialogBuilder.setTitle("Offline")
        alerDialogBuilder.setMessage("Your network is unavaliable. Check your data or wifi connection")
        alerDialogBuilder.setCancelable(false)
        alerDialogBuilder.setPositiveButton("Close App") { dialog, which ->
            ActivityCompat.finishAffinity(this)
            exitProcess(0);
        }
        alerDialogBuilder.setNegativeButton("Turn On Wifi") { dialog, which ->
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            this.startActivity(intent)
        }

        alerDialogBuilder.show()
    }

    private fun showToast(text:String){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }


}