package com.example.blogexplorer_demo_retrofit.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogexplorer_demo_retrofit.api.RetrofitInstance
import com.example.blogexplorer_demo_retrofit.models.Post
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "EditViewModel"

class EditViewModel : ViewModel() {
    private val _post: MutableLiveData<Post?> = MutableLiveData()
    val post: LiveData<Post?>
        get() = _post

    private val _currentStatus = MutableLiveData<ResultStatus>(ResultStatus.IDLE)
    val currentStatus: LiveData<ResultStatus>
        get() = _currentStatus

    private val _wasDeletionSuccessful = MutableLiveData<Boolean>(false)
    val wasDeletionSuccessful: LiveData<Boolean>
        get() = _wasDeletionSuccessful

    fun updatePost(postId: Int, newPostData: Post) {
        viewModelScope.launch {
            try {
                _currentStatus.value = ResultStatus.WORKING
                _post.value = null
                val api = RetrofitInstance.api
                val updatedPost = api.updatePost(postId, newPostData)
                Log.i(TAG, "Updated Post: $updatedPost")
                _post.value = updatedPost
                _currentStatus.value = ResultStatus.SUCCESS
            } catch (e: Exception) {
                _currentStatus.value = ResultStatus.ERROR
            }
        }
    }

    fun patchPost(postId: Int, title: String, body: String) {
        viewModelScope.launch {
            try {
                _currentStatus.value = ResultStatus.WORKING
                _post.value = null
                val patchedPost =
                    RetrofitInstance.api.patchPost(postId, mapOf("title" to title, "body" to body))
                Log.i(TAG, "Patched Post $patchedPost")
                _post.value = patchedPost
                _currentStatus.value = ResultStatus.SUCCESS
            } catch (e: Exception) {
                _currentStatus.value = ResultStatus.ERROR
            }
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            try {
                _currentStatus.value = ResultStatus.WORKING
                RetrofitInstance.api.deletePost("1234AuthToken", postId)
                _post.value = null
                _wasDeletionSuccessful.value = true
                _currentStatus.value = ResultStatus.SUCCESS
            } catch (e: Exception) {
                _currentStatus.value = ResultStatus.ERROR
            }
        }
    }
}