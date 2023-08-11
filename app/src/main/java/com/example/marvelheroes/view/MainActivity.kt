package com.example.marvelheroes.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.network_alert_title)
        alertDialogBuilder.setMessage(R.string.network_alert_message)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton(R.string.network_alert_negative_button) { dialog, which ->
            ActivityCompat.finishAffinity(this)
            exitProcess(0);
        }
        alertDialogBuilder.setNegativeButton(R.string.network_alert_positive_button) { dialog, which ->
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            this.startActivity(intent)
        }
        alertDialogBuilder.show()
    }

    private fun showToast(text:String){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }


}