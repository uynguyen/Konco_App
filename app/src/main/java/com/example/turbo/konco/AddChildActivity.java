package com.example.turbo.konco;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddChildActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_babyName;
    private RadioGroup rg_type;
    private RadioButton rb_fetus;
    private RadioButton rb_child;
    private Button btn_submit;
    private Button btn_reset;

    private RadioButton rb_male;
    private RadioButton rb_female;


    private DatePicker dp_lastDay;
    private TextView tv_lastDay;
    private TextView tv_birthday;

    private RelativeLayout rl_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        et_babyName = (EditText) findViewById(R.id.et_baby_name);
        rg_type = (RadioGroup) findViewById(R.id.rg_type);
        rb_fetus = (RadioButton) findViewById(R.id.rb_fetus);
        rb_child = (RadioButton) findViewById(R.id.rb_child);

        rl_gender = (RelativeLayout) findViewById(R.id.rl_gender);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);

        btn_submit= (Button) findViewById(R.id.btn_submit);
        btn_reset= (Button) findViewById(R.id.btn_reset);

        dp_lastDay =(DatePicker) findViewById(R.id.dp_lastDay);
        tv_lastDay = (TextView) findViewById(R.id.tv_lastDay);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);

        rb_fetus.setChecked(true);
        rb_child.setChecked(false);
        rb_male.setChecked(true);
        rb_female.setChecked(false);
        rl_gender.setVisibility(View.INVISIBLE);
        tv_birthday.setText("Ngày kinh cuối cùng:");

        rb_fetus.setOnClickListener(this);
        rb_child.setOnClickListener(this);
        rb_male.setOnClickListener(this);
        rb_female.setOnClickListener(this);

        dp_lastDay.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getApplicationContext(), "check chage", Toast.LENGTH_SHORT).show();
            }
        });

        dp_lastDay.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                tv_lastDay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        });

        //
        hideKeyBoard();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_fetus:
                rb_fetus.setChecked(true);
                rb_child.setChecked(false);
                tv_birthday.setText("Ngày kinh cuối cùng:");
                rl_gender.setVisibility(View.INVISIBLE);

                et_babyName.clearFocus();
                rb_fetus.requestFocus();

                break;
            case R.id.rb_child:
                rb_child.setChecked(true);
                rb_fetus.setChecked(false);
                et_babyName.clearFocus();
                rb_child.requestFocus();
                tv_birthday.setText("Ngày sinh:");
                break;

            case R.id.rb_male:
                rb_male.setChecked(true);
                rb_female.setChecked(false);
                et_babyName.clearFocus();
                rb_male.requestFocus();
                break;

            case R.id.rb_female:
                rb_female.setChecked(true);
                rb_male.setChecked(false);
                et_babyName.clearFocus();
                rb_female.requestFocus();
                break;


            case R.id.dp_lastDay:

                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                et_babyName.clearFocus();
                Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
                dp_lastDay.requestFocus();
                break;

            case R.id.btn_reset:
                et_babyName.setText("");
                tv_lastDay.setText("");
                rb_fetus.setChecked(true);
                rb_child.setChecked(false);

                Calendar ca= Calendar.getInstance();
                int year    = ca.get(Calendar.YEAR);
                int month   = ca.get(Calendar.MONTH);      // Need to subtract 1 here.
                int day     = ca.get(Calendar.DAY_OF_MONTH);
                dp_lastDay.updateDate(year, month, day);
                btn_reset.requestFocus();
                hideKeyBoard();
                break;

            case R.id.btn_submit:
                btn_submit.requestFocus();
                //TODO: VALIDATE tên bé k được trùng với tên các bé khác của mẹ
                //TODO: VALIDATE các field và push veef server
                finish();
                break;
        }
    }


    public void hideKeyBoard(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }


    boolean isOpened = false;
    public void setListenerToRootView(){
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("<<info>>", "test1");

                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.
                    Toast.makeText(getApplicationContext(), "Gotcha!!! softKeyboardup", 0).show();

                    if (isOpened == false) {
                        //Do two things, make the view top visible and the editText smaller
                    }
                    isOpened = true;
                } else if (isOpened == true) {
                    Toast.makeText(getApplicationContext(), "softkeyborad Down!!!", 0).show();
                    isOpened = false;
                }
            }
        });
    }
}
