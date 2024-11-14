package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.myapplication.MainActivity;
import com.example.myapplication.RegistrarActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtSenha;
    private TextView txtRegistrar;
    private Button btnEntrar;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialização dos elementos de interface
        txtEmail = findViewById(R.id.editTextEmail);
        txtSenha = findViewById(R.id.editTextSenha);
        btnEntrar = findViewById(R.id.buttonEntrar);
        txtRegistrar = findViewById(R.id.TextViewRegistrar);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        // Ação para o botão de entrar
        btnEntrar.setOnClickListener(v -> entrar());

        // Ação para o texto de registrar
        txtRegistrar.setOnClickListener(v -> registrar());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Verificação se o usuário já está autenticado
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void entrar() {
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login bem-sucedido
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        // Falha na autenticação
                        Toast.makeText(LoginActivity.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                });
    }

    private void registrar() {
        Intent i = new Intent(getApplicationContext(), RegistrarActivity.class);
        startActivity(i);
        finish();
    }
}
