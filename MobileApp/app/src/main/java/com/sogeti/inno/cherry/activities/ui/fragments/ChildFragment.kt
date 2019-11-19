package com.sogeti.inno.cherry.activities.ui.fragments




// not used


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sogeti.inno.cherry.R

class ChildFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = ChildFragment()
    }

    private lateinit var viewModel: ChildViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.child_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChildViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
