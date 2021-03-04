package com.example.mentoriapp.Fragmentos_side;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaRelatosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_relatos, container, false);

        FloatingActionButton addRelato = view.findViewById(R.id.btnAdicionarRelato);

        addRelato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), CadastroReuniaoActivity.class));
            }
        });

        return view;
    }
}