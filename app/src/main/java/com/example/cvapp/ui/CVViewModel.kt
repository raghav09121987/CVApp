package com.example.cvapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.cvapp.models.CVResponse
import com.example.cvapp.repository.CVRepository
import javax.inject.Inject

class CVViewModel @Inject constructor(private val repository: CVRepository): ViewModel() {

   var data: MediatorLiveData<Resource<CVResponse>> = MediatorLiveData()

    fun response(): LiveData<Resource<CVResponse>> {
        return data
    }
    fun getCV(){
        data.value = Resource.loading()
        var source = LiveDataReactiveStreams.fromPublisher(repository.getCVData())
        with(data) {
            addSource(source) { res -> data.value = res}
        }
    }
}