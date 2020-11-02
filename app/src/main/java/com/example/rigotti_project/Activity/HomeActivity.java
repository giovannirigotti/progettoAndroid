package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rigotti_project.Support.DatabaseHelper;
import com.example.rigotti_project.Support.ImageManager;
import com.example.rigotti_project.Support.PersonalData;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

// ---------------------------------
// ---------------------------------
// Activity Home: il fulcro dell'app
// L'utente appena loggato visualizzerà la propria foto profilo e i propri dati principali
// Consente inoltre di andare direttamente alla lista dei campionati e alla activity per testare le notifiche
// Il servizio per notificare l'utente viene avviato da questa Activity (prima aperta dopo il login)
// I dati presenti sul json insieme a quelli salvati su un DB sqlite vengono caricati in locale per mantenere lo stato
// ---------------------------------
// ---------------------------------

public class HomeActivity extends AppCompatActivity {

    //Immagine
    private Bitmap bitmap;
    private ImageView profile_image;

    //Textview
    private TextView nome, nazione, numero;

    //Button
    private Button campionati, modifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        // CARICO I DATI DAI JSON
        // Da qui vengono presi anche i dati sul DB per non perdere le modifiche
        // effettuate nelle precedenti sessioni
        Utili.readCampionati(this);
        Utili.readClassifiche(this);

        // AVVIO SERVIZIO DI NOTIFICA
        NotifyTask nt = new NotifyTask();
        nt.execute();


        // Imposto immagine
        profile_image = (ImageView) findViewById(R.id.home_profile_image);
        String foto = PersonalData.getFOTO();
        bitmap = ImageManager.StringToBitMap(foto);
        profile_image.setImageBitmap(bitmap);


        // Imposto dati utente
        nome = (TextView) findViewById(R.id.home_nome);
        nazione = (TextView) findViewById(R.id.home_nazione);
        numero = (TextView) findViewById(R.id.home_numero);
        nome.setText(PersonalData.getNOME());
        nazione.setText(PersonalData.getNAZIONE());
        numero.setText(PersonalData.getNUMERO().toString());

        // Imposto bottoni
        campionati = (Button) findViewById(R.id.btn_home_campionati);
        modifica = (Button) findViewById(R.id.btn_home_modifica);

        // Creo i Listener per i bottoni
        campionati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ChampionshipListActivity.class);
                startActivity(i);
            }
        });

        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, EditDataActivity.class);
                startActivity(i);
            }
        });


    }

    // region IMPORTO MENU

    // AGGIUNGO MENU ALLA ACTIVITY
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //GESTISCO MENU NELLA ACTIVITY
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Ritorno True se il quando premo su un Item questo è nello switch case
        // Ritrono "super.onOptionsItemSelected(item)" altrimenti
        return (Utili.setMenu(HomeActivity.this,item)) ? true : super.onOptionsItemSelected(item);
    }

    //endregion

    // Classe asincrona per svolgere in background un servizio in grado
    // di percepire quando viene svolta una modifica e di allertare di
    // conseguenza l'utente.
    class NotifyTask extends AsyncTask<Void, Void, Void> {

        private Context context;
        private NotificationCompat.Builder notification_builder;
        private int id = 1337;

        public NotifyTask() {
            super();
            this.context = HomeActivity.this;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("NOTIFICHE", "Notifier listener is starting");
        }

        // Metodo per creare la notifica "personalizzata" (titolo e testo)
        private void createNotification(String title, String text, Context c) {
            Log.w("NOTIFICA", "INIZIO");
            //Build the notification using Notification.Builder
            NotificationManager notification_manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {           // Se la versione in utilizzo è recenete devo creare un NotificationChannel
                String chanel_id = "3000";
                CharSequence name = "Channel Edit Data";
                String description = "Notifiche modifica dei dati";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                mChannel.setDescription(description);
                mChannel.setShowBadge(true);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.BLUE);
                notification_manager.createNotificationChannel(mChannel);
                notification_builder = new NotificationCompat.Builder(c, chanel_id);
            } else {
                notification_builder = new NotificationCompat.Builder(c);     // Altrimenti mi basta il NotificationBuilder
            }
            // Creo PendingIntent per mandare l'utente alla activity corretta quando preme sulla notifica ricevuta
            Intent i = new Intent(HomeActivity.this, ChampionshipListActivity.class);
            PendingIntent pi = PendingIntent.getActivity(HomeActivity.this, 0, i, 0);

            // Creo notifica vera e propria
            notification_builder.setSmallIcon(R.drawable.ic_notifica)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true)
                    .setContentIntent(pi);

            notification_manager.notify(id, notification_builder.build());
            Log.w("NOTIFICA", "FINE, tutto ok.");
        }

        // Servizio in background vero e proprio
        // Logica:  se viene svolta una modifica allora una variabile di stato Utili.MODIFICA viene modificata.
        //          il servizio se ne accorge e crea la notifica riportando Utili.MODIFICA a '0' (nessuna modifica da notificare)
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Log.e("NOTIFICHE", "Notifier listener is listening for changes...");
                while (true) {
                    //ASPETTO 2 secondi per controllare
                    Thread.sleep(2000);
                    if (Utili.MODIFICA == 0) {
                        Log.e("NOTIFICHE", "ascolto modifiche");
                    } else if (Utili.MODIFICA == 1) {
                        Log.e("NOTIFICHE", "modifica avvenuta");
                        String not ="Tipo: " + Utili.TIPO + ";  Nuovo valore: " + Utili.VALORE + ";";
                        String tit ="Modifica impostazioni campionato: " +  Utili.CAMPIONATO + ";";
                        createNotification(tit, not,HomeActivity.this);
                        Log.e("NOTIFICA", not);
                    } else if (Utili.MODIFICA == 2) {
                        Log.e("NOTIFICHE", "modifica annullata");
                    }
                    Utili.MODIFICA = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utili.doToast(context, "Errore notifier listener");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            DatabaseHelper db = new DatabaseHelper(context);
            db.updatePiloti();
            if (!db.isRulesEmpty()) {
                db.updateRules();
            }
            Log.e("NOTIFICHE", "Notifier listener is closed");
        }
    }
}
