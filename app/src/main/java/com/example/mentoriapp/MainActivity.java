package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mentoriapp.Fragmentos.ChatFragment;
import com.example.mentoriapp.Fragmentos.ContatoFragment;
import com.example.mentoriapp.Fragmentos.PerfilFragment;
import com.example.mentoriapp.Fragmentos.ReunioesFragment;
import com.example.mentoriapp.Fragmentos.TutorialFragment;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TutorialFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_tutorial);
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
            case R.id.nav_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PerfilFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
                break;
            case R.id.nav_tutorial:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TutorialFragment()).commit();
                break;
            case R.id.nav_reunioes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReunioesFragment()).commit();
                break;
            case R.id.nav_contato:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContatoFragment()).commit();
                break;
            case R.id.nav_sobre:
                Toast.makeText(this,"Sobre",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_configuracao:
                Toast.makeText(this,"Configurações",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sair:
                Toast.makeText(this,"Sair",Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}