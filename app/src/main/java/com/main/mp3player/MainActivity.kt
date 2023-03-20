package com.main.mp3player

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaMetadataRetriever
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.main.mp3player.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getAudioFiles()
    }

    @SuppressLint("Range")
    private fun getAudioFiles() {
        val filePath = "/path/to/song.mp3"
        val music = createMusicFromPath(filePath)
        val list= mutableListOf<Music>()
        val cursor= contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null)
        if(cursor!=null && cursor.moveToFirst()){
            do {
                val filePath= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                list.add(music)
            }while (cursor.moveToNext())
            cursor.close()
        }
        musicAdapter=MusicAdapter(list)
        binding.recylerList.layoutManager=LinearLayoutManager(this)
        binding.recyclerList.adapter=musicAdapter
    }

    private fun requestPermission(){
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1){
            if(grantResults.isEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
        else
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)

    }
    private fun createMusicFromPath(filePath:String):Music {
        val file=File(filePath)
        val retriever=MediaMetadataRetriever()
        val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        return Music(title ?: file.name)
    }
}