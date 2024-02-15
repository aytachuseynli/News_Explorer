package com.aytachuseynli.newsexplorer.ui.language

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aytachuseynli.newsexplorer.R
import com.aytachuseynli.newsexplorer.common.base.BaseFragment
import com.aytachuseynli.newsexplorer.databinding.FragmentLanguageBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>(FragmentLanguageBinding::inflate) {

    private val viewModel by viewModels<LanguageViewModel>()

    override fun observeEvents() {

    }

    override fun onCreateFinish() {

    }

    override fun setupListeners() {
        with(binding){
            binding.langBackBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            binding.en.setOnClickListener {
                viewModel.saveLang("en")

                setAppLocale(updateSelectedLanguage("en"))

                findNavController().popBackStack()
            }
            binding.ru.setOnClickListener {
                viewModel.saveLang("ru")
                setAppLocale(updateSelectedLanguage("ru"))

                findNavController().popBackStack()
            }
        }
    }

    private fun setAppLocale(locale: Locale) {
        val resources = requireContext().resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun updateSelectedLanguage(languageCode: String?) : Locale {
        return when (languageCode) {
            "en" -> Locale("en")
            "ru" -> Locale("ru")
            else -> Locale("en")
        }
    }

}