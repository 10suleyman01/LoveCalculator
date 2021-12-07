package ru.suleyman.lovecalculator.results

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.suleyman.lovecalculator.LoveResultModel
import ru.suleyman.lovecalculator.databinding.ItemResultBinding

class ListResultAdapter : RecyclerView.Adapter<ListResultAdapter.ListResultHolder>() {

    private val list = mutableListOf<LoveResultModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun addAllResult(newList: List<LoveResultModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListResultHolder {
        return ListResultHolder(
            ItemResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListResultHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ListResultHolder(
        private val itemResultBinding: ItemResultBinding
    ) : RecyclerView.ViewHolder(itemResultBinding.root) {

        fun bind(loveResultModel: LoveResultModel) {
            itemResultBinding.apply {
                tvSname.text = loveResultModel.sname.trim()
                tvFname.text = loveResultModel.fname.trim()
                itemHeartBar.progress = loveResultModel.percentage
            }
        }

    }
}