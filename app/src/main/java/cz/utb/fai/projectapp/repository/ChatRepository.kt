package cz.utb.fai.projectapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.utb.fai.projectapp.api.ChatResponse.ChatRequest
import cz.utb.fai.projectapp.api.ChatResponse.ChatResponse
import cz.utb.fai.projectapp.api.ChatResponse.Message
import cz.utb.fai.projectapp.api.ChatGPTService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRepository(private val apiService: ChatGPTService) {

    private val CHATGPTMODEL = "gpt-3.5-turbo"
    private val _chatResponse = MutableLiveData<String>()
    val chatResponse: LiveData<String>
        get() = _chatResponse

    fun chatCompletion(message:String){
            try {
                val chatRequest = ChatRequest(
                    arrayListOf(
                        Message("Start chat",
                            "system"),
                        Message(
                            message,
                            "user")
                        ),
                    CHATGPTMODEL
                )

                val response = apiService.chatCompletion(chatRequest).enqueue(object : Callback<ChatResponse>{
                    override fun onResponse(
                        call: Call<ChatResponse>,
                        response: Response<ChatResponse>
                    ) {
                        val code = response.code()
                        if (code == 200){
                            _chatResponse.value = response.body()?.choices?.get(0)?.message?.content
                        } else {
                            Log.d("Error: ", response.errorBody().toString())
                        }

                    }

                    override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
            } catch (e:Exception) {
                e.printStackTrace()
            }
    }
}