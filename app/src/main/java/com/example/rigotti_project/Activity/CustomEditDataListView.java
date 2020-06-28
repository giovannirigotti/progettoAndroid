package com.example.rigotti_project.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rigotti_project.Championship.CustomSettingsListView;
import com.example.rigotti_project.R;

import java.util.ArrayList;

public class CustomEditDataListView extends ArrayAdapter<String> {

    private ArrayList<String> tipi;
    private ArrayList<String> valori;

    private Activity context;

    public CustomEditDataListView(Activity context, ArrayList<String> tipi, ArrayList<String> valori) {
        super(context, R.layout.edit_setting_item, tipi);
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
            r = layoutInflater.inflate(R.layout.edit_setting_item, null, true);
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

        TextView tv_tipo, tv_valore;
        Button btn_modifica;

        ViewHolder(View v) {
            tv_tipo = (TextView) v.findViewById(R.id.edit_tipo);
            tv_valore = (TextView) v.findViewById(R.id.edit_valore);
            btn_modifica = (Button) v.findViewById(R.id.btn_edit);
        }
    }
}

