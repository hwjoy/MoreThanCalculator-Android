package com.redant.morethancalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

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
    private DecimalFormat mNumberFormat;

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

        mNumberFormat = new DecimalFormat();
        mNumberFormat.setMinimumFractionDigits(0);
        mNumberFormat.setMaximumFractionDigits(16);
        mNumberFormat.setGroupingUsed(false);
    }


    private void initViews () {
        // Disable keyboard
        mEditText.setKeyListener(null);
    }

    @Override
    public void onClick(View v) {
        Editable text = mEditText.getText();
        String string = text.toString();
        double value = parseDouble(string);
        switch (v.getId()) {
            case R.id.clearButton:
                text.clear();
                lastNumber = 0.0;
                lastOperator = null;
                break;

            case R.id.signButton:
                setValueToEditText(-1 * value);
                break;

            case R.id.percentButton:
                setValueToEditText(value / 100);
                break;

            case R.id.plusButton:
                lastNumber = value;
                lastOperator = Operator.Plus;
                text.clear();
                shouldUpdateLastNumber = true;
                break;

            case R.id.minusButton:
                lastNumber = value;
                lastOperator = Operator.Minus;
                text.clear();
                shouldUpdateLastNumber = true;
                break;

            case R.id.multiplyButton:
                lastNumber = value;
                lastOperator = Operator.Multiply;
                text.clear();
                shouldUpdateLastNumber = true;
                break;

            case R.id.divideButton:
                lastNumber = value;
                lastOperator = Operator.Divide;
                text.clear();
                shouldUpdateLastNumber = true;
                break;

            case R.id.equalButton:
                shouldClearText = true;
                if (lastOperator != null) {
                    double calculatorValue = 0.0;
                    switch (lastOperator) {
                        case Plus:
                            calculatorValue = lastNumber + value;
                            break;

                        case Minus:
                            if (shouldUpdateLastNumber) {
                                calculatorValue = lastNumber - value;
                            } else {
                                calculatorValue = value - lastNumber;
                            }
                            break;

                        case Multiply:
                            calculatorValue = lastNumber * value;
                            break;

                        case Divide:
                            if (shouldUpdateLastNumber) {
                                calculatorValue = lastNumber / value;
                            } else {
                                calculatorValue = value / lastNumber;
                            }
                            break;
                    }
                    setValueToEditText(calculatorValue);
                    if (shouldUpdateLastNumber) {
                        shouldUpdateLastNumber = false;
                        lastNumber = value;
                    }
                }
                break;

            case R.id.dotButton:
                if (!string.isEmpty()) {
                    if (!Double.valueOf(value).isInfinite() && !Double.valueOf(value).isNaN() && !string.contains(".")) {
                        text.append(".");
                    }
                } else {
                    text.append(".");
                }
                break;

            default: {
                Button button = (Button) v;
                CharSequence buttonText = button.getText();
                if (shouldClearText) {
                    shouldClearText = false;
                    mEditText.setText(buttonText);
                } else {
                    text.append(buttonText);
                }
                break;
            }
        }
    }

    private void setValueToEditText(double value) {
        if (Double.toString(value).contains("E")) {
            mEditText.setText(Double.toString(value));
        } else {
            String string = mNumberFormat.format(value);
            Log.i(TAG, "Transform " + value + " To " + string);
            mEditText.setText(string);
        }

//        if (value - (int) value == 0) {
//            mEditText.setText(Integer.toString((int) value));
//        } else {
//            mEditText.setText(Double.toString(value));
//        }
    }

    private double parseDouble(String string) {
        if (string.isEmpty()) {
            return 0;
        } else if (string.equals("∞")) {
            return Double.POSITIVE_INFINITY;
        } else if (string.equals("-∞")) {
            return Double.NEGATIVE_INFINITY;
        } else if (string.equals(".")) {
            return 0;
        } else {
            return Double.parseDouble(string);
        }
    }
}
