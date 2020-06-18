package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rigotti_project.Support.DatabaseHelper;
import com.example.rigotti_project.Support.ImageManager;
import com.example.rigotti_project.Support.PersonalData;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

import java.io.IOException;

public class AccounActivity extends AppCompatActivity {

    //Database
    DatabaseHelper db;

    //VIEW DELLA PAGINA
    //TextView
    private TextView nome, cognome, email, data, nazione;

    //Bottoni
    private Button btn_aggiorna_immagine, btn_dati_gioco, btn_cambia_password, btn_elimina_account;


    //Immagine
    private ImageView profile_image;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accoun);
        setTitle("Account");

        db = new DatabaseHelper(this);

        // "INIZIALIZZO VIEW"
        nome = (TextView) findViewById(R.id.acc_nome);
        cognome = (TextView) findViewById(R.id.acc_cognome);
        email = (TextView) findViewById(R.id.acc_email);
        nazione = (TextView) findViewById(R.id.acc_nazione);
        data = (TextView) findViewById(R.id.acc_data);

        profile_image = (ImageView) findViewById(R.id.acc_profile_image);

        btn_aggiorna_immagine = (Button) findViewById(R.id.btn_cambia_foto);
        btn_dati_gioco = (Button) findViewById(R.id.btn_dati_gioco);
        btn_cambia_password = (Button) findViewById(R.id.btn_cambia_password);
        btn_elimina_account = (Button) findViewById(R.id.btn_elimina_account);

        //POPOLO VIEW CON DATI UTENTE
        nome.setText(PersonalData.getNOME());
        cognome.setText(PersonalData.getCOGNOME());
        email.setText(PersonalData.getEMAIL());
        nazione.setText(PersonalData.getNAZIONE());
        data.setText(PersonalData.getDATA());

        //OSS:  setto immagine convertendo la foto salvata come Stringa (in PersonalData)
        //      a Bitmap tramite il metodo Utili.StringToBitMap(stringa)
        profile_image.setImageBitmap(ImageManager.StringToBitMap(PersonalData.getFOTO()));

        btn_aggiorna_immagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //premission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show pop up for runtime permission
                        requestPermissions(permissions, ImageManager.PERMISSION_CODE);

                    } else {
                        //permission already granted
                        ImageManager.chooseFile(AccounActivity.this);
                    }
                } else {
                    //system os is less then marshmellow
                    ImageManager.chooseFile(AccounActivity.this);
                }
            }
        });

        btn_dati_gioco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccounActivity.this, ManageDataActivity.class);
                startActivity(i);
            }
        });

        btn_cambia_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordDialog(v);
            }
        });

        btn_elimina_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(v);
            }
        });

    }

    private void showPasswordDialog(View v) {
        //APRO DIALOG PASSWORD
        AlertDialog.Builder builder = new AlertDialog.Builder(AccounActivity.this);
        View view = getLayoutInflater().inflate(R.layout.password_dialog, null);

        final EditText old_pass, new_pass, confirm_pass;
        Button btn_aggiorna, btn_annulla;

        old_pass = (EditText) view.findViewById(R.id.et_old_pass);
        new_pass = (EditText) view.findViewById(R.id.et_new_pass);
        confirm_pass = (EditText) view.findViewById(R.id.et_confirm_pass);

        btn_aggiorna = (Button) view.findViewById(R.id.btn_up_password);
        btn_annulla = (Button) view.findViewById(R.id.btn_annulla_password);

        btn_aggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logica cambio password;
                String o_pass, n_pass, c_pass;
                o_pass = old_pass.getText().toString().trim();
                n_pass = new_pass.getText().toString().trim();
                c_pass = confirm_pass.getText().toString().trim();

                if (checkPassword(v.getContext(), o_pass, n_pass, c_pass)) {
                    db.updatePassword(n_pass);
                    Utili.doToast(v.getContext(), "Password aggiornata correttamente.");
                    Intent i = new Intent(v.getContext(), AccounActivity.class);
                    startActivity(i);
                }
            }
        });

        btn_annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utili.doToast(v.getContext(), "Password NON aggiornata.");
                Intent i = new Intent(v.getContext(), AccounActivity.class);
                startActivity(i);
            }
        });

        builder.setTitle("Aggiorna password").setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private boolean checkPassword(Context context, String vecchia_password, String nuova_password, String conferma_password) {
        String err = "";
        if (vecchia_password.isEmpty() || nuova_password.isEmpty() || conferma_password.isEmpty()) {
            err = "Riempi tutti i campi per aggiornare la password.";
            Utili.doToast(context, err);
            return false;
        } else {
            if (!vecchia_password.equals(PersonalData.getPASSWORD())) {
                err = "La \"vecchia\" password immessa non è corretta (password attuale)";
                Utili.doToast(context, err);
                return false;
            } else if (!Utili.validatePassword(nuova_password)) {
                err = "La nuova password immessa non ha i requisiti minimi di sicurezza:\nLunga almeno 8 caratteri con maiuscole, minuscole, numeri e caratteri speciali.";
                Utili.doToast(context, err);
                return false;
            } else if (!nuova_password.equals(conferma_password)) {
                err = "La nuova password e la sua conferma non corrispondono.\nAssicurati di immettere correttamente la password!";
                Utili.doToast(context, err);
                return false;
            }
        }
        return true;
    }

    private void showDeleteDialog(View v) {
        //APRO DIALOG PASSWORD
        AlertDialog.Builder builder = new AlertDialog.Builder(AccounActivity.this);
        View view = getLayoutInflater().inflate(R.layout.delete_account_dialog, null);

        Button btn_elimina, btn_annulla;

        btn_elimina = (Button) view.findViewById(R.id.btn_conferma_elimina);
        btn_annulla = (Button) view.findViewById(R.id.btn_annulla_eliminazione);

        btn_elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logica eliminazione account;
                try {
                    db.deleteUser();
                    Utili.doToast(v.getContext(), "Account eliminato.");
                    Utili.doLogout(AccounActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utili.doToast(v.getContext(), "Account non eliminato.");
                }
            }
        });

        btn_annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AccounActivity.class);
                startActivity(i);
            }
        });

        builder.setTitle("Elimina account").setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //GESTIONE IMMAGINE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImageManager.IMAGE_PICK_CODE) {
            // set image to image view
            Uri filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);

                String new_img = ImageManager.BitMapToString(bitmap);

                //SALVO NUOVA IMMAGINE IN LOCALE
                PersonalData.setFOTO(new_img);

                //SALVO NUOVA IMMAGINE SUL DB
                db.updateImage(new_img);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ImageManager.PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    ImageManager.chooseFile(AccounActivity.this);
                } else {
                    // permission was dained
                    Utili.doToast(this, "Permesso negato");
                }
            }
        }
    }

    // AGGIUNGO MENU ALLA ACTIVITY
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //GESTISCO MENU NELLA ACTIVITY
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Ritorno True se il quando premo su un Item questo è nello switch case
        // Ritrono "super.onOptionsItemSelected(item)" altrimenti
        return (Utili.setMenu(AccounActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }


}
