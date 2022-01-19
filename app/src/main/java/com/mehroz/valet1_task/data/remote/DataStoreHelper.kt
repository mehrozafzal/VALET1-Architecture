/*
package com.mehroz.valet1_task.data.remote

import androidx.lifecycle.*
import com.mehroz.valet1_task.core.Resource
import javax.inject.Inject

open class DataStoreHelper @Inject constructor() {

    suspend fun <T> fetchFromNetwork(networkCall: suspend () -> Resource<T>, liveData: MutableLiveData<Resource<T>>) {
        liveData.postValue(Resource.Loading())
        liveData.postValue(networkCall.invoke())
    }
}

*/
