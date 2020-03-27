package com.daemonw.deviceinfo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daemonw.deviceinfo.R

class MainFragment : Fragment() {

    private var mViewModel: NetworkInfoViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(NetworkInfoViewModel::class.java)
        // TODO: Use the ViewModel
        mViewModel?.get()?.observe(viewLifecycleOwner, Observer {

        })
    }

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
