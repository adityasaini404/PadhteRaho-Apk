package com.example.padhteraho.player;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.padhteraho.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SleepTimerBottomSheet extends BottomSheetDialogFragment {


    private RadioGroup radioGroupTimer;


    public SleepTimerBottomSheet(){

        super(R.layout.bottom_sheet_sleep_timer);

    }



    @Override
    public void onViewCreated(
            @NonNull View view,
            Bundle savedInstanceState
    ){

        super.onViewCreated(
                view,
                savedInstanceState
        );


        radioGroupTimer =
                view.findViewById(
                        R.id.radioGroupTimer
                );



        radioGroupTimer.setOnCheckedChangeListener(
                (group, checkedId) -> {


                    int minutes = 0;


                    if(checkedId == R.id.rb5){

                        minutes = 5;

                    }

                    else if(checkedId == R.id.rb10){

                        minutes = 10;

                    }

                    else if(checkedId == R.id.rb15){

                        minutes = 15;

                    }

                    else if(checkedId == R.id.rb30){

                        minutes = 30;

                    }

                    else if(checkedId == R.id.rb45){

                        minutes = 45;

                    }

                    else if(checkedId == R.id.rb60){

                        minutes = 60;

                    }

                    else if(checkedId == R.id.rbOff){

                        minutes = 0;

                    }



                    PlayerActivity activity =
                            (PlayerActivity) requireActivity();



                    activity.setSleepTimer(minutes);



                    dismiss();


                }
        );


    }

}