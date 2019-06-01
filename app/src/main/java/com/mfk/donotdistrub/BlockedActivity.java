package com.mfk.donotdistrub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BlockedActivity extends AppCompatActivity {

    ListView contactsChooser;
    Button btnDone;
    EditText txtFilter;
    TextView txtLoadInfo;
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked);
        contactsChooser = (ListView) findViewById(R.id.lst_contacts_chooser);
        btnDone = (Button) findViewById(R.id.btn_done);
        txtFilter = (EditText) findViewById(R.id.txt_filter);
        txtLoadInfo = (TextView) findViewById(R.id.txt_load_progress);


        contactsListAdapter = new ContactsListAdapter(this,new ContactsList());

        contactsChooser.setAdapter(contactsListAdapter);


        loadContacts("");
         db=new DatabaseHelper(this);


        txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsListAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contactsListAdapter.selectedContactsList.contactArrayList.isEmpty()){
                    setResult(RESULT_CANCELED);
                    Toast.makeText(BlockedActivity.this,"here",Toast.LENGTH_LONG);
                }
                else{
                                 addDATA();
                   /// Intent resultIntent = new Intent();


                    //resultIntent.putParcelableArrayListExtra("SelectedContacts", contactsListAdapter.selectedContactsList.contactArrayList);
                    //setResult(RESULT_OK,resultIntent);

                }
               // finish();

            }
        });
    }



    private void loadContacts(String filter){

        if(contactsLoader!=null && contactsLoader.getStatus()!= AsyncTask.Status.FINISHED){
            try{
                contactsLoader.cancel(true);
            }catch (Exception e){

            }
        }
        if(filter==null) filter="";

        try{
            //Running AsyncLoader with adapter and  filter
            contactsLoader = new ContactsLoader(this,contactsListAdapter);
            contactsLoader.txtProgress = txtLoadInfo;
            contactsLoader.execute(filter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

public void addDATA()
{
    ArrayList<Contact>c=contactsListAdapter.selectedContactsList.contactArrayList;
    Toast.makeText(BlockedActivity.this,"here2",Toast.LENGTH_LONG);
    boolean insertdata;
    for(int i=0;i<c.size();i++)
    {
        insertdata = db.addData(c.get(i).name.toString(),c.get(i).phone.toString(),"",0,-1,-1,-1,-1);
        MainActivity.blockList=db.getAll();
        if(insertdata==true)
        {
            Toast.makeText(BlockedActivity.this,"Successfull",Toast.LENGTH_LONG);
        }
        else
        {
            Toast.makeText(BlockedActivity.this,"UnSuccessfull",Toast.LENGTH_LONG);
        }
    }
}


}
