package com.example.words.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.words.Adapter.LearnedAdapter;
import com.example.words.Adapter.MyAdapter;
import com.example.words.Bean._learned;
import com.example.words.Bean._words;
import com.example.words.Pages.AWord;
import com.example.words.Pages.Select_word;
import com.example.words.R;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/30.
 */

public class CFragment extends androidx.fragment.app.Fragment {
    private List<_learned> learnedList = new ArrayList<>();
    private ListView listView;
    private LearnedAdapter learnedAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        listView = (ListView) view.findViewById(R.id.learn_list);
        initData();
        return view;
    }

    //提示消息
    public void showToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        learnedList = LitePal.findAll(_learned.class);
        learnedAdapter = new LearnedAdapter(learnedList, getActivity());
        listView.setAdapter(learnedAdapter);
    }
}
