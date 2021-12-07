package ru.suleyman.lovecalculator.results

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.suleyman.lovecalculator.databinding.ListResultBinding

@AndroidEntryPoint
class ListResultsActivity: AppCompatActivity() {

    private var _binding: ListResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListResultsViewModel by viewModels()

    private lateinit var adapter: ListResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ListResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListResultAdapter()
        binding.apply {
            rvList.layoutManager = LinearLayoutManager(this@ListResultsActivity)
            rvList.adapter = adapter
        }

        viewModel.getAllList()

        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                when(state) {
                    is ListResultsViewModel.LoveResultsState.Results -> {
                        adapter.addAllResult(state.list)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}