package com.dl.pocketlongpresschangelistviewitem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final int TEST_DATA_COUNT = 20;

    private ListView mTestListView;
    private TestAdapter mTestAdapter;
    private ArrayList<String> mTestData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        findViews();
        initTestData();
        initTestListView();
    }

    private void initTestData() {
        for(int i = 0 ; i < TEST_DATA_COUNT ; i++) {
            mTestData.add("Data is " + i);
        }
    }

    private void findViews() {
        mTestListView = (ListView) findViewById(R.id.test_list_view);
    }

    private void initTestListView() {
        mTestAdapter = new TestAdapter(this, R.layout.test_list_item, mTestData.toArray(new String[mTestData.size()]));
        mTestListView.setAdapter(mTestAdapter);
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
