package com.example.mymodule.wearnotification.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class MainActivity extends Activity {

    public static final String NOTIFICATION_DATA = "NOTIFICATION_DATA";


    private ImageView createEvent;
    private PendingIntent dospen;
    private EditText notificationData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createEvent=(ImageView) findViewById(R.id.button);
        notificationData =(EditText) findViewById(R.id.editText);
        createEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createNotification(Calendar.getInstance().getTimeInMillis(), notificationData.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void createNotification(long when,String data){
        lanzarAcercaDe();
        String notificationContent ="Contenido de la notificación. Pulsa para ver";
        String notificationTitle ="Esto es una notificación";
        //large icon for notification,normally use App icon
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        int smalIcon =R.drawable.ic_launcher;
        String notificationData="Estos son los datos : "+data;

		/*create intent for show notification details when user clicks notification*/
        Intent intent =new Intent(getApplicationContext(), NotificationDetailsActivity.class);
        intent.putExtra(NOTIFICATION_DATA, notificationData);

		/*create unique this intent from  other intent using setData */
        intent.setData(Uri.parse("content://"+when));
		/*create new task for each notification with pending intent so we set Intent.FLAG_ACTIVITY_NEW_TASK */
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		/*get the system service that manage notification NotificationManager*/
        NotificationManager notificationManager =(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

		/*build the notification*/
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                getApplicationContext())
                .setWhen(when)
                .setContentText(data) //You can use notificationContent
                .setContentTitle(notificationTitle)
                .setSmallIcon(smalIcon)
                .setAutoCancel(true)
                .setTicker(notificationTitle)
                .setLargeIcon(largeIcon)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)

                .addAction(R.drawable.lamparados, "Accion1", dospen)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(data));

		/*Create notification with builder*/
        Notification notification=notificationBuilder.build();

		/*sending notification to system.Here we use unique id (when)for making different each notification
		 * if we use same id,then first notification replace by the last notification*/
        notificationManager.notify((int) when, notification);
    }
    public void lanzarAcercaDe(){
        Intent unpen = new Intent(this, acerca.class);
        dospen = PendingIntent.getActivity(this,0,unpen,0);
    }
    public void lanzarAcerca(View view){
//        onPause();
        Intent ea = new Intent(this, acerca.class);
        startActivity(ea);
    }
//    public void salir(View view){
//        finish();
//    }

    //ADITIONALY
//    Intent mapIntent = new Intent(Intent.ACTION_VIEW);
//    Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode("https://www.google.es/maps/@37.1065167,-3.6021691,15z"));
//    mapIntent.setData(geoUri);
//    PendingIntent mapPendingIntent =
//            PendingIntent.getActivity(this, 0, mapIntent, 0);

}
