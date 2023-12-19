package com.cevdetkilickeser.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cevdetkilickeser.besinlerkitabi.R
import com.cevdetkilickeser.besinlerkitabi.adapter.BesinRVAdapter
import com.cevdetkilickeser.besinlerkitabi.databinding.FragmentBesinListesiBinding
import com.cevdetkilickeser.besinlerkitabi.viewmodel.BesinListesiViewModel

class BesinListesiFragment : Fragment() {
    private var b = 5
    var a = 5
    private lateinit var binding: FragmentBesinListesiBinding
    private lateinit var viewModel: BesinListesiViewModel
    private var besinRVAdapter = BesinRVAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_besin_listesi,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempViewModel: BesinListesiViewModel by viewModels()
        viewModel = tempViewModel

        viewModel.refreshData()

        binding.rvBesinListesi.layoutManager = LinearLayoutManager(context)
        binding.rvBesinListesi.adapter = besinRVAdapter

        binding.swipeRL.setOnRefreshListener {
            binding.prgbarBesinListesi.visibility = View.VISIBLE
            binding.hataBesinListesi.visibility = View.GONE
            binding.rvBesinListesi.visibility = View.GONE
            viewModel.refreshFromInternet()
            binding.swipeRL.isRefreshing = false
        }

        observeLiveData()
    }

/*    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: BesinListesiViewModel by viewModels()
        viewModel = tempViewModel
    }*/


    fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {

                binding.rvBesinListesi.visibility = View.VISIBLE
                besinRVAdapter.besinListesiniGuncelle(besinler)

            }
        })

        viewModel.hataMesaji.observe(viewLifecycleOwner, Observer { hataMesaji ->
            hataMesaji?.let {
                if (it){
                    binding.hataBesinListesi.visibility = View.VISIBLE
                    binding.rvBesinListesi.visibility = View.GONE
                }else{
                    binding.hataBesinListesi.visibility = View.GONE
                }
            }
        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                if ( it){
                    binding.rvBesinListesi.visibility = View.GONE
                    binding.hataBesinListesi.visibility = View.GONE
                    binding.prgbarBesinListesi.visibility = View.VISIBLE
                }else{
                    binding.prgbarBesinListesi.visibility = View.GONE
                }
            }
        })

    }

}