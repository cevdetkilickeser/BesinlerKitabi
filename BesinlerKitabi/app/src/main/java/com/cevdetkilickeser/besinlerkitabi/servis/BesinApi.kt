package com.cevdetkilickeser.besinlerkitabi.servis

import com.cevdetkilickeser.besinlerkitabi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface BesinApi {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Single<List<Besin>>
}