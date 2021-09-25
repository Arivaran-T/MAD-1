package com.example.hairdo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hairdo.model.Holiday;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class HolidaysEdit extends AppCompatActivity {
    ImageButton selectDate;
    Button updateHolidayBtn;
    EditText selectedDate;
    EditText remark;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String monthName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays_edit);
        Holiday Oldholiday = new Holiday();
        updateHolidayBtn = findViewById(R.id.btn_editHD);
        selectDate = findViewById(R.id.editDatePicker);
        selectedDate = findViewById(R.id.editedDate);
        remark = findViewById(R.id.editedRemark);
        Intent intent=getIntent();
        Oldholiday.setFbKey(intent.getStringExtra("editKey"));
        Oldholiday.setSelected_Date(intent.getStringExtra("OldDate"));
        Oldholiday.setRemark(intent.getStringExtra("OldRemark"));

//         Oldholiday.setSelected_Date("17/01/2020");
//        Oldholiday.setRemark("dhaidjpoi");



        selectedDate.setText(Oldholiday.getSelected_Date());
        remark.setText(Oldholiday.getRemark());

        updateHolidayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FDate = selectedDate.getText().toString().trim();
                String FRemark = remark.getText().toString().trim();
                HashMap<String,Object>hashMap=new HashMap<>();
                hashMap.put("selected_Date",FDate);
                hashMap.put("remark",FRemark);




                FirebaseDatabase.getInstance().getReference(Holiday.class.getSimpleName()).child(Oldholiday.getFbKey()).updateChildren(hashMap).addOnSuccessListener(suc -> {
                    Toast.makeText(HolidaysEdit.this, "Successfully updated Holiday ", Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(HolidaysEdit.this,Holidays.class);
                    startActivity(intent1);
                }).addOnFailureListener(er -> {
                    Toast.makeText(HolidaysEdit.this, "Not Updated. Error is: " + er.getMessage(), Toast.LENGTH_SHORT).show();
                });

            }
        });
    }

    public void goBackToHolidays(View view) {
        Intent intent3 = new Intent(this, Holidays.class);
        startActivity(intent3);
    }

    public String getMonthName(int m) {
        String mName = null;
        switch (m) {
            case 1:
                mName = "January";
                break;
            case 2:
                mName = "February";
                break;
            case 3:
                mName = "March";
                break;
            case 4:
                mName = "April";
                break;
            case 5:
                mName = "May";
                break;
            case 6:
                mName = "June";
                break;
            case 7:
                mName = "July";
                break;
            case 8:
                mName = "August";
                break;
            case 9:
                mName = "September";
                break;
            case 10:
                mName = "October";
                break;
            case 11:
                mName = "November";
                break;
            case 12:
                mName = "December";
                break;
            default:
                mName = null;
        }
        return mName;
    }

    public void PickDate(View v) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(HolidaysEdit.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                monthName = getMonthName(month + 1);
//                selectedDate.setText(day + " / " + (month + 1) + " / " + year);
                selectedDate.setText(monthName + " " + day + " , " + year);


            }
        }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//        Toast.makeText(this, year+"/"+(month+1)+"/"+dayOfMonth, Toast.LENGTH_SHORT).show();
        datePickerDialog.show();
    }
}