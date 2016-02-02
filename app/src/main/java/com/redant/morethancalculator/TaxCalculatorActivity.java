package com.redant.morethancalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TaxCalculatorActivity extends AppCompatActivity {

    private static final String TAG = "TaxCalculatorActivity";
    private Spinner mIncomeTypeSpinner;
    private EditText mPreTaxIncomeEditText;
    private EditText mSocialInsuranceChargesEditText;
    private EditText mLowestTaxableLimitEditText;
    private EditText mTaxPayableEditText;
    private EditText mAfterTaxIncomeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIncomeTypeSpinner = (Spinner) findViewById(R.id.incomeTypeSpinner);
        mPreTaxIncomeEditText = (EditText) findViewById(R.id.preTaxIncomeEditText);
        mSocialInsuranceChargesEditText = (EditText) findViewById(R.id.socialInsuranceChargesEditText);
        mLowestTaxableLimitEditText = (EditText) findViewById(R.id.lowestTaxableLimitEditText);
        mTaxPayableEditText = (EditText) findViewById(R.id.taxPayableEditText);
        mAfterTaxIncomeEditText = (EditText) findViewById(R.id.afterTaxIncomeEditText);

        initViews();

        findViewById(R.id.calculatorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                calculatorTax();
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetViews();
            }
        });
    }

    private void initViews() {
        mLowestTaxableLimitEditText.setText("3500");
    }

    private void calculatorTax() {
        if (mPreTaxIncomeEditText.getText().toString().isEmpty()) {
            mTaxPayableEditText.setText(null);
            mAfterTaxIncomeEditText.setText(null);
        } else {
            double[][] taxArray = {{80000, 0.45, 13505}, {55000, 0.35, 5505}, {35000, 0.3, 2775},
                    {9000, 0.25, 1005}, {4500, 0.2, 555}, {1500, 0.1, 105}, {0, 0.03, 0}};

            double preTaxIncomeValue = Double.parseDouble(mPreTaxIncomeEditText.getText().toString());

            int selectedItemPosition = mIncomeTypeSpinner.getSelectedItemPosition();
            switch (selectedItemPosition) {
                case 0: {
                    if (mSocialInsuranceChargesEditText.getText().toString().isEmpty()) {
                        mSocialInsuranceChargesEditText.setText("0");
                    }
                    if (mLowestTaxableLimitEditText.getText().toString().isEmpty()) {
                        mLowestTaxableLimitEditText.setText("3500");
                    }

                    double socialInsuranceChargesValue = Double.parseDouble(mSocialInsuranceChargesEditText.getText().toString());
                    double lowestTaxableLimitValue = Double.parseDouble(mLowestTaxableLimitEditText.getText().toString());
                    double resultValue = preTaxIncomeValue - socialInsuranceChargesValue - lowestTaxableLimitValue;
                    if (resultValue > 0) {
                        for (int i = 0; i < taxArray.length; i++) {
                            if (resultValue > taxArray[i][0]) {
                                resultValue = resultValue * taxArray[i][1] - taxArray[i][2];
                                break;
                            }
                        }
                    } else {
                        resultValue = 0;
                    }

                    mTaxPayableEditText.setText(String.format("%.2f", resultValue));
                    mAfterTaxIncomeEditText.setText(String.format("%.2f", preTaxIncomeValue - resultValue - socialInsuranceChargesValue));
                    break;
                }

                case 1: {
                    mSocialInsuranceChargesEditText.setText(null);
                    mLowestTaxableLimitEditText.setText(null);

                    double preMonthValue = preTaxIncomeValue / 12;
                    double resultValue = 0;
                    if (preMonthValue > 0) {
                        for (int i = 0; i < taxArray.length; i++) {
                            if (preMonthValue > taxArray[i][0]) {
                                resultValue = preTaxIncomeValue * taxArray[i][1] - taxArray[i][2];
                                break;
                            }
                        }
                    } else {
                        resultValue = 0;
                    }

                    mTaxPayableEditText.setText(String.format("%.2f", resultValue));
                    mAfterTaxIncomeEditText.setText(String.format("%.2f", preTaxIncomeValue - resultValue));
                    break;
                }
            }
        }
    }

    private void resetViews() {
        mPreTaxIncomeEditText.setText(null);
        mSocialInsuranceChargesEditText.setText(null);
        mTaxPayableEditText.setText(null);
        mAfterTaxIncomeEditText.setText(null);
    }
}
