package com.example.words.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.words.Adapter.MyAdapter;
import com.example.words.Bean._words;
import com.example.words.Adapter.MyAdapter;
import com.example.words.Bean._words;
import com.example.words.Pages.Select_word;
import com.example.words.R;
import com.example.words.TransImpl.TransApi;
import com.example.words.TransImpl.TranslateResult;
import com.google.gson.Gson;

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
    private ImageButton searchBtn;
    private TextView resultTextView;
    private Handler handler = new Handler();
    private EditText myEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        myEdit = (EditText)view.findViewById(R.id.edit_word);
        searchBtn = (ImageButton)view.findViewById(R.id.search_btn);
        resultTextView = (TextView)view.findViewById(R.id.result);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String query = myEdit.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String resultJson = new TransApi().getTransResult(query, "auto", "zh");
                        Log.i("Tran Result============================", resultJson);
                        //拿到结果，对结果进行解析。
                        Gson gson = new Gson();
                        TranslateResult translateResult = gson.fromJson(resultJson, TranslateResult.class);
                        final List<TranslateResult.TransResultBean> trans_result = translateResult.getTrans_result();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                String dst = "";
                                for (TranslateResult.TransResultBean s : trans_result
                                ) {
                                    dst = dst + "\n" + s.getDst();
                                }
                                resultTextView.setText(dst);
                            }
                        });

                    }
                }).start();
            }
        });
        initView();
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

    private void initView() {



    }
}
