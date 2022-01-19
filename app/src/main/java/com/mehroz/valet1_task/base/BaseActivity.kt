package com.mehroz.valet1_task.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mehroz.valet1_task.utils.ProgressDialog


/**
 * Base class used by Activities implementing the MVVM pattern.
 * @param <DB> the type of the Data Binding
 */
abstract class BaseActivity<DB : ViewDataBinding> :
    AppCompatActivity() {

    /**
     * @return [Int] get id for the layout resource
     */
    @LayoutRes
    abstract fun getLayoutRes(): Int


    val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }


    val progress by lazy {
         ProgressDialog.progressDialog(this)
    }

    /**
     * If you want to do some initialization of resources, setting listeners
     * on your activity, you can override this.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        onInit()
    }

    fun hideProgress() {
        progress.hide()
    }

    fun showProgress() {
        progress.show()
    }


    /**
     *  You need override this method.
     *  Initialize view model listen to live data of [viewModel]
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     *
     */
    abstract fun onInit()
    abstract fun screenName() : String
}