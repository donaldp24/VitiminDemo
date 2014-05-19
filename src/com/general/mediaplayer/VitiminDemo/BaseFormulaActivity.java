package com.general.mediaplayer.VitiminDemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android_serialport_api.SerialPort;
import hidusb.UsbManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

/**
 * Created by donald on 2/21/14.
 */
public class BaseFormulaActivity extends BaseActivity {


    protected Application mApplication = null;
    protected SerialPort mSerialPort = null;
    protected OutputStream mOutputStream = null;
    private InputStream mInputStream = null;
    private ReadThread mReadThread = null;

    protected int subId = 0;

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while(!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size);

                        Log.v("BaseFormulaActivity", "ReadThread size:" + size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private void DisplayError(int resourceId) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Error");
        b.setMessage(resourceId);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //ResultActivity.this.finish();
            }
        });
        b.show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // related with serial
        mApplication = (Application) getApplication();
        if (CommonData.LIGHT_MODE == CommonData.LIGHT_COMM)
        {
            try {
                mSerialPort = mApplication.getSerialPort();
                mOutputStream = mSerialPort.getOutputStream();
                mInputStream = mSerialPort.getInputStream();

                /* Create a receiving thread */
                mReadThread = new ReadThread();
                mReadThread.start();
            } catch (SecurityException e) {
                DisplayError(R.string.error_security);
            } catch (IOException e) {
                DisplayError(R.string.error_unknown);
            } catch (InvalidParameterException e) {
                DisplayError(R.string.error_configuration);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {

        if (CommonData.LIGHT_MODE == CommonData.LIGHT_COMM)
        {
            if (mReadThread != null)
                mReadThread.interrupt();
            mApplication.closeSerialPort();
            mSerialPort = null;
        }

        super.onDestroy();
    }

    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
                String RecepString = "";
                RecepString=ByteToHexString(buffer,size);
                RecepString=RecepString+"\n";
            }
        });
    }

    public static String ByteToHexString( byte[] b,int size)
    {
        String hexString="";
        for (int i = 0; i < size; i++)
        {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            hexString=hexString+" "+hex;
        }
        return hexString;
    }

    protected boolean SendSubN(int SubID)
    {
        if (CommonData.LIGHT_MODE == CommonData.LIGHT_COMM ) {
            int i=0;
            int j=0;
            boolean ret=false;
            byte[] buffer = new byte[5];
            byte SubIDTemp;
            SubIDTemp=(byte)SubID;
            if(SubID>0)
            {

                SubIDTemp=(byte)(SubID-1);

            }
            buffer[i++]=(byte)0xF5;
            buffer[i++]=0x04;
            buffer[i++]=0x01;
            buffer[i++]=SubIDTemp;
            buffer[i]=0x00;
            for(j=0;j<i;j++)
            {
                buffer[i]=(byte)(buffer[i]+buffer[j]);
            }
            try {
                if (mOutputStream != null)
                    mOutputStream.write(buffer);
                ret=true;
                //mOutputStream.write('\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ret;
        }
        else if (CommonData.LIGHT_MODE == CommonData.LIGHT_USBHID)
        {
            UsbManager usbManager = mApplication.getUsbManager();
            return usbManager.SendSubN(SubID);
        }
        else
            return false;
    }

    protected boolean SendSectionNum(int sectionNumber)
    {
        if (CommonData.LIGHT_MODE == CommonData.LIGHT_COMM ) {
            boolean ret=false;
            byte[] buffer = new byte[1];
            buffer[0] = (byte)sectionNumber;
            try {
                if (mOutputStream != null)
                    mOutputStream.write(buffer);
                ret=true;
                //mOutputStream.write('\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ret;
        }
        else
            return false;
    }
}