package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    String userName ;
    int numberOfCoffees = 0;
    int base_price = 5;
    EditText userNameView;
    public void submitOrder(View view){
        display(numberOfCoffees);
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream) ;
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocoBar = (CheckBox) findViewById(R.id.choco_bar) ;
        boolean hasChocoBar = chocoBar.isChecked();
        userNameView = (EditText) findViewById(R.id.input_user_name) ;
        userName = userNameView.getText().toString();
        String message = createOrderSummary(calculatePrice(hasWhippedCream,hasChocoBar), hasWhippedCream, hasChocoBar,userName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for" + userName);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

        displayMessage(message);

    }

    public void incrementCoffees(View view){

        display(numberOfCoffees += 1);
    }

    public void decrementCoffees(View view){
        if (numberOfCoffees >0)
        {
            display(numberOfCoffees -= 1);
        }
    }

    private void displayMessage(String message) {
        TextView orderTextView = (TextView) findViewById(R.id.order_text_view);
        orderTextView.setText(message);
    }

    @SuppressLint("SetTextI18n")
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int calculatePrice(boolean addWhippedCream,boolean addChocoBar){
        if(addWhippedCream && addChocoBar){
            base_price = 10;
        }
        else if (addWhippedCream){
            base_price = 7;
        }
        else if(addChocoBar){
            base_price = 8;
        }

        return (numberOfCoffees)*(base_price);
    }

    private String createOrderSummary(int price,boolean addWhipped_value,boolean addChoco_value,String userName){
        String priceMessage = "Name : " + userName + "\n";
        priceMessage       += "Quantity : " + numberOfCoffees + "\n";
        priceMessage       += "Add whipped cream : " + addWhipped_value +"\n";
        priceMessage       += "Add Choco Bar : " + addChoco_value + "\n";
        priceMessage       += "Total : $ " + price + "\n";
        priceMessage       += "\n\nThank You!";
        return priceMessage;
    }

}