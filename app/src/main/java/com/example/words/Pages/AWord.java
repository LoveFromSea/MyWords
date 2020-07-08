package com.example.words.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.words.Bean._words;
import com.example.words.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import org.litepal.LitePal;

import java.util.List;

public class AWord extends AppCompatActivity {
    private TextView titleWord;
    private TextView wordMean;
    private TextView wordUsPhone;
    private TextView wordSentence;
    private TextView wordLikeWord;
    private CommonTitleBar commonTitleBar;
    private TextView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_aword);
        commonTitleBar = (CommonTitleBar) findViewById(R.id.a_word_title_bar);
        commonTitleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    AWord.this.finish();
                }
            }
        });

        /**
         * 获取数据并查询
         */
        String word = getIntent().getStringExtra("Word");
        LayoutInflater factory = LayoutInflater.from(AWord.this);
        View layout = factory.inflate(R.layout.title_text, null);
        myView = (TextView) layout.findViewById(R.id.title_title);
//        myView=(TextView)findViewById(R.id.title_title);
        myView.setText(word);
//        commonTitleBar.setCenterView(layout);
        titleWord = (TextView) findViewById(R.id.word_head);
        titleWord.setText(word);
        List<_words> words = LitePal.where("word=?", word).order("word").find(com.example.words.Bean._words.class);
        com.example.words.Bean._words oneWord = words.get(0);
        /**
         * 查找音标、意思、句子、相近词
         */
        wordUsPhone = (TextView) findViewById(R.id.us_phone);
        wordUsPhone.setText("[" + oneWord.getUsphone() + "]");
        wordMean = (TextView) findViewById(R.id.word_mean);
        String word_Mean = oneWord.getMeans().replace("|||", "\n");
        wordMean.setText(word_Mean);
        wordSentence = (TextView) findViewById(R.id.word_sentence);
        String word_sentence = oneWord.getSentence().replace("|||", "\n");
        word_sentence = word_sentence.replace("。", "\n");
        wordSentence.setText(word_sentence);
        wordLikeWord = (TextView) findViewById(R.id.word_like_word);
        String word2Word;
        word2Word = oneWord.getLikewords().replace(".", "\n");
        wordLikeWord.setText(word2Word);
    }
}
