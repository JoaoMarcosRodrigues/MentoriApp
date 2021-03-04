package com.example.mentoriapp.Fragmentos_side;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mentoriapp.CadastroDificuldadeActivity;
import com.example.mentoriapp.CadastroDuvidaActivity;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaDificuldadesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_dificuldades, container, false);

        FloatingActionButton addDificuldade = view.findViewById(R.id.btnAdicionarDificuldade);

        addDificuldade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CadastroDificuldadeActivity.class));
            }
        });

        return view;
    }
}