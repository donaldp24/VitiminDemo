package com.general.mediaplayer.VitiminDemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by donald on 4/4/14.
 */
public class ResultActivity extends BaseFormulaActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        // back button
        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ScanMediaActivity.class);
                startActivity(intent);
                overridePendingTransition(TransformManager.GetBackInAnim(), TransformManager.GetBackOutAnim());
                finish();
            }
        });

        // coupon button
        Button btnCoupon = (Button)findViewById(R.id.btn_coupon);
        btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, CouponActivity.class);
                startActivity(intent);
                overridePendingTransition(TransformManager.GetContinueInAnim(), TransformManager.GetContinueOutAnim());
                finish();
            }
        });

        /*
        // arrange result screen and set lighting
        Intent intent = getIntent();
        Integer key = intent.getIntExtra(CommonData.PARAM_KEY, CommonData.KEY_ARCHITECTURE);
        ResultInfo info = ResultInfo.getResultInfo(key);

        if (info != null)
        {
            CommonData.arranger.arrangeResult(findViewById(R.id.layout_result), info);
            //SendSubN(info.subId);
            SendSectionNum(info.subId);
        }
        */

        Intent intent = getIntent();
        Integer key = intent.getIntExtra(CommonData.PARAM_SUBID, CommonData.SUBID_HERBAL);
        SendSectionNum(key);

        // rearrange children
        ResolutionSet._instance.iterateChild(findViewById(R.id.layout_result));
    }
}