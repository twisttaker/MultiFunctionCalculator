
package com.example.multifunctioncalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private EditText[] subjectFields;
    private TextView tvNumberResult;
    private TextView tvGradeResult;
    private Button btnCheck;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect XML components
        etNumber = findViewById(R.id.etNumber);

        subjectFields = new EditText[]{
                findViewById(R.id.etSubject1),
                findViewById(R.id.etSubject2),
                findViewById(R.id.etSubject3),
                findViewById(R.id.etSubject4),
                findViewById(R.id.etSubject5)
        };

        tvNumberResult = findViewById(R.id.tvNumberResult);
        tvGradeResult = findViewById(R.id.tvGradeResult);

        btnCheck = findViewById(R.id.btnCheck);
        btnCalculate = findViewById(R.id.btnCalculate);

        // Number checking button
        btnCheck.setOnClickListener(v -> checkNumber());

        // Grade calculation button
        btnCalculate.setOnClickListener(v -> calculateGrade());
    }

    // Check prime, even/odd and palindrome
    private void checkNumber() {

        String input = etNumber.getText().toString().trim();

        if (input.isEmpty()) {
            etNumber.setError("Please enter a number");
            return;
        }

        try {
            long number = Long.parseLong(input);

            boolean prime = isPrime(number);
            boolean even = number % 2 == 0;
            boolean palindrome = isPalindrome(number);

            String result =
                    "Prime Number: " + (prime ? "Yes" : "No") +
                            "\nEven/Odd: " + (even ? "Even" : "Odd") +
                            "\nPalindrome: " + (palindrome ? "Yes" : "No");

            tvNumberResult.setText(result);

        } catch (NumberFormatException e) {
            etNumber.setError("Enter a valid whole number");
        }
    }

    // Prime number logic
    private boolean isPrime(long number) {

        if (number < 2) {
            return false;
        }

        for (long i = 2; i <= number / i; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    // Palindrome number logic
    private boolean isPalindrome(long number) {

        if (number < 0) {
            return false;
        }

        long original = number;
        long reverse = 0;

        while (number > 0) {
            long digit = number % 10;
            reverse = reverse * 10 + digit;
            number = number / 10;
        }

        return original == reverse;
    }

    // Calculate total marks, percentage and grade
    private void calculateGrade() {

        double total = 0;

        for (int i = 0; i < subjectFields.length; i++) {

            String marksText = subjectFields[i]
                    .getText().toString().trim();

            if (marksText.isEmpty()) {
                subjectFields[i].setError("Enter subject marks");
                return;
            }

            try {
                double marks = Double.parseDouble(marksText);

                if (marks < 0 || marks > 100) {
                    subjectFields[i].setError(
                            "Marks must be between 0 and 100"
                    );
                    return;
                }

                total += marks;

            } catch (NumberFormatException e) {
                subjectFields[i].setError("Enter valid marks");
                return;
            }
        }

        double percentage = total / 5.0;

        String grade;

        if (percentage >= 80) {
            grade = "A";
        } else if (percentage >= 70) {
            grade = "B";
        } else if (percentage >= 60) {
            grade = "C";
        } else if (percentage >= 40) {
            grade = "D";
        } else {
            grade = "Fail";
        }

        String result = String.format(
                Locale.getDefault(),
                "Total Marks: %.2f / 500\nPercentage: %.2f%%\nGrade: %s",
                total,
                percentage,
                grade
        );

        tvGradeResult.setText(result);
    }
}
