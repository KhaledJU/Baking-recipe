package com.example.baking.Fragments;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baking.R;
import com.example.baking.Recipe.Recipe;
import com.example.baking.RecipeDetails;
import com.example.baking.Steps.Step;
import com.example.baking.VideoPlayerConfig;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment implements Player.EventListener {

    @BindView(R.id.video_player)@Nullable() SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.step_description_tv)  TextView stepDescriptionTv;
    @BindView(R.id.step_wrong_message) TextView mStep_wrong_message;
    @BindView(R.id.prev) TextView prevBt;
    @BindView(R.id.next) TextView nextBt;

    SimpleExoPlayer mSimpleExoPlayer;
    Handler mHandler;
    Runnable mRunnable;
    List<Step> stepList;
    String url;
    int position;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_fragment, container, false);
        ButterKnife.bind(this,rootView);

        position = getArguments().getInt("position");
        stepList = ((Recipe) getArguments().getSerializable("Recipe")).getSteps();

        updateStep();

        prevBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0)
                    Toast.makeText(getContext(), R.string.noPrev,Toast.LENGTH_SHORT).show();
                else{
                    position--;
                    ((RecipeDetails)getActivity()).position=position;
                    updateStep();
                }
            }
        });

        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == stepList.size()-1)
                    Toast.makeText(getContext(), R.string.noNext,Toast.LENGTH_SHORT).show();
                else{
                    position++;
                    ((RecipeDetails)getActivity()).position=position;
                    updateStep();
                }
            }
        });

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void updateStep() {
        stepDescriptionTv.setText(stepList.get(position).getDescription());
        mStep_wrong_message.setText("");
        url = stepList.get(position).getVideoURL();

        initializePlayer();

        if(url == null || url.isEmpty())
            mStep_wrong_message.setText("There is no video for this step :(");

        buildMediaSource(Uri.parse(url));

    }

    private void buildMediaSource(Uri parse) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        MediaSource mediaSource=new ExtractorMediaSource(parse,new DefaultDataSourceFactory(getContext(),getString(R.string.app_name)),
                new DefaultExtractorsFactory(),null,null);

        mSimpleExoPlayer.prepare(mediaSource);
        mSimpleExoPlayer.setPlayWhenReady(true);
        mSimpleExoPlayer.addListener(this);
    }


    private void resumePlayer() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(true);
            mSimpleExoPlayer.getPlaybackState();
        }
    }

    private void initializePlayer() {
        if(mSimpleExoPlayer==null){

            LoadControl l=new DefaultLoadControl(new DefaultAllocator(true, 16),
                    VideoPlayerConfig.MIN_BUFFER_DURATION,
                    VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();


            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);

            TrackSelector trackSelector=new DefaultTrackSelector(videoTrackSelectionFactory);

            mSimpleExoPlayer=    ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector, l);
            simpleExoPlayerView.setPlayer(mSimpleExoPlayer);

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
        
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mSimpleExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            position = (int) mSimpleExoPlayer.getCurrentPosition();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }

    }

}
