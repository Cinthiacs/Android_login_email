package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView txtUsuario;
    private Button btnLogout;
    private Button btnRedefinirSenha;
    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.buttonLogout);
        txtUsuario = findViewById(R.id.textViewUsuario);
        btnRedefinirSenha = findViewById(R.id.buttonRedefinirSenha);

        user = auth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            txtUsuario.setText(user.getEmail());
        }

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        });

        btnRedefinirSenha.setOnClickListener(v -> {
            redefinirSenha();
        });
    }

    private void redefinirSenha() {
        String emailAddress = user.getEmail();
        if (emailAddress != null) {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Email de redefinição enviado!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Erro ao enviar email de redefinição.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Não foi possível obter o email do usuário.", Toast.LENGTH_SHORT).show();
        }
    }
}
