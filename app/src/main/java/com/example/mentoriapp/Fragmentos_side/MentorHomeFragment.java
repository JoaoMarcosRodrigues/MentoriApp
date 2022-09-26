package com.example.mentoriapp.Fragmentos_side;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mentoriapp.Cadastro.CadastroAvaliacaoFragment;
import com.example.mentoriapp.Cadastro.CadastroTarefaMentorFragment;
import com.example.mentoriapp.Listas.ListaAvaliacoesFragment;
import com.example.mentoriapp.Listas.ListaFeedbacksFragment;
import com.example.mentoriapp.Listas.ListaReunioesMentorFragment;
import com.example.mentoriapp.Listas.ListaTarefasMentorFragment;
import com.example.mentoriapp.Listas.ListaTarefasMentoradoFragment;
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
                            // .replace(R.id.fragment, new NotificacoesFragment()).addToBackStack(null)
                            // .commit();
                            Toast.makeText(getContext(),"Notificações em produção",Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new ListaTarefasMentorFragment()).addToBackStack(null)
                                    .commit();
                            break;
                        case 2:
                            /*
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new ListaAvaliacoesFragment()).addToBackStack(null)
                                    .commit();
                             */
                            Toast.makeText(getContext(),"Avaliações em produção",Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new MeusMentoradosFragment()).addToBackStack(null)
                                    .commit();
                            break;
                        case 4:
                            /*
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new ListaReunioesMentorFragment()).addToBackStack(null)
                                    .commit();
                             */
                            startActivity(new Intent(getContext(),ReuniaoActivity.class));
                            break;
                        case 5:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new ListaFeedbacksFragment()).addToBackStack(null)
                                    .commit();
                            break;
                    }
                }
            });
        }
    }
}