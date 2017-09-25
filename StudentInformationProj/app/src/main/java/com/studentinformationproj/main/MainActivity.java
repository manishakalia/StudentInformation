package com.studentinformationproj.main;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.studentinformationproj.adapter.StudentInfoAdapter;
import com.studentinformationproj.adapter.StudentListAdapter;
import com.studentinformationproj.utils.ServerConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
/*
 *This activity is doing all the functionality for storing,displaying,Updating and creating
 * and it reflected into recycleView
 */

public class MainActivity extends AppCompatActivity {


    RecyclerView studentRecyclerView;
    //String Value of student information which will set into list
    String studentID, studentName, studentRoll, studentClass, studentSubject, studentMarks;
    //student information adapter class where student information is stored
    public static StudentInfoAdapter studentInfoAdapter;
    Context studentContext;
    //Recycleview Adapter
    StudentListAdapter studentListAdapter;
    StudentInfoAdapter studentInfoAp;
    //Linkedlist of studentInfoAdapter
    private LinkedList<StudentInfoAdapter> linkedListStudentValue;
    Dialog alertDialog;
    EditText tvStudentIdValue,tvStudentNameValue,tvStudentRollValue,tvStudentClassValue,tvStudentMarksValue,tvStudentSubjectValue;
    HashMap<String,String> params;
    int UpdatePosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentContext = getApplicationContext();
        studentRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linkedListStudentValue = new LinkedList<>();
        //Setting URL in servercommunication class and getting the list of data to display in recycleview
        StudentRequestResponseURLGet();
        //Setting the recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(studentContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        studentRecyclerView.setLayoutManager(linearLayoutManager);
        //Setting onClick of item to update the list and display the update using dialogbox
        studentRecyclerView.addOnItemTouchListener(
                new StudentListAdapter(getApplicationContext(), new StudentListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        //Getting the position of the linkedlist which is passing into the recycleview
                        studentInfoAp = linkedListStudentValue.get(position);
                        //storing the position into int to use for updating at that particular position
                        UpdatePosition = position;
                        //Setting the value of all view in dialogbox
                        View addingView =  View.inflate(studentContext, R.layout.dialogbox_studentinfo, null);
                        TextView tvStudentId = (TextView) addingView.findViewById(R.id.tvDialogId);
                        tvStudentId.setTextColor(Color.BLACK);
                        tvStudentIdValue = (EditText) addingView.findViewById(R.id.etDialogIdValue);
                        studentID = linkedListStudentValue.get(position).getStID();
                        tvStudentIdValue.setText(studentID);
                        TextView tvStudentName = (TextView) addingView.findViewById(R.id.tvNameDialog);
                        tvStudentName.setTextColor(Color.BLACK);
                        tvStudentNameValue = (EditText) addingView.findViewById(R.id.etNameDialogValue);
                        studentName = linkedListStudentValue.get(position).getStName();
                        tvStudentNameValue.setText(studentName);
                        TextView tvStudentRoll = (TextView) addingView.findViewById(R.id.tvRollDialog);
                        tvStudentRoll.setTextColor(Color.BLACK);
                        tvStudentRollValue = (EditText) addingView.findViewById(R.id.etRollDialogValue);
                        studentRoll = linkedListStudentValue.get(position).getStRoll();
                        tvStudentRollValue.setText(studentRoll);
                        TextView tvStudentClass = (TextView) addingView.findViewById(R.id.tvClassDialog);
                        tvStudentClass.setTextColor(Color.BLACK);
                        tvStudentClassValue = (EditText) addingView.findViewById(R.id.etClassDialogValue);
                        studentClass = linkedListStudentValue.get(position).getStClass();
                        tvStudentClassValue.setText(studentClass);
                        TextView tvStudentSubject = (TextView) addingView.findViewById(R.id.tvSubjectDialog);
                        tvStudentSubject.setTextColor(Color.BLACK);
                        tvStudentSubjectValue = (EditText) addingView.findViewById(R.id.etSubjectDialogValue);
                        studentSubject = linkedListStudentValue.get(position).getStSubject();
                        tvStudentSubjectValue.setText(studentSubject);
                        TextView tvStudentMarks = (TextView) addingView.findViewById(R.id.tvMarksDialog);
                        tvStudentMarks.setTextColor(Color.BLACK);
                        tvStudentMarksValue = (EditText) addingView.findViewById(R.id.etMarksDialogValue);
                        studentMarks = linkedListStudentValue.get(position).getStMarks();
                        tvStudentMarksValue.setText(studentMarks);
                        Button btnCancel = (Button) addingView.findViewById(R.id.btnCancel);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.cancel();

                            }
                        });
                        Button btnUpdate = (Button) addingView.findViewById(R.id.btnAdd);
                        btnUpdate.setText("Update");
                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.cancel();
                                studentID = tvStudentIdValue.getText().toString();
                                studentName = tvStudentNameValue.getText().toString();
                                studentRoll = tvStudentRollValue.getText().toString();
                                studentClass = tvStudentClassValue.getText().toString();
                                studentSubject = tvStudentSubjectValue.getText().toString();
                                studentMarks = tvStudentMarksValue.getText().toString();
                                //Adding value into hashmap which will store into servercommunication
                                HashMap<String,String> params = new HashMap<String, String>();
                                params.put("id",studentID);
                                params.put("name",studentName);
                                params.put("roll",studentRoll);
                                params.put("class",studentClass);
                                params.put("subject",studentSubject);
                                params.put("marks",studentMarks);
                                String urlUpdateUser= "http://krshubh.webutu.com/student/update";
                                StudentRequestResponseURLUpdate(urlUpdateUser,params);
                            }
                        });
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogCustom);
                        alert.setView(addingView);
                        alertDialog = alert.create();
                        alertDialog.show();


                    }
                })
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            //Giving functionality for adding
                AddingNewStudentData();

            }

        });
    }
    //This method is for passing url and hashmap in servercommunication for updating the list
    public  void StudentRequestResponseURLUpdate(String URL, HashMap<String,String> hashMapNew){
        String AddingRequestURL = URL;//"http://krshubh.webutu.com/student/create";
        ServerConnection serverConnection = new ServerConnection(studentContext);
        serverConnection.PostServerCommunication(this, AddingRequestURL,hashMapNew,new ServerConnection.PostCommentResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(String data) {

                try {
                    StudentExtractJsonValueUpdate(data);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestEndedWithError(VolleyError error) {

            }
        });

    }
    //Storing response data into JSOn object and adding into adapter and recycleview
    public void StudentExtractJsonValueUpdate(String Response)  throws IOException, JSONException {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(Response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        /*studentID = jsonObject.getString("id");*/
        studentName = jsonObject.getString("name");
        studentRoll = jsonObject.getString("roll");
        studentClass = jsonObject.getString("class");
        studentSubject = jsonObject.getString("subject");
        studentMarks = jsonObject.getString("marks");
        studentInfoAdapter = new StudentInfoAdapter(studentName, studentRoll, studentClass, studentSubject, studentMarks);
       //removing the old position data and adding the new on same position updated data
        linkedListStudentValue.remove(UpdatePosition);
        linkedListStudentValue.add(UpdatePosition,studentInfoAdapter);
        //adding the linkedlist into adapter
        studentListAdapter = new StudentListAdapter(studentContext, linkedListStudentValue);
        studentRecyclerView.setAdapter(studentListAdapter);
    }

    // This method is for adding the new student information
    private void AddingNewStudentData() {

        View addingView =  View.inflate(studentContext, R.layout.dialogbox_studentinfo, null);
        TextView tvStudentId = (TextView) addingView.findViewById(R.id.tvDialogId);
        tvStudentId.setTextColor(Color.BLACK);
        tvStudentId.setVisibility(View.INVISIBLE);
        tvStudentIdValue = (EditText) addingView.findViewById(R.id.etDialogIdValue);
        tvStudentIdValue.setVisibility(View.INVISIBLE);
        TextView tvStudentName = (TextView) addingView.findViewById(R.id.tvNameDialog);
        tvStudentName.setTextColor(Color.BLACK);
        tvStudentNameValue = (EditText) addingView.findViewById(R.id.etNameDialogValue);

        TextView tvStudentRoll = (TextView) addingView.findViewById(R.id.tvRollDialog);
        tvStudentRoll.setTextColor(Color.BLACK);
        tvStudentRollValue = (EditText) addingView.findViewById(R.id.etRollDialogValue);

        TextView tvStudentClass = (TextView) addingView.findViewById(R.id.tvClassDialog);
        tvStudentClass.setTextColor(Color.BLACK);
        tvStudentClassValue = (EditText) addingView.findViewById(R.id.etClassDialogValue);
        TextView tvStudentSubject = (TextView) addingView.findViewById(R.id.tvSubjectDialog);
        tvStudentSubject.setTextColor(Color.BLACK);
        tvStudentSubjectValue = (EditText) addingView.findViewById(R.id.etSubjectDialogValue);

        TextView tvStudentMarks = (TextView) addingView.findViewById(R.id.tvMarksDialog);
        tvStudentMarks.setTextColor(Color.BLACK);
        tvStudentMarksValue = (EditText) addingView.findViewById(R.id.etMarksDialogValue);

        Button btnCancel = (Button) addingView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();

            }
        });
        Button btnAdd = (Button) addingView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                studentID = tvStudentIdValue.getText().toString();
                studentName = tvStudentNameValue.getText().toString();
                studentRoll = tvStudentRollValue.getText().toString();
                studentClass = tvStudentClassValue.getText().toString();
                studentSubject = tvStudentSubjectValue.getText().toString();
                studentMarks = tvStudentMarksValue.getText().toString();
                params = new HashMap<String, String>();
                // params.put("id",studentID);
                params.put("name",studentName);
                params.put("roll",studentRoll);
                params.put("class",studentClass);
                params.put("subject",studentSubject);
                params.put("marks",studentMarks);
                String urlCreateUser= "http://krshubh.webutu.com/student/create";
                StudentRequestResponseURLPost(urlCreateUser,params);
            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogCustom);
        alert.setView(addingView);
        alertDialog = alert.create();
        alertDialog.show();
    }
