package com.pucmm.loginandmainpage.ui.listos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pucmm.loginandmainpage.R;
import com.pucmm.loginandmainpage.ui.slideshow.SlideshowViewModel;

public class ListOS extends Fragment {

    private ListOSViewModel listOSViewModel;

    String items[] = new String[]{
            "Windows 10",
            "macOS",
            "Ubuntu",
            "Fedora",
            "Linux",
            "Raspbian OS",
            "Android OS",
            "iOS"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listOSViewModel =
                ViewModelProviders.of(this).get(ListOSViewModel.class);
        View root = inflater.inflate(R.layout.list_os_fragment, container, false);
        //final TextView textView = root.findViewById(R.id.text_listos);
        final ListView listView = root.findViewById(R.id.listview_listos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        return root;
    }
}