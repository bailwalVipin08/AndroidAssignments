package com.vb.android.incometaxcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //incomes
    private double basicIncome, interestIncome, fundsIncome;

    //allowances
    private double totalHRA, totalSpecialAl, totalLTA;
    private double cessPercent = 3;
    private double hraDeduction;
    private double ltaDeduction;

    //exemptions
    private double fundsExemption;
    private double grossIncome ;
    private double totalDeduction;
    private double totalTaxableIncome;
    private double taxLiability ;
    private double taxToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //incomes

      final EditText basicSal = findViewById(R.id.yearly_sal);
      final EditText sa = findViewById(R.id.savings_income);
      final EditText funds = findViewById(R.id.equity);

      final TextView taxTxtView = findViewById(R.id.tax);


        //allowances

       final EditText hra = findViewById(R.id.hra);
       final EditText lta= findViewById(R.id.lta);
       final EditText special = findViewById(R.id.special);


       final double[] results = new double[6];


        Button resultBtn = findViewById(R.id.resultBtn);
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basicIncome = Double.parseDouble(basicSal.getText().toString());
                interestIncome = Double.parseDouble(sa.getText().toString());

                fundsIncome = Double.parseDouble(funds.getText().toString());

                totalHRA = Double.parseDouble(hra.getText().toString());
                totalLTA = Double.parseDouble(lta.getText().toString());
                totalSpecialAl = Double.parseDouble(special.getText().toString());
                taxCalculations();
                results[0] = grossIncome;
                results[1] = totalDeduction;
                results[2] = fundsExemption;
                results[3] = taxLiability;
                results[4] = totalTaxableIncome;
                results[5] = taxToPay;

                TextView resultTxtView = findViewById(R.id.resultTxt);

                resultTxtView.setText("Gross Income : " + String.valueOf(results[0]) + "\n" +
                                      "Total Deductions: " + String.valueOf(results[1]) + "\n"+
                                      "Total Exemption : " + String.valueOf(results[2]) + "\n"+
                                      "Tax Liability   : " + String.valueOf(results[3]) + "\n"+
                                      "Taxable Income  : " + String.valueOf(results[4]) + "\n"
                );

                taxTxtView.setText("Tax To Pay      : " + String.valueOf(results[5]) + "\n");
                taxTxtView.setVisibility(View.VISIBLE);

            }
        });
    }

    private final void taxCalculations(){

        //deductions

        //NOTE: special allowance and basic salary are fully taxable so deductions for them = 0

        hraDeduction = totalHRA - 0.1*basicIncome;
        ltaDeduction = totalLTA - 0.7*totalLTA;

        //exemptions
        fundsExemption = fundsIncome - 150000; //upto 1.5 lakhs are exempted under IT Regulation 80C

        grossIncome = basicIncome + totalHRA + totalLTA + totalSpecialAl + fundsIncome ;

        totalDeduction = hraDeduction + ltaDeduction;

        totalTaxableIncome = grossIncome - (totalDeduction + fundsExemption);

        taxLiability = 0.05 * (totalTaxableIncome - 250000) + 0.2*(totalTaxableIncome - 500000);

        taxToPay = (cessPercent*taxLiability)/100 + taxLiability;
    }

}
