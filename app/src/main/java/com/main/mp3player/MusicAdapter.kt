package com.main.mp3player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.main.mp3player.databinding.RecyclerListBinding

class MusicAdapter (private val musics: MutableList<Music>):// new var and inside it list of music(data class)
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {


    inner class MusicViewHolder(private val binding: RecyclerListBinding):RecyclerView.ViewHolder(binding.root){

        val musicName=binding.textView
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {//when needed provide a view holder

        val binding=RecyclerListBinding.inflate( LayoutInflater.from(parent.context),parent,false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val currentMusic = musics.get(position)
        holder.apply {
            musicName.text =musics[position].name
        }

    }

    override fun getItemCount(): Int {
        return musics.size
    }

    fun add(music: Music) {
        musics.add(music)
        notifyItemInserted(-1)
    }


}