package com.sun.demo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sun.demo.R
import com.sun.demo.ui.mviExample1.Example1Fragment
import com.sun.demo.ui.main.adapter.ViewPaperAdapter
import com.sun.demo.ui.mviExample2.Example2Fragment
import com.sun.demo.ui.mviExample3.Example3Fragment
import com.sun.demo.ui.mviExample4.Example4Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewPaperAdapter: ViewPaperAdapter
    private lateinit var example1Fragment: Example1Fragment
    private lateinit var example2Fragment: Example2Fragment
    private lateinit var example3Fragment: Example3Fragment
    private lateinit var example4Fragment: Example4Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
        setUpViewPaperAndTabLayout()
    }

    private fun initFragments() {
        example1Fragment = Example1Fragment.newInstance()
        example2Fragment = Example2Fragment.newInstance()
        example3Fragment = Example3Fragment.newInstance()
        example4Fragment = Example4Fragment.newInstance()
    }

    private fun setUpViewPaperAndTabLayout() {
        val fragments = listOf(example1Fragment, example2Fragment, example3Fragment, example4Fragment)
        viewPaperAdapter = ViewPaperAdapter(supportFragmentManager, fragments)
        mainViewPaper.apply {
            offscreenPageLimit = fragments.size
            adapter = viewPaperAdapter
            mainTabLayout.setupWithViewPager(this)
        }
    }
}
