package com.appsfactory.test.ui.search_artist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.appsfactory.test.R
import com.appsfactory.test.databinding.FragmentSearchArtistBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchArtistFragment : Fragment(R.layout.fragment_search_artist) {

    private var _binding: FragmentSearchArtistBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchArtistViewModel>()
    private lateinit var artistAdapter: ArtistAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchArtistBinding.bind(view)

        artistAdapter = ArtistAdapter { }

        binding.apply {
            artistRecyclerView.adapter = artistAdapter
        }

        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.artists.collectLatest {
                        artistAdapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}