package com.example.cvapp.repository

import com.example.cvapp.models.CVResponse
import com.example.cvapp.network.CVApi
import com.example.cvapp.ui.Resource
import io.reactivex.Flowable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CVRepository @Inject constructor(private var cvApi: CVApi) {
    open fun getCVData() : Flowable<Resource<CVResponse>> {
        return cvApi.getCVData()
            .onErrorReturn { CVResponse(null, null,null,null,null) }
            .map(object : Function<CVResponse, Resource<CVResponse>> {
                override fun apply(cvResponse: CVResponse): Resource<CVResponse> {
                    if(cvResponse.email.equals(null)){
                        return Resource.error("Err!!", null)
                    }else{
                        return Resource.success(cvResponse)
                    }
                }
            })
            .subscribeOn(Schedulers.io())
    }
}