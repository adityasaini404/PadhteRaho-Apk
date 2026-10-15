package com.example.padhteraho.player;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.adapters.PlaylistVideoAdapter;
import com.example.padhteraho.repository.VideoItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import androidx.activity.OnBackPressedCallback;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.pm.ActivityInfo;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.slider.Slider;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.util.Rational;
import android.content.res.Configuration;

import java.util.ArrayList;


public class PlayerActivity extends AppCompatActivity {


    private YouTubePlayerView youtubePlayerView;
    private YouTubePlayer youTubePlayer;


    private ArrayList<VideoItem> videoList;

    private int currentPosition = 0;


    private TextView txtTitle;
    private TextView txtTeacher;
    private TextView txtUpNextCount;


    private MaterialButton btnNext;
    private MaterialButton btnPrevious;


    private RecyclerView recyclerUpNext;


    private PlaylistVideoAdapter adapter;

    private ImageButton btnPlayPause;
    private ImageButton btnReplay10;
    private ImageButton btnForward10;
    private ImageButton btnFullscreen;
    private ImageButton btnSettings;
    private ImageButton btnLock;

    private Slider sliderProgress;

    private TextView txtCurrentTime;
    private TextView txtDuration;

    private View playerControls;
    private View bottomControls;
    private View loadingPlayer;
    private View topGradient;
    private View bottomGradient;
    private View playerTouchLayer;
    private ImageButton btnUnlock;
    private boolean isLocked = false;
    private boolean isPlaying = false;

    private boolean isFullscreen = false;
    private float currentSecond = 0;
    private float videoDuration = 0f;
    private boolean isUserSeeking = false;

    private LinearLayout layoutVideoInfo;
    private LinearLayout layoutUpNext;

    private MaterialCardView cardControls;

    private ViewGroup.LayoutParams playerParams;
    private int playerPortraitHeight;
    private MaterialCardView cardPlayer;
    private NestedScrollView scrollRoot;
    private TextView txtSpeed;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private Handler sleepHandler =
            new Handler(Looper.getMainLooper());


    private Runnable sleepRunnable;

    private Handler controlHandler = new Handler(Looper.getMainLooper());

    private Runnable hideControlsRunnable = new Runnable() {
        @Override
        public void run() {
            hidePlayerControls();
        }
    };

    private void hidePlayerControls(){

        if(playerControls != null){
            playerControls.setVisibility(View.GONE);
        }


        if(bottomControls != null){
            bottomControls.setVisibility(View.GONE);
        }

        if(topGradient != null){
            topGradient.setVisibility(View.GONE);
        }


        if(bottomGradient != null){
            bottomGradient.setVisibility(View.GONE);
        }

    }



    private void showPlayerControls(){

        if(playerControls != null){
            playerControls.setVisibility(View.VISIBLE);
        }


        if(bottomControls != null){
            bottomControls.setVisibility(View.VISIBLE);
        }


        controlHandler.removeCallbacks(
                hideControlsRunnable
        );

        if(topGradient != null){
            topGradient.setVisibility(View.VISIBLE);
        }


        if(bottomGradient != null){
            bottomGradient.setVisibility(View.VISIBLE);
        }


        controlHandler.postDelayed(
                hideControlsRunnable,
                3000
        );

    }

    public void setSleepTimer(int minutes){


        sleepHandler.removeCallbacksAndMessages(null);



        if(minutes == 0){

            sleepRunnable = null;

            return;

        }



        sleepRunnable = () -> {


            if(youTubePlayer != null){

                youTubePlayer.pause();

            }


        };



        sleepHandler.postDelayed(

                sleepRunnable,

                minutes * 60 * 1000L

        );


    }

