package com.example.mentoriapp.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Reuniao;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReuniaoMentorAdapter extends FirestoreRecyclerAdapter<Reuniao, ReuniaoMentorAdapter.ReuniaoHolder> {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public ReuniaoMentorAdapter(@NonNull FirestoreRecyclerOptions<Reuniao> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReuniaoHolder holder, int position, @NonNull Reuniao model) {
        holder.txtTitulo.setText(model.getTitulo());
        holder.txtDescricao.setText(model.getDescricao());
        holder.txtHorario.setText(model.getHorario());
        holder.txtData.setText(model.getData());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(position);
                String documentPath = documentSnapshot.getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                        .setTitle("Excluir reunião")
                        .setMessage("Tem certeza que deseja excluir esta reunião?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("mentores").document(user.getUid()).collection("reunioes").document(documentPath).delete();
                                notifyItemRemoved(holder.getAdapterPosition());
                                //Toast.makeText(v.getContext(),"Clicou SIM!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(v.getContext(),"Cancelou id ="+documentPath,Toast.LENGTH_SHORT).show();
                            }
                        });

                builder.create();
                builder.show();

                return true;
            }
        });
    }

    @NonNull
    @Override
    public ReuniaoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_reuniao,
                parent,false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        return new ReuniaoMentorAdapter.ReuniaoHolder(v);
    }


    class ReuniaoHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtDescricao;
        TextView txtHorario;
        TextView txtData;

        public ReuniaoHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txt_titulo_reuniao);
            txtDescricao = itemView.findViewById(R.id.txt_descricao_reuniao);
            txtHorario = itemView.findViewById(R.id.txt_horario_reuniao);
            txtData = itemView.findViewById(R.id.txt_data_reuniao);
        }

    }

}
