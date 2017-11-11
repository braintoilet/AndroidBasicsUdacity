package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     *
     */
    public void submitOrder(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.add_whipped_cream);
        boolean hasWhippedCream = checkBox.isChecked();

        checkBox = (CheckBox) findViewById(R.id.add_chocolate);
        boolean hasChocolate = checkBox.isChecked();

        EditText nameText = (EditText) findViewById(R.id.name_edit);
        String name = nameText.getText().toString();

        String priceMessage = createOrderSummary(calculatePrice(hasWhippedCream, hasChocolate), hasWhippedCream, hasChocolate, name);
//        displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view){
        if(quantity < 100){
            quantity++;
            displayQuantity(quantity);
        }
    }

    public void decrement(View view){
        if(quantity > 0){
            quantity--;
            displayQuantity(quantity);
        }
    }

    /**
     *  Calculates the total price of the order.
     * @param addWhippedCream if true add 1 to price
     * @param addChocolate if true add 2 to price
     * @return Total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int price = 5;

        if(addWhippedCream){
            price = price + 1;
        }
        if(addChocolate){
            price = price + 2;
        }

        return quantity * price;
    }


    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
        String result = "";
        result += "Name: "+ name + "\n";
        result += "near  " + addWhippedCream + "\n";
        result += "Add Chocolate? " + addChocolate + "\n";
        result += "Quantity: " + quantity + "\n";
        result += "Total: " + price + "€\n";
        result += "Thank You!";

        return result;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view    );
        orderSummaryTextView.setText(message);
    }
}