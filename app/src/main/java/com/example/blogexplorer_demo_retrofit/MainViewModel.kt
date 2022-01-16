package com.example.blogexplorer_demo_retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogexplorer_demo_retrofit.api.RetrofitInstance
import com.example.blogexplorer_demo_retrofit.models.Post
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private var currentPage = 1

    fun getPosts() {
        viewModelScope.launch {
            Log.i(TAG, "Query with page $currentPage")
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val fetchedPosts = RetrofitInstance.api.getPosts(currentPage)
                currentPage+=1
                Log.i(TAG, "Got posts: $fetchedPosts")
                val currentPosts = _posts.value?: emptyList()
                _posts.value = currentPosts +  fetchedPosts
            } catch (e: Exception) {
                Log.e(TAG, "Exception $e")
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }

        }
    }
}
