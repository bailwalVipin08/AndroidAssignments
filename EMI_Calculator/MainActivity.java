package com.vb.android.emicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating a reference for Calculate Button
        Button calculateBtn = findViewById(R.id.calculate_emi);
        calculateBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                EditText e1 = findViewById(R.id.principal_amount);
                EditText e2 = findViewById(R.id.interest_rate);
                EditText e3 = findViewById(R.id.num_of_months);

                final TextView resultTextView = findViewById(R.id.result_text_view);

                double p = Double.parseDouble(e1.getText().toString());
                double r = Double.parseDouble(e2.getText().toString());
                double m = Double.parseDouble(e3.getText().toString());

                final double result = calculateEMI(p, r, m);
                resultTextView.setText(String.format("EMI Value: ₹ %.2f", result));
            }
        });


    }

    private double calculateEMI(double p, double R, double m){
        double monthlyInterest = R/(12*100);
        double powerExpression = Math.pow(1 + monthlyInterest, m);
        double e1 = p*monthlyInterest*powerExpression;
        double e2 = powerExpression-1;

        double EMI = e1/e2;
        return EMI;
    }
}
