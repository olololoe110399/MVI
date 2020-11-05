package com.sun.demo.ui.mviExample2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.demo.R

class Example2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_example_2, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = Example2Fragment()
    }
}
