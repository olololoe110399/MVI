package com.sun.demo.ui.mviExample3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.demo.R

class Example3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_example_3, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = Example3Fragment()
    }
}
