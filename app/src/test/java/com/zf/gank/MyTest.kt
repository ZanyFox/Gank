package com.zf.gank

import com.google.gson.Gson
import com.zf.gank.api.WanAndroidService
import com.zf.gank.http.ApiResponse
import com.zf.gank.pojo.MyData
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.concurrent.thread

class MyTest {

    @Test
    fun coroutineTest() {

        val s = ""
        if (String::class.java.isAssignableFrom(Nothing::class.java)) {
            println("哈哈哈哈哈")
        }
    }

    fun hhh(block: String.() -> Unit) {

        block.invoke(" ")
    }

//    @Test
//    fun testFun() = runBlocking {
//        val retrofit = Retrofit.Builder().baseUrl("https://www.wanandroid.com")
////            .addConverterFactory(ScalarsConverterFactory.create())
//            .build().create(WanAndroidService::class.java)
//
//        val response = retrofit.getArticleResponse(0)
//        val gson = Gson()
//        println(response.code())
//        println(response.raw().headers)
//        val str = response.body()?.string()
////        val s = gson.fromJson(str, ApiResponse::class.java)
//
//        val s = gson.fromJson(str, MyData::class.java)
//        println(s.data.datas?.get(0).toString())
//    }



    @Test
    fun testGson()  {

        thread {
            val retrofit = Retrofit.Builder().baseUrl("https://www.wanandroid.com")
                .build().create(WanAndroidService::class.java)

            val response = retrofit.getArticleResponse(0).execute()

            println(response.code())
            println(response.raw().headers)
            val str = response.body()?.string() ?: ""
            val s = testGson<MyData>(str)
            println(s)
        }

        while (true);
    }

    inline fun <reified T> testGson(json: String): T {

        val gson = Gson()
        return gson.fromJson(json, T::class.java)
    }

    @Test
    fun mapTest() {

        val map = hashMapOf<String, String>()
        map["1"] = "1"
        map["2"] = "2"
        println(map.size)
    }

}