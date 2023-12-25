package cz.utb.fai.projectapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import cz.utb.fai.projectapp.ChatGPTApplication
import cz.utb.fai.projectapp.databinding.ActivitySettingsBinding
import cz.utb.fai.projectapp.utility.SecurePreferences
import cz.utb.fai.projectapp.viewModel.MainViewModel

// Define a class SettingsActivity that extends AppCompatActivity
class SettingsActivity : AppCompatActivity() {

    // Declare a late-initialized variable for binding
    private lateinit var binding: ActivitySettingsBinding

    // Declare a late-initialized variable for the ViewModel
    private lateinit var viewModel: MainViewModel

    // Override the onCreate function
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Inflate the layout for this activity
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        // Set the content view to the root of the binding
        setContentView(binding.root)

        // Get the ViewModelFactory from the application
        val app = (application as ChatGPTApplication).provideViewModelFactory()

        // Get the ViewModel from the ViewModelProvider
        viewModel = ViewModelProvider(this, app).get(MainViewModel::class.java)

        // Set an onClickListener for the delete button
        binding.btnDelete.setOnClickListener {

            // Call the function to delete all messages
            deleteAllMessages()
        }

        // Set an onClickListener for the save key button
        binding.btnSaveKey.setOnClickListener {

            // Get the text from the apiKey field and convert it to a string
            val apiKey = binding.apiKey.text.toString()

            // Check if the apiKey is empty
            if (apiKey.isEmpty()) {

                // Show a toast message if the apiKey is empty
                Toast.makeText(this, "You must enter an API key", Toast.LENGTH_SHORT).show()

            } else { // If the apiKey is not empty

                // Save the apiKey
                SecurePreferences.saveApiKey(this, apiKey)

                // Show a toast message indicating that the apiKey was saved
                Toast.makeText(this, "API Key saved securely", Toast.LENGTH_SHORT).show()
            }
        }

        // Set an onClickListener for the save model button
        binding.btnSaveModel.setOnClickListener {

            // Get the selected item from the modelSelector and convert it to a string
            val selectedModel = binding.modelSelector.selectedItem.toString()

            // Save the selected model
            SecurePreferences.saveModel(this, selectedModel)

            // Show a toast message indicating that the model was saved
            Toast.makeText(this, "Model saved", Toast.LENGTH_SHORT).show()
        }

        // Set click listener for the About button
        binding.btnAbout.setOnClickListener {

            // Create an intent for the AboutActivity
            val intent = Intent(this, AboutActivity::class.java)

            // Start the AboutActivity
            startActivity(intent)
        }
    }

    // Define a function to delete all messages
    private fun deleteAllMessages() {

        // Call the ViewModel's function to delete all messages
        viewModel.deleteAllMessages()

        // Show a toast message indicating that all messages were deleted
        Toast.makeText(this, "All messages deleted", Toast.LENGTH_SHORT).show()
    }
}