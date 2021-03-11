package com.example.mentoriapp.Cadastro;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.mentoriapp.Adapters.PagerAdapter;
import com.example.mentoriapp.R;
import com.google.android.material.tabs.TabLayout;


public class RelatoFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relato, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.pager);

        getActivity().setActionBar(toolbar);
        getActivity().getActionBar().setTitle("Cadastro de Relato");

        getTabs();

        return view;
    }

    public void getTabs(){
        final PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                pagerAdapter.addFragment(CadastroRelatoFragment.getInstance(),"RELATO");
                pagerAdapter.addFragment(CadastroAprendizadoFragment.getInstance(),"APRENDIZADO");
                pagerAdapter.addFragment(CadastroDificuldadeFragment.getInstance(),"DIFICULDADE");

                viewPager.setAdapter(pagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });

    }
}