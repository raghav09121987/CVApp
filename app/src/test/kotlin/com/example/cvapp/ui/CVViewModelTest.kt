package com.example.cvapp.ui

import androidx.lifecycle.MutableLiveData
import util.LiveDataTestUtil
import com.example.cvapp.models.CVResponse
import com.example.cvapp.repository.CVRepository
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import util.InstantExecutorExtension

@ExtendWith(InstantExecutorExtension::class)
class CVViewModelTest {

    //System under test
    lateinit var cvViewModel: CVViewModel

    @Mock
    private lateinit var cvRepository: CVRepository

    @BeforeEach
    fun initEach() {
        MockitoAnnotations.initMocks(this)
        cvViewModel = CVViewModel(cvRepository)
    }

    @Test
    fun observeEmptyResponse(){
        var cv : Resource<CVResponse> = LiveDataTestUtil.getValue(cvViewModel.response())
        assertNull(cv)
    }

    @Test
    fun getData_returnSuccess(){
        //Arrange
        var response = CVResponse("abc@gmail.com", "Test User", emptyList(), "000-000-0000", "Dummy Summary")
        var returnedValue: MutableLiveData<Resource<CVResponse>> = MutableLiveData()
        returnedValue.value = Resource.success(response)
        whenever(cvRepository.getCVData()).thenReturn(Flowable.just(returnedValue.value))

        //Act
        cvViewModel.getCV()
        var returnResponse: Resource<CVResponse> = LiveDataTestUtil.getValue(cvViewModel.response())
        println("Raghav ${returnResponse.data}")

        //Assert
        Assertions.assertEquals(Resource.success(response).data?.email, returnResponse.data?.email)
        Assertions.assertEquals(Resource.success(response).data?.name, returnResponse.data?.name)
        Assertions.assertEquals(Resource.success(response).data?.pastExperience, returnResponse.data?.pastExperience)
        Assertions.assertEquals(
            Resource.success(response).data?.professionalSummary,
            returnResponse.data?.professionalSummary
        )
        Assertions.assertEquals(Resource.success(response).data?.phones, returnResponse.data?.phones)
    }
}