package cz.utb.fai.projectapp.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecurePreferences {

    private const val PREFS_FILENAME = "secure_prefs"
    private const val API_KEY = "api_key"
    private const val PREFERENCES_FILE_KEY = "cz.utb.fai.projectapp.utility"
    private const val MODEL_KEY = "model_key"
    private const val DEFAULT_MODEL = "GPT-3.5-turbo"

    fun saveModel(context: Context, model: String) {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        prefs.edit().putString(MODEL_KEY, model).apply()
    }

    fun getModel(context: Context): String {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return prefs.getString(MODEL_KEY, DEFAULT_MODEL) ?: DEFAULT_MODEL
    }

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

    fun saveApiKey(context: Context, apiKey: String) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putString(API_KEY, apiKey).apply()
    }

    fun getApiKey(context: Context): String? {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getString(API_KEY, null)
    }
}