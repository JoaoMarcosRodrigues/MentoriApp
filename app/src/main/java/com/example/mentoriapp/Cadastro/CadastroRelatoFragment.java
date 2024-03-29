package com.example.mentoriapp.Cadastro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.Visualizer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Listas.ListaRelatosFragment;
import com.example.mentoriapp.R;
import com.example.mentoriapp.Classes.Relato;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastroRelatoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String tarefa_associada;
    ProgressDialog progressDialog;

    private Spinner spinner;
    private Button botao_pronto_relato;
    private TextInputEditText editTema, editTitulo, editDescricao;
    private TextInputLayout inputTema,inputTitulo,inputDescricao;
    private TextView txtData;
    private ImageView imgData;
    private RadioGroup radioGroup;
    private RadioButton radioBtnSim,radioBtnNao;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private CollectionReference ref;
    private String presencial;
    private int radioId;
    int maxid;

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
        inputDescricao = view.findViewById(R.id.layout_descricao_relato);
        inputTitulo = view.findViewById(R.id.layout_titulo_relato);
        inputTema = view.findViewById(R.id.layout_tema_relato);
        radioGroup = view.findViewById(R.id.radio_group_presencial);
        radioBtnSim = view.findViewById(R.id.radio_btn_sim);
        radioBtnNao = view.findViewById(R.id.radio_btn_nao);
        imgData = view.findViewById(R.id.img_data_relato);
        txtData = view.findViewById(R.id.txt_data_relato);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        ref = mFirestore.collection("tarefas");
        List<String> listaTarefas = new ArrayList<>();
        listaTarefas.add("Selecione...");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,listaTarefas){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        ref.whereEqualTo("emailDestinatario",mUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("titulo");
                        listaTarefas.add(subject);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tarefa_associada = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Quando nada for selecionado
            }
        });

        mFirestore.collection("relatos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    maxid = 1;
                    for(DocumentSnapshot document : task.getResult()){
                        maxid++;
                    }
                }
            }
        });

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
                String dia,mes,data;
                month=month+1;

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

                txtData.setText(data);
            }
        };

        return view;
    }

    private void criarRelato() {
        String titulo = editTitulo.getText().toString();
        String tema = editTema.getText().toString();
        String descricao = editDescricao.getText().toString();
        String data = txtData.getText().toString();
        String emailMentorado = mUser.getEmail();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(titulo.isEmpty()){
            inputTitulo.setError("Título obrigatório!");
        }else{
            inputTitulo.setError("");
        }

        if(tema.isEmpty()){
            inputTema.setError("Tema obrigatório!");
        }else{
            inputTema.setError("");
        }

        if(descricao.isEmpty()){
            inputDescricao.setError("Descricação obrigatória!");
        }else{
            inputDescricao.setError("");
        }

        if(data.isEmpty()){
            Toast.makeText(getContext(),"Data do Relato obrigatória!",Toast.LENGTH_SHORT).show();
        }

        if(tarefa_associada.equals("Selecione...")){
            Toast.makeText(getContext(),"Selecione uma Tarefa para continuar!",Toast.LENGTH_SHORT).show();
        }

        if(!titulo.isEmpty() && !tema.isEmpty() && !descricao.isEmpty() && !data.isEmpty() && !tarefa_associada.equals("Selecione...")) {
            if(radioBtnSim.isChecked())
                presencial = "Sim";
            else
                presencial = "Não";

            progressDialog.setMessage("Cadastrando relato...");
            progressDialog.show();

            Relato relato = new Relato(maxid,titulo,tema,descricao,data,presencial,tarefa_associada,emailMentorado);
            FirebaseFirestore.getInstance().collection("relatos")
                    .add(relato)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.dismiss();
                            Log.i("Teste",documentReference.getId());
                            Bundle bundle = new Bundle();
                            bundle.putInt("idRelato",maxid);

                            builder.setTitle("Cadastro do relato")
                                    .setMessage("Relato cadastrado!")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.create();
                            builder.show();
                            //Toast.makeText(getContext(),"Relato cadastrado!",Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentorado, new ListaRelatosFragment()).addToBackStack(null)
                                    .commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            builder.setTitle("Cadastro do relato")
                                    .setMessage("Ops, houve um erro no cadastro do relato! Tente novamente.")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.create();
                            builder.show();
                            //Toast.makeText(getContext(),"Ops, houve um erro no cadastro do relato! Tente novamente.",Toast.LENGTH_SHORT).show();
                            Log.i("Teste",e.getMessage());
                        }
                    });
        }
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