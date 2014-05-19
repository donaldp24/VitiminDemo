package hidusb;

import android.os.Handler;

public class Hidusb {

	public static final int HIDUSB__SUCCESS = 0;
	public static final int HIDUSB_FAIL = -1;

	private static Hidusb mHidusb;

	private boolean mFlag;

	private OnDataChangeListener mDataChangeListener;

	static{
		System.loadLibrary("hid_raws");
	}
	private static int[] mData = new int[0];
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
	
		};
	};

	public native int openhidraws();
	public native byte[] get_tag();
	public native int set_led(char num,int color);
	public native int[] get_data();
	public native int[] send_data(int[] buffer,int length);
	
	public native int[] Trans_UID(int[] uidbuffer,int[] blockDataBuffer);
	public native void usbpwr_reset(int ioState);		//����USB��Դ����0Ϊ��λ��1Ϊ����
	public native void wdg_sw(int ioState);		//����watchDog�����һ��ģ�ⷽ���źţ�����0Ϊ��λ��1Ϊ����
	
	public Hidusb() throws Exception{
		if(openhidraws() == HIDUSB_FAIL){
			throw new Exception("It's not found device!");
		}
	}

	public static Hidusb newInstance() throws Exception{
		if(mHidusb == null){
			mHidusb = new Hidusb();
		}
		return mHidusb;
	}

	public synchronized int[] sendData(int[] buffer){
		return send_data(buffer, buffer.length);
	}
 
	public int[] getUidId(int[] uidBuffer,int[] blockDataBuffer ){
		return Trans_UID(uidBuffer,blockDataBuffer);
		//return null;
	}
	public int[] getData(){
		return get_data();
	}


	public void setOnDataChangeListener(OnDataChangeListener listener){
		mDataChangeListener = listener;
		mFlag = true;
//		new Thread(){
//			public void run() {
//				while(mFlag){
//					mHandler.sendEmptyMessage(0);
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			};
//		}.start();
	}

	public void close(){
		mFlag = false;
	}

	public interface OnDataChangeListener{
		public void onDataChange(int[] data);
	}

}
