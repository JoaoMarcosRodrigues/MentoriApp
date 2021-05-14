package com.example.mentoriapp.Fragmentos_side;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.mentoriapp.Listas.ListaAprendizadosFragment;
import com.example.mentoriapp.Listas.ListaDificuldadesFragment;
import com.example.mentoriapp.Listas.ListaRelatosFragment;
import com.example.mentoriapp.Listas.ListaReunioesMentoradoFragment;
import com.example.mentoriapp.Listas.ListaTarefasFragment;
import com.example.mentoriapp.R;

public class MentoradoHomeFragment extends Fragment {
    GridLayout main_grid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_mentorado, container, false);

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
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentorado, new ListaRelatosFragment()).addToBackStack(null)
                                    .commit();
                            break;
                        case 1:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentorado, new ListaTarefasFragment()).addToBackStack(null)
                                    .commit();
                            break;
                        case 2:
                            //startActivity(new Intent(getActivity(), ContatoMentorFragment.class));
                            Toast.makeText(getActivity(),"Contato Mentor em produção",Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentorado, new ListaReunioesMentoradoFragment()).addToBackStack(null)
                                    .commit();
                            break;
                        case 4:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentorado, new ListaAprendizadosFragment()).addToBackStack(null)
                                    .commit();
                            break;
                        case 5:
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentorado, new ListaDificuldadesFragment()).addToBackStack(null)
                                    .commit();
                            break;
                    }
                }
            });
        }
    }
}