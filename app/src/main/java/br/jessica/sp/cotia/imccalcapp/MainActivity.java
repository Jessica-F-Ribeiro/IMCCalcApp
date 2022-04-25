package br.jessica.sp.cotia.imccalcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

        private EditText editPeso, editAltura, resultado, classificacao;
        private Button btCalcular, btLimpar;
        private RadioGroup groupGenero;
        private Spinner spinnerGenero;
        private  String genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // referenciando os componentes na tela
        editPeso = findViewById(R.id.edit_peso);
        editAltura = findViewById(R.id.edit_altura);
        btCalcular = findViewById(R.id.bt_calcular);
        btLimpar = findViewById(R.id.bt_limpar);
        resultado = findViewById(R.id.resultado);
        classificacao = findViewById(R.id.classificacao);
        spinnerGenero = findViewById(R.id.spinner_genero);
        groupGenero = findViewById(R.id.group_genero);

        btLimpar.setOnClickListener(v -> {
            editPeso.setText("");
            editAltura.setText("");
            resultado.setText("");
            classificacao.setText("");
        });

        btCalcular.setOnClickListener(v -> { //v = view

            // validar os campos
            if (editPeso.getText().toString().isEmpty()){
                editPeso.setError(getString(R.string.valida_peso));
                Toast.makeText(getBaseContext(), R.string.valida_peso, Toast.LENGTH_SHORT).show();
            }else if (editAltura.getText().toString().isEmpty()){
                editAltura.setError(getString(R.string.valida_altura));
                Snackbar.make(editAltura, R.string.valida_altura, Snackbar.LENGTH_SHORT).show();
            }else {
                double peso, altura, imc;
                peso = Double.parseDouble(editPeso.getText().toString());
                altura = Double.parseDouble(editAltura.getText().toString());
                imc = peso / (altura * altura);
                if (imc < 18){
                    classificacao.setText(R.string.abaixo);
                    classificacao.setBackgroundColor(getResources().getColor(R.color.azul));
                }else if (imc < 24){
                    classificacao.setText(R.string.normal);
                    classificacao.setBackgroundColor(getResources().getColor(R.color.verde));
                }else if (imc < 29){
                    classificacao.setText(R.string.sobrepeso);
                    classificacao.setBackgroundColor(getResources().getColor(R.color.claro));
                }else if (imc < 34){
                    classificacao.setText(R.string.grau_I);
                    classificacao.setBackgroundColor(getResources().getColor(R.color.laranja));
                }else if (imc < 39){
                    classificacao.setText(R.string.grau_II);
                    classificacao.setBackgroundColor(getResources().getColor(R.color.vermelho));
                }else {
                    classificacao.setText(R.string.grau_III);
                    classificacao.setBackgroundColor(getResources().getColor(R.color.purple_500));
                }
                resultado.setText(imc+"");

                // Pegar o valor selecinado no spinner
                if (spinnerGenero.getSelectedItemPosition() ==0){
                    genero = "masculino";
                }else{
                    genero = "feminino";
                }

                //pega o valor selecionado no radio
                switch (groupGenero.getCheckedRadioButtonId()){
                    case R.id.radio_feminino:
                        genero = "feminino";
                        break;
                    case R.id.radio_masculino:
                        genero = "masculino";
                        break;
                }
                // abrir a ActivityResultado
                Intent intent = new Intent(this, ResultadoActivity.class);
                intent.putExtra("imc", imc);
                intent.putExtra("genero", genero);
                startActivity(intent);
                //finish(); encerra a Activity
            }
        });
    }
}