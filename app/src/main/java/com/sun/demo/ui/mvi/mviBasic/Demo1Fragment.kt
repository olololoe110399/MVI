package com.sun.demo.ui.mvi.mviBasic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.demo.R

class Demo1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo1, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = Demo1Fragment()
    }
}
