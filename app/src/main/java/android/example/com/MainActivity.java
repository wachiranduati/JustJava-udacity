/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
package android.example.com;

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
    int quantity = 2;
    boolean whippedCrm;
    boolean chocolate;
    String customer;
    int whippedCreamPrice = 1;
    int chococalteTPrice = 2;
    int plainCoffecupPrice = 5;
    int baseTotalPerCup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice() {
        if(whippedCrm == true && chocolate == true){
            // add whippedcream & chocholate to the total base of the cup price
            baseTotalPerCup = plainCoffecupPrice + whippedCreamPrice + chococalteTPrice;
        }else if(chocolate == true){
            // add chocolate to the total base of the cup price
            baseTotalPerCup = plainCoffecupPrice + chococalteTPrice;
        }else if(whippedCrm == true ){
            // only whipped cream was added
            baseTotalPerCup = plainCoffecupPrice + whippedCreamPrice;
        }else{
            // nothing was added to teh cup
            baseTotalPerCup =  plainCoffecupPrice;
        }

        int price = quantity * baseTotalPerCup;
        return price;
    }

    public void composeEmail(String addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void createOrderSummary(){
        int price = calculatePrice();
        EditText customerText = findViewById(R.id.customername);
        customer = customerText.getText().toString();

        String message = "" + getString(R.string.order_summary_name, customer) + "\n";
        message = message + "" + getString(R.string.order_summary_quantity, quantity) + "\n";
        message += ""+ getString(R.string.order_summary_whipped_cream, whippedCrm) + "\n";
        message += ""+ getString(R.string.order_summary_chocolate, chocolate) + "\n";
        message = message + ""+getString(R.string.order_summary_price) + price + "\n";
        message = message + ""+getString(R.string.thank_you);
//        return message;

        composeEmail("annekabera@gmail.com", "concerning the food at the party", message);
    }



    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        String priceMessage = createOrderSummary();
//        displayMessage(priceMessage);
        createOrderSummary();

    }

    /** This method will increase the order by one - to 3**/
    public void increment(View view){
        quantity = quantity + 1;
        if(quantity > 100){
            Toast.makeText(this, "You cannot order more than 100 cups", Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }

    /**This method will decrease the order by one -  to 1**/
    public void decrement(View view){
        quantity =  quantity - 1;
        if(quantity < 1){
            Toast.makeText(this, "Miminum number of cups you can order is 1", Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }

    /*
    * This will get the state of teh checkbox click event
    * */
    public void whippedCreamchexbox(View view){
        CheckBox whipedCrmcheckbox = findViewById(R.id.toppingsCheckbox);
        whippedCrm = whipedCrmcheckbox.isChecked();

    }

    public void chocolateaction(View view){
        CheckBox chocolateCheckbox = findViewById(R.id.chocolateCheckbox);
        chocolate = chocolateCheckbox.isChecked();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}
