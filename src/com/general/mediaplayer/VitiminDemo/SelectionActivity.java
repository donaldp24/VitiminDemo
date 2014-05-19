package com.general.mediaplayer.VitiminDemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;

/**
 * Created by donald on 4/4/14.
 */
public class SelectionActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);

        // back button
        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, ScanMediaActivity.class);
                startActivity(intent);
                overridePendingTransition(TransformManager.GetBackInAnim(), TransformManager.GetBackOutAnim());
                finish();
            }
        });

        // herbal button
        Button btnHerbal = (Button)findViewById(R.id.btn_herbal);
        btnHerbal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_HERBAL);
            }
        });

        // minerals button
        Button btnMinerals = (Button)findViewById(R.id.btn_minerals);
        btnMinerals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_MINERALS);
            }
        });

        // vitimin button
        Button btnVitimin = (Button)findViewById(R.id.btn_vitiman);
        btnVitimin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_VITIMIN);
            }
        });

        // organic button
        Button btnOrganic = (Button)findViewById(R.id.btn_organic);
        btnOrganic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_ORGANIC);
            }
        });

        // multi-vit button
        Button btnMulti = (Button)findViewById(R.id.btn_multi);
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_MULTI);
            }
        });

        // supplements button
        Button btnSupplements = (Button)findViewById(R.id.btn_supplements);
        btnSupplements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResult(CommonData.KEY_SUPPLEMENTS);
            }
        });

        // rearrange children
        ResolutionSet._instance.iterateChild(findViewById(R.id.layout_selection));
    }

    private void gotoResult(int key)
    {
        Intent intent = new Intent(SelectionActivity.this, ResultActivity.class);
        intent.putExtra(CommonData.PARAM_KEY, key);
        startActivity(intent);
        overridePendingTransition(TransformManager.GetContinueInAnim(), TransformManager.GetContinueOutAnim());
        finish();
    }
}