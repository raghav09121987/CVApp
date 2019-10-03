package com.example.cvapp.di

import dagger.Module
import android.R
import android.R.attr.logo
import android.app.Application
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import dagger.Provides
import javax.inject.Singleton
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.RequestManager
import com.example.cvapp.network.CVApi
import com.example.cvapp.repository.CVRepository
import com.example.cvapp.util.Constant
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.ic_menu_search)
            .error(R.drawable.ic_menu_search)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }
    @Singleton
    @Provides
    fun provideCVApi(retrofit: Retrofit): CVApi {
        return retrofit.create(CVApi::class.java)
    }
    @Singleton
    @Provides
    fun provideCVRepository(cvApi: CVApi): CVRepository {
        return CVRepository(cvApi)
    }

//    @Singleton
//    @Provides
//    fun provideAppDrawable(application: Application): Drawable? {
//        return ContextCompat.getDrawable(application, R.drawable.ic_menu_search)
//    }
}