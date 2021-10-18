package com.example.android.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.calculator.databinding.ItemKeypadBinding

class KeypadAdapter(private val clickListener: DigitClickListener) :
    RecyclerView.Adapter<KeypadAdapter.KeypadViewHolder>() {

    private val data = listOf(7, 8, 9, 4, 5, 6, 1, 2, 3)

    inner class KeypadViewHolder(private val binding: ItemKeypadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(digit: Int, clickListener: DigitClickListener) {
            with(binding) {
                digitText.text = digit.toString()
                root.setOnClickListener {
                    clickListener.onClick(digit)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeypadViewHolder {
        val binding = ItemKeypadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeypadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeypadViewHolder, position: Int) {
        val digit = data[position]
        holder.bind(digit, clickListener)
    }

    class DigitClickListener(val clickListener: (Int) -> Unit) {
        fun onClick(digit: Int) = clickListener(digit)
    }

    override fun getItemCount(): Int = data.size
}