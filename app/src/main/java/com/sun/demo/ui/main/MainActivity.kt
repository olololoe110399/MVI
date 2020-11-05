package com.sun.demo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sun.demo.R
import com.sun.demo.ui.count.CountFragment
import com.sun.demo.ui.main.adapter.ViewPaperAdapter
import com.sun.demo.ui.mvi.mviBasic.Demo1Fragment
import com.sun.demo.ui.mvi.mviVM.Demo2Fragment
import com.sun.demo.ui.mvi.mviVMFixed.Demo3Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewPaperAdapter: ViewPaperAdapter
    private lateinit var countFragment: CountFragment
    private lateinit var demo1Fragment: Demo1Fragment
    private lateinit var demo2Fragment: Demo2Fragment
    private lateinit var demo3Fragment: Demo3Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
        setUpViewPaperAndTabLayout()
    }

    private fun initFragments() {
        countFragment = CountFragment.newInstance()
        demo1Fragment = Demo1Fragment.newInstance()
        demo2Fragment = Demo2Fragment.newInstance()
        demo3Fragment = Demo3Fragment.newInstance()
    }

    private fun setUpViewPaperAndTabLayout() {
        val fragments = listOf(countFragment, demo1Fragment, demo2Fragment, demo3Fragment)
        viewPaperAdapter = ViewPaperAdapter(supportFragmentManager, fragments)
        mainViewPaper.apply {
            offscreenPageLimit = fragments.size
            adapter = viewPaperAdapter
            mainTabLayout.setupWithViewPager(this)
        }
    }
}