    private final Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {

            if (youTubePlayer != null && videoDuration > 0 && !isUserSeeking) {

                float progress = (currentSecond / videoDuration) * 100f;

                sliderProgress.setValue(progress);

                txtCurrentTime.setText(formatTime(currentSecond));

                txtDuration.setText(formatTime(videoDuration));

            }

            handler.postDelayed(this, 300);

        }
    };



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);
        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        if (isFullscreen) {

                            toggleFullscreen();

                        } else {

                            finish();

                        }

                    }
                }
        );



        youtubePlayerView =
                findViewById(R.id.youtubePlayerView);
        btnUnlock = findViewById(R.id.btnUnlock);


        txtTitle = findViewById(R.id.txtTitle);
        txtTeacher = findViewById(R.id.txtTeacher);
        txtUpNextCount = findViewById(R.id.txtUpNextCount);

        topGradient = findViewById(R.id.topGradient);

        bottomGradient = findViewById(R.id.bottomGradient);

        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        playerTouchLayer = findViewById(R.id.playerTouchLayer);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnReplay10 = findViewById(R.id.btnReplay10);
        btnForward10 = findViewById(R.id.btnForward10);
        btnFullscreen = findViewById(R.id.btnFullscreen);
        btnSettings = findViewById(R.id.btnSettings);
        btnLock = findViewById(R.id.btnLock);
        txtSpeed = findViewById(R.id.txtSpeed);
        sliderProgress = findViewById(R.id.sliderProgress);

        txtCurrentTime = findViewById(R.id.txtCurrentTime);
        txtDuration = findViewById(R.id.txtDuration);

        playerControls = findViewById(R.id.playerControls);
        bottomControls = findViewById(R.id.bottomControls);
        loadingPlayer = findViewById(R.id.loadingPlayer);
        layoutVideoInfo = findViewById(R.id.layoutVideoInfo);
        layoutUpNext = findViewById(R.id.layoutUpNext);

        cardControls = findViewById(R.id.cardControls);

        cardPlayer = findViewById(R.id.cardPlayer);
        playerParams = cardPlayer.getLayoutParams();

        playerPortraitHeight = (int) (250 *
                getResources().getDisplayMetrics().density);

        scrollRoot = findViewById(R.id.scrollRoot);




        recyclerUpNext =
                findViewById(R.id.recyclerUpNext);



        getLifecycle()
                .addObserver(youtubePlayerView);


        IFramePlayerOptions options =
                new IFramePlayerOptions.Builder(this)
                        .controls(0)
                        .rel(0)
                        .modestBranding(1)
                        .ivLoadPolicy(3)
                        .build();


        youtubePlayerView.initialize(
                new AbstractYouTubePlayerListener() {


                    @Override
                    public void onReady(
                            @NonNull YouTubePlayer player
                    ) {

                        youTubePlayer = player;


                        handler.post(progressRunnable);


                        loadingPlayer.setVisibility(
                                View.GONE
                        );


                        playCurrentVideo();

                    }



                    @Override
                    public void onStateChange(
                            @NonNull YouTubePlayer player,
                            @NonNull PlayerConstants.PlayerState state
                    ) {


                        if(state ==
                                PlayerConstants.PlayerState.PLAYING) {


                            isPlaying = true;


                            btnPlayPause.setImageResource(
                                    android.R.drawable.ic_media_pause
                            );

                            showPlayerControls();


                        }
                        else {


                            isPlaying = false;


                            btnPlayPause.setImageResource(
                                    android.R.drawable.ic_media_play
                            );

                        }

                    }



                    @Override
                    public void onCurrentSecond(
                            @NonNull YouTubePlayer player,
                            float second
                    ) {


                        currentSecond = second;


                        if(!isUserSeeking && videoDuration > 0) {


                            sliderProgress.setValue(
                                    (second / videoDuration) * 100f
                            );


                            txtCurrentTime.setText(
                                    formatTime(second)
                            );

                        }

                    }



                    @Override
                    public void onVideoDuration(
                            @NonNull YouTubePlayer player,
                            float duration
                    ) {


                        videoDuration = duration;


                        txtDuration.setText(
                                formatTime(duration)
                        );

                    }


                },

                options
        );



        videoList =
                getIntent()
                        .getParcelableArrayListExtra("videos");

        btnFullscreen.setOnClickListener(v -> toggleFullscreen());

        btnLock.setOnClickListener(v -> toggleLock());

        btnLock.setOnClickListener(v -> lockPlayer());

        btnUnlock.setOnClickListener(v -> unlockPlayer());

        youtubePlayerView.setOnClickListener(v -> {

            if(playerControls.getVisibility()==View.VISIBLE){

                hidePlayerControls();

            }
            else{

                showPlayerControls();

            }

        });



        currentPosition =
                getIntent()
                        .getIntExtra("position",0);



        if(videoList == null){

            videoList = new ArrayList<>();

        }



        txtUpNextCount.setText(
                videoList.size()+" Videos"
        );




        recyclerUpNext.setLayoutManager(
                new LinearLayoutManager(this)
        );




        adapter = new PlaylistVideoAdapter(

                videoList,

                currentPosition,

                position -> {


                    currentPosition = position;


                    playCurrentVideo();


                }

        );

        recyclerUpNext.setAdapter(adapter);


        loadingPlayer.setVisibility(View.VISIBLE);


        sliderProgress.addOnChangeListener(
                (slider, value, fromUser) -> {

                    if(fromUser){

                        isUserSeeking = true;

                    }

                }
        );

        sliderProgress.addOnSliderTouchListener(
                new Slider.OnSliderTouchListener() {

                    @Override
                    public void onStartTrackingTouch(@NonNull Slider slider) {

                        isUserSeeking = true;

                    }

                    @Override
                    public void onStopTrackingTouch(@NonNull Slider slider) {

                        isUserSeeking = false;

                        if (youTubePlayer != null && videoDuration > 0) {

                            float seekSecond =
                                    (slider.getValue() / 100f) * videoDuration;

                            youTubePlayer.seekTo(seekSecond);

                        }

                    }

                }
        );

        playerTouchLayer.setOnClickListener(v -> {


            if(playerControls.getVisibility() == View.VISIBLE){

                hidePlayerControls();

            }
            else{

                showPlayerControls();

            }


        });






