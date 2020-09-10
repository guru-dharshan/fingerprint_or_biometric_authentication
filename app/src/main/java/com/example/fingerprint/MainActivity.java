package com.example.fingerprint;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.BiometricPrompt.AuthenticationCallback;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainActivity activity=this;
        final Executor executor=Executors.newSingleThreadExecutor();
        final BiometricPrompt biometricPrompt=new BiometricPrompt.Builder(this)
                .setTitle("Biometric Authentication")
                .setSubtitle("User your finger print to unlock")
                .setNegativeButton("cancel", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).build();
                biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // for demo purpose we created a whatsapp chat (dummy)
                                Intent intent=new Intent(getApplicationContext(),WhatsApp.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}