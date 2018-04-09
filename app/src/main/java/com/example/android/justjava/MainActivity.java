/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import java.text.NumberFormat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox= findViewById(R.id.whipped_cream);
        boolean whippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox= findViewById(R.id.chocolate_top);
        boolean hasChocolate= chocolateCheckBox.isChecked();
        EditText nameField = findViewById(R.id.name_field);
        String  name = nameField.getText().toString();
        int price= calculatePrice(whippedCream, hasChocolate);
        String priceMessage=createOrderSummary(price,whippedCream,hasChocolate,name);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT," Just Java Order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
       // displayMessage(priceMessage);
    }
    /**
     * Calculates the price of the order.
     * with added whip or/and chocolate
     *
     **/

    private int calculatePrice(boolean whippedCream, boolean hasChocolate) {
        int baseCost=5;
// add a dollar for whipped cream
        if( whippedCream)
        {
            baseCost=baseCost+1;
        }
        // add 2 dollars for chocolate checked
        if( hasChocolate){
            baseCost=baseCost+2;
        }

        return quantity*baseCost;
    }

    public void increment(View view){
       if(quantity == 100){
           Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
           return;
       }
       else {
           quantity = quantity + 1;
           displayQuantity(quantity);
       }
    }
    public void decrement(View view){
        if(quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + amount);
    }
    /**
     * This method displays the given price on the screen.

    private void displayPrice(int number) {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
     **/
    /**
     * This method displays the given text on the screen.

    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
*/
    private String createOrderSummary(int price, boolean whippedCream, boolean hasChocolate, String name){
        String priceMessage=getString(R.string.order_summary_name, name);
        priceMessage+="\n"+getString(R.string.order_summary_whipped_cream,whippedCream);
        priceMessage+="\n"+getString(R.string.order_summary_chocolate, hasChocolate);
        priceMessage+="\n"+getString(R.string.order_summary_quantity + quantity);
        priceMessage+="\n"+getString(R.string.order_summary_price,
       NumberFormat.getCurrencyInstance().format(price));
        priceMessage+="\n"+ getString(R.string.thank_you);
        return(priceMessage);

    }

}