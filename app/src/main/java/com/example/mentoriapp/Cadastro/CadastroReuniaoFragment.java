package com.example.mentoriapp.Cadastro;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Reuniao;
import com.example.mentoriapp.Listas.ListaReunioesFragment;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class CadastroReuniaoFragment extends Fragment {

    private Button btnCadastrarReuniao;
    private ImageView imgRelogio,imgCalendario;
    private TextView txtHorario,txtCalendario;
    private TextInputEditText editTitulo,editDescricao;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastro_reuniao, container, false);

        /*
        toolbar = view.findViewById(R.id.toolbar);
        getActivity().setActionBar(toolbar);
        getActivity().getActionBar().setTitle("Cadastro de Reunião");
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
         */

        imgRelogio = view.findViewById(R.id.img_relogio);
        imgCalendario = view.findViewById(R.id.img_calendario);
        btnCadastrarReuniao = view.findViewById(R.id.btn_cadastrar_reuniao);
        txtHorario = view.findViewById(R.id.txt_horario);
        txtCalendario = view.findViewById(R.id.txt_data);
        editDescricao = view.findViewById(R.id.edit_descricao);
        editTitulo = view.findViewById(R.id.edit_titulo);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        progressDialog = new ProgressDialog(getContext());

        imgRelogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hora = cal.get(Calendar.HOUR_OF_DAY);
                int minuto = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hora,minuto,true);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
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

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
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
                String dia,mes,data;

                if(dayOfMonth < 10 && month < 10) {
                    dia = "0" + dayOfMonth;
                    mes = "0" + month;
                    data = dia+"/"+mes+"/"+year;
                }else if(dayOfMonth < 10){
                    dia = "0" + dayOfMonth;
                    data = dia+"/"+month+"/"+year;
                }else if(month < 10){
                    mes = "0" + month;
                    data = dayOfMonth+"/"+mes+"/"+year;
                }else{
                    data = dayOfMonth+"/"+month+"/"+year;
                }

                txtCalendario.setText(data);
            }
        };

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora,minuto,horario;

                if(hourOfDay < 10 && minute < 10) {
                    hora = "0" + hourOfDay;
                    minuto = "0" + minute;
                    horario = "("+hora+":"+minuto+")";
                }else if(hourOfDay < 10){
                    hora = "0" + hourOfDay;
                    horario = "("+hora+":"+minute+")";
                }else if(minute < 10){
                    minuto = "0" + minute;
                    horario = "("+hourOfDay+":"+minuto+")";
                }else{
                    horario = "("+hourOfDay+":"+minute+")";
                }

                txtHorario.setText(horario);
            }
        };

        btnCadastrarReuniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarReuniao();
            }
        });

        return view;
    }

    private void cadastrarReuniao() {
        String titulo = editTitulo.getText().toString();
        String descricao = editDescricao.getText().toString();
        String data = txtCalendario.getText().toString();
        String horario = txtHorario.getText().toString();
        String autor = user.getEmail();

        if(descricao == null || data == null || horario == null ||
                descricao.isEmpty() || data.isEmpty() || horario.isEmpty()){
            Toast.makeText(getContext(),"Todos os campos são obrigatórios!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando reunião...");
        progressDialog.show();

        Reuniao reuniao = new Reuniao(1,titulo,descricao,data,horario,autor,"teste@gmail.com");
        FirebaseFirestore.getInstance().collection("reunioes")
                .add(reuniao)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Reunião cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_mentorado, new ListaReunioesFragment())
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Ops, houve um erro ao cadastrar a reunião! Tente novamente.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}