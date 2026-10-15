package com.example.padhteraho.player;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Rational;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.padhteraho.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SettingsBottomSheet extends BottomSheetDialogFragment {

    public SettingsBottomSheet() {
        super(R.layout.bottom_sheet_settings);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            android.os.Bundle savedInstanceState
    ) {

        super.onViewCreated(view, savedInstanceState);

        View sleep = view.findViewById(R.id.itemSleepTimer);
        View pip = view.findViewById(R.id.itemPip);
        View about = view.findViewById(R.id.itemAbout);

        sleep.setOnClickListener(v -> {

            dismiss();

            new SleepTimerBottomSheet()
                    .show(
                            getParentFragmentManager(),
                            "sleep"
                    );

        });

        pip.setOnClickListener(v -> {

            dismiss();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                if (getActivity() instanceof PlayerActivity) {


                    PlayerActivity playerActivity =
                            (PlayerActivity) getActivity();


                    PictureInPictureParams params =
                            new PictureInPictureParams.Builder()
                                    .setAspectRatio(
                                            new Rational(16, 9)
                                    )
                                    .build();


                    playerActivity.enterPictureInPictureMode(params);


                }

            }

        });

        about.setOnClickListener(v -> {

            dismiss();

            new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("About PadhteRaho")
                    .setMessage(
                            "PadhteRaho\n\n" +
                                    "Version : 1.0\n\n" +
                                    "Developed by Aditya\n\n" +
                                    "PadhteRaho is a free educational platform that helps students learn from YouTube courses without distractions.\n\n" +
                                    "✨ Features\n" +
                                    "• Free Learning\n" +
                                    "• Clean UI\n" +
                                    "• Sleep Timer\n" +
                                    "• Picture in Picture\n" +
                                    "• Resume Playback\n\n" +
                                    "Thank you for using PadhteRaho ❤️"
                    )
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();

        });

    }

}