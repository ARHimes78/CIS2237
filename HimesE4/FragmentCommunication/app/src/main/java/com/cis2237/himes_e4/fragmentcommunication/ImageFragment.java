package com.cis2237.himes_e4.fragmentcommunication;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    private ImageView imgFace;
    private AnimationDrawable animFaceChange;

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        imgFace = (ImageView)view.findViewById(R.id.imgFace);
        imgFace.setBackgroundResource(R.drawable.face_change);

        animFaceChange = (AnimationDrawable)imgFace.getBackground();

        return view;
    }

    //start frame animation of face looking panicked
    public void switchFace() {
        if (animFaceChange.isRunning())
            animFaceChange.stop();

        animFaceChange.start();
    }

    //start counter clockwise or clockwise rotation tween animation
    public void rotateFace(boolean clockWise) {
        if (clockWise)
            imgFace.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.rotate_right));
        else
            imgFace.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.rotate_left));
    }
}
