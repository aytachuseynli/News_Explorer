package com.aytachuseynli.newsexplorer.ui.detail

import android.content.Intent
import android.net.Uri
import android.transition.TransitionInflater
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aytachuseynli.newsexplorer.R
import com.aytachuseynli.newsexplorer.common.base.BaseFragment
import com.aytachuseynli.newsexplorer.common.utils.Extensions.showMessage
import com.aytachuseynli.newsexplorer.databinding.FragmentDetailBinding
import com.aytachuseynli.newsexplorer.domain.mapper.Mapper.toSavedDTO
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()
    private var isSaved: Boolean = false

    override fun observeEvents() {
        args.newsDetail.title?.let {
            viewModel.isNewsSaved(it) {
                isSaved = it
            }
        }
        viewModel.detailState.observe(viewLifecycleOwner) {
            it?.let {
                handleState(it)
            }
        }

    }

    override fun setupListeners() {
        binding.txtReadMore.setOnClickListener {
            val uri = Uri.parse(args.newsDetail.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onCreateFinish() {
        val newsDetail = args.newsDetail
        with(binding) {
            newsData = newsDetail
            btnBackTxt.text = shortTitle(newsDetail.title.toString(), 11)
            icMore.setOnClickListener {
                popUpMenu(it)
            }

        }

        val animation = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        postponeEnterTransition(500, TimeUnit.MILLISECONDS)

        binding.imgNews.transitionName=args.newsDetail.urlToImage
    }

    private fun handleState(state: DetailUiState) {
        when (state) {
            is DetailUiState.Loading -> {}
            is DetailUiState.SuccessSaveNews -> {
                requireActivity().showMessage(state.message, FancyToast.SUCCESS)
                viewModel.resetData()
            }

            is DetailUiState.SuccessDeleteSave -> {
                requireActivity().showMessage(state.message, FancyToast.SUCCESS)
                viewModel.resetData()
            }

            is DetailUiState.Error -> {
                requireActivity().showMessage(state.message, FancyToast.ERROR)
                viewModel.resetData()
            }
        }


    }

    private fun popUpMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)

        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.gravity = Gravity.END
        val popmenu = popupMenu.menu.findItem(R.id.menu_save)
        if (isSaved) {
            popmenu.setIcon(R.drawable.ic_bookmark)
            popmenu.title = "Unsave"
        } else {
            popmenu.setIcon(R.drawable.ic_unselected_saved)
            popmenu.title = "Save"
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_save -> {
                    isSaved = if (isSaved) {
                        viewModel.deleteSaves(args.newsDetail.toSavedDTO())
                        false
                    } else {
                        viewModel.addSaves(args.newsDetail.toSavedDTO())
                        true
                    }
                    true
                }

                R.id.menu_share -> {
                    true
                }

                else -> false
            }
        }

        try {
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            popupMenu.show()

        }
    }

    private fun shortTitle(txt: String, maxLength: Int): String {
        return if (txt.length <= maxLength) {
            txt
        } else {
            val kisaltilmisMetin = txt.substring(0, maxLength - 3) + "..."
            kisaltilmisMetin
        }
    }
}