package com.example.decise.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.decise.R
import com.example.decise.utils.isConnectedToNetwork
import com.example.decise.utils.showDialog

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    protected val TAG: String by lazy {
        this.javaClass.simpleName
    }

    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getFragmentView(), container, false)

        return binding.root
    }

    protected fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configUi()
        setupNavigation()
        if (isConnectedToNetwork(requireContext())) {
            binObserver()
        } else {
            showDialog(
                context = requireActivity(),
                title = getString(R.string.no_internet_connection),
                details = getString(R.string.no_internet_msg),
                resId = R.drawable.ic_round_warning,
                yesContent = getString(R.string.okay),
                noContent = getString(R.string.cancel),
                showNoBtn = false,
                positiveFun = {
                }, {}
            )
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    abstract fun getFragmentView(): Int
    open fun configUi() {}
    open fun setupNavigation() {}

    open fun binObserver() {}


}