package com.example.mentoriapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CadastroReuniaoActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button btnCadastrarReuniao;
    ImageView imgRelogio,imgCalendario;
    TextView txtHorario,txtCalendario;
    TextInputEditText editDescricao;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_reuniao);

        imgRelogio = findViewById(R.id.img_relogio);
        imgCalendario = findViewById(R.id.img_calendario);
        btnCadastrarReuniao = findViewById(R.id.btn_cadastrar_reuniao);
        txtHorario = findViewById(R.id.txt_horario);
        txtCalendario = findViewById(R.id.txt_data);
        editDescricao = findViewById(R.id.edit_descricao);

        imgRelogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        imgCalendario.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CadastroReuniaoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        ano,mes,dia);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String data = dayOfMonth+"/"+month+"/"+year;

                txtCalendario.setText(data);
            }
        };

        btnCadastrarReuniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editDescricao.getText().equals("") && !txtCalendario.getText().equals("Data") && !txtHorario.getText().equals("Horário")){
                    cadastrarReuniao();
                }
            }
        });
    }

    private void cadastrarReuniao() {
        Toast.makeText(this,"Reunião cadastrada",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        txtHorario.setText(hourOfDay+":"+minute);
    }
}