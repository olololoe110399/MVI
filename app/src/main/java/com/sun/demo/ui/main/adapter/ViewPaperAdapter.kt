package com.sun.demo.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPaperAdapter(
    fm: FragmentManager, private val fragments: List<Fragment>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int) = when (position) {
        0 -> "Count"
        1 -> "MVI"
        2 -> "MVI+VM"
        3 -> "MVI+VM+Fixed"
        else -> null
    }
}
