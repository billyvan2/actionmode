package com.sueword.actionmode;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String[]names=new String[]{"One","Two","Three","Four","Five"};
    private ListView listView;
    private ArrayList<Integer>checklist=new ArrayList<>();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int j=0;j<5;j++){
            checklist.add(0);
        }
        listView=findViewById(R.id.mylist);
        final List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        for(int i=0;i<names.length;i++){
            Map<String,Object>showItem=new HashMap<>();
            showItem.put("name",names[i]);
            list.add(showItem);
        }

        final SimpleAdapter simpleAdapter=new SimpleAdapter(this,list,R.layout.list_layout,new String[]{"name"},new int[]{R.id.name});
        listView.setAdapter(simpleAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(b){
                    Log.d("TAG","The position is "+i);
                    checklist.set(i,1);
                    Log.d("TAG","the checklist value in "+i+"is "+checklist.get(i));
                }else {
                    Log.d("TAG","The position is "+i);
                    checklist.set(i,0);
                    Log.d("TAG","the checklist value in "+i+"is "+checklist.get(i));
                }
                actionMode.setSubtitle(listView.getCheckedItemCount()+" selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

                MenuInflater inflater=actionMode.getMenuInflater();
                inflater.inflate(R.menu.action_mode,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_delete:
                        for(int i=checklist.size()-1;i>=0;i--){
                            if(checklist.get(i)==1){
                                list.remove(i);
                                checklist.remove(i);
                                simpleAdapter.notifyDataSetChanged();
                                actionMode.finish();
                            }
                        }
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

    }

}
