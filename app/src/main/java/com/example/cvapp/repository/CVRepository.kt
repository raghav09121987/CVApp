package com.example.cvapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.cvapp.models.CVResponse
import com.example.cvapp.network.CVApi
import com.example.cvapp.ui.Resoource
import io.reactivex.Flowable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CVRepository @Inject constructor(val cvApi: CVApi) {

    fun getCVData() : Flowable<Resoource<CVResponse>> {
        return cvApi.getCVData()
            .onErrorReturn { CVResponse(null, null,null,null,null) }
            .map(object : Function<CVResponse, Resoource<CVResponse>> {
                override fun apply(t: CVResponse): Resoource<CVResponse> {
                    if(t.email.equals(null)){
                        return Resoource.error("Err!!", null)
                    }else{
                        return Resoource.success(t)
                    }
                }
            })
            .subscribeOn(Schedulers.io())
    }
}