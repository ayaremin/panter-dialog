package com.panterlib.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eminayar.panter.enums.Animation;
import com.eminayar.panter.DialogType;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.interfaces.OnDialogClickListener;
import com.eminayar.panter.interfaces.OnSingleCallbackConfirmListener;
import com.eminayar.panter.interfaces.OnTextInputConfirmListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) (findViewById(R.id.header_logo))).setOnClickListener(this);
        ((Button) (findViewById(R.id.header_title))).setOnClickListener(this);
        ((Button) (findViewById(R.id.only_header))).setOnClickListener(this);
        ((Button) (findViewById(R.id.one_button))).setOnClickListener(this);
        ((Button) (findViewById(R.id.input_button))).setOnClickListener(this);
        ((Button) (findViewById(R.id.single_choice))).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_logo:
                new PanterDialog(this)
                        .setHeaderBackground(R.drawable.pattern_bg_orange)
                        .setHeaderLogo(R.drawable.sample_logo)
                        .setPositive("I GOT IT", new OnDialogClickListener() {
                            @Override
                            public void onDialogButtonClicked(PanterDialog dialog) {
                                Toast.makeText(getApplicationContext(),"Custom click event",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegative("DISMISS")
                        .setMessage(R.string.lorem_ipsum)
                        .withAnimation(Animation.POP)
                        .isCancelable(false)
                        .show();
                break;
            case R.id.header_title:
                new PanterDialog(this)
                        .setHeaderBackground(R.drawable.pattern_bg_blue)
                        .setTitle("Sample Title", 22)
                        .setPositive("I GOT IT")
                        .setNegative("DISMISS")
                        .setMessage(R.string.lorem_ipsum)
                        .isCancelable(false)
                        .show();
                break;
            case R.id.only_header:
                new PanterDialog(this)
                        .setHeaderBackground(R.drawable.pattern_bg_yellow)
                        .setPositive("I GOT IT")
                        .setNegative("DISMISS")
                        .setMessage(R.string.lorem_ipsum)
                        .isCancelable(false)
                        .show();
                break;
            case R.id.one_button:
                new PanterDialog(this)
                        .setHeaderBackground(R.drawable.pattern_bg_blue)
                        .setPositive("DISMISS")
                        .setMessage(R.string.lorem_ipsum)
                        .isCancelable(false)
                        .show();
                break;
            case R.id.input_button:
                new PanterDialog(this)
                        .setHeaderBackground(R.drawable.pattern_bg_orange)
                        .setHeaderLogo(R.drawable.sample_logo)
                        .withAnimation(Animation.SIDE)
                        .setDialogType(DialogType.INPUT)
                        .isCancelable(false)
                        .input("THIS IS HINT FOR INPUT AREA YOU CAN WRITE HERE ANY LONGER TEXTS", new
                                OnTextInputConfirmListener() {
                                    @Override
                                    public void onTextInputConfirmed(String text) {
                                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                                    }
                                })
                        .show();
                break;
            case R.id.single_choice:
                new PanterDialog(this)
                        .setHeaderBackground(R.drawable.pattern_bg_blue)
                        .setHeaderLogo(R.drawable.sample_logo)
                        .setDialogType(DialogType.SINGLECHOICE)
                        .withAnimation(Animation.SLIDE)
                        .isCancelable(false)
                        .items(R.array.sample_array, new OnSingleCallbackConfirmListener() {
                            @Override
                            public void onSingleCallbackConfirmed(PanterDialog dialog, int pos, String text) {
                                Toast.makeText(MainActivity.this, "position : " + String.valueOf(pos) +
                                                " value = " + text,
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
                break;
        }
    }
}
