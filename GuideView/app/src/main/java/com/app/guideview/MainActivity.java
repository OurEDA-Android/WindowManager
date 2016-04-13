package com.app.guideview;

import android.graphics.PixelFormat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WindowManager wm;
    View root, view1, view2;
    ArrayList<View> viewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showGuidePager();
    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    };

    private void showGuidePager() {
        wm = getWindowManager();
        root = LayoutInflater.from(this).inflate(R.layout.guide, null);
        view1 = LayoutInflater.from(this).inflate(R.layout.guide_tab1, null);
        view2 = LayoutInflater.from(this).inflate(R.layout.guide_tab2, null);

        viewList.add(view1);
        viewList.add(view2);
        ViewPager vp = (ViewPager) root.findViewById(R.id.viewPager);
        vp.addView(view1);
        vp.addView(view2);
        vp.setAdapter(pagerAdapter);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.START | Gravity.TOP;
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_STARTING;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        wm.addView(root, params);

        Button startButton = (Button) view2.findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeViewImmediate(root);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
