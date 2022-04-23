package com.example.my_todo_app.viewmodel

import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_todo_app.R
import com.example.my_todo_app.model.Note
import com.example.my_todo_app.model.QuoteResponseItem
import com.example.my_todo_app.repo.NoteRepository
import com.example.my_todo_app.retrofit.Retrofit
import kotlinx.coroutines.launch
import retrofit2.HttpException
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.lang.Exception

class DashboardActivityViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    val TAG = "DashboardActivityVM"
    private val _quote : MutableLiveData<QuoteResponseItem> = MutableLiveData()
    val quote : LiveData<QuoteResponseItem> = _quote

    fun getNotes() : LiveData<List<Note>> = repository.notes

    fun getRandomQuote() = viewModelScope.launch {
        val response = try{
            Retrofit.zenQuotesApi.getRandomQuote()
        }catch (e : HttpException){
            Log.e(TAG, "No Internet Connection")
            return@launch
        }catch (e : Exception){
            Log.e(TAG, "An unexpected error occur")
            return@launch
        }

        if(response.isSuccessful){
            response.body()?.let { quoteResponse ->
                //first element of response, tho response returns only 1 item
                _quote.postValue(quoteResponse[0])
            }
        }else{
            Log.e(TAG, "Response not successful. ${response.errorBody().toString()}")
        }
    }

}