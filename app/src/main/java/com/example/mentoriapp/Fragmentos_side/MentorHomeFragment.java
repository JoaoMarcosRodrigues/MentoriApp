package com.example.mentoriapp.Fragmentos_side;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mentoriapp.Cadastro.CadastroTarefaFragment;
import com.example.mentoriapp.Listas.ListaAvaliacoesFragment;
import com.example.mentoriapp.Listas.ListaFeedbacksFragment;
import com.example.mentoriapp.Listas.ListaReunioesMentorFragment;
import com.example.mentoriapp.Listas.MeusMentoradosFragment;
import com.example.mentoriapp.R;

public class MentorHomeFragment extends Fragment {

    GridLayout main_grid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_mentor, container, false);

        main_grid = view.findViewById(R.id.grid_layout_home);

        setSingleAction(main_grid);

        return view;
    }

    private void setSingleAction(GridLayout main_grid) {
        for(int i=0; i<main_grid.getChildCount(); i++){
            CardView cardView = (CardView) main_grid.getChildAt(i);
            int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(finalI){
                        case 0:
                            //getActivity().getSupportFragmentManager()
                            // .beginTransaction()
                            // .replace(R.id.fragment, new NotificacoesFragment())
                            // .commit();
                            Toast.makeText(getContext(),"Em produção",Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new CadastroTarefaFragment())
                                    .commit();
                            break;
                        case 2:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new ListaAvaliacoesFragment())
                                    .commit();
                            break;
                        case 3:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new MeusMentoradosFragment())
                                    .commit();
                            break;
                        case 4:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new ListaReunioesMentorFragment())
                                    .commit();
                            break;
                        case 5:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new ListaFeedbacksFragment())
                                    .commit();
                            break;
                    }
                }
            });
        }
    }
}