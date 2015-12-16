package com.cis2237.himes_e4.fragmentcommunication;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectionFragment extends Fragment {
    private Button btnLeft;
    private Button btnRight;

    public SelectionFragment() {
        // Required empty public constructor
    }

    SelectionInterface selectionInterface;

    public interface SelectionInterface {
        public void rotateFace(boolean clockWise);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selection, container, false);

        btnLeft = (Button)view.findViewById(R.id.btnLeft);
        btnRight = (Button)view.findViewById(R.id.btnRight);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate face counter clockwise.
                selectionInterface.rotateFace(false);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate face clockwise.
                selectionInterface.rotateFace(true);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try {
            selectionInterface = (SelectionInterface)activity;
        } catch (ClassCastException cce) {
            MainActivity.showToast(getActivity(), cce.getMessage());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        selectionInterface = null;
    }
}
