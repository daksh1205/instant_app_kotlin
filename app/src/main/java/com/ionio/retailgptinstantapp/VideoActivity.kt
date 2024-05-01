package com.ionio.retailgptinstantapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.RelativeLayout
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class VideoActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var rlProgressLayout: RelativeLayout
    private val database = Firebase.database
    private val videoUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video)
        init()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadVideo()
    }

    private fun loadVideo() {
        val videoRef = database.reference.child("video").ref
        videoRef.get().addOnCompleteListener {
            var videoURI: Uri = Uri.parse(videoUrl)
            if (it.isSuccessful) {
                val videoUrl = it.result.value as String?
                if (!videoUrl.isNullOrBlank()) {
                    Log.d("Firebase video url", "loadVideo: $videoUrl")
                    videoURI = Uri.parse(videoUrl)
                }
            }
            videoView.setVideoURI(videoURI)
            videoView.start()
        }

         /*youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
             override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                 val videoId = "vG2PNdI8axo"
                 youTubePlayer.loadVideo(videoId, 0f)
             }
         })*/

    }

    private fun init() {
        videoView = findViewById(R.id.videoView)
        rlProgressLayout = findViewById(R.id.rlProgressLayout)
        youTubePlayerView = findViewById(R.id.youTubePlayerView)

        videoView.setOnPreparedListener { mp ->
            mp.setOnBufferingUpdateListener { _, percent ->
                if (percent == 100) {
                    rlProgressLayout.visibility = GONE
                }
            }
        }
    }
}