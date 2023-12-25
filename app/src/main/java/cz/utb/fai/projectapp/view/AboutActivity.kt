package cz.utb.fai.projectapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.utb.fai.projectapp.R

// Define AboutActivity class that extends AppCompatActivity
class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)
    }
}