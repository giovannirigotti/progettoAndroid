package com.example.rigotti_project.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.DatabaseHelper;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

public class CustomEditDataListView extends ArrayAdapter<String> {

    private ArrayList<String> tipi;
    private ArrayList<String> valori;
    private Integer indice_campionato;
    private DatabaseHelper db;

    private Activity context;

    public CustomEditDataListView(Activity context, ArrayList<String> tipi, ArrayList<String> valori, Integer indice_campionato) {
        super(context, R.layout.edit_setting_item, tipi);
        db = new DatabaseHelper(context);
        this.context = context;
        this.tipi = tipi;
        this.valori = valori;
        this.indice_campionato = indice_campionato;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.edit_setting_item, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.tv_tipo.setText(tipi.get(position));
        viewHolder.tv_valore.setText(valori.get(position));
        viewHolder.btn_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDataDialog(position, indice_campionato);
            }
        });
        return r;
    }

    public void showEditDataDialog(Integer position, Integer indice_campionato) {
        final Integer camp = indice_campionato;
        final Integer pos = position;
        //APRO DIALOG EDIT_DATA
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(R.layout.edit_data_dialog, null);

        final EditText new_value;
        final TextView tipo, valore;
        Button btn_conferma, btn_annulla;

        new_value = (EditText) view.findViewById(R.id.edited_value);

        tipo = (TextView) view.findViewById(R.id.text_vecchio_tipo);
        valore = (TextView) view.findViewById(R.id.text_vecchio_valore);
        //Log.e("DEBUG",Utili.listaCampionati.getCampionato(indice_campionato).getImpostazioni().get(position).getTipo());
        tipo.setText(Utili.listaCampionati.getCampionato(indice_campionato).getImpostazioni().get(position).getTipo());
        valore.setText(Utili.listaCampionati.getCampionato(indice_campionato).getImpostazioni().get(position).getValore());

        btn_conferma = (Button) view.findViewById(R.id.btn_edit_confirm);
        btn_annulla = (Button) view.findViewById(R.id.btn_edit_annulla);

        btn_conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logica cambio valore;
                String value;
                value = new_value.getText().toString().trim();

                if (!value.isEmpty()) {
                    if (Utili.validateSimpleCommaText(value, 40)){
                        String ti = Utili.listaCampionati.getCampionato(camp).getImpostazioni().get(pos).getTipo();
                        //CAMBIO VALORE SU DB E IN LOCALE
                        db.changeRule(camp, ti, value, pos);
                        Utili.VALORE = value;
                        Utili.TIPO = ti;
                        Utili.CAMPIONATO = Utili.listaCampionati.getCampionato(camp).getNome();
                        Utili.MODIFICA = 1;
                        Utili.CAMP = camp;
                        String msg = "Valore aggiornato correttamente";
                        Utili.doToast(context, msg);
                        Intent i = new Intent(v.getContext(), HomeActivity.class);
                        context.startActivity(i);
                    } else {
                        String err = "Assicurati di aver inserito solo caratteri alfabetici nel campo \'Nuovo valore\'\nLunghezza massima 20 caratteri";
                        Utili.doToast(context, err);
                    }
                } else {
                    String err = "Inserisci il nuovo valore";
                    Utili.doToast(context, err);
                }
            }
        });

        btn_annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utili.doToast(v.getContext(), "Valore NON aggiornato.");
                Utili.MODIFICA = 2;
                Intent i = new Intent(v.getContext(), EditDataActivity.class);
                context.startActivity(i);
            }
        });

        builder.setTitle("Aggiorna impostazioni").setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    class ViewHolder {

        TextView tv_tipo, tv_valore;
        Button btn_modifica;

        ViewHolder(View v) {
            tv_tipo = (TextView) v.findViewById(R.id.edit_tipo);
            tv_valore = (TextView) v.findViewById(R.id.edit_valore);
            btn_modifica = (Button) v.findViewById(R.id.btn_edit);
        }
    }
}

