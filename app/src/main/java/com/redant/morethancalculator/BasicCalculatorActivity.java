package com.redant.morethancalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BasicCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BasicCalculatorActivity";
    private EditText mEditText;
    private Button mClearButton;
    private Button mSignButton;
    private Button mPercentButton;
    private Button mDivideButton;
    private Button mSevenButton;
    private Button mEightButton;
    private Button mNineButton;
    private Button mMultiplyButton;
    private Button mFourButton;
    private Button mFiveButton;
    private Button mSixButton;
    private Button mMinusButton;
    private Button mOneButton;
    private Button mTwoButton;
    private Button mThreeButton;
    private Button mPlusButton;
    private Button mZeroButton;
    private Button mDotButton;
    private Button mEqualButton;

    private Button[] allButtonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditText = (EditText) findViewById(R.id.editText);
        mClearButton = (Button) findViewById(R.id.clearButton);
        mSignButton = (Button) findViewById(R.id.signButton);
        mPercentButton = (Button) findViewById(R.id.percentButton);
        mDivideButton = (Button) findViewById(R.id.divideButton);
        mSevenButton = (Button) findViewById(R.id.sevenButton);
        mEightButton = (Button) findViewById(R.id.eightButton);
        mNineButton = (Button) findViewById(R.id.nineButton);
        mMultiplyButton = (Button) findViewById(R.id.multiplyButton);
        mFourButton = (Button) findViewById(R.id.fourButton);
        mFiveButton = (Button) findViewById(R.id.fiveButton);
        mSixButton = (Button) findViewById(R.id.sixButton);
        mMinusButton = (Button) findViewById(R.id.minusButton);
        mOneButton = (Button) findViewById(R.id.oneButton);
        mTwoButton = (Button) findViewById(R.id.twoButton);
        mThreeButton = (Button) findViewById(R.id.threeButton);
        mPlusButton = (Button) findViewById(R.id.plusButton);
        mZeroButton = (Button) findViewById(R.id.zeroButton);
        mDotButton = (Button) findViewById(R.id.dotButton);
        mEqualButton = (Button) findViewById(R.id.equalButton);

        allButtonArray = new Button[]{
                mClearButton, mSignButton, mPercentButton,
                mPlusButton, mMinusButton, mMultiplyButton, mDivideButton,
                mZeroButton, mOneButton, mTwoButton, mThreeButton, mFourButton,
                mFiveButton, mSixButton, mSevenButton, mEightButton, mNineButton,
                mDotButton, mEqualButton
        };

        initViews();

        for (Button button: allButtonArray) {
            button.setOnClickListener(this);
        }
    }


    private void initViews () {
        // Disable keyboard
        mEditText.setKeyListener(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearButton:
                mEditText.setText(null);
                break;

            case R.id.signButton:
                break;

            case R.id.percentButton: {
                String string = mEditText.getText().toString();
                double value = Double.parseDouble(string) / 100;
                mEditText.setText(Double.toString(value));
                break;
            }

            case R.id.plusButton:
                break;

            case R.id.minusButton:
                break;

            case R.id.multiplyButton:
                break;

            case R.id.divideButton:
                break;

            case R.id.equalButton:
                break;

            case R.id.dotButton:
                break;

            default: {
                Button button = (Button) v;
                mEditText.setText(mEditText.getText() + button.getText().toString());
                break;
            }
        }
    }
}
