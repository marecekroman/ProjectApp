package cz.utb.fai.projectapp.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

// This is a singleton object of SecurePreferences
object SecurePreferences {

    // Declaring a constant for the preferences file name
    private const val PREFS_FILENAME = "secure_prefs"
    // Declaring a constant for the API key
    private const val API_KEY = "api_key"
    // Declaring a constant for the preferences file key
    private const val PREFERENCES_FILE_KEY = "cz.utb.fai.projectapp.utility"
    // Declaring a constant for the model key
    private const val MODEL_KEY = "model_key"
    // Declaring a constant for the default model
    private const val DEFAULT_MODEL = "GPT-3.5-turbo"

    // Save the selected model to shared preferences
    fun saveModel(context: Context, model: String) {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        prefs.edit().putString(MODEL_KEY, model).apply()
    }

    // Get the selected model from shared preferences
    fun getModel(context: Context): String {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return prefs.getString(MODEL_KEY, DEFAULT_MODEL) ?: DEFAULT_MODEL
    }

    // Get the encrypted shared preferences
    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_FILENAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // Save the API key to encrypted shared preferences
    fun saveApiKey(context: Context, apiKey: String) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putString(API_KEY, apiKey).apply()
    }

    // Get the API key from encrypted shared preferences
    fun getApiKey(context: Context): String? {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getString(API_KEY, null)
    }
}