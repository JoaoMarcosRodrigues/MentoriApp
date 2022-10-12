package com.example.mentoriapp.Fragmentos_side;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mentoriapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContatoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContatoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContatoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContatoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContatoFragment newInstance(String param1, String param2) {
        ContatoFragment fragment = new ContatoFragment();
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
        View view = inflater.inflate(R.layout.fragment_contato, container, false);

        TextView txtWhatsapp = view.findViewById(R.id.txt_whatsapp);
        TextView txtInstagram = view.findViewById(R.id.txt_instagram);

        txtWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://api.whatsapp.com/send?phone=5579996751725");
            }
        });

        txtInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://www.instagram.com/joaomarcosor/");
            }
        });

        return view;
    }

    private void goToUrl(String s) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(s));
        startActivity(intent);
    }
}