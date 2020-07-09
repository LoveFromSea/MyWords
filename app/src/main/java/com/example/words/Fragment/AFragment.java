package com.example.words.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.words.Bean._learned;
import com.example.words.Bean._words;
//import com.example.wwword.Bean._words;
//import com.example.wwword.MainActivity;
//import com.example.wwword.R;
import com.example.words.R;
import com.example.words.Bean._words;
import com.example.words.MainActivity;
import com.next.easynavigation.utils.NavigationUtil;
import com.next.easynavigation.view.EasyNavigationBar;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import org.litepal.LitePal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2020/6/30.
 */

public class AFragment extends androidx.fragment.app.Fragment {
    private Button btnCre;
    private Context context;
    private static List<String> data = null;
    private CommonTitleBar commonTitleBar;
    private TextView myView;
    private Integer wordCount = 0;
    private TextView textCenterTip;
    private TextView textCenterUsPhone;
    private TextView textCenterMeans;
    private TextView textCenterSentences;
    private TextView textCenterLikely;
    private static List<_words> concreteData = null;
    private RelativeLayout relativeLayout;
    private ScrollView scrollView;
    private Button unCertain;
    private Button unKnown;
    private EasyNavigationBar easyNavigationBar;

    /**
     * 测试一下
     */
//    private EasyNavigationBar navigationBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, null);
        this.context = getActivity();
        data = getActivity().getIntent().getStringArrayListExtra("ListWords");
        int j = 0;
        commonTitleBar = (CommonTitleBar) view.findViewById(R.id.first_word_title);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.main);
        scrollView = (ScrollView) view.findViewById(R.id.all_content);
        textCenterUsPhone = (TextView) view.findViewById(R.id.main_us_phone);
        textCenterMeans = (TextView) view.findViewById(R.id.main_means);
        textCenterSentences = (TextView) view.findViewById(R.id.main_exam);
        textCenterLikely = (TextView) view.findViewById(R.id.main_likely);
        MainActivity mainActivity = (MainActivity) getActivity();
        easyNavigationBar = (EasyNavigationBar) mainActivity.findViewById(R.id.navigationBar);
        if (data != null && data.size() != 0)
            commonTitleBar.getCenterTextView().setText(data.get(wordCount));
//        if (data != null) {
//            for (int i = 0; i < data.size(); i++) {
//                Log.i("MSG", LitePal.where("word=?", data.get(i)).order("word").find(OneWord.class).get(0).getMeans());
//            }
//        }

        unCertain = (Button) view.findViewById(R.id.uncertain);
        unKnown = (Button) view.findViewById(R.id.unknown);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                textCenterTip.setText(concreteData.get(wordCount).getMeans());
                if (data != null && data.size() != 0) {
                    _words words = LitePal.where("word=?", data.get(wordCount)).order("word").find(_words.class).get(0);
                    String theMean = words.getMeans().replace("|||", "\n");
                    String theUsPhone = words.getUsphone();
                    String theSentence = words.getSentence().replace("|||", "\n");
                    theSentence = theSentence.replace("。", "\n");
                    String theLikeWord = words.getLikewords().replace(".", "\n");
                    relativeLayout.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    textCenterUsPhone.setText("[" + theUsPhone + "]");
                    textCenterMeans.setText(theMean);
                    textCenterSentences.setText(theSentence);
                    textCenterLikely.setText(theLikeWord);
//                    textCenterTip.setText(theMean);
                } else if (data == null || data.size() == 0) {
                    Toast.makeText(context, "未选择单词，请去选词中挑选", Toast.LENGTH_SHORT);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnCre = (Button) getActivity().findViewById(R.id.know);
        btnCre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.wordCounts--;
                if (data != null && data.size() != 0) {
                    wordCount++;
                    _learned learnedWords = new _learned();
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.CHINA);
                    String result = format.format(date);
                    Log.i("MSG================================", date.toString());
                    if (wordCount < data.size()) {
                        easyNavigationBar.setMsgPointCount(0, MainActivity.wordCounts);
                        String deleteStr = data.get(wordCount - 1);
                        learnedWords.setWord(deleteStr);
                        learnedWords.setLearned_day(result);
                        learnedWords.save();
                        LitePal.deleteAll(_words.class, "word=?", deleteStr);
                        commonTitleBar.getCenterTextView().setText(data.get(wordCount));
                        scrollView.setVisibility(View.INVISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    } else if (wordCount == data.size()) {
                        easyNavigationBar.clearMsgPoint(0);
                        String deleteStr = data.get(wordCount - 1);
                        learnedWords.setWord(data.get(wordCount - 1));
                        learnedWords.setLearned_day(result);
                        learnedWords.save();
                        LitePal.deleteAll(_words.class, "word=?", deleteStr);
                    } else {
                        Toast.makeText(context, "完成了今天的计划", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        unCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data != null && data.size() != 0) {
                    if (wordCount < data.size()) {
                        scrollView.setVisibility(View.INVISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    } else {
//                        Intent intent=new Intent();
                        Toast.makeText(context, "完成了今天的计划", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        unKnown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "这都忘记了?自己好好背背", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FileInput(String content) {
        String FILENAME = "my_words.txt";
        String string = content;
        FileOutputStream fos = null;
        try {
            fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(string.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
