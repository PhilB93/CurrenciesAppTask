package com.example.android.presentation.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android.databinding.FragmentCurrenciesBinding
import com.example.android.domain.model.Currency
import com.example.android.domain.utill.Record
import com.example.android.presentation.ui.adapter.AdaptersListener
import com.example.android.presentation.ui.adapter.CurrencyAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CurrenciesFragment : Fragment(), AdaptersListener {

    private var _binding: FragmentCurrenciesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrenciesViewModel by viewModels()

    @Inject
    lateinit var adapter: CurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setOnClickListener(this)

        binding.rvCharacters.adapter = adapter

        lifecycleScope.launch {
            viewModel.response.collect {
                when (it) {
                    is Record.Success -> {
                        binding.progressBar.isVisible = false
                        adapter.submitList(it.data)
                    }
                    is Record.Error -> startToast(it.error.toString())
                    else -> {
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }

    private fun scrollToTop() {
        lifecycleScope.launch {
            delay(500)
            binding.rvCharacters.scrollToPosition(0)
        }
    }

    private fun startToast(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(currency: Currency) {
        viewModel.setCounter(currency)
        adapter.notifyDataSetChanged()
        scrollToTop()
    }
}