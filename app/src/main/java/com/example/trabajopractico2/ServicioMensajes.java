package com.example.trabajopractico2;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServicioMensajes extends Service {
    public ServicioMensajes() {
    }

    public  int onStartCommand(Intent intent, int flags, int startId) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mensajes = Uri.parse("content://sms/inbox");
                ContentResolver cr = getContentResolver();
                while (true) {
                    Cursor c = cr.query(mensajes,
                            null,
                            null,
                            null,
                            null);
                    String dia = null;
                    String msj = null;
                    if (c != null && c.getCount() > 0) {
                        int i = 1;
                        while (c.moveToNext() && i < 6) {
                            dia = c.getString(c.getColumnIndex(Telephony.Sms._ID));
                            msj = c.getString(c.getColumnIndex(Telephony.Sms.BODY));
                            Log.d("mensajes", dia + " " + msj);
                            i++;
                        }
                        try {
                            Thread.sleep(9000);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    c.close();
                }
            }
        });
        th.start();
        return START_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
