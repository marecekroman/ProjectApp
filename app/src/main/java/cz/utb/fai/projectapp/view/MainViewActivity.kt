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

// Define ViewActivity class that extends AppCompatActivity
class MainViewActivity : AppCompatActivity() {

    // Declare variables for binding, viewModel, adapter, and layoutManager
 private lateinit var binding: ActivityviewMainBinding
 private lateinit var viewModel: MainViewModel
 private lateinit var adapter: MessageAdapter
 private lateinit var layoutManager: LinearLayoutManager

 override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)

    // Install splash screen and set conditions
    installSplashScreen().apply {
        setKeepOnScreenCondition {

            // Keep the splash screen until the viewModel is ready
            !viewModel.isReady.value
        }

        setOnExitAnimationListener { screen ->
            // Set exit animation for the splash screen
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

            // Start the animations
            zoomX.start()
            zoomY.start()
        }
    }

    // Inflate the main activity view
    binding = ActivityviewMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Get the application instance and initialize the viewModel
    val app = application as ChatGPTApplication

    viewModel = ViewModelProvider(this, ChatGPTModelFactory(app.repository, app.database))
        .get(MainViewModel::class.java)

    binding.viewModel = viewModel
    binding.lifecycleOwner = this

    // Initialize the adapter with an empty list
    adapter = MessageAdapter(emptyList())
    binding.messages.adapter = adapter

    // Setup RecyclerView layout manager
    binding.messages.layoutManager = LinearLayoutManager(this)
    layoutManager = LinearLayoutManager(this)
    binding.messages.layoutManager = layoutManager

    // Observe the processToSettings LiveData
    viewModel.processToSettings.observe(this) { value ->

        if (value) {

            // If true, start the SettingsActivity
            val intent = Intent(this, SettingsActivity::class.java)

            startActivity(intent)
            viewModel.processToSettings.value = false
        }
    }

    // Initialize ViewModel and observe allMessages LiveData
    viewModel.allMessages.observe(this) { messages ->

        // Update the adapter data and scroll to the bottom
        adapter.updateData(messages)
        scrollToBottom()
    }

     // Observe apiError LiveData
    viewModel.apiError.observe(this) { errorMessage ->

        if (errorMessage.isNotEmpty()) {

            // If there's an error, show a toast message
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

     // Observing the typing status of the viewModel
     viewModel.isTyping.observe(this) { isTyping ->
         // If the user is typing
         if (isTyping) {
             // Make the animation view visible
             binding.animationView.visibility = View.VISIBLE
             // Start the animation
             binding.animationView.playAnimation()
         } else {
             // If the user is not typing, cancel the animation
             binding.animationView.cancelAnimation()
             // And make the animation view gone
             binding.animationView.visibility = View.GONE
         }
     }
}

    // This function is called when the object is destroyed
    override fun onDestroy() {
        // Cancel any ongoing animation on the animation view
        binding.animationView.cancelAnimation()
        // Call the parent class's onDestroy method
        super.onDestroy()
    }

    // Function to scroll to the bottom of the messages
    private fun scrollToBottom() {

        adapter.itemCount.takeIf { it > 0 }?.let {

            layoutManager.scrollToPosition(it - 1)
        }
    }
}