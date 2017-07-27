package cz.lamorak.fleky.adapter

import android.app.Activity
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cz.lamorak.fleky.DetailActivity
import cz.lamorak.fleky.R
import cz.lamorak.fleky.databinding.ItemMemoryBinding
import cz.lamorak.fleky.model.Memory
import cz.lamorak.fleky.util.start

/**
 * Created by ondrej on 26.7.2017.
 *
 */
class MemoriesAdapter (private val memories: List<Memory>) : RecyclerView.Adapter<MemoriesAdapter.MemoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MemoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = DataBindingUtil.inflate<ItemMemoryBinding>(layoutInflater, R.layout.item_memory, parent, false)
        return MemoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoryViewHolder?, position: Int) {
        holder?.bind(memories[position])
    }

    override fun getItemCount(): Int {
        return memories.size
    }

    class MemoryViewHolder(val binding: ItemMemoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(memory: Memory) {
            binding.memory = memory
            itemView.setOnClickListener {
                (itemView.context as Activity).start(DetailActivity::class.java, Pair("memoryId", memory.id))
            }
        }
    }
}