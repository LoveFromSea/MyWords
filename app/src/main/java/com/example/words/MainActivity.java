package com.example.words;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.words.R;
import com.example.words.Fragment.AFragment;
import com.example.words.Fragment.BFragment;
import com.example.words.Fragment.CFragment;
import com.example.words.Fragment.DFragment;
import com.example.words.Implement.DataBaseUtil;
import com.next.easynavigation.view.EasyNavigationBar;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] tabText = {"复习", "选词", "统计", "设置"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.review, R.mipmap.book, R.mipmap.statistic, R.mipmap.setting};
    //选中时icon
    private int[] selectIcon = {R.mipmap.review_pressed, R.mipmap.book_pressed, R.mipmap.statistic_pressed, R.mipmap.setting_pressed};
    public static EasyNavigationBar navigationMainBar;
    private List<Fragment> fragments = new ArrayList<>();
    //    private int oneDayWord;
    private SQLiteDatabase database;
    public static int wordCounts = 0;
    private Button button;
    private long firstTime = 0;

    /**
     * 双击退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
//                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//            return true;
//        return super.onKeyDown(keyCode, event);
//    }//屏蔽返回键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_main);
        DataBaseUtil util = new DataBaseUtil(MainActivity.this);
        if (!util.checkDataBase()) {
            Log.i("MainActivity", String.valueOf(!util.checkDataBase()));
            try {
                util.copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         * 这一段暂时先空着，留着处理本地读取数据和保存数据，暂时不能更改线程，时间不够
         */

//        EditText editText = (EditText) EditView.findViewById(R.id.oneDay);
//        editText.getText().toString();
        /**
         * 到这里开始正常
         */
        SQLiteDatabase db = LitePal.getDatabase();

        ArrayList<String> wordLists = new ArrayList<String>();
        wordLists = getIntent().getStringArrayListExtra("ListWords");
        try {
            wordCounts = wordLists.size();
        } catch (Exception e) {
            wordCounts = 0;
            e.printStackTrace();
        }
        navigationMainBar = findViewById(R.id.navigationBar);
        fragments.add(new AFragment());
        fragments.add(new BFragment());
        fragments.add(new CFragment());
        fragments.add(new DFragment());
        navigationMainBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .build();

        navigationMainBar.defaultSetting()
                .titleItems(tabText)      //  Tab文字集合  只传文字则只显示文字
                .normalIconItems(normalIcon)   //  Tab未选中图标集合
                .selectIconItems(selectIcon)   //  Tab选中图标集合
                .fragmentList(fragments)       //  fragment集合
                .fragmentManager(getSupportFragmentManager())
                .iconSize(24)     //Tab图标大小
                .tabTextSize(14)   //Tab文字大小
                .tabTextTop(2)     //Tab文字距Tab图标的距离
                .normalTextColor(Color.parseColor("#707070"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#22B3FC"))   //Tab选中时字体颜色
                .navigationBackground(Color.parseColor("#FFFFFF"))   //导航栏背景色
                .setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabSelectEvent(View view, int position) {
                        //Tab点击事件  return true 页面不会切换
                        return false;
                    }

                    @Override
                    public boolean onTabReSelectEvent(View view, int position) {
                        //Tab重复点击事件
                        return false;
                    }
                })
                .smoothScroll(false)  //点击Tab  Viewpager切换是否有动画
                .canScroll(true)    //Viewpager能否左右滑动
                .hintPointLeft(-3)  //调节提示红点的位置hintPointLeft hintPointTop（看文档说明）
                .hintPointTop(-3)
                .hintPointSize(6)    //提示红点的大小
                .msgPointLeft(-10)  //调节数字消息的位置msgPointLeft msgPointTop（看文档说明）
                .msgPointTop(-10)
                .msgPointTextSize(9)  //数字消息中字体大小
                .msgPointSize(18)    //数字消息红色背景的大小
                .centerAlignBottom(true)  //加号是否同Tab文字底部对齐  RULE_BOTTOM时有效；
                .centerTextTopMargin(50)  //加号文字距离加号图片的距离
                .centerTextSize(15)      //加号文字大小
                .centerNormalTextColor(Color.parseColor("#ff0000"))    //加号文字未选中时字体颜色
                .centerSelectTextColor(Color.parseColor("#00ff00"))    //加号文字选中时字体颜色
                .setMsgPointColor(Color.RED) //数字消息、红点背景颜色
                .setMsgPointMoreRadius(5) //消息99+角度半径
                .setMsgPointMoreWidth(35)  //消息99+宽度
                .setMsgPointMoreHeight(40)  //消息99+高度
                .textSizeType(EasyNavigationBar.TextSizeType.TYPE_DP)  //字体单位 建议使用DP  可切换SP
                .setOnTabLoadListener(new EasyNavigationBar.OnTabLoadListener() { //Tab加载完毕回调
                    @Override
                    public void onTabLoadCompleteEvent() {
                        navigationMainBar.setMsgPointCount(0, wordCounts);
                        navigationMainBar.setMsgPointCount(1, 107);
                        navigationMainBar.setHintPoint(4, true);
                    }
                })
                .build();
    }
}
