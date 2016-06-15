package com.zzptc.joker.baiduguard.fragment;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.bean.UpdateInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetworkFragmentDialog extends DialogFragment {


    public NetworkFragmentDialog() {
        // Required empty public constructor
    }

    public static NetworkFragmentDialog newInstance(String netInfo) {
        NetworkFragmentDialog dialog = new NetworkFragmentDialog();
        Bundle bundle = new Bundle();
        bundle.putString("netInfo", netInfo);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static NetworkFragmentDialog newInstance(String netInfo, UpdateInfo updateInfo){
        NetworkFragmentDialog dialog = new NetworkFragmentDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("updateInfo",updateInfo);
        bundle.putString("netInfo",netInfo);
        dialog.setArguments(bundle);
        return dialog;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.notnetwork_fragment_dialog, container, false);
        Button okBtn = (Button) view.findViewById(R.id.ok_btn);
        TextView textInfo = (TextView) view.findViewById(R.id.tv_info);

        if(getArguments()!=null){
            textInfo.setText(getArguments().getString("netInfo"));
        }



        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInfo updateInfo = (UpdateInfo) getArguments().getSerializable("updateInfo");
                if(updateInfo!=null){

                }
                dismiss();
            }
        });
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return false;
            }
        });

        return view;


    }

}
