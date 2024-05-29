package com.example.lab5_20185910;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID_HIGH = "channelHighPriority";
    private static final String CHANNEL_ID_DEFAULT = "channelDefaultPriority";
    private static final String CHANNEL_ID_LOW = "channelLowPriority";

    private TextView textViewUser;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUser = findViewById(R.id.textViewUser);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        // Obtener el código PUCP del SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String puCpCode = sharedPreferences.getString("PUCP_CODE", "Usuario Desconocido");
        textViewUser.setText("Usuario: " + puCpCode);

        // Cargar las tareas desde la base de datos
        loadTasks();

        // Crear los canales de notificación
        createNotificationChannels();

        // Solicitar permisos para notificaciones en Android 13+
        askPermission();
    }

    private void loadTasks() {
        List<Task> taskList = db.taskDao().getAllTasks();
        taskAdapter = new TaskAdapter(taskList);
        recyclerViewTasks.setAdapter(taskAdapter);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            NotificationChannel channelHigh = new NotificationChannel(CHANNEL_ID_HIGH,
                    "High Priority Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            channelHigh.setDescription("Channel for high priority notifications");
            notificationManager.createNotificationChannel(channelHigh);

            NotificationChannel channelDefault = new NotificationChannel(CHANNEL_ID_DEFAULT,
                    "Default Priority Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channelDefault.setDescription("Channel for default priority notifications");
            notificationManager.createNotificationChannel(channelDefault);

            NotificationChannel channelLow = new NotificationChannel(CHANNEL_ID_LOW,
                    "Low Priority Notifications",
                    NotificationManager.IMPORTANCE_LOW);
            channelLow.setDescription("Channel for low priority notifications");
            notificationManager.createNotificationChannel(channelLow);
        }
    }

    private void askPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

}

