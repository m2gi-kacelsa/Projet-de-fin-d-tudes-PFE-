package com.example.cbscomputer.appcomparateur;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class UserFragment extends Fragment {
    SqliteConnexion connexion;
    TextInputEditText nom, email ;
    Button buttoninscrit;
    String nom_assurance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        nom_assurance = getArguments().getString("assurance");
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            connexion = new SqliteConnexion(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        nom = (TextInputEditText) view.findViewById(R.id.nom_user);
        email = (TextInputEditText) view.findViewById(R.id.email_user);
        buttoninscrit = (Button) view.findViewById(R.id.send_user);
        addData();
    }

    private void addData() {
        buttoninscrit.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Boolean isInserted = connexion.insertDATA(nom_assurance, nom.getText().toString(), email.getText().toString());
                if (isInserted == true)
                    Toast.makeText(getActivity(), "DATA INSERTED", Toast.LENGTH_LONG).show();
            }

        });

    }

}
