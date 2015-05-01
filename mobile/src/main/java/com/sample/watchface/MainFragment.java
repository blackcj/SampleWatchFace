package com.sample.watchface;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.blackcj.drawinglibrary.Constants;
import com.blackcj.drawinglibrary.RoundWatchFace;
import com.blackcj.drawinglibrary.SquareWatchFace;
import com.commonsware.cwac.colormixer.ColorMixer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;

/**
 * Created by chris.black on 12/12/14.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ISender sender;

    DrawView drawView;

    @InjectView(R.id.face_type_spinnar)
    Spinner faceTypeSpinner;

    @InjectView(R.id.mixer)
    ColorMixer mixer;

    @InjectView(R.id.radial_mixer)
    ColorMixer radial_mixer;

    @InjectView(R.id.second_hand)
    CompoundButton secondHandSwitch;

    @InjectView(R.id.radial_gradient)
    CompoundButton radialSwitch;

    @InjectView(R.id.minute_ticks)
    CompoundButton minuteSwitch;

    private int backgroundColor = 0;
    private int radialColor = 0;

    SharedPreferences prefs;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        drawView = new DrawView(this.getActivity());
        prefs = this.getActivity().getSharedPreferences(
                Constants.KEY_PACKAGE_NAME, Context.MODE_PRIVATE);
        loadPreferences();

        RelativeLayout faceView = (RelativeLayout) rootView.findViewById(R.id.roundFaceView);
        faceView.addView(drawView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.face_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        faceTypeSpinner.setAdapter(adapter);
        faceTypeSpinner.setOnItemSelectedListener(this);
        mixer.setOnColorChangedListener(onBackgroundColorChange);
        radial_mixer.setOnColorChangedListener(onRadialColorChange);

        View swatch = (View) rootView.findViewById(R.id.swatch);
        swatch.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            sender = (ISender) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ISender");
        }
    }

    @Override
    public void onDetach() {
        sender = null;
        super.onDetach();
    }

    // PREFERENCES

    protected void loadPreferences() {
        backgroundColor = prefs.getInt(Constants.KEY_BACKGROUND_COLOR, getActivity().getResources().getColor(R.color.yellow));
        mixer.setColor(backgroundColor);
        drawView.setFaceBackgroundColor(backgroundColor);

        showSecondHand = prefs.getBoolean(Constants.KEY_SHOW_SECOND_HAND, true);
        secondHandSwitch.setChecked(showSecondHand);
        drawView.showSecondHand(showSecondHand);

        showMinuteTicks = prefs.getBoolean(Constants.KEY_SHOW_MINUTE_TICKS, true);
        minuteSwitch.setChecked(showMinuteTicks);
        drawView.showMinuteTicks(showMinuteTicks);

        showRadialGradient = prefs.getBoolean(Constants.KEY_SHOW_RADIAL_GRADIENT, true);
        radialSwitch.setChecked(showRadialGradient);
        drawView.showRadialGradient(showRadialGradient);

        radialColor = prefs.getInt(Constants.KEY_RADIAL_COLOR, getActivity().getResources().getColor(R.color.gradient_start));
        radial_mixer.setColor(radialColor);
        drawView.setRadialColor(radialColor);

        if(showRadialGradient) {
            radial_mixer.setVisibility(View.VISIBLE);
        } else {
            radial_mixer.setVisibility(View.GONE);
        }

        drawView.invalidate();
    }

    protected void savePreferences() {
        prefs.edit().putInt(Constants.KEY_BACKGROUND_COLOR, backgroundColor).apply();
        prefs.edit().putInt(Constants.KEY_RADIAL_COLOR, radialColor).apply();
        prefs.edit().putBoolean(Constants.KEY_SHOW_MINUTE_TICKS, showMinuteTicks).apply();
        prefs.edit().putBoolean(Constants.KEY_SHOW_SECOND_HAND, showSecondHand).apply();
        prefs.edit().putBoolean(Constants.KEY_SHOW_RADIAL_GRADIENT, showRadialGradient).apply();

        if(showRadialGradient) {
            radial_mixer.setVisibility(View.VISIBLE);
        } else {
            radial_mixer.setVisibility(View.GONE);
        }
    }

    // HANDLERS

    private ColorMixer.OnColorChangedListener onRadialColorChange=
            new ColorMixer.OnColorChangedListener() {
                public void onColorChange(int i) {
                    radialColor = i;
                    drawView.setRadialColor(i);
                    savePreferences();
                    drawView.invalidate();
                }
            };

    private ColorMixer.OnColorChangedListener onBackgroundColorChange=
            new ColorMixer.OnColorChangedListener() {
                public void onColorChange(int i) {
                    backgroundColor = i;
                    drawView.setFaceBackgroundColor(i);
                    savePreferences();
                    drawView.invalidate();
                }
            };

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        if(pos == 0) {
            drawView.watchFace = new SquareWatchFace();

            loadPreferences();
        } else {
            drawView.watchFace = new RoundWatchFace();
            loadPreferences();
        }
        Resources resources = this.getResources();

        Drawable minuteDrawable = resources.getDrawable(com.blackcj.drawinglibrary.R.drawable.hand_minute_normal);
        drawView.watchFace.setMinuteHand(minuteDrawable);

        Drawable hourDrawable = resources.getDrawable(com.blackcj.drawinglibrary.R.drawable.hand_hour_normal);
        drawView.watchFace.setHourHand(hourDrawable);

        drawView.watchFace.setRatio(getRatio(this.getActivity()));
        drawView.invalidate();
    }

    public float getRatio(Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (metrics.densityDpi / 160f);
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    boolean showSecondHand = true;
    @OnCheckedChanged(R.id.second_hand)
    public void onSecondHandChanged(CompoundButton buttonView, boolean isChecked) {
        showSecondHand = isChecked;
        drawView.showSecondHand(showSecondHand);
        savePreferences();
        drawView.invalidate();
    }

    boolean showRadialGradient = true;
    @OnCheckedChanged(R.id.radial_gradient)
    public void onRadialChanged(CompoundButton buttonView, boolean isChecked) {
        showRadialGradient = isChecked;
        drawView.showRadialGradient(showRadialGradient);
        savePreferences();
        drawView.invalidate();
    }

    boolean showMinuteTicks = true;
    @OnCheckedChanged(R.id.minute_ticks)
    public void onMinuteTicksChanged(CompoundButton buttonView, boolean isChecked) {
        showMinuteTicks = isChecked;
        drawView.showMinuteTicks(showMinuteTicks);
        savePreferences();
        drawView.invalidate();
    }
}