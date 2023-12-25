package cz.utb.fai.projectapp.view

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.utb.fai.projectapp.ChatGPTApplication
import cz.utb.fai.projectapp.adapter.MessageAdapter
import cz.utb.fai.projectapp.model.ChatGPTModelFactory
import cz.utb.fai.projectapp.databinding.ActivityviewMainBinding
import cz.utb.fai.projectapp.viewModel.MainViewModel

class MainViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityviewMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MessageAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }

            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 800L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 800L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

        binding = ActivityviewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = application as ChatGPTApplication
        viewModel = ViewModelProvider(this, ChatGPTModelFactory(app.repository, app.database))
            .get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Initialize the adapter with an empty list or initial data
        adapter = MessageAdapter(emptyList())
        binding.messages.adapter = adapter

        // Setup RecyclerView layout manager
        binding.messages.layoutManager = LinearLayoutManager(this)
        layoutManager = LinearLayoutManager(this)
        binding.messages.layoutManager = layoutManager

        viewModel.processToSettings.observe(this) { value ->
            if (value) {
                // go to settings activity
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                viewModel.processToSettings.value = false
            }
        }

        // Initialize ViewModel and observe LiveData
        viewModel.allMessages.observe(this, { messages ->
            adapter.updateData(messages)
            scrollToBottom()
        })
        // Observe apiError
        viewModel.apiError.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun scrollToBottom() {
        adapter.itemCount.takeIf { it > 0 }?.let {
            layoutManager.scrollToPosition(it - 1)
        }
    }
}