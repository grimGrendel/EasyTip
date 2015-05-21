package easytip.easytip;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

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
