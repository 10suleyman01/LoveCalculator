package ru.suleyman.lovecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.suleyman.lovecalculator.database.LoveListDatabase
import ru.suleyman.lovecalculator.databinding.ActivityMainBinding
import ru.suleyman.lovecalculator.results.ListResultsActivity

@AndroidEntryPoint
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
                                heartBar.startAnimation()
                                heartBar.progress = it.percentage
                                tvResult.text = it.result
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.listResults) {
            val intent = Intent(this, ListResultsActivity::class.java)
            startActivity(intent)
            true
        } else return super.onOptionsItemSelected(item)
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