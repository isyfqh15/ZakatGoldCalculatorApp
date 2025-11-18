package com.example.zakatgoldcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Declarations
    EditText etGoldWeight, etGoldValue;
    RadioGroup rgGoldType;
    RadioButton rbKeep, rbWear;
    Button btnCalculate, btnReset;
    TextView tvTotalValue, tvPayableValue, tvTotalZakat;

    private static final String TAG = "MainActivity";
    private DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  APP HEADER
        getSupportActionBar().setTitle("Zakat Gold Calculator");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);

        // Connect Java - XML
        etGoldWeight = findViewById(R.id.etGoldWeight);
        etGoldValue = findViewById(R.id.etGoldValue);
        rgGoldType = findViewById(R.id.rgGoldType);
        rbKeep = findViewById(R.id.rbKeep);
        rbWear = findViewById(R.id.rbWear);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvPayableValue = findViewById(R.id.tvPayableValue);
        tvTotalZakat = findViewById(R.id.tvTotalZakat);


        formatter = new DecimalFormat("RM #,##0.00");


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click to calculate
                calculateZakat();
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // click to reset
                resetForm();
            }
        });

        Log.d(TAG, "MainActivity onCreate: App has started successfully.");
    }

    // calculation
    private void calculateZakat() {
        try {
            String weightStr = etGoldWeight.getText().toString();
            String valueStr = etGoldValue.getText().toString();

            // Error handling
            if (weightStr.isEmpty() || valueStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Calculation failed: Empty fields.");
                return;
            }

            double weight = Double.parseDouble(weightStr);
            double valuePerGram = Double.parseDouble(valueStr);

            int selectedTypeId = rgGoldType.getCheckedRadioButtonId();
            double uruf;
            if (selectedTypeId == R.id.rbKeep) {
                uruf = 85.0; // 85g for Keep
            } else {
                uruf = 200.0; // 200g for Wear
            }

            // Calculate Total Gold Value
            double totalGoldValue = weight * valuePerGram;

            double weightMinusUruf = weight - uruf;
            double zakatPayableValue;

            // Calculate Zakat Payable Value
            if (weightMinusUruf > 0) {
                zakatPayableValue = weightMinusUruf * valuePerGram;
            } else {
                zakatPayableValue = 0.0; // Zakat is 0 if below uruf
            }

            // Calculate Total Zakat
            double totalZakat = 0.025 * zakatPayableValue;

            // Log for debugging (Logcat requirement)
            Log.i(TAG, "Calculation Success:");
            Log.i(TAG, "Total Gold Value: RM" + totalGoldValue);
            Log.i(TAG, "Zakat Payable Value: RM" + zakatPayableValue);
            Log.i(TAG, "Total Zakat: RM" + totalZakat);


            tvTotalValue.setText(formatter.format(totalGoldValue));
            tvPayableValue.setText(formatter.format(zakatPayableValue));
            tvTotalZakat.setText(formatter.format(totalZakat));

        } catch (NumberFormatException e) {
            // This is for the Logcat requirement
            Log.e(TAG, "NumberFormatException: User entered invalid number", e);
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "An unexpected error occurred", e);
            Toast.makeText(this, "An error occurred.", Toast.LENGTH_SHORT).show();
        }
    }

    // Reset form
    private void resetForm() {
        etGoldWeight.setText("");
        etGoldValue.setText("");
        rbKeep.setChecked(true);

        // Reset result text
        tvTotalValue.setText("RM 0.00");
        tvPayableValue.setText("RM 0.00");
        tvTotalZakat.setText("RM 0.00");

        etGoldWeight.requestFocus();
        Log.d(TAG, "Form reset.");
    }


    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // If click share button
        if (id == R.id.action_share) {
            Log.d(TAG, "Share button clicked.");
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "Check out this Zakat Gold Calculator app! Find it at: https://github.com/isyfqh15/ICT602-ZakatGoldCalculator-.git";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zakat Gold Calculator App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }

        // If click about button
        if (id == R.id.action_about) {
            Log.d(TAG, "About button clicked.");
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}