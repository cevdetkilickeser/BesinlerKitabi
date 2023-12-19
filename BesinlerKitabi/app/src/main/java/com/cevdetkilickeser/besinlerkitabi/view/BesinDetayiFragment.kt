package com.cevdetkilickeser.besinlerkitabi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.cevdetkilickeser.besinlerkitabi.R
import com.cevdetkilickeser.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.cevdetkilickeser.besinlerkitabi.utils.gorselIndir
import com.cevdetkilickeser.besinlerkitabi.utils.placeholderYap
import com.cevdetkilickeser.besinlerkitabi.viewmodel.BesinDetayiViewModel

class BesinDetayiFragment : Fragment() {

    private lateinit var binding: FragmentBesinDetayiBinding
    private lateinit var viewModel: BesinDetayiViewModel
    private lateinit var fragment: BesinListesiFragment

    var besinId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_besin_detayi, container, false)
        binding.besinDetayiFragment = this

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        val tempViewModel: BesinDetayiViewModel by viewModels()
        viewModel = tempViewModel*/

        val bundle : BesinDetayiFragmentArgs by navArgs()
        besinId = bundle.besinId

        viewModel = ViewModelProvider(this)[BesinDetayiViewModel::class.java]
        viewModel.roomVerisiniAl(besinId)


        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->

            besin?.let {

                binding.secilenBesin = besin
/*
                binding.isimDetay.text = besin.isim
                binding.kaloriDetay.text = besin.kalori
                binding.karbonhidratDetay.text = besin.karbonhidrat
                binding.proteinDetay.text = besin.protein
                binding.yagDetay.text = besin.yag
                context?.let {
                    binding.ivDetay.gorselIndir(besin.gorsel, placeholderYap(it))
                }*/
            }
        })
    }
}