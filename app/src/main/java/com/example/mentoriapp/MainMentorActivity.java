package com.example.mentoriapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mentoriapp.Fragmentos_side.ChatMentorFragment;
import com.example.mentoriapp.Fragmentos_side.ConfiguracaoFragment;
import com.example.mentoriapp.Fragmentos_side.ContatoFragment;
import com.example.mentoriapp.Fragmentos_side.MentorHomeFragment;
import com.example.mentoriapp.Fragmentos_side.PerfilMentorFragment;
import com.example.mentoriapp.Fragmentos_side.SobreFragment;
import com.example.mentoriapp.Fragmentos_side.TutorialFragment;
import com.example.mentoriapp.Listas.ListaReunioesMentoradoFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class MainMentorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_mentor);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor, new MentorHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        //verifyAuthentication();
        atualizarHeader();
        //NavigationView navigationView = findViewById(R.id.nav_view_mentor);
        if(currentUser != null){
            View headerView = navigationView.getHeaderView(0);

            TextView nome = headerView.findViewById(R.id.txt_nome);
            TextView email = headerView.findViewById(R.id.txt_email);
            ImageView foto = headerView.findViewById(R.id.image_perfil);

            nome.setText(currentUser.getDisplayName());
            email.setText(currentUser.getEmail());

            Picasso.get().load(currentUser.getPhotoUrl()).into(foto);
        } else
            Toast.makeText(this,"Usuário não está logado! Faça o login.",Toast.LENGTH_SHORT).show();
    }

    private void verifyAuthentication() {
        if(FirebaseAuth.getInstance().getUid() == null){
            Intent intent = new Intent(MainMentorActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor, new MentorHomeFragment()).commit();
                break;
            case R.id.nav_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor,new PerfilMentorFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor, new ChatMentorFragment()).commit();
                break;
            case R.id.nav_reuniao:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor, new ListaReunioesMentoradoFragment()).commit();
                break;
            case R.id.nav_tutorial:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor, new TutorialFragment()).commit();
                break;
            case R.id.nav_contato:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor, new ContatoFragment()).commit();
                break;
            case R.id.nav_sobre:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor, new SobreFragment()).commit();
                break;
            case R.id.nav_configuracoes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mentor, new ConfiguracaoFragment()).commit();
                break;
            case R.id.nav_sair:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Sair do aplicativo");
                alertDialog.setMessage("Tem certeza que deseja sair?");
                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        verifyAuthentication();
                    }
                });
                alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainMentorActivity.this,"Ok!",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.create().show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void atualizarHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view_mentor);
        View headerView = navigationView.getHeaderView(0);

        TextView nome = headerView.findViewById(R.id.txt_nome);
        TextView email = headerView.findViewById(R.id.txt_email);
        ImageView foto = headerView.findViewById(R.id.image_perfil);

        nome.setText(currentUser.getDisplayName());
        email.setText(currentUser.getEmail());

        Picasso.get().load(currentUser.getPhotoUrl()).into(foto);


    }
}