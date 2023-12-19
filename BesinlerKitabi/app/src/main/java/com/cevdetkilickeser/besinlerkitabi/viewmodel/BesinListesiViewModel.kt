package com.cevdetkilickeser.besinlerkitabi.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.cevdetkilickeser.besinlerkitabi.model.Besin
import com.cevdetkilickeser.besinlerkitabi.servis.ApiUtils
import com.cevdetkilickeser.besinlerkitabi.servis.BesinDatabase
import com.cevdetkilickeser.besinlerkitabi.utils.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val hataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    private val besinApiServis = ApiUtils()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData() {

        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            verileriSQLitetanAl()
        } else {
            verileriInternettenAl()
        }
    }

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSQLitetanAl(){
        besinYukleniyor.value = true

        launch {

            val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri ROOM'dan aldık",Toast.LENGTH_LONG).show()

        }

    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value = true

        disposable.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Besinleri internetten aldık",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        hataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        hataMesaji.value = false
        besinYukleniyor.value = false
    }

    private fun sqliteSakla(besinlerListesi : List<Besin>){

        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteALlBesin()
            val uuidListesi = dao.insertAll(*besinlerListesi.toTypedArray())
            var i = 0
            while (i < besinlerListesi.size){
                besinlerListesi[i].uuid = uuidListesi[i].toInt()
                i = i+1
            }
            besinleriGoster(besinlerListesi)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }

}