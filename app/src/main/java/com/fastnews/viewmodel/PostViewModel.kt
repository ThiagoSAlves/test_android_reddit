package com.fastnews.viewmodel

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fastnews.mechanism.Coroutines
import com.fastnews.repository.PostRepository
import com.fastnews.service.model.PostData

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private var limit = 25
    private lateinit var posts: MutableLiveData<MutableList<PostData>>

    @UiThread
    fun getPosts(after: String): LiveData<MutableList<PostData>> {
        posts = MutableLiveData()

        Coroutines.ioThenMain({
            PostRepository.getPosts(after, limit)
        }) {
            posts.postValue(it.orEmpty() as MutableList<PostData>?)
        }

        return posts
    }

}