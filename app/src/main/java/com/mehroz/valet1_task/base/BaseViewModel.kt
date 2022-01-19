package com.mehroz.valet1_task.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel(){}
