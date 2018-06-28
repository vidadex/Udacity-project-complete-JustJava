/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText enterText = (EditText) findViewById(R.id.name);
        String enterTexto = enterText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, enterTexto);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + enterTexto);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);


    }


    /**
     * Calculates the price of the order.
     *@param addChocolate is the check if user wants chocolate and adds $2 to the original price
     * @param addWippedCream is the check if user wants wipped cream and adds $1 to the original price
     */
    private int calculatePrice(boolean addWippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWippedCream){
            basePrice = basePrice + 1;
        }
        if (addChocolate){
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * Create Summary of the order
     * @param price of the order
     * @return text summary
     * @param hasWhippedCream  is to check whether the user wants whipped cream or not
     * @param hasChocolate  is to check whether the user wants chocolate or not
     * @param enterText is for the users name inputted
     */

    private  String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String enterText){
        String priceMessage = "Name: " + enterText ;
        priceMessage =  priceMessage + "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolate;
        priceMessage = priceMessage + "\nQuantity " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + (price) + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }



    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method is called when the plus button is pressed.
     */
    public void increment(View view) {
        if (quantity == 100){
            Context context = getApplicationContext();
            CharSequence text = "You cannot have more than 100 coffees!";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is pressed.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Context context = getApplicationContext();
            CharSequence text = "You cannot have less than 1 coffee!";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

}