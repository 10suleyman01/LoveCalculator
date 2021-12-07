package ru.suleyman.lovecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import ru.suleyman.lovecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: LoveCalcViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnResult.setOnClickListener(this)

        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                when(state) {
                    is LoveCalcViewModel.LoveCalculatorState.Loading -> {
                        if (state.loading) {

                            binding.tvResult.text = "Loading..."
                        }
                    }
                    is LoveCalcViewModel.LoveCalculatorState.Response -> {
                        val model = state.response
                        model?.let {
                            binding.apply {
                                binding.heartBar.startAnimation()
                                heartBar.progress = it.percentage
                                tvResult.text = it.result
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        binding.apply {
            val sname = etFirstName.text.toString()
            val fname = etLastName.text.toString()

            if (sname.isNotEmpty() && fname.isNotEmpty()) {
                viewModel.getPercentage(sname, fname)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}