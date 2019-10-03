package com.example.cvapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.cvapp.models.CVResponse
import com.example.cvapp.repository.CVRepository
import javax.inject.Inject

class CVViewModel @Inject constructor(private val repository: CVRepository): ViewModel() {

   var data: MediatorLiveData<Resoource<CVResponse>> = MediatorLiveData()

    fun response(): LiveData<Resoource<CVResponse>> {
        return data
    }
    fun getCV(){
        data.value = Resoource.loading()
        var source = LiveDataReactiveStreams.fromPublisher(repository.getCVData())
        with(data) {
            addSource(source) { res -> data.value = res}
        }
    }
}