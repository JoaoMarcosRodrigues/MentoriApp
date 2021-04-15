package com.example.mentoriapp.Cadastro;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.R;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.TimePickerFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class CadastroRelatoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String tarefa_associada;
    ProgressDialog progressDialog;

    Spinner spinner;
    Button botao_pronto_relato;
    TextInputEditText editTema, editTitulo, editDescricao;
    TextView txtData;
    ImageView imgData;
    RadioGroup radioGroup;
    RadioButton radioBtn;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String presencial;

    public static CadastroRelatoFragment getInstance(){
        CadastroRelatoFragment cadastroRelatoFragment = new CadastroRelatoFragment();
        return cadastroRelatoFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_relato, container, false);
        progressDialog = new ProgressDialog(getActivity());
        spinner = view.findViewById(R.id.spinner_tarefas_associadas);
        botao_pronto_relato = view.findViewById(R.id.btn_relato_pronto);
        editTitulo = view.findViewById(R.id.edit_titulo_relato);
        editTema = view.findViewById(R.id.edit_tema_relato);
        editDescricao = view.findViewById(R.id.edit_descricao_relato);
        radioGroup = view.findViewById(R.id.radio_group_presencial);
        imgData = view.findViewById(R.id.img_data_relato);
        txtData = view.findViewById(R.id.txt_data_relato);

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioBtn = view.findViewById(radioId);
        presencial = radioBtn.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        botao_pronto_relato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarRelato();
            }
        });

        imgData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
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
                int mes = month+1;

                String data = dayOfMonth+"/"+mes+"/"+year;

                txtData.setText(data);
            }
        };

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.numeros, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    private void criarRelato() {
        int id = 1;
        String titulo = editTitulo.getText().toString();
        String tema = editTema.getText().toString();
        String descricao = editDescricao.getText().toString();
        String data = txtData.getText().toString();
        String emailMentorado = mUser.getEmail();

        if(titulo == null || titulo.isEmpty() || tema == null || tema.equals("") || descricao == null || descricao.equals("") ||
                data == null || data.equals("")) {
            Toast.makeText(getActivity(),"Título, Tema, descrição e data do relato obrigatórios!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando relato...");
        progressDialog.show();

        Relato relato = new Relato(id,titulo,tema,descricao,data,presencial,tarefa_associada,emailMentorado);
        FirebaseFirestore.getInstance().collection("relatos")
                .add(relato)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Log.i("Teste",documentReference.getId());
                        Bundle bundle = new Bundle();
                        bundle.putInt("idRelato",id);

                        Toast.makeText(getContext(),"Relato cadastrado!",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_mentorado, new CadastroAprendizadoFragment())
                                .commit();
                        /*
                        editTitulo.setEnabled(false);
                        editTema.setEnabled(false);
                        editDescricao.setEnabled(false);
                        radioBtn.setEnabled(false);
                        spinner.setEnabled(false);
                        imgData.setClickable(false);
                        botao_pronto_relato.setEnabled(false);
                         */
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Ops, houve um erro no cadastro do relato! Tente novamente.",Toast.LENGTH_SHORT).show();
                        Log.i("Teste",e.getMessage());
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tarefa_associada = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        tarefa_associada = "Nenhuma";
    }
}