package com.example.cvapp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cvapp.R
import com.example.cvapp.viewmodels.ViewModelsProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class CVActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelsProvidersFactory

    lateinit var viewModel: CVViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(CVViewModel::class.java)
        viewModel.response().observe(this, Observer {
            if(it!=null){
                when(it.status){
                    Resoource.AuthStatus.LOADING -> Log.e("Raghav", "show progress bar")

                    Resoource.AuthStatus. SUCCESS-> Log.e("Raghav", it.data.toString())

                    Resoource.AuthStatus.ERROR -> "Something went Wrong"
                }
            }
        })
        viewModel.getCV()
    }
}
