package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Button;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    private EditText totalBillInput;
    private TextView tipAmountOutput;
    private TextView totalAmountOutput;
    private EditText peopleNumberInput;
    private TextView totalPerPersonOutput;
    private TextView overageOutput;

    public RadioButton radio_twelve;
    public RadioButton radio_fifteen;
    public RadioButton radio_eighteen;
    public RadioButton radio_twenty;

    public ConstraintLayout background;

    public Button go;
    public Button clear;

    private static double tipAmount = 0;
    private static double totalWithTip = 0;
    private static double totalPerPerson = 0;
    private static double overage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalBillInput = findViewById(R.id.displayBill);
        tipAmountOutput = findViewById(R.id.displayTipAmount);
        totalAmountOutput = findViewById(R.id.displayTotalAndTip);
        peopleNumberInput = findViewById(R.id.numOfPeopleTxT);
        totalPerPersonOutput = findViewById(R.id.displayTotalPerPerson);
        overageOutput = findViewById(R.id.displayOverage);

        background = findViewById(R.id.tipscreen);

        radio_twelve = findViewById(R.id.tip_twelve);
        radio_fifteen = findViewById(R.id.tip_fifteen);
        radio_eighteen = findViewById(R.id.tip_eighteen);
        radio_twenty = findViewById(R.id.tip_twenty);

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("tipAmountOutput", tipAmountOutput.getText().toString());
        outState.putString("totalAmountOutput", totalAmountOutput.getText().toString());
        outState.putString("totalPerPersonOutput", totalPerPersonOutput.getText().toString());
        outState.putString("overageOutput", overageOutput.getText().toString());


        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        tipAmountOutput.setText(savedInstanceState.getString("tipAmountOutput"));
        totalAmountOutput.setText(savedInstanceState.getString("totalAmountOutput"));
        totalPerPersonOutput.setText(savedInstanceState.getString("totalPerPersonOutput"));
        overageOutput.setText(savedInstanceState.getString("overageOutput"));


    }



    public void radioButtonClicked(View v) {

        //If input is invalid
        if (totalBillInput.getText().toString().isEmpty()) {

            radio_twelve.setChecked(false);
            radio_fifteen.setChecked(false);
            radio_eighteen.setChecked(false);
            radio_twenty.setChecked(false);

            tipAmountOutput.setText("");
            totalAmountOutput.setText("");


            //Display Error message

            Snackbar snackbar = Snackbar
                    .make(v, "Please enter the bill.", Snackbar.LENGTH_LONG);
            snackbar.show();

        }

        //Valid Input
        else {

            //Retrieve the bill from the Edit Text field and convert to double
            String totalBillInput_string = totalBillInput.getText().toString();
            Double totalBillInput_value = Double.parseDouble(totalBillInput_string);


            //Calculate the tip when any radio button is checked
            boolean checked = ((RadioButton) v).isChecked();

            switch(v.getId()) {
                case R.id.tip_twelve:
                    if (checked)
                        tipAmount = totalBillInput_value * 0.12;
                        totalWithTip = tipAmount + totalBillInput_value;

                        //Just added to clean uo
                        peopleNumberInput.setText("");
                        totalPerPersonOutput.setText("");
                        overageOutput.setText("");
                    break;

                case R.id.tip_fifteen:
                    if (checked)
                        tipAmount = totalBillInput_value * 0.15;
                        totalWithTip = tipAmount + totalBillInput_value;
                        peopleNumberInput.setText("");
                        totalPerPersonOutput.setText("");
                        overageOutput.setText("");
                    break;

                case R.id.tip_eighteen:
                    if (checked)
                        tipAmount = totalBillInput_value * 0.18;
                        totalWithTip = tipAmount + totalBillInput_value;

                        peopleNumberInput.setText("");
                        totalPerPersonOutput.setText("");
                        overageOutput.setText("");
                    break;


                case R.id.tip_twenty:
                    if (checked)
                        tipAmount = totalBillInput_value * 0.20;
                        totalWithTip = tipAmount + totalBillInput_value;

                        peopleNumberInput.setText("");
                        totalPerPersonOutput.setText("");
                        overageOutput.setText("");
                    break;


            }


            //Set text for tip and total with tip
            String tempStringOne = String.format("%.2f", tipAmount);

            String tempStringTwo = String.format("%.2f",totalWithTip);

            tipAmountOutput.setText("$".concat(tempStringOne));

            totalAmountOutput.setText("$".concat(tempStringTwo));


        }

    }




    public void goButtonClicked(View v) {


        if(peopleNumberInput.getText().toString().isEmpty() || Integer.parseInt(peopleNumberInput.getText().toString())<=0){

            peopleNumberInput.setText("");

            totalPerPersonOutput.setText("");

            overageOutput.setText("");

            //Display Error Message

            Snackbar snackbar = Snackbar
                    .make(v, "Please enter a valid number of people.", Snackbar.LENGTH_LONG);

            snackbar.show();
        }


        else {
            //Retrieve the number of people from the Edit Text field and convert to double
            String peopleNumberInput_string = peopleNumberInput.getText().toString();
            Double peopleNumberInput_value = Double.parseDouble(peopleNumberInput_string);


            //Round the total per person to two decimal digits (always rounds up)
            double temp = totalWithTip/peopleNumberInput_value;

            double roundOff = Math.round(temp * 100.0) / 100.0;

            if (roundOff < temp) {
                roundOff = Math.round((roundOff + 0.01) * 100) / 100.0;
            }

            totalPerPerson = roundOff;

            //Set text for total per person
            totalPerPersonOutput.setText("$".concat(String.format("%.2f",totalPerPerson)));


            //Calculate and set text for overage (two decimal places)
            overage = (totalPerPerson * peopleNumberInput_value) - totalWithTip;

            overageOutput.setText("$".concat(String.format("%.2f",overage)));
        }

    }





    public void clearButtonClicked(View v) {


        //Clear everything

        radio_twelve.setChecked(false);
        radio_fifteen.setChecked(false);
        radio_eighteen.setChecked(false);
        radio_twenty.setChecked(false);

        totalBillInput.setText("");
        tipAmountOutput.setText("");
        totalAmountOutput.setText("");
        peopleNumberInput.setText("");
        totalPerPersonOutput.setText("");
        overageOutput.setText("");

        radio_twelve.setEnabled(true);
        radio_fifteen.setEnabled(true);
        radio_eighteen.setEnabled(true);
        radio_twenty.setEnabled(true);

    }






}

