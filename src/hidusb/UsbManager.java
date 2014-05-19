package hidusb;

import android.util.Log;

/**
 * Created by donald on 3/3/14.
 */
public class UsbManager {

    private static final String TAG = "FindFormula";

    private Hidusb      mHidusb = null;
    private UsbHidCmd mUsbHidCmd = null;
    private int         mResponseId = 0;

    public interface UsbManagerListener {
        public void onUsbReady(boolean bReady);
    }

    private UsbManagerListener  mListener = null;
    private boolean     mReady = false;

    private int[] mSendDataResponse = null;

    public void startUsb() {
        if (mReady == true) {
            if (mListener != null)
                mListener.onUsbReady(true);
        }
        else
        {
             new Thread() {
                 int count = 0;
                @Override
                public void run() {
                    if (!mReady) {
                        int []usbHidCmdResponse = usbHidSendData(mUsbHidCmd.mWakeCmd);
                        int []usbHidCmdResponseByte = intArrayToByteArray(usbHidCmdResponse);

                        String str="";
                        count++;
                        str = "";
                        if(usbHidCmdResponseByte != null) {
                            for(int k=0; k < usbHidCmdResponseByte.length; k++){
                                str = str + Integer.toHexString(usbHidCmdResponseByte[k]) + " ";
                            }
                            Log.v(TAG, "mWakeCmd_return length=" + usbHidCmdResponse.length + "data= " + str);
                            if(usbHidCmdResponseByte[0] == 0xaa && usbHidCmdResponseByte[1] == 0x15 &&
                                    usbHidCmdResponseByte[2] == 0x06 /*&& usbHidCmdResponseByte[3] == 0x00*/){
                                int i=0;
                                int sum=0;
                                for(i=0; i < usbHidCmdResponseByte[1] + 2; i++){
                                    sum += usbHidCmdResponseByte[i];
                                }
                                String str1 = "";
                                str1 = " sum=" + Integer.toHexString(sum & 0x000000ff) + " usbHidCmdResponseByte["+(i)+"]=" + Integer.toHexString(usbHidCmdResponseByte[i]) ;

                                if((sum & 0x000000ff) == usbHidCmdResponseByte[i]){
                                    count = 0;
                                    mReady = true;
                                    Log.v(TAG, "Open Device success");
                                    if (mListener != null)
                                        mListener.onUsbReady(true);
                                }
                            }
                        }
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public boolean SendSubN(int SubID) {
        int[] ActiveSlave= new int[1];
        ActiveSlave[0] = SubID;
        return setBlockData(1, ActiveSlave);
    }

    private synchronized int[] usbHidSendData(int[] sendUidData){
        mSendDataResponse =mHidusb.sendData(sendUidData);
        return mSendDataResponse;
    }

    private synchronized int[] intArrayToByteArray(int[] intArray){
        int[] ret=null;
        if(intArray !=null && intArray.length>0){
            ret = new int[intArray.length *4];
            for(int i=0;i<intArray.length;i++){
                ret[4*i]= (intArray[i] & 0x000000ff);
                ret[4*i +1]= ((intArray[i]>>8)&0x000000ff);
                ret[4*i +2]= ((intArray[i]>>16)&0x000000ff);
                ret[4*i +3]= ((intArray[i]>>24)&0x000000ff);
            }
        }
        return ret;
    }

    private boolean setBlockData(int Identifier, int[] data){
        boolean ret = false;
        mResponseId++;
        mResponseId = mResponseId &0x0000000ff;

        if(data == null || data.length >16){
            return ret;
        }
        int dataSize = data.length;
        int dataSum = 0;
        for(int i = 0; i < dataSize; i++){
            dataSum = (dataSum + data[i]) & 0x000000ff;
        }

        int[] sendBlockDataCmd = new int[mUsbHidCmd.mSetBlockDataBase.length];
        for(int i = 0; i < mUsbHidCmd.mSetBlockDataBase.length; i++) {
            sendBlockDataCmd[i] = mUsbHidCmd.mSetBlockDataBase[i];
            if(i == 3) {
                sendBlockDataCmd[i] = mResponseId;
            }
            if(i == 4) {
                sendBlockDataCmd[i] = Identifier ;
            }
            if(i == 5) {
                sendBlockDataCmd[i] = dataSize;
            }
            if(i == 6) {
                sendBlockDataCmd[i] = dataSum;
            }
        }

        for(int i=0; i < dataSize; i++) {
            sendBlockDataCmd[i + 7] = data[i];
        }

        int cmdLength = mUsbHidCmd.mSetBlockDataBase.length;
        sendBlockDataCmd[cmdLength-1] = 0;
        for(int i = 0; i < cmdLength - 1; i++) {
            sendBlockDataCmd[cmdLength - 1] = (sendBlockDataCmd[cmdLength - 1] + sendBlockDataCmd[i]) & 0x000000ff;
        }
        String str = "";

        for(int i = 0; i < sendBlockDataCmd.length; i++){
            str = str + Integer.toHexString(sendBlockDataCmd[i]) + " ";
        }
        Log.v(TAG, "setBlockDataCmd_send= " + str);

        int []usbHidCmdResponse = usbHidSendData(sendBlockDataCmd);
        usbHidCmdResponse = usbHidSendData(sendBlockDataCmd);
        int []usbHidCmdResponseByte = intArrayToByteArray(usbHidCmdResponse);
        if(usbHidCmdResponseByte != null){

            str = "";
            for(int i = 0; i < usbHidCmdResponseByte.length; i++){
                str = str + Integer.toHexString(usbHidCmdResponseByte[i]) + " ";
            }
            Log.v(TAG, "setBlockDataCmd_return= " + str);

            if(usbHidCmdResponseByte[0] == 0xaa && usbHidCmdResponseByte[1] == 0x02 &&
                    usbHidCmdResponseByte[2] == mResponseId && usbHidCmdResponseByte[3] == 0x00){

                str = "";
                for(int i = 0; i< usbHidCmdResponseByte.length; i++){
                    str = str + Integer.toHexString(usbHidCmdResponseByte[i]) + " ";
                }
                Log.v(TAG, "setBlockDataCmd_return= " + str);

                int i = 0;
                int sum = 0;
                for(i = 0; i < usbHidCmdResponseByte[1] + 2; i++){
                    sum += usbHidCmdResponseByte[i];
                }

                if((sum & 0x000000ff) == (usbHidCmdResponseByte[i] & 0x000000ff)){
                    ret=true;
                }
            }
        }
        return ret;
    }
}
