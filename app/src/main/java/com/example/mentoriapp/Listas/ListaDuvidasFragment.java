package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mentoriapp.Cadastro.CadastroDuvidaActivity;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaDuvidasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_duvidas, container, false);

        FloatingActionButton addDuvida = view.findViewById(R.id.btnAdicionarDuvida);

        addDuvida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CadastroDuvidaActivity.class));
            }
        });

        return view;
    }
}