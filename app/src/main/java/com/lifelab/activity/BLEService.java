package com.lifelab.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.lifelab.coreclass.Experiment;
import com.lifelab.coreclass.Parameter;
import com.lifelab.coreclass.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/***************************************************************************************************
 * @version 1.0 Created by E9949942 Narendra Kalekar on 12/20/2016.
 **************************************************************************************************/
public class BLEService extends Service implements BluetoothAdapter.LeScanCallback{


    private BluetoothGatt mConnectedGatt;
    public final UUID UUID_DESCRIPTOR_NOTIFICATION_CFG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private final String DEVICE_NAME = "SensorTag";
    private BluetoothDevice mDevice;
    // private int countEnableSensor =1;
    private List<Sensor> sensorList;
    private List<Parameter> parameterList;
    private BluetoothAdapter mBluetoothAdapter;
    Experiment objExperiment;
    final static String SEND_EXP_OBJ_ACTION = "SEND_EXP_OBJ";

    public BLEService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("INTENT"," "+intent);
        objExperiment = (Experiment) intent.getSerializableExtra("EXP_OBJ");

        // Toast.makeText(this, "NK "+objExperiment.toString(), Toast.LENGTH_LONG).show();
        sensorList = new ArrayList<>();
        parameterList = new ArrayList<>();

        for (int i =0;i<objExperiment.getListOfParameters().size();i++){
            Parameter parameter = objExperiment.getListOfParameters().get(i);
            parameterList.add(parameter);
            sensorList.addAll(parameter.getListOfSensors());
        }

        startScan();
        return  START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean stopService(Intent name) {
        Log.i("STOP","stopping SERVICE");
        if(mConnectedGatt != null){
            mConnectedGatt.disconnect();
            mConnectedGatt = null;
            Log.i("STOP","BLE SERVICE");
        }
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        this.stopSelf();
        if(mConnectedGatt != null){
            mConnectedGatt.disconnect();
            mConnectedGatt = null;
            Log.i("STOP","BLE SERVICE");
        }
        super.onDestroy();
    }

    private void startScan(){
        Log.i("SCAN", "SCAN");
        mBluetoothAdapter.startLeScan(this);
    }

    private void stopScan(){
        Log.i("STOP","STOP SCAN");
        mBluetoothAdapter.stopLeScan(this);
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Log.i("Found","found"+device.getName());
        if(device.getName().contains(DEVICE_NAME)){
            mDevice = device;
            mConnectedGatt =  mDevice.connectGatt(this,true,mGattCallback);
            stopScan();
        }
    }

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        private int mState = 0;

        private void reset(){
            mState = 0;
        }

        private void advance(){
            mState++;
            if(mState >= sensorList.size())
                reset();
        }

        private void enableNextSensor(BluetoothGatt gatt){
            BluetoothGattCharacteristic characteristic;
            characteristic = gatt.getService(sensorList.get(mState).getUUID_SERV()).getCharacteristic(sensorList.get(mState).getUUID_CONF());
            characteristic.setValue(sensorList.get(mState).getBitValue());
           // Log.i("Yogesh Config Byte ","Value:"+ sensorList.get(mState).getBitValue()[0]);
            gatt.writeCharacteristic(characteristic);


        }

        private void readNextSensor(BluetoothGatt gatt){
            BluetoothGattCharacteristic characteristic;
            characteristic = gatt.getService(sensorList.get(mState).getUUID_SERV()).getCharacteristic(sensorList.get(mState).getUUID_DATA());
            gatt.readCharacteristic(characteristic);
        }

        private void setNotifyNextSensor(BluetoothGatt gatt){
            BluetoothGattCharacteristic characteristic;
            characteristic = gatt.getService(sensorList.get(mState).getUUID_SERV()).getCharacteristic(sensorList.get(mState).getUUID_DATA());
            gatt.setCharacteristicNotification(characteristic,true);
            BluetoothGattDescriptor desc = characteristic.getDescriptor(UUID_DESCRIPTOR_NOTIFICATION_CFG);
            desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(desc);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if(status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED){
                Log.i("Yogesh","OnCOnectionSTateChange calling discoverServices");
                gatt.discoverServices();
            }else if(status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_DISCONNECTED){
                //clear all values from ui
            }else if (status != BluetoothGatt.GATT_SUCCESS){
                gatt.disconnect();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.i("Yogesh","onServicesDiscovered calling enableNextSensor");
            reset();
            enableNextSensor(gatt);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            for(int i=0;i<sensorList.size();i++){
                Log.i("Read UUID VALUE ","UUID"+sensorList.get(i).getUUID_DATA());
                Log.i("Read Char UUID VALUE ","Char UUID"+characteristic.getUuid());
                if(sensorList.get(i).getUUID_DATA().equals(characteristic.getUuid())){
                    Log.i("Read Char VALUE ","Char Value read"+characteristic.getValue());
                    byte[] data = characteristic.getValue();
                    for(int ij = 0;ij<data.length;ij++)
                        Log.i("P"+ij,"DATA "+data[ij]);
                    // Integer upperByte = (int) characteristic.getValue()[0+1]; // // Interpret MSB as signed
                    // Log.i("P1","lowerByte"+lowerByte);
                    //  Log.i("P1","upperByte"+upperByte);
                    sensorList.get(i).convertAndSet(characteristic.getValue());
                    //refresh
                    for(int j=0;j<parameterList.size();j++){
                        parameterList.get(j).calculateAndSet();
                        Intent intent = new Intent();
                        intent.setAction(SEND_EXP_OBJ_ACTION);
                        intent.putExtra("EXPERIMENT_OBJ",objExperiment);
                        sendBroadcast(intent);
                    }
                }
            }
            setNotifyNextSensor(gatt);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            readNextSensor(gatt);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            for(int i=0;i<sensorList.size();i++){
                Log.i("UUID VALUE ","UUID"+sensorList.get(i).getUUID_DATA());
                Log.i("Char UUID VALUE ","Char UUID"+characteristic.getUuid());
                if(sensorList.get(i).getUUID_DATA().equals(characteristic.getUuid())){
                    Log.i("characteristic VALUE ","Characteristic"+characteristic.getValue());
                    sensorList.get(i).convertAndSet(characteristic.getValue());
                    //refresh parameters
                    for(int j=0;j<parameterList.size();j++){
                        parameterList.get(j).calculateAndSet();
                        Log.i("VALUE ",parameterList.get(j).getParameterValue());
                    }
                    // Log.i("on change "+i,sensorList.get(i).getSensorName()+""+sensorList.get(i).getSensorValue());
                }
            }

        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            advance();
            enableNextSensor(gatt);
            //readNextSensor(gatt);
        }
    };
}
