package cz.utb.fai.projectapp.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import cz.utb.fai.projectapp.R

// Define AboutActivity class that extends AppCompatActivity
class AboutActivity : AppCompatActivity() {
    // This function is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the parent class's onCreate function
        super.onCreate(savedInstanceState)
        // Set the content view to the activity_about layout
        setContentView(R.layout.activity_about)

        // Find the TextView for the chatGPTIcon
        val chatGPTIcon: TextView = findViewById(R.id.chatGPTIcon)
        // Display the HTML for the chatGPTIcon
        showHTML(chatGPTIcon, getString(R.string.linkChatGPTIcon))

        // Find the TextView for the settingsIcon
        val settingsIcon: TextView = findViewById(R.id.settingsIcon)
        // Display the HTML for the settingsIcon
        showHTML(settingsIcon, getString(R.string.linkSettingsIcon))

        // Find the TextView for the sendIcon
        val sendIcon: TextView = findViewById(R.id.sendIcon)
        // Display the HTML for the sendIcon
        showHTML(sendIcon, getString(R.string.linkSendIcon))

        // Find the TextView for the jsonAnimation
        val jsonAnimation: TextView = findViewById(R.id.jsonAnimation)
        // Display the HTML for the jsonAnimation
        showHTML(jsonAnimation, getString(R.string.linkJSONAnimation))
    }

    // This function displays the HTML for a given TextView and link
    private fun showHTML(text: TextView, link: String){
        // If the Android version is Nougat or later
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Set the text of the TextView to the HTML from the link
            text.text = Html.fromHtml(link, Html.FROM_HTML_MODE_COMPACT)
        } else {
            // Set the text of the TextView to the HTML from the link
            text.text = Html.fromHtml(link)
        }
        // Set the movement method of the TextView to LinkMovementMethod
        text.movementMethod = LinkMovementMethod.getInstance()
    }
}