package ru.suleyman.lovecalculator

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request

object LoveCalculatorApi {

    private const val API_URL = "https://love-calculator.p.rapidapi.com/getPercentage?sname=%sn%&fname=%fn%"
    private const val API_KEY = "8fba2ce675msh6fd6101d72df119p1a8e48jsn97f47b492480"

    private val httpClient: OkHttpClient = OkHttpClient()
    private val gsonBuilder = GsonBuilder()
    private val gson = gsonBuilder.create()

    private fun requestLove(sname: String, fName: String): String? {
        val request = Request.Builder()
            .url(API_URL.replace("%sn%", sname).replace("%fn%", fName))
            .addHeader("x-rapidapi-host", "love-calculator.p.rapidapi.com")
            .addHeader("x-rapidapi-key", API_KEY)
            .build()

        val response = httpClient.newCall(request).execute()
        return response.body?.string()
    }

    fun getPercentage(sname: String, fName: String): LoveResultModel? {
        val loveResultJson = requestLove(sname, fName)
        return gson.fromJson(loveResultJson, LoveResultModel::class.java)
    }
}