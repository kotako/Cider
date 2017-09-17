package info.kotlin.kotako.cider.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotlin.kotako.cider.R

class TimelineFragment: Fragment(){

    companion object {
        fun newInstance(): Fragment = TimelineFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }
}