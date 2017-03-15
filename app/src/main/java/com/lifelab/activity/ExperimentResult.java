package com.lifelab.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.lifelab.R;
import com.lifelab.experiments.AnemometerExperiment;
import com.lifelab.coreclass.Experiment;
import com.lifelab.coreclass.Parameter;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 12/20/2016.
 **************************************************************************************************/
public class ExperimentResult extends AppCompatActivity implements View.OnClickListener {

    int position;
    Experiment objExp;
    Button start,end,next,prev;
    TextView pName,pValue;
    private final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BluetoothAdapter mBluetoothAdapter;
    MyReceiver myReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_result);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // builder.setTitle("This app needs location access");
                //builder.setMessage("Please grant location access so this app can detect beacons.");
                //  builder.setPositiveButton();
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        position = getIntent().getIntExtra("Position",0);

        start = (Button) findViewById(R.id.startExp);
        end = (Button) findViewById(R.id.endExp);
        next = (Button) findViewById(R.id.nextParameter);
        prev = (Button) findViewById(R.id.prevParameter);

        start.setOnClickListener(this);
        end.setOnClickListener(this);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);

        pName = (TextView) findViewById(R.id.parameterName);
        pName.setText("Parameter");
        pValue = (TextView) findViewById(R.id.parameterValue);
        pValue.setText("0"+"\nUnit");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableIntent);
            finish();
            return;
        }

        switch (position){
            case 0:
                Log.i("hi", "here" + position);
                objExp = new AnemometerExperiment();
                //Intent i = new Intent(this,BLEServiceActivity.class);

               // startActivity(i);
                break;

            default:
                Log.i("hii","other");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent iEnd = new Intent(ExperimentResult.this,BLEService.class);
        // iEnd.putExtra("EXP_OBJ", objExp);
        Log.i("intent End", " " + iEnd);
        objExp.endExperiment();
        stopService(iEnd);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startExp:
                if(objExp==null)
                    Log.i("null ","NULL ahe");
                myReceiver = new MyReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(BLEService.SEND_EXP_OBJ_ACTION);
                registerReceiver(myReceiver, intentFilter);

                Intent i = new Intent(ExperimentResult.this,BLEService.class);
                i.putExtra("EXP_OBJ", objExp);
                Log.i("intent ", " " + i);
                startService(i);
                objExp.startExperiment();
                pName.setText(objExp.getListOfParameters().get(0).getParameterName());
                Log.i("VALUE P1 ", (objExp.getListOfParameters().get(0).getParameterValue()));
           /*     pName.post(new Runnable() {
                    @Override
                    public void run() {
                        pName.setText(objExp.getListOfParameters().get(0).getParameterName());
                    }
                });
            */
                pValue.setText(objExp.getListOfParameters().get(0).getParameterValue() + "\n" + objExp.getListOfParameters().get(0).getParameterUnit());
                start.setEnabled(false);
                break;

            case R.id.endExp:
                Intent iEnd = new Intent(ExperimentResult.this,BLEService.class);
               // iEnd.putExtra("EXP_OBJ", objExp);
                Log.i("intent End", " " + iEnd);
                unregisterReceiver(myReceiver);
                objExp.endExperiment();
                stopService(iEnd);
                Log.i("log","log end");
                break;

            case R.id.nextParameter:
                Parameter nextParameter = objExp.getNextParameter();
                pName.setText(nextParameter.getParameterName());
                Log.i("NAME P", nextParameter.getParameterName());
                pValue.setText(nextParameter.getParameterValue()+ "\n" + nextParameter.getParameterUnit());
                Log.i("VALUE P",nextParameter.getParameterValue());
                break;

            case R.id.prevParameter:
                Parameter prevParameter = objExp.getPrevParameter();
                pName.setText(prevParameter.getParameterName());
                pValue.setText(prevParameter.getParameterValue()+ "\n" + prevParameter.getParameterUnit());
                break;
        }
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

             objExp = (Experiment)arg1.getSerializableExtra("EXPERIMENT_OBJ");
          //  Log.i("OBJ","reference"+objExp.toString());
        }

    }
}

