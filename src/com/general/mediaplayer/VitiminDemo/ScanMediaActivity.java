package com.general.mediaplayer.VitiminDemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.general.mediaplayer.Utils.CsrActivity;
import com.general.mediaplayer.VitiminDemo.DroidPHP.ServerService;
import com.general.mediaplayer.VitiminDemo.DroidPHP.ServerUtils;
import hidusb.UsbManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ScanMediaActivity extends CsrActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mCardsViewPager;
    private CardsPagerAdapter mCardsPagerAdapter;
    private float MIN_SCALE = 1f - 1f / 4f;
    private float MAX_SCALE = 1f;
    private int mCurrItem = 0;
    private float alphaWeight = 1.3f;

    private static boolean isStarted = false;
    private SharedPreferences prefs;
    private Context mContext;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // initialize resolution set
        Point ptSize = ResolutionSet.getScreenSize(ScanMediaActivity.this, true, false);
        ResolutionSet._instance.setResolution(ptSize.x, ptSize.y, false);

        // rearrange children
        ResolutionSet._instance.iterateChild(findViewById(R.id.layout_main));

        mCardsViewPager = (ViewPager) findViewById(R.id.viewpager_cards);
        mCardsPagerAdapter = new CardsPagerAdapter();
        mCardsViewPager.setAdapter(mCardsPagerAdapter);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mCardsViewPager.getLayoutParams();
        int nWidth = params.width;
        int nImageWidth = params.height;
        mCardsViewPager.setPageMargin(-(nWidth / 2 - nImageWidth / 2));
        mCardsViewPager.setOffscreenPageLimit(3);
        mCardsViewPager.setOnPageChangeListener(this);

        // start usb hub
        if (CommonData.LIGHT_MODE == CommonData.LIGHT_USBHID)
        {
            Application application = (Application) getApplication();
            UsbManager usbManager = application.getUsbManager();
            usbManager.startUsb();
        }

        // related with HTTP server

        if (CommonData.START_SERVER == 1)
        {
            mContext = ScanMediaActivity.this;

            ServerUtils.setContext(mContext);
            prefs = PreferenceManager.getDefaultSharedPreferences(this);

            if (ScanMediaActivity.isStarted == false)
            {
                ServerUtils.StrictModePermitAll();
                ServerUtils.setHttpDocsUri(prefs.getString("k_docs_dir", "htdocs"));
                ServerUtils.setServerPort(prefs.getString("k_server_port", "8080"));

                ScanMediaActivity.isStarted = true;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (CommonData.START_SERVER == 1)
        {
            try{
                if (false == ServerUtils.checkIfInstalled()) {
                    new InstallerAsync(this).execute();
                } else {
                    if (isServerRunning() == false) {
                        programInstalled();
                    }
                }
            }catch (IOException e) {
                //
            }
        }
    }

    private class CardsPagerAdapter extends PagerAdapter {

        private boolean mIsDefaultItemSelected = false;

        public int[] mCards = {
                R.drawable.first_children,
                R.drawable.first_men,
                R.drawable.first_women
        };

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout itemLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.imageview_card, null);
            ImageView cardImageView = (ImageView)itemLayout.findViewById(R.id.content_imgview);
            cardImageView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrItem = mCardsViewPager.getCurrentItem();

                    Intent intent = null;
                    if (mCurrItem == 0) // children
                    {
                        intent = new Intent(ScanMediaActivity.this, SelectionActivity.class);
                        intent.putExtra(CommonData.PARAM_KEY, CommonData.KEY_CHILDREN);
                    }
                    else if (mCurrItem == 1) // men
                    {
                        intent = new Intent(ScanMediaActivity.this, SelectionActivity.class);
                        intent.putExtra(CommonData.PARAM_KEY, CommonData.KEY_MEN);
                    }
                    else if (mCurrItem == 2)    // women
                    {
                        intent = new Intent(ScanMediaActivity.this, SelectionActivity.class);
                        intent.putExtra(CommonData.PARAM_KEY, CommonData.KEY_WOMEN);
                    }

                    if (intent != null)
                    {
                        startActivity(intent);
                        overridePendingTransition(TransformManager.GetContinueInAnim(), TransformManager.GetContinueOutAnim());
                        finish();
                    }
                }
            });
            cardImageView.setImageDrawable(getResources().getDrawable(mCards[position]));
            cardImageView.setTag(position);

            if (!mIsDefaultItemSelected) {
                cardImageView.setScaleX(MAX_SCALE);
                cardImageView.setScaleY(MAX_SCALE);
                cardImageView.setAlpha(1f);
                mIsDefaultItemSelected = true;
            } else {
                cardImageView.setScaleX(MIN_SCALE);
                cardImageView.setScaleY(MIN_SCALE);
                cardImageView.setAlpha(1f - 1f / alphaWeight);
            }

            container.addView(cardImageView);
            return cardImageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mCards.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < mCardsViewPager.getChildCount(); i++) {
            View cardView = mCardsViewPager.getChildAt(i);
            int itemPosition = (Integer) cardView.getTag();

            if (itemPosition == position) {
                cardView.setScaleX(MAX_SCALE - positionOffset / 4f);
                cardView.setScaleY(MAX_SCALE - positionOffset / 4f);
                cardView.setAlpha(1 - positionOffset / alphaWeight);
            }

            if (itemPosition == (position + 1)) {
                cardView.setScaleX(MIN_SCALE + positionOffset / 4f);
                cardView.setScaleY(MIN_SCALE + positionOffset / 4f);
                cardView.setAlpha(1 - 1 / alphaWeight + positionOffset / alphaWeight);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        ImageView imgLefArrow = (ImageView )findViewById(R.id.img_leftarrow);
        ImageView imgRightArrow = (ImageView )findViewById(R.id.img_rightarrow);
        if (position == mCardsPagerAdapter.mCards.length - 1)
        {
            imgLefArrow.setVisibility(View.VISIBLE);
            imgRightArrow.setVisibility(View.INVISIBLE);
        }
        else if (position == 0)
        {
            imgLefArrow.setVisibility(View.INVISIBLE);
            imgRightArrow.setVisibility(View.VISIBLE);
        }
        else
        {
            imgLefArrow.setVisibility(View.VISIBLE);
            imgRightArrow.setVisibility(View.VISIBLE);
        }
    }

    final protected boolean isServerRunning() throws IOException {
        InputStream is;
        java.io.BufferedReader bf;
        boolean isRunning = false;
        try {
            is = Runtime.getRuntime().exec("ps").getInputStream();
            bf = new java.io.BufferedReader(new java.io.InputStreamReader(is));

            String r;
            while ((r = bf.readLine()) != null) {
                if (r.contains("lighttpd")) {
                    isRunning = true;
                    break;
                }
            }
            is.close();
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return isRunning;
    }

    public void programInstalled() {
        // start service

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ServerUtils.runServer();
                String msg = "Unable to Start Server";
                try {
                    if (isServerRunning()) {
                        msg = "Server successfully Started";
                       // _appPrefs.setServerStarted(true);
                    }
                } catch (IOException e) {

                }
                android.widget.Toast.makeText(mContext, msg,
                        android.widget.Toast.LENGTH_LONG).show();

                Intent i = new Intent(mContext, ServerService.class);

                i.putExtra(ServerService.EXTRA_PORT,
                        prefs.getString("k_server_port", "8080"));

                startService(i);
                //_appPrefs.setServerStarted(true);
            }
        });
    }

    private class InstallerAsync extends
            android.os.AsyncTask<Void, String, Void> {

        ScanMediaActivity parentActivity;
        String loc;

        InstallerAsync(ScanMediaActivity parent) {
            parentActivity = parent;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            loc = ServerUtils.getAppDirectory() + "/";
            try {

                dirChecker("");
                ZipInputStream zin = new ZipInputStream(getAssets().open(
                        "data.zip"));
                ZipEntry ze = null;

                while ((ze = zin.getNextEntry()) != null) {

                    if (ze.isDirectory()) {
                        dirChecker(ze.getName());
                    } else {
                        FileOutputStream fout = new FileOutputStream(loc
                                + ze.getName());

                        publishProgress("Extracting : " + ze.getName());

                        byte[] buffer = new byte[4096 * 10];
                        int length = 0;
                        while ((length = zin.read(buffer)) != -1) {

                            fout.write(buffer, 0, length);

                        }

                        zin.closeEntry();
                        fout.close();
                    }

                }
                publishProgress("ok");

                zin.close();

            } catch (java.lang.Exception e) {
                publishProgress("error");
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            String text = "Error";
            //tv_install_exec.setVisibility(1);
            if (values[0] == "error")
                text = getString(R.string.bin_error);
            if (values[0] == "ok")
                text = getString(R.string.bin_installed);
            else
                text = values[0];

            //tv_install_exec.setText(text);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            parentActivity.programInstalled();
        }

        private void dirChecker(String dir) {
            File f = new File(loc + dir);

            if (!f.isDirectory()) {
                f.mkdirs();
            }
        }

    }
}
