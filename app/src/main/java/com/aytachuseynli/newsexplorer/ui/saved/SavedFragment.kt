package com.aytachuseynli.newsexplorer.ui.saved

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aytachuseynli.newsexplorer.common.base.BaseFragment
import com.aytachuseynli.newsexplorer.common.utils.Extensions.gone
import com.aytachuseynli.newsexplorer.common.utils.Extensions.showMessage
import com.aytachuseynli.newsexplorer.common.utils.Extensions.visible
import com.aytachuseynli.newsexplorer.databinding.FragmentSavedBinding
import com.aytachuseynli.newsexplorer.ui.home.adapter.NewsAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : BaseFragment<FragmentSavedBinding>(FragmentSavedBinding::inflate) {

    private val viewModel by viewModels<SavedViewModel>()
    private val newsAdapter by lazy {
        NewsAdapter()
    }

    override fun observeEvents() {
        lifecycleScope.launch {
            viewModel.savedState.flowWithLifecycle(lifecycle).collectLatest {
                handleState(it)
            }
        }

    }

    override fun setupListeners() {
        newsAdapter.onClick = {
            findNavController().navigate(
                SavedFragmentDirections.actionSavedFragmentToDetailFragment(
                    it
                )
            )
        }
    }

    override fun onCreateFinish() {
        binding.rvSaved.adapter = newsAdapter
        viewModel.getSaves()
    }

    private fun handleState(state: SavedUiState) {
        with(binding) {
            when (state) {
                is SavedUiState.Loading -> {
                    progressBar.visible()
                }
                is SavedUiState.SuccessSavedData -> {
                    progressBar.gone()
                    newsAdapter.submitList(state.list.reversed())
                }

                is SavedUiState.Error -> {
                    progressBar.gone()
                    requireActivity().showMessage(state.message, FancyToast.ERROR)
                }
            }
        }

    }

}