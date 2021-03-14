package com.example.mentoriapp.Fragmentos_side;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mentoriapp.R;

public class PerfilMentoradoVisitaFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visita_mentorado_perfil, container, false);

        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }
}