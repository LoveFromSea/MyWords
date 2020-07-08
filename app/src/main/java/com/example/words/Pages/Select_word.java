package com.example.words.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.words.Adapter.MyAdapter;
import com.example.words.Bean._words;
import com.example.words.MainActivity;
import com.example.words.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Select_word extends AppCompatActivity {
    private ListView listView;
    //列表标题list
    private List<String> listText;
    private List<_words> listWord;
    //创建适配器对象
    private MyAdapter adapter;
    //标题栏
    private CommonTitleBar commonTitleBar;
    //对象数据
    private Map<Integer, Boolean> map = new HashMap<>();

    //    private TextView titleWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_select_word);
        commonTitleBar = (CommonTitleBar) findViewById(R.id.select_title);
        init();
        //将数据显示在页面上
        initDate();
        commonTitleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    Select_word.this.finish();
                }
                if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                    map = adapter.getMap();
                    if (map != null) {
                        ArrayList<Integer> strings = getKeyList(map, true);
                        Intent intent = new Intent(Select_word.this, MainActivity.class);
                        ArrayList<String> tempListWord = new ArrayList<String>();
                        for (int i = 0; i < strings.size(); i++) {
                            tempListWord.add(listWord.get(strings.get(i)).getWord());
                        }
                        intent.putStringArrayListExtra("ListWords", tempListWord);
                        intent.putExtra("wordCount", tempListWord.size());
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Select_word.this, MainActivity.class);
                        intent.putStringArrayListExtra("ListWords", null);
                        intent.putExtra("wordCount", 0);

                        finish();
                    }
                }
            }
        });

    }

    public static Integer getKey(Map<Integer, Boolean> map, Boolean bool) {
        Integer key = null;
        //Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
        for (Integer getKey : map.keySet()) {
            if (map.get(getKey) == bool) {
                key = getKey;
            }
        }
        return key;
        //这个key肯定是最后一个满足该条件的key.
    }

    public static ArrayList<Integer> getKeyList(Map<Integer, Boolean> map, Boolean bool) {
        ArrayList<Integer> keyList = new ArrayList();
        for (Integer getKey : map.keySet()) {
            if (map.get(getKey) == bool) {
                keyList.add(getKey);
            }
        }
        return keyList;
    }

    private void init() {
        /**
         * 这个取消注释可以了
         */
        listView = (ListView) findViewById(R.id.word_list);
        listText = new ArrayList<String>();
    }

    private void initDate() {
        listWord = LitePal.findAll(_words.class);
        //模拟创建数据
        for (_words o : listWord) {
            listText.add(o.getWord());
            Log.i("MSG===============================", o.getWord());
        }
        adapter = new MyAdapter(listText, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                _words o = listWord.get(i);
                Intent intent = new Intent(Select_word.this, AWord.class);
                intent.putExtra("Word", o.getWord());
                startActivity(intent);
            }
        });
    }
}