//This method is for post to server for create the new student information
    public  void StudentRequestResponseURLPost(String URL,HashMap<String,String> hashMapNew){
        String AddingRequestURL = URL;//"http://krshubh.webutu.com/student/create";
        ServerConnection serverConnection = new ServerConnection(studentContext);
        serverConnection.PostServerCommunication(this, AddingRequestURL,hashMapNew,new ServerConnection.PostCommentResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(String data) {

                try {
                    StudentExtractJsonValueGet(data);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestEndedWithError(VolleyError error) {

            }
        });

    }

    //Extracting the data from response and storing into the jsonobject and displaying and storing
    public void StudentExtractJsonValueGet(String Response)  throws IOException, JSONException {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(Response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        /*studentID = jsonObject.getString("id");*/
        studentName = jsonObject.getString("name");
        studentRoll = jsonObject.getString("roll");
        studentClass = jsonObject.getString("class");
        studentSubject = jsonObject.getString("subject");
        studentMarks = jsonObject.getString("marks");
        /*LinkedList<StudentInfoAdapter> linkedListTemp = new LinkedList<>();*/
       // linkedListStudentValue = studentListAdapter.getCurrentStudentList();
        studentInfoAdapter = new StudentInfoAdapter(studentName, studentRoll, studentClass, studentSubject, studentMarks);
        linkedListStudentValue.add(studentInfoAdapter);
        studentListAdapter = new StudentListAdapter(studentContext, linkedListStudentValue);
        studentRecyclerView.setAdapter(studentListAdapter);

    }

//This is for displaying the get response from the using get method
    public void StudentRequestResponseURLGet() {

        String requestURL = "http://krshubh.webutu.com/student/get?page=1";
        ServerConnection serverConnection = new ServerConnection(studentContext);
        serverConnection.GetServerCommunication(this, requestURL, new ServerConnection.GetCommentResponseListener() {
            @Override
            public void getRequestStarted() {

            }

            @Override
            public void getRequestCompleted(String data) {
                try {
                    StudentExtractJsonValue(data);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void getRequestEndedWithError(VolleyError error) {

            }

        });

    }

    //Storing the data into json object and displaying into recycleview
    public void StudentExtractJsonValue(String responsevalue) throws IOException, JSONException {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responsevalue);
            JSONArray resultsDetailsJsonArray = jsonObject.getJSONArray("results");

            System.out.println(" response json " + resultsDetailsJsonArray);

            for (int i = 0; i < resultsDetailsJsonArray.length(); i++) {
                //  studentInfoAdapter = new StudentInfoAdapter[resultsDetailsJsonArray.length()];
                JSONObject tripJsonObject = resultsDetailsJsonArray.getJSONObject(i);
                studentID = tripJsonObject.getString("id");
                studentName = tripJsonObject.getString("name");
                studentRoll = tripJsonObject.getString("roll");
                studentClass = tripJsonObject.getString("class");
                studentSubject = tripJsonObject.getString("subject");
                studentMarks = tripJsonObject.getString("marks");

                studentInfoAdapter = new StudentInfoAdapter(studentID, studentName, studentRoll, studentClass, studentSubject, studentMarks);
                linkedListStudentValue.add(studentInfoAdapter);

            }

            studentListAdapter = new StudentListAdapter(studentContext, linkedListStudentValue);
            studentRecyclerView.setAdapter(studentListAdapter);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
