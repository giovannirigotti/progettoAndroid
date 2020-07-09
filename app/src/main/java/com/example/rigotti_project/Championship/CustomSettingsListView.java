package com.example.rigotti_project.Championship;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rigotti_project.R;

import java.util.ArrayList;

// ---------------------------------
// ---------------------------------
// Classe per la creazione di una ListView personalizzata
// Nello specifico crea una ListView per visualizzare le regole/impostazioni di un campionato
// ---------------------------------
// ---------------------------------

public class CustomSettingsListView extends ArrayAdapter<String> {

    private ArrayList<String> tipi;
    private ArrayList<String> valori;

    private Activity context;

    public CustomSettingsListView(Activity context, ArrayList<String> tipi, ArrayList<String> valori) {
        super(context, R.layout.setting_item, tipi);
        this.context = context;
        this.tipi = tipi;
        this.valori = valori;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.setting_item, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.tv_tipo.setText(tipi.get(position));
        viewHolder.tv_valore.setText(valori.get(position));
        return r;
    }

    class ViewHolder {

        TextView tv_tipo,tv_valore;

        ViewHolder(View v) {
            tv_tipo = (TextView) v.findViewById(R.id.tv_tipo);
            tv_valore = (TextView) v.findViewById(R.id.tv_valore);
        }
    }
}


