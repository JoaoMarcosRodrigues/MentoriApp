package com.example.mentoriapp.Cadastro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Fragmentos_side.MentoradoHomeFragment;
import com.example.mentoriapp.MainActivity;
import com.example.mentoriapp.Mentorado.CadastroMentoradoActivity;
import com.example.mentoriapp.Mentorado.Mentorado;
import com.example.mentoriapp.R;
import com.example.mentoriapp.Relato.Relato;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class CadastroRelatoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    boolean presencial;
    String tarefa_associada;
    ProgressDialog progressDialog;

    Spinner spinner;
    Button botao_pronto_relato;
    TextInputEditText editTema;
    TextInputEditText editDescricao;
    RadioButton radioBtnSim,radioBtnNao;
    TextView txtData;

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
        editTema = view.findViewById(R.id.edit_tema_relato);
        editDescricao = view.findViewById(R.id.edit_descricao_relato);
        radioBtnSim = view.findViewById(R.id.radio_btn_sim);
        radioBtnNao = view.findViewById(R.id.radio_btn_nao);
        txtData = view.findViewById(R.id.txt_data_relato);

        if(radioBtnSim.isChecked())
            presencial = true;
        if(radioBtnNao.isChecked())
            presencial = false;

        // CORRIGIR
        //tarefa_associada = spinner.getSelectedItem().toString();
        tarefa_associada = "Nenhuma";

        botao_pronto_relato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarRelato();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.numeros, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    private void criarRelato() {
        String tema = editTema.getText().toString();
        String descricao = editDescricao.getText().toString();
        String data = txtData.getText().toString();

        data = "04/04/2021";

        if(tema == null || tema.equals("") || descricao == null || descricao.equals("") ||
                data == null || data.equals("")) {
            Toast.makeText(getActivity(),"Tema, descrição e data do relato obrigatórios!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando relato...");
        progressDialog.show();

        Relato relato = new Relato(tema,descricao,data,presencial,tarefa_associada);
        FirebaseFirestore.getInstance().collection("relatos")
                .add(relato)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Log.i("Teste",documentReference.getId());
                        Toast.makeText(getContext(),"Relato cadastrado!",Toast.LENGTH_SHORT).show();
                        /*
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment, new CadastroAprendizadoFragment())
                                .commit();

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
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}