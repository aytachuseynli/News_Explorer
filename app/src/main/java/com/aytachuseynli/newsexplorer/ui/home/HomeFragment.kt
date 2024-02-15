package com.aytachuseynli.newsexplorer.ui.home

import android.content.Context
import android.content.res.Configuration
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.aytachuseynli.newsexplorer.common.base.BaseFragment
import com.aytachuseynli.newsexplorer.common.utils.Extensions.gone
import com.aytachuseynli.newsexplorer.common.utils.Extensions.visible
import com.aytachuseynli.newsexplorer.databinding.FragmentHomeBinding
import com.aytachuseynli.newsexplorer.ui.home.adapter.NewsAdapter
import com.aytachuseynli.newsexplorer.ui.home.adapter.TopNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    private val viewModel by viewModels<HomeViewModel>()

    private val topNewsAdapter by lazy {
        TopNewsAdapter()
    }
    private val newsAdapter by lazy {
        NewsAdapter()
    }
    private var lang="en"
    private var searchText=""



    override fun observeEvents() {
        lifecycleScope.launch {
            viewModel.homeState.flowWithLifecycle(lifecycle).collectLatest {
                handleState(it)
            }
        }
        lifecycleScope.launch {
            viewModel.selectedLanguage.flowWithLifecycle(lifecycle).collectLatest {
                viewModel.getTopNews(it)
                lang=it
                setAppLocale(updateSelectedLanguage(it))
                viewModel.searchNews(lang,searchText)
                binding.langBtn.text=it.toUpperCase(Locale.ROOT)
            }
        }

    }

    override fun onCreateFinish() {
        setAdapters()
        viewModel.getLanguage()
        searchNews()

    }

    override fun setupListeners() {

        newsAdapter.onClick={

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))

        }
        topNewsAdapter.onClick={

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))

        }

        binding.langBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLanguageFragment())
        }

        newsAdapter.onClickListener={news,img->
            val directions=HomeFragmentDirections.actionHomeFragmentToDetailFragment(news)
            val extras = FragmentNavigatorExtras(
                img to news.urlToImage!!
            )
            findNavController().navigate(directions,extras)
        }

        postponeEnterTransition()
        binding.rvNews.doOnPreDraw {
            startPostponedEnterTransition()
        }

    }

    private fun searchNews(){
        binding.searchText.setOnEditorActionListener { text, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchText = text.text.toString()
                viewModel.searchNews(lang, searchText)

                val inputMethodManager =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(text.windowToken, 0)

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setAdapters() {
        binding.rvNews.adapter=topNewsAdapter
        binding.rvNews.adapter=newsAdapter
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

    private fun handleState(state: HomeUiState) {
        with(binding){

            when(state){
                is HomeUiState.Loading->{ loading.visible()  }

                is HomeUiState.SuccessTopNewsData->{
                    loading.gone()
                    topNewsAdapter.submitList(state.list)
                }
                is HomeUiState.SuccessSearchData->{
                    loading.gone()
                    newsAdapter.submitList(state.list) }

                is HomeUiState.Error->{ loading.gone()}

            }

        }


    }


}