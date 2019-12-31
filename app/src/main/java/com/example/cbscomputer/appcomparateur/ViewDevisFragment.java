package com.example.cbscomputer.appcomparateur;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ViewDevisFragment extends Fragment {
    String devisCASH, devisSAA, primeRC;
    double devis6mois, devis3mois, devisAuto;
    private ListView listDevis;
    private String nom_assurance;
    private Devis devis1, devis2;
    private boolean cash = false, saa = false;
    private int user_cash = 0, user_saa = 0;

    public ViewDevisFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        devisCASH = getArguments().getString("devisCASH");
        devisSAA = getArguments().getString("devisSAA");
        primeRC = getArguments().getString("primeRC");
        return inflater.inflate(R.layout.fragment_view_devis, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //reccupérer la liste view
        listDevis = (ListView) view.findViewById(R.id.list_view);
        final List<Devis> devis = genererDevis ();
        DevisAdapter adapter = new DevisAdapter(getActivity(), devis);
        listDevis.setAdapter(adapter);
        listDevis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch ( position){
                    case 0:
                        devisAuto = Double.parseDouble(devisSAA);
                        devis6mois = 0.55* (Double.parseDouble(devisSAA));
                        devis3mois = 0.35* (Double.parseDouble(devisSAA));
                        showMessage("http://www.saa.dz/");
                        saa = true;
                        break;
                    case 1:
                        devisAuto = Double.parseDouble(devisCASH);
                        devis6mois = 0.55* (Double.parseDouble(devisCASH));
                        devis3mois = 0.35* (Double.parseDouble(devisCASH));
                        showMessage("https://www.cash-assurances.dz/");
                        cash = true;
                        break;
                }
            }
        });
    }

    public void showMessage(final String site) {
        final Bundle bundle = new Bundle();
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View viewDetail = layoutInflater.inflate(R.layout.details_assurance, null, false);
        final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
        alertDialog.setView(viewDetail);
        final TextView rc_text = (TextView) viewDetail.findViewById(R.id.primeRC);
        final TextView devisSix = (TextView) viewDetail.findViewById(R.id.devisSix);
        final TextView devisTrois = (TextView) viewDetail.findViewById(R.id.devisTrois);
        final Button  client = (Button) viewDetail.findViewById(R.id.save);
        alertDialog.setPositiveButton("Souscrire en Ligne", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
                startActivity(browser);
            }});
        alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        devis6mois = (double) Math.round(devis6mois*100)/100;
        devis3mois = (double) Math.round(devis3mois*100)/100;

        rc_text.setText(primeRC+" DA");
        devisSix.setText(devis6mois+" DA");
        devisTrois.setText(devis3mois+" DA");

        if (saa){
            user_saa ++;
            nom_assurance = devis1.getPseudo();
        }
        if (cash){
            user_cash ++;
            nom_assurance = devis2.getPseudo();
        }

        client.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                UserFragment userFragment = new UserFragment();
                bundle.putString("assurance", nom_assurance);
                userFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction.replace(R.id.screen_area, userFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        final android.support.v7.app.AlertDialog alertDetailDialog = alertDialog.create();
        alertDetailDialog.show();
    }

    private List<Devis> genererDevis() {
        List<Devis> devis = new ArrayList<Devis>();
        String  tarifs[] = {devisCASH, devisSAA};
        devis1 = new Devis(R.drawable.saa, "SAA", devisSAA+" DA");
        devis2 = new Devis(R.drawable.cash, "CASH", devisCASH+" DA");
        devis.add(devis1);
        devis.add(devis2);

        return devis;
    }

}
