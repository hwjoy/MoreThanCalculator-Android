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

    enum Operator {
        Plus, Minus, Multiply, Divide
    }

    private boolean shouldClearText = false;
    private double lastNumber = 0.0;
    private Operator lastOperator = null;
    private boolean shouldUpdateLastNumber = false;

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
        String string = mEditText.getText().toString();
        switch (v.getId()) {
            case R.id.clearButton:
                mEditText.setText(null);
                lastNumber = 0.0;
                lastOperator = null;
                break;

            case R.id.signButton:
                if (!string.isEmpty() && string.equalsIgnoreCase("NaN") && string.equalsIgnoreCase("Infinity")) {
                    if (string.startsWith("-")) {
                        mEditText.setText(mEditText.getText().subSequence(1, string.length()));
                    } else {
                        mEditText.setText("-" + string);
                    }
                }
                break;

            case R.id.percentButton:
                if (!string.isEmpty()) {
                    if (string.equals(".")) {
                        setValueToEditText(0);
                    } else if (!string.equalsIgnoreCase("NaN") && !string.equalsIgnoreCase("Infinity")) {
                        double value = Double.parseDouble(string) / 100;
                        setValueToEditText(value);
                    }
                }
                break;

            case R.id.plusButton:
                if (!string.isEmpty()) {
                    lastNumber = Double.parseDouble(string);
                }
                lastOperator = Operator.Plus;
                mEditText.setText(null);
                shouldUpdateLastNumber = true;
                break;

            case R.id.minusButton:
                if (!string.isEmpty()) {
                    lastNumber = Double.parseDouble(string);
                }
                lastOperator = Operator.Minus;
                mEditText.setText(null);
                shouldUpdateLastNumber = true;
                break;

            case R.id.multiplyButton:
                if (!string.isEmpty()) {
                    lastNumber = Double.parseDouble(string);
                }
                lastOperator = Operator.Multiply;
                mEditText.setText(null);
                shouldUpdateLastNumber = true;
                break;

            case R.id.divideButton:
                if (!string.isEmpty()) {
                    lastNumber = Double.parseDouble(string);
                }
                lastOperator = Operator.Divide;
                mEditText.setText(null);
                shouldUpdateLastNumber = true;
                break;

            case R.id.equalButton:
                shouldClearText = true;
                if (lastOperator != null) {
                    double value = 0.0;
                    double currentNumber = 0.0;
                    if (!string.isEmpty()) {
                        currentNumber = Double.parseDouble(string);
                    }
                    switch (lastOperator) {
                        case Plus:
                            value = lastNumber + currentNumber;
                            break;

                        case Minus:
                            if (shouldUpdateLastNumber) {
                                value = lastNumber - currentNumber;
                            } else {
                                value = currentNumber - lastNumber;
                            }
                            break;

                        case Multiply:
                            value = lastNumber * currentNumber;
                            break;

                        case Divide:
                            if (shouldUpdateLastNumber) {
                                value = lastNumber / currentNumber;
                            } else {
                                value = currentNumber / lastNumber;
                            }
                            break;
                    }
                    setValueToEditText(value);
                    if (shouldUpdateLastNumber) {
                        shouldUpdateLastNumber = false;
                        lastNumber = currentNumber;
                    }
                }
                break;

            case R.id.dotButton:
                if (string.equalsIgnoreCase("NaN") && string.equalsIgnoreCase("Infinity") && !string.contains(".")) {
                    mEditText.setText(string + ".");
                }
                break;

            default: {
                Button button = (Button) v;
                if (shouldClearText) {
                    shouldClearText = false;
                    mEditText.setText(button.getText().toString());
                } else {
                    mEditText.setText(string + button.getText().toString());
                }
                break;
            }
        }
    }

    private void setValueToEditText(double value) {
//        NumberFormat numberFormat = NumberFormat.getInstance();
//        numberFormat.setMinimumFractionDigits(0);
//        mEditText.setText(numberFormat.format(value));

        if (value - (int) value == 0) {
            mEditText.setText(Integer.toString((int) value));
        } else {
            mEditText.setText(Double.toString(value));
        }
    }
}
