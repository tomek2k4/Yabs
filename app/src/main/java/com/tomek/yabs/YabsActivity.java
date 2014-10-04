package com.tomek.yabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tomek.yabs.yabscommon.YabsManager;

public class YabsActivity extends Activity implements View.OnClickListener {

    private final static String TAG = "BeaconActivity";

    private Button buttonPost;
    private EditText editText;
    private YabsManager yabsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        editText = (EditText) findViewById(R.id.edit_beacon_id);
        buttonPost = (Button) findViewById(R.id.button_send_request);
        buttonPost.setOnClickListener(this);

        yabsManager = new YabsManager(this);
    }


    @Override
    public void onClick(View view) {
        //long beaconId = Long.valueOf(editText.getText().toString());
        Log.d(TAG, "onClick'd beackon Id:");
        String beaconIdStr = editText.getText().toString();
        Integer beaconId;
        if(beaconIdStr.matches("")){
            Log.d(TAG,"Input is null");
            beaconId = 0;
        }
        else{
            beaconId = Integer.parseInt(beaconIdStr);
        }
        Log.d(TAG,"beacon id is: "+beaconId.toString());
        boolean result = yabsManager.beaconIdUpdate(beaconId.longValue());
        if(result==true)
        {
            Toast.makeText(this,"Succesfully sent HTTP request",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Failed to sent HTTP request",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Activity onDestry() called");
        yabsManager.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.beacon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
