package owner.credoadmins.com.ui

import android.annotation.SuppressLint
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.support.constraint.Constraints.TAG
import android.support.v7.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.content_youtube_videos.*
import owner.credoadmins.com.R
import owner.credoadmins.com.common.Constants

@Suppress("PLUGIN_WARNING")
@SuppressLint("Registered")
class YoutubeVideos : YouTubeBaseActivity()
{
    var constant = Constants()
    private var youTubeUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_youtube_videos)

        if (savedInstanceState == null) {
            val extras = getIntent().getExtras()
            if (extras == null) {
                youTubeUrl = null
            } else {
                youTubeUrl = extras.getString("value")
            }
        } else {
            youTubeUrl = savedInstanceState.getSerializable("value") as String
        }

        val youTubePlayerView = videoViewYoutube
        val onInitializedListener: YouTubePlayer.OnInitializedListener
        onInitializedListener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, b: Boolean) {
                Log.d(TAG, "onInitializationSuccess: youtube player")
                youTubePlayer.loadVideo(youTubeUrl)
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider,
                youTubeInitializationResult: YouTubeInitializationResult
            ) {
                Log.d(TAG, "onInitializationFailure: youtube player")
            }
        }
        youTubePlayerView.initialize(constant.API_KEY, onInitializedListener)
    }
}
