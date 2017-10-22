package ca.six.unittestapp.contrib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.six.unittestapp.R;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-10-20.
 */

public class ItemListActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 41; i++) {
            data.add(getString(R.string.item) + i);
        }
        RecyclerView rvItemList = (RecyclerView) findViewById(R.id.rv_items);
        rvItemList.setLayoutManager(new LinearLayoutManager(this));
        rvItemList.setAdapter(new ItemListAdapter(this, data));
    }
}
