package com.towhid.fieldbuzz.fragment.fg_success

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.towhid.fieldbuzz.R
import kotlinx.android.synthetic.main.fragment_success.*
import kotlinx.android.synthetic.main.fragment_success.view.*
import nl.dionsegijn.konfetti.models.Shape

class SuccessFragment : Fragment() {
    private var isFirstTime: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isFirstTime = it.getBoolean("FIRSTTIME")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_success, container, false)

        init(rootView)
        action(rootView)
        return rootView
    }

    private fun init(rootView: View?) {

    }

    private fun action(rootView: View) {
        if (isFirstTime==true){
            rootView.konfettiView.setVisibility(View.VISIBLE);
            rootView.konfettiView.build()
                    .addColors(Color.RED,Color.GREEN,Color.BLUE)
                    .setDirection(0.0, 359.0)
                    .setSpeed(2f, 10f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .setPosition(-50f, rootView.konfettiView.getWidth() + 50f, -50f, -50f)
                    .streamFor(300, 3000L);
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isFirstTime: Boolean) =
                SuccessFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean("FIRSTTIME", isFirstTime)
                    }
                }
    }
}