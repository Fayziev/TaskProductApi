package uz.gita.taskproductapi.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.taskproductapi.BuildConfig
import uz.gita.taskproductapi.app.App
import uz.gita.taskproductapi.utils.timber

object ApiClient {

    var retrofit: Retrofit? = null

    fun getProduct(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl("http://94.158.54.194:9092/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()
        return retrofit!!
    }
    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addLogging()
            .build()
    }

    fun OkHttpClient.Builder.addLogging(): OkHttpClient.Builder {
        HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.LOGGING) {
            addInterceptor(ChuckInterceptor(App.instance))
        }
        return this
    }
}