package com.example.mentoriapp.Fragmentos_side;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.mentoriapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MentoradoHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MentoradoHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    GridLayout main_grid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MentoradoHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MentoradoHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MentoradoHomeFragment newInstance(String param1, String param2) {
        MentoradoHomeFragment fragment = new MentoradoHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
                    Toast.makeText(getActivity(),"Item "+ finalI +" selecionado",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}