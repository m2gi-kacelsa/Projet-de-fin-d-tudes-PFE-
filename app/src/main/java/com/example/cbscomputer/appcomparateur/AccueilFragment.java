package com.example.cbscomputer.appcomparateur;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;


public class AccueilFragment extends Fragment {
    ImageView auto ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accueil, container, false);
        auto = (ImageView) rootView.findViewById(R.id.imauto);
        auto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi (api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                AutomobileFragment auto_fragment = new AutomobileFragment();
                FragmentManager fragmanager = getFragmentManager();
                fragmanager.beginTransaction().replace(R.id.screen_area, auto_fragment).addToBackStack(null).commit();
            }
        });
        return rootView;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
