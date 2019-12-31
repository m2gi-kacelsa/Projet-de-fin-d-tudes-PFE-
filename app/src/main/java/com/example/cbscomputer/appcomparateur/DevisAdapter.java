package com.example.cbscomputer.appcomparateur;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DevisAdapter extends ArrayAdapter<Devis>{
    private int [] images_list = {R.drawable.cash, R.drawable.saa, R.drawable.cash};

    public DevisAdapter(@NonNull Context context, List<Devis> list) {
        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.devis, parent, false);
        }
        DevisViewHolder viewHolder = (DevisViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new DevisViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Devis> devis
        Devis devis = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(devis.getPseudo());
        viewHolder.text.setText(devis.getText());
        viewHolder.avatar.setImageResource(devis.getImage());

        return convertView;
    }

    private class DevisViewHolder{
        public TextView pseudo;
        public TextView text;
        public ImageView avatar;
    }

}
