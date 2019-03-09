package owner.credoadmins.com.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import owner.credoadmins.com.interfaces.CredoAdminApi
import owner.credoadmins.com.common.Urls
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

class CredoAdminSource {

    internal var restAPI: CredoAdminApi? = null

    private fun init() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10 , TimeUnit.SECONDS)
            .writeTimeout(10 , TimeUnit.SECONDS)
            .readTimeout(30 , TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Urls.BASE_URL_CREDOADMIN)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
            .build()

        restAPI = retrofit.create(CredoAdminApi::class.java)
    }

    fun getRestAPI(): CredoAdminApi? {
        if (restAPI == null) init()
        return restAPI
    }
}
