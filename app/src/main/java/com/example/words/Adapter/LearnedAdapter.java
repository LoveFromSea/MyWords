package com.example.words.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.words.Bean._learned;
import com.example.words.R;

import java.util.ArrayList;
import java.util.List;

public class LearnedAdapter extends BaseAdapter {
    private List<_learned> learnedList;
    private Context context;

    public LearnedAdapter(List<_learned> learnedList, Context context) {
        this.context = context;
        this.learnedList = learnedList;
    }

    @Override
    public int getCount() {
        return learnedList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.list_learned_words, null);
        } else {
            view = convertView;//复用历史缓存对象
        }
        TextView wordText = (TextView) view.findViewById(R.id.learned_words_content);
        TextView timeText =(TextView)view.findViewById(R.id.learned_time);
        wordText.setText(learnedList.get(i).getWord());
        timeText.setText(learnedList.get(i).getLearned_day());
        return view;
    }
}
