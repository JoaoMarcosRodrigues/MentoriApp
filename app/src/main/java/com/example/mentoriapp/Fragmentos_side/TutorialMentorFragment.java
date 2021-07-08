package com.example.mentoriapp.Fragmentos_side;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mentoriapp.Adapters.SliderAdapter;
import com.example.mentoriapp.Adapters.SliderMentorAdapter;
import com.example.mentoriapp.R;

public class TutorialMentorFragment extends Fragment {
    private TextView[] mDots;
    private SliderMentorAdapter sliderAdapter;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private Button mAnteriorBtn, mProximoBtn;
    private int mCurrentPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial_mentor, container, false);

        mAnteriorBtn = view.findViewById(R.id.btn_anterior);
        mProximoBtn = view.findViewById(R.id.btn_proximo);

        mSlideViewPager = view.findViewById(R.id.view_pager);
        mDotLayout = view.findViewById(R.id.layout_navegacao);

        sliderAdapter = new SliderMentorAdapter(getActivity());
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mProximoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage+1);
            }
        });

        mAnteriorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });

        return view;
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i=0; i < mDots.length; i++){
            mDots[i] = new TextView(getActivity());
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparent));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;

            if(position == 0){
                mProximoBtn.setEnabled(true);
                mAnteriorBtn.setEnabled(false);
                mAnteriorBtn.setVisibility(View.INVISIBLE);

                mProximoBtn.setText("Próximo");
                mAnteriorBtn.setText("");

            }else if(position == mDots.length-1){
                mProximoBtn.setEnabled(true);
                mAnteriorBtn.setEnabled(true);
                mAnteriorBtn.setVisibility(View.VISIBLE);

                mProximoBtn.setText("Pronto");
                mAnteriorBtn.setText("Anterior");
            }else{
                mProximoBtn.setEnabled(true);
                mAnteriorBtn.setEnabled(true);
                mAnteriorBtn.setVisibility(View.VISIBLE);

                mProximoBtn.setText("Próximo");
                mAnteriorBtn.setText("Anterior");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}