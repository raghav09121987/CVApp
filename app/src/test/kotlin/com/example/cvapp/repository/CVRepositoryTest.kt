package com.example.cvapp.repository

import com.example.cvapp.models.CVResponse
import com.example.cvapp.network.CVApi
import com.example.cvapp.ui.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CVRepositoryTest {

    //System under test
    private lateinit var cvRepository: CVRepository

    @Mock
    lateinit var cvApi: CVApi

    @BeforeEach
    fun initEach() {
        MockitoAnnotations.initMocks(this)
        cvRepository = CVRepository(cvApi)
    }

    @Test
    fun getCVData_returnSuccess() {

        //Arrange
        var response: CVResponse = CVResponse("abc@gmail.com", "Test User", emptyList(), "000-000-0000", "Dummy Summary")
        var returnedData: Flowable<CVResponse>? = Flowable.just(response)
        whenever(cvApi.getCVData()).thenReturn(returnedData)

        //Act
        var returnedValue: Resource<CVResponse> = cvRepository.getCVData().blockingFirst()

        //Assert
        verify(cvApi).getCVData()
        verifyNoMoreInteractions(cvApi)

        println("Returned value: ${returnedValue.data}")

        returnedValue.data?.pastExperience?.isEmpty()?.let { assert(it) }
        assertEquals(Resource.success(response).data?.email, returnedValue.data?.email)
        assertEquals(Resource.success(response).data?.name, returnedValue.data?.name)
        assertEquals(Resource.success(response).data?.pastExperience, returnedValue.data?.pastExperience)
        assertEquals(Resource.success(response).data?.professionalSummary, returnedValue.data?.professionalSummary)
        assertEquals(Resource.success(response).data?.phones, returnedValue.data?.phones)
    }

    @Test
    fun getCVData_returnFailure() {

        //Arrange
        var failedResponse: CVResponse = CVResponse("null", "null", null, "null", "null")
        var returnedData: Flowable<CVResponse>? = Flowable.just(failedResponse)
        whenever(cvApi.getCVData()).thenReturn(returnedData)

        //Act
        var returnedValue: Resource<CVResponse> = cvRepository.getCVData().blockingFirst()

        //Assert
        verify(cvApi).getCVData()
        verifyNoMoreInteractions(cvApi)

        println("Returned value: ${returnedValue.data}")

        returnedValue.data?.pastExperience?.isNullOrEmpty()?.let { assert(it) }

        assertEquals(Resource.success(failedResponse).data?.email, returnedValue.data?.email)
        assertEquals(Resource.success(failedResponse).data?.name, returnedValue.data?.name)
        assertEquals(Resource.success(failedResponse).data?.pastExperience, returnedValue.data?.pastExperience)
        assertEquals(Resource.success(failedResponse).data?.professionalSummary, returnedValue.data?.professionalSummary)
        assertEquals(Resource.success(failedResponse).data?.phones, returnedValue.data?.phones)
    }
}