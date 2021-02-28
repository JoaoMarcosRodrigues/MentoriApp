package com.example.mentoriapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    // Arrays
    public int[] slide_images = {
            R.drawable.aprendizado,
            R.drawable.cadastrar_tarefa,
            R.drawable.contato_mentor
    };

    public String[] slide_titulos = {
            "PASSO 1",
            "PASSO 2",
            "PASSO 3"
    };

    public String[] slide_conteudos = {
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
    };

    @Override
    public int getCount() {
        return slide_titulos.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideTitulo = view.findViewById(R.id.titulo_slide);
        TextView slideConteudo = view.findViewById(R.id.conteudo_slide);

        slideImageView.setImageResource(slide_images[position]);
        slideTitulo.setText(slide_titulos[position]);
        slideConteudo.setText(slide_conteudos[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
