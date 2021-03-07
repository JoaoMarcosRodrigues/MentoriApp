package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mentoriapp.Fragmentos_side.ChatFragment;
import com.example.mentoriapp.Fragmentos_side.ConfiguracaoFragment;
import com.example.mentoriapp.Fragmentos_side.ContatoFragment;
import com.example.mentoriapp.Fragmentos_side.MentoradoHomeFragment;
import com.example.mentoriapp.Fragmentos_side.PerfilFragment;
import com.example.mentoriapp.Listas.ListaReunioesFragment;
import com.example.mentoriapp.Fragmentos_side.SobreFragment;
import com.example.mentoriapp.Fragmentos_side.TutorialFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MentoradoHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MentoradoHomeFragment()).commit();
                break;
            case R.id.nav_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new PerfilFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ChatFragment()).commit();
                break;
            case R.id.nav_reuniao:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ListaReunioesFragment()).commit();
                break;
            case R.id.nav_tutorial:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new TutorialFragment()).commit();
                break;
            case R.id.nav_contato:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ContatoFragment()).commit();
                break;
            case R.id.nav_sobre:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new SobreFragment()).commit();
                break;
            case R.id.nav_configuracoes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ConfiguracaoFragment()).commit();
                break;
            case R.id.nav_sair:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Sair do aplicativo");
                alertDialog.setMessage("Tem certeza que deseja sair?");
                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent_sair = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent_sair);
                    }
                });
                alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Ok!",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.create().show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}