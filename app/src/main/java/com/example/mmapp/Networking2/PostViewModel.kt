package com.example.mmapp.Networking2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {
    private val _posts = MutableLiveData<String>()
    val posts: LiveData<String>  = _posts

    fun createPost(postRequest: PostRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = postRepository.createPost(postRequest)
                if (response.isSuccessful) {
                    _posts.postValue(response.body().toString())
                } else {
                    _posts.postValue(response.errorBody()?.toString())
                }
            } catch (e: Exception) {
               _posts.postValue(e.message)
            }
        }
    }


    private val _gets=MutableLiveData<String>()
    val gets:LiveData<String> =_gets
    fun getPosts()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try{
                val response=postRepository.getPosts()
                if(response.isSuccessful)
                {
                    _gets.postValue(response.body().toString())
                }
                else
                {
                    _gets.postValue(response.errorBody().toString())
                }
            }
            catch (e:Exception)
            {
                _gets.postValue(e.message)
            }
        }
    }
}