// PLAY PAUSE BUTTON

        btnPlayPause.setOnClickListener(v -> {


            if(youTubePlayer == null)
                return;



            if(isPlaying){


                youTubePlayer.pause();


            }
            else{


                youTubePlayer.play();


            }


        });

        btnReplay10.setOnClickListener(v -> {

            if(youTubePlayer != null){

                youTubePlayer.seekTo(
                        Math.max(currentSecond - 10, 0)
                );

            }

        });



        btnForward10.setOnClickListener(v -> {

            if(youTubePlayer != null){

                youTubePlayer.seekTo(
                        currentSecond + 10
                );

            }

        });






        btnNext.setOnClickListener(v -> {


            if(currentPosition < videoList.size()-1){


                currentPosition++;


                playCurrentVideo();


            }


        });



        btnPrevious.setOnClickListener(v -> {


            if(currentPosition > 0){


                currentPosition--;


                playCurrentVideo();


            }


        });

        txtSpeed.setOnClickListener(this::onClick);

        btnSettings.setOnClickListener(v -> {

            new SettingsBottomSheet().show(
                    getSupportFragmentManager(),
                    "settings"
            );

        });

    }


    private void unlockPlayer(){

        isLocked = false;

        playerControls.setVisibility(View.VISIBLE);

        sliderProgress.setVisibility(View.VISIBLE);

        txtCurrentTime.setVisibility(View.VISIBLE);

        txtDuration.setVisibility(View.VISIBLE);

        btnSettings.setVisibility(View.VISIBLE);

        btnFullscreen.setVisibility(View.VISIBLE);

        btnLock.setVisibility(View.VISIBLE);

        txtSpeed.setVisibility(View.VISIBLE);

        btnUnlock.setVisibility(View.GONE);

    }

    private void lockPlayer(){

        isLocked = true;

        playerControls.setVisibility(View.GONE);

        sliderProgress.setVisibility(View.GONE);

        txtCurrentTime.setVisibility(View.GONE);

        txtDuration.setVisibility(View.GONE);

        btnSettings.setVisibility(View.GONE);

        btnFullscreen.setVisibility(View.GONE);

        btnLock.setVisibility(View.GONE);

        txtSpeed.setVisibility(View.GONE);

        btnUnlock.setVisibility(View.VISIBLE);

    }

    private void toggleLock() {

        isLocked = !isLocked;

        if (isLocked) {

            btnReplay10.setVisibility(View.GONE);
            btnPlayPause.setVisibility(View.GONE);
            btnForward10.setVisibility(View.GONE);

            sliderProgress.setVisibility(View.GONE);

            txtCurrentTime.setVisibility(View.GONE);
            txtDuration.setVisibility(View.GONE);

            btnSettings.setVisibility(View.GONE);
            btnFullscreen.setVisibility(View.GONE);

            txtSpeed.setVisibility(View.GONE);

            btnLock.setImageResource(android.R.drawable.ic_lock_idle_lock);

        } else {

            btnReplay10.setVisibility(View.VISIBLE);
            btnPlayPause.setVisibility(View.VISIBLE);
            btnForward10.setVisibility(View.VISIBLE);

            sliderProgress.setVisibility(View.VISIBLE);

            txtCurrentTime.setVisibility(View.VISIBLE);
            txtDuration.setVisibility(View.VISIBLE);

            btnSettings.setVisibility(View.VISIBLE);
            btnFullscreen.setVisibility(View.VISIBLE);

            txtSpeed.setVisibility(View.VISIBLE);

            btnLock.setImageResource(android.R.drawable.ic_lock_lock);
        }

    }


    private void playCurrentVideo(){


        if(videoList.isEmpty())
            return;



        VideoItem video =
                videoList.get(currentPosition);



        txtTitle.setText(
                video.getTitle()
        );



        if(video.getTeacher()!=null
                &&
                !video.getTeacher().isEmpty()){


            txtTeacher.setText(
                    video.getTeacher()
            );


        }
        else{


            txtTeacher.setText(
                    "PadhteRaho"
            );


        }




        if(youTubePlayer != null){

            currentSecond = 0;
            videoDuration = 0;

            sliderProgress.setValue(0);

            txtCurrentTime.setText("00:00");
            txtDuration.setText("00:00");

            youTubePlayer.loadVideo(

                    video.getVideoId(),

                    0

            );


        }



        if(adapter != null){

            adapter.setPlayingPosition(
                    currentPosition
            );

        }



        recyclerUpNext
                .smoothScrollToPosition(
                        currentPosition
                );



        btnPrevious.setEnabled(
                currentPosition != 0
        );


        btnNext.setEnabled(
                currentPosition != videoList.size()-1
        );

    }

    private void toggleFullscreen() {

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(
                        getWindow(),
                        getWindow().getDecorView()
                );


        if (!isFullscreen) {


            // Landscape mode
            setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            );


            WindowCompat.setDecorFitsSystemWindows(
                    getWindow(),
                    false
            );


            // Hide other views
            scrollRoot.setVisibility(View.GONE);


            // Full screen player
            ViewGroup.LayoutParams params =
                    cardPlayer.getLayoutParams();


            params.width =
                    ViewGroup.LayoutParams.MATCH_PARENT;

            params.height =
                    ViewGroup.LayoutParams.MATCH_PARENT;


            cardPlayer.setLayoutParams(params);


            if(controller != null){

                controller.hide(
                        WindowInsetsCompat.Type.systemBars()
                );


                controller.setSystemBarsBehavior(
                        WindowInsetsControllerCompat
                                .BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                );
            }



            btnFullscreen.setImageResource(
                    R.drawable.ic_fullscreen_exit
            );


            isFullscreen = true;



        } else {



            // Portrait mode
            setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            );


            WindowCompat.setDecorFitsSystemWindows(
                    getWindow(),
                    true
            );


            scrollRoot.setVisibility(View.VISIBLE);



            ViewGroup.LayoutParams params =
                    cardPlayer.getLayoutParams();


            params.width =
                    ViewGroup.LayoutParams.MATCH_PARENT;


            params.height =
                    playerPortraitHeight;


            cardPlayer.setLayoutParams(params);



            if(controller != null){

                controller.show(
                        WindowInsetsCompat.Type.systemBars()
                );

            }



            btnFullscreen.setImageResource(
                    R.drawable.ic_fullscreen
            );


            isFullscreen = false;

        }
    }

    private float[] speeds = {
            0.25f,
            0.5f,
            0.75f,
            1f,
            1.25f,
            1.5f,
            1.75f,
            2f
    };

    private int speedIndex = 3;


    private String formatTime(float seconds){

        int total = (int) seconds;

        int min = total / 60;

        int sec = total % 60;

        return String.format("%02d:%02d", min, sec);

    }


    @Override
    protected void onDestroy() {


        super.onDestroy();


        // Progress timer stop
        handler.removeCallbacks(
                progressRunnable
        );


        // Sleep timer stop
        sleepHandler.removeCallbacksAndMessages(
                null
        );


        // Release YouTube Player
        if(youtubePlayerView != null){

            youtubePlayerView.release();

        }


    }
    public void enterPipMode(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){


            PictureInPictureParams params =
                    new PictureInPictureParams.Builder()

                            .setAspectRatio(
                                    new Rational(16,9)
                            )

                            .build();



            enterPictureInPictureMode(params);


        }

    }

    @Override
    public void onUserLeaveHint() {

        super.onUserLeaveHint();


        if(isPlaying){

            enterPipMode();

        }

    }

    @Override
    public void onPictureInPictureModeChanged(
            boolean isInPictureInPictureMode,
            Configuration newConfig
    ) {

        super.onPictureInPictureModeChanged(
                isInPictureInPictureMode,
                newConfig
        );


        if(isInPictureInPictureMode){


            // Hide app UI

            scrollRoot.setVisibility(View.GONE);

            playerControls.setVisibility(View.GONE);

            bottomControls.setVisibility(View.GONE);



        }
        else {


            // Restore UI

            scrollRoot.setVisibility(View.VISIBLE);

            showPlayerControls();


        }

    }

    private void onClick(View v) {

        speedIndex++;

        if (speedIndex >= speeds.length)
            speedIndex = 0;

        PlayerConstants.PlaybackRate rate;

        switch (speedIndex) {

            case 0:
                rate = PlayerConstants.PlaybackRate.RATE_0_25;
                break;

            case 1:
                rate = PlayerConstants.PlaybackRate.RATE_0_5;
                break;

            case 2:
                rate = PlayerConstants.PlaybackRate.RATE_0_75;
                break;

            case 3:
                rate = PlayerConstants.PlaybackRate.RATE_1;
                break;

            case 4:
                rate = PlayerConstants.PlaybackRate.RATE_1_25;
                break;

            case 5:
                rate = PlayerConstants.PlaybackRate.RATE_1_5;
                break;

            case 6:
                rate = PlayerConstants.PlaybackRate.RATE_1_75;
                break;

            default:
                rate = PlayerConstants.PlaybackRate.RATE_2;
                break;
        }

        youTubePlayer.setPlaybackRate(rate);

        txtSpeed.setText(speeds[speedIndex] + "x");

    }
}