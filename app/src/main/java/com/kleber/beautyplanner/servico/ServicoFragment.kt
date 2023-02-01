package com.kleber.beautyplanner.servico

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kleber.beautyplanner.R

class ServicoFragment : Fragment() {

    companion object {
        fun newInstance() = ServicoFragment()
    }

    private lateinit var viewModel: ServicoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_servico, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ServicoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}