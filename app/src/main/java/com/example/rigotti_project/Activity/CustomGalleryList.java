package com.example.rigotti_project.Activity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CustomGalleryList extends ArrayAdapter<String> {

    private ArrayList<Integer> ids_foto;
    private ArrayList<String> nomi_foto;
    private String stringToPass;
    private Integer resource_id;

    private Activity context;

    public CustomGalleryList(Activity context, ArrayList<String> nomi_foto) {
        super(context, R.layout.foto_item, nomi_foto);
        this.context = context;
        this.nomi_foto = nomi_foto;
        this.ids_foto = new ArrayList<>();
        for(int i = 0; i < nomi_foto.size(); i++){
            Integer resource_id = Utili.getResId(context, nomi_foto.get(i));
            ids_foto.add(resource_id);
        }
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.foto_item, null);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.img.setImageResource(ids_foto.get(position));
        viewHolder.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] splitted = nomi_foto.get(position).split("_");
                stringToPass = splitted[1];
                resource_id = Utili.getResId(context, stringToPass);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id_risorsa", resource_id);
                context.startActivity(intent);
            }
        });
        viewHolder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] splitted = nomi_foto.get(position).split("_");
                stringToPass = splitted[1];
                resource_id = Utili.getResId(context, stringToPass);
                Utili.shareImage(context,resource_id);
            }
        });

        return r;
    }


    class ViewHolder {
        ImageView img;
        Button btn_open, btn_share;


        ViewHolder(View v) {
            img = v.findViewById(R.id.foto_img);
            btn_open = v.findViewById(R.id.foto_open);
            btn_share = v.findViewById(R.id.foto_share);



        }
    }
}
