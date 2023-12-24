package cz.utb.fai.projectapp.view

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import cz.utb.fai.projectapp.model.ChatGPTModelFactory
import cz.utb.fai.projectapp.databinding.ActivityviewMainBinding
import cz.utb.fai.projectapp.mainViewModel.MainViewModel

class MainViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityviewMainBinding
    private lateinit var viewModel: MainViewModel
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
        viewModel = ViewModelProvider(this, ChatGPTModelFactory(app.repository))
            .get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this



    }
}