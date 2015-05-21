package easytip.easytip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SummaryActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.summary_expandable_list_view);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Calculate and display the bill
       // calculateBillAmount();
    }

    /*
     *   Calculating the total bill, the tip, and the amount each person pays
     *   and displays those in the proper textViews
     */
    private void calculateBillAmount() {
        /* TODO refer each value to the now protected variables in Welcome to avoid NullPointerErrors.
        *  (The variables are : billAmountET, tipPercentET, and ratingRB) I'm working on it.
        */

        // Get the user inputs
        EditText etInput = (EditText) findViewById(R.id.welcome_bill_amount_input_field);
        final double initialBill = Double.parseDouble(String.valueOf(etInput.getText()));

        etInput = (EditText) findViewById(R.id.welcome_tip_amount_input_field);
        final double tipPercent = Double.parseDouble(String.valueOf(etInput.getText()));

        RatingBar rtInput = (RatingBar) findViewById(R.id.welcome_rating_bar);
        final double rating = Double.parseDouble(String.valueOf(rtInput.getRating()));

        etInput = (EditText) findViewById(R.id.welcome_number_persons);
        final int numPersons = Integer.parseInt(String.valueOf(etInput.getText()));
        final double OFFSET = 0.001;

        // Calculate total tip
        double totalTip = initialBill*(tipPercent);
        if (rating > OFFSET)
            totalTip = initialBill*(10 + 2*rating);

        // Calculate total bill
        double totalBill = initialBill + totalTip;

        // Calculate bill per person
        double billPerPerson = totalBill / numPersons;

        // Display everything
        TextView totalAmountTV = (TextView) findViewById(R.id.textView1);
        totalAmountTV.setText(String.valueOf(initialBill));

        TextView totalTipTV = (TextView) findViewById(R.id.textView2);
        totalTipTV.setText(String.valueOf(totalTip));

        TextView totalBillTV = (TextView) findViewById(R.id.textView3);
        totalBillTV.setText(String.valueOf(totalBill));
    }

    /*
     *   Preparing the list data
     */
    private void prepareListData(){
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // adding header
        listDataHeader.add("Bill Summary By Person");

        // adding child data
        List<String> personBills = new ArrayList<>();
        personBills.add("Person's 1 Bill");
        personBills.add("Person's 2 Bill");
        personBills.add("Person's 3 Bill");
        personBills.add("Person's 4 Bill");

        listDataChild.put(listDataHeader.get(0), personBills);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
