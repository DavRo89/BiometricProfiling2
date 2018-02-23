package com.example.davide.biometricprofiling;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainFragment extends ListFragment {
    private  List<String> names = new ArrayList<>();
    ListView lv;
    private String Profileclick;
   private ArrayAdapter<String> adapter;
    private List<String> items;
    private int posizione;
    public interface OnListItemClickListener {
        void onListItemClick(int position);
    }

    private OnListItemClickListener mItemClickListener;

    public MainFragment() {
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mItemClickListener = (OnListItemClickListener) activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("dentro","va");
        Bundle args = getArguments();
          names= args.getStringArrayList("nomiB");


          items = names;
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
lv=getListView();
lv.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener(){

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {


            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)contextMenuInfo;
        contextMenu.setHeaderTitle(names.get(info.position));
        posizione=info.position;
        Log.d("posizione", ""+posizione);
        Profileclick=names.get(info.position);
            String[] menuItems = getResources().getStringArray(R.array.actions);
            for (int i = 0; i<menuItems.length; i++) {
                contextMenu.add(Menu.NONE, i, i, menuItems[i]);
            }

    }




});


    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mItemClickListener.onListItemClick(position);

    }
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:

                File mydir = getContext().getFilesDir(); //get your internal directory
               File myFile = new File(mydir.toString(), Profileclick);
                Log.d("path", Profileclick);
                myFile.delete();



                items=names;
                Log.d("items",""+items.size());
                lv=getListView();
adapter.remove(adapter.getItem(posizione));
                adapter.notifyDataSetChanged();



                return true;
            case 1:
                // your second action code
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
