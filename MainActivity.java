package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

package com.example.notasacademicas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextIdade, editTextDisciplina, editTextNota1, editTextNota2;
    private TextView textViewErro, textViewResultado;
    private Button buttonEnviar, buttonLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextIdade = findViewById(R.id.editTextIdade);
        editTextDisciplina = findViewById(R.id.editTextDisciplina);
        editTextNota1 = findViewById(R.id.editTextNota1);
        editTextNota2 = findViewById(R.id.editTextNota2);
        textViewErro = findViewById(R.id.textViewErro);
        textViewResultado = findViewById(R.id.textViewResultado);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        buttonLimpar = findViewById(R.id.buttonLimpar);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDados();
            }
        });

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparFormulario();
            }
        });
    }

    private void validarDados() {
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String idadeStr = editTextIdade.getText().toString().trim();
        String disciplina = editTextDisciplina.getText().toString().trim();
        String nota1Str = editTextNota1.getText().toString().trim();
        String nota2Str = editTextNota2.getText().toString().trim();

        StringBuilder erros = new StringBuilder();

        if (nome.isEmpty()) {
            erros.append("O campo de nome está vazio.\n");
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            erros.append("O email é inválido.\n");
        }
        int idade = -1;
        try {
            idade = Integer.parseInt(idadeStr);
            if (idade <= 0) {
                erros.append("A idade deve ser um número positivo.\n");
            }
        } catch (NumberFormatException e) {
            erros.append("A idade deve ser um número válido.\n");
        }
        double nota1 = validarNota(nota1Str, "1o Bimestre");
        double nota2 = validarNota(nota2Str, "2o Bimestre");

        if (erros.length() > 0) {
            textViewErro.setText(erros.toString());
            textViewErro.setVisibility(View.VISIBLE);
            textViewResultado.setVisibility(View.GONE);
        } else {
            exibirResultado(nome, email, idade, disciplina, nota1, nota2);
        }
    }

    private double validarNota(String notaStr, String bimestre) {
        double nota = -1;
        try {
            nota = Double.parseDouble(notaStr);
            if (nota < 0 || nota > 10) {
                textViewErro.append("A " + bimestre + " deve ser entre 0 e 10.\n");
                nota = -1;
            }
        } catch (NumberFormatException e) {
            textViewErro.append("A nota do " + bimestre + " deve ser um número válido.\n");
        }
        return nota;
    }

    private void exibirResultado(String nome, String email, int idade, String disciplina, double nota1, double nota2) {
        double media = (nota1 + nota2) / 2;
        String status = media >= 6 ? "Aprovado" : "Reprovado";

        String resultado = "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Idade: " + idade + "\n" +
                "Disciplina: " + disciplina + "\n" +
                "Notas 1o e 2o Bimestres: " + nota1 + ", " + nota2 + "\n" +
                "Média: " + media + "\n" +
                "Status: " + status;

        textViewResultado.setText(resultado);
        textViewResultado.setVisibility(View.VISIBLE);
        textViewErro.setVisibility(View.GONE);
    }

    private void limparFormulario() {
        editTextNome.setText("");
        editTextEmail.setText("");
        editTextIdade.setText("");
        editTextDisciplina.setText("");
        editTextNota1.setText("");
        editTextNota2.setText("");
        textViewErro.setVisibility(View.GONE);
        textViewResultado.setVisibility(View.GONE);
    }
}
