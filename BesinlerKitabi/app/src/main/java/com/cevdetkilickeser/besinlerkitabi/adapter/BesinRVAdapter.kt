package com.cevdetkilickeser.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.besinlerkitabi.R
import com.cevdetkilickeser.besinlerkitabi.databinding.CardListeBinding
import com.cevdetkilickeser.besinlerkitabi.model.Besin
import com.cevdetkilickeser.besinlerkitabi.utils.gorselIndir
import com.cevdetkilickeser.besinlerkitabi.utils.placeholderYap
import com.cevdetkilickeser.besinlerkitabi.view.BesinListesiFragmentDirections

class BesinRVAdapter (val besinListesi:ArrayList<Besin>) : RecyclerView.Adapter<BesinRVAdapter.CardListeHolder>() {
    inner class CardListeHolder(binding: CardListeBinding) : RecyclerView.ViewHolder(binding.root){
        val binding : CardListeBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardListeBinding.inflate(layoutInflater,parent,false)
        return CardListeHolder(binding)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: CardListeHolder, position: Int) {
        val besin = besinListesi.get(position)
        val uuid = besin.uuid
        val b = holder.binding
        b.besin = besinListesi[position]

        b.ivRV.setOnClickListener {
            val action = BesinListesiFragmentDirections.listedenDetaya(uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun besinListesiniGuncelle(yeniBesinListesi:List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

}