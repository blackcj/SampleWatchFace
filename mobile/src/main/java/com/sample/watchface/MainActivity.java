package com.sample.watchface;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.blackcj.drawinglibrary.Constants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends ActionBarActivity implements ISender {

    private GoogleApiClient mGoogleApiClient;
    private String mPeerId;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        prefs = this.getSharedPreferences(
                Constants.KEY_PACKAGE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sendConfigUpdateMessage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // COMMUNICATION

    private void sendConfigUpdateMessage() {
        if (mPeerId != null) {
            DataMap config = new DataMap();
            config.putInt(Constants.KEY_BACKGROUND_COLOR, prefs.getInt(Constants.KEY_BACKGROUND_COLOR, getResources().getColor(R.color.yellow)));
            config.putInt(Constants.KEY_RADIAL_COLOR, prefs.getInt(Constants.KEY_RADIAL_COLOR, Color.WHITE));
            byte[] rawData = config.toByteArray();
            Wearable.MessageApi.sendMessage(mGoogleApiClient, mPeerId, Constants.PATH_WITH_FEATURE, rawData);

            if (Log.isLoggable("MainActivity", Log.DEBUG)) {
                Log.d("MainActivity", "Sent watch face config message");
            }
        }
    }

}
