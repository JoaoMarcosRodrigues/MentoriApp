package com.example.mentoriapp.Mentor;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.ItemMentorado;
import com.example.mentoriapp.ItemMentoradoAdapter;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class CadastroMentor2Activity extends AppCompatActivity {
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private ItemMentoradoAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mentor2);

        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.pesquisar_mentorados);

        ArrayList<ItemMentorado> itemsMentorados = new ArrayList<>();
        itemsMentorados.add(new ItemMentorado(true,"João Marcos","Front-End"));
        itemsMentorados.add(new ItemMentorado(false,"Rivanildo","Back-End"));
        itemsMentorados.add(new ItemMentorado(true,"Kelveng","Banco de Dados"));
        itemsMentorados.add(new ItemMentorado(true,"João Marcos","Front-End"));
        itemsMentorados.add(new ItemMentorado(false,"Rivanildo","Back-End"));
        itemsMentorados.add(new ItemMentorado(true,"Kelveng","Banco de Dados"));
        itemsMentorados.add(new ItemMentorado(true,"João Marcos","Front-End"));
        itemsMentorados.add(new ItemMentorado(false,"Rivanildo","Back-End"));
        itemsMentorados.add(new ItemMentorado(true,"Kelveng","Banco de Dados"));
        itemsMentorados.add(new ItemMentorado(true,"João Marcos","Front-End"));
        itemsMentorados.add(new ItemMentorado(false,"Rivanildo","Back-End"));
        itemsMentorados.add(new ItemMentorado(true,"Kelveng","Banco de Dados"));

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new ItemMentoradoAdapter(itemsMentorados);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}