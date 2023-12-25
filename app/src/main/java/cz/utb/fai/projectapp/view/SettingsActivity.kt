package cz.utb.fai.projectapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cz.utb.fai.projectapp.R
import cz.utb.fai.projectapp.databinding.ActivitySettingsBinding
import cz.utb.fai.projectapp.utility.SecurePreferences

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveKey.setOnClickListener {
            val apiKey = binding.apiKey.text.toString()
            SecurePreferences.saveApiKey(this, apiKey)
            Toast.makeText(this, "API Key saved securely", Toast.LENGTH_SHORT).show()
        }
    }
}