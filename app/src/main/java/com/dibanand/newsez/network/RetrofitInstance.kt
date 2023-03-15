package com.dibanand.newsez.network

class RetrofitInstance {
    companion object {
        /*private val retrofit by lazy {
            val loggingInterceptor = HttpLoggingInterceptor().also {
                it.setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }*/

        /*val newsApiService: NewsApiService by lazy {
            retrofit.create(NewsApiService::class.java)
        }*/
    }
}