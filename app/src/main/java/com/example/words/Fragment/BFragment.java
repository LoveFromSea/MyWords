package com.example.words.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import com.example.words.Adapter.MyAdapter;
import com.example.words.Bean._words;
import com.example.words.Adapter.MyAdapter;
import com.example.words.Bean._words;
import com.example.words.Pages.Select_word;
import com.example.words.R;

import java.util.List;

/**
 * Created by Administrator on 2020/6/30.
 */

public class BFragment extends androidx.fragment.app.Fragment {
    private ListView listView;
    //列表标题list
    private List<String> listText;
    private List<_words> listWord;
    //创建适配器对象
    private MyAdapter adapter;
    private Context BContext;
    private Button GoButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.BContext = getActivity();
        GoButton = (Button) getActivity().findViewById(R.id.GotoBook);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(BContext, Select_word.class);
                startActivity(intent);
            }
        });

    }

}
