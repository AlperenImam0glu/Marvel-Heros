package com.example.marvelheroes.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelheroes.R
import com.example.marvelheroes.service.MarvelAPIService
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}