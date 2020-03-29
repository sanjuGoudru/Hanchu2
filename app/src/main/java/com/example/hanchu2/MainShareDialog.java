package com.example.hanchu2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class MainShareDialog extends AppCompatDialogFragment {
    private RadioRealButton mOnlineButton, mOfflineButton,mMakeCopyButton,mSameFileButton,mReadOnlyButton,mWriteButton;
    private RadioRealButtonGroup mOnlineOfflineGroup,mMakeCopySameFileGroup,mReadWriteGroup;
    private static final String TAG = "MainShareDialog";
    Boolean[] selectionChoice = new Boolean[3];
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.main_share_dialog,null);
        //This is setup for all viewByID() statements
        setupIDs(view);
        //This sets up the builder view
        builder.setView(view).setTitle("How would you like to share")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "TEST: onClick: Clicked on: "+selectionChoice[0]+" "+selectionChoice[1]+" "+selectionChoice[2]);
                    }
                });

        //This sets up listeners for Radio Buttons
        setUpListeners();
        return builder.create();
    }

    private void setUpListeners() {
        selectionChoice[0] = selectionChoice[1] = selectionChoice[2] = null;
        mOnlineOfflineGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                if(position==0){
                    selectionChoice[0] = false;
                }else{
                    selectionChoice[0] = true;
                }
            }
        });
        mMakeCopySameFileGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                if(position==0){
                    selectionChoice[1] = false;
                }else{
                    selectionChoice[1] = true;
                }
            }
        });
        mReadWriteGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                if(position==0){
                    selectionChoice[2] = false;
                }else{
                    selectionChoice[2] = true;
                }
            }
        });
    }

    private void setupIDs(View view) {
        mOnlineButton = view.findViewById(R.id.online_radio);
        mOfflineButton = view.findViewById(R.id.offline_radio);
        mOnlineOfflineGroup = view.findViewById(R.id.online_offline_radio_group);

        mMakeCopyButton = view.findViewById(R.id.make_copy_radio);
        mSameFileButton = view.findViewById(R.id.same_file_radio);
        mMakeCopySameFileGroup = view.findViewById(R.id.copy_same_radio_group);

        mReadOnlyButton = view.findViewById(R.id.read_only_radio);
        mWriteButton = view.findViewById(R.id.write_radio);
        mReadWriteGroup = view.findViewById(R.id.read_write_radio_group);
    }
}
