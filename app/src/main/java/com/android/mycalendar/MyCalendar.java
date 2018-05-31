package com.android.mycalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by luotao
 * 2018/5/31
 * emil:luotaosc@foxmail.com
 * qq:751423471
 *
 * @author 罗涛
 */
public class MyCalendar extends LinearLayout implements View.OnClickListener {

    private LayoutInflater mInflater;
    //上一个月、下一个月
    private AppCompatImageButton mPreMonth, mNextMonth;
    /**
     * 顶部年月
     */
    private AppCompatTextView mDate;
    /**
     * 日历列表
     */
    private GridView mGridView;


    private Calendar mCalendar;

    public MyCalendar(Context context) {
        super(context);
        init();
    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initView();
        initListener();
        initCalenderCell();
    }

    private void initListener() {
        mNextMonth.setOnClickListener(this);
        mPreMonth.setOnClickListener(this);
    }

    private void initView() {
        mInflater = LayoutInflater.from(getContext());
        mInflater.inflate(R.layout.calender_view, this, true);
        mPreMonth = findViewById(R.id.preMonth);
        mNextMonth = findViewById(R.id.nextMonth);
        mDate = findViewById(R.id.tv_date);
        mGridView = findViewById(R.id.gv_calendar);

        mCalendar = Calendar.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //下月
            case R.id.nextMonth:
                //月份+1
                mCalendar.add(Calendar.MONTH, 1);
                initCalenderCell();
                break;
            //上月
            case R.id.preMonth:
                //月份-1
                mCalendar.add(Calendar.MONTH, -1);
                initCalenderCell();
                break;
            default:
                break;
        }
    }

    /**
     * 计算列表Cell的值
     */
    private void initCalenderCell() {
        //设置顶部年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
        mDate.setText(sdf.format(mCalendar.getTime()));


        /**
         * 1.计算我们的cell的个数   7*6=42 任何一个月的天数都能包含在里面
         *
         * 2.计算这个当前月1号所在星期几，计算上个月显示的天数
         *
         * 3.将cell里面添加值
         */
        //日历每天的cell
        ArrayList<Date> cells = new ArrayList<>();
        //总的天数
        int count = 7 * 6;
        //克隆下calender
        Calendar calendar = (Calendar) mCalendar.clone();
        //将calender置于当月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //计算当月1号之前还有几天
        int preDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        //将当前日期向前移动preDays
        calendar.add(Calendar.DAY_OF_MONTH, -preDays);
        //填满42个cells
        while (cells.size() < count) {
            //填充cell
            cells.add(calendar.getTime());
            //填充一次之后，向后移动一天
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        mGridView.setAdapter(new CalenderAdapter(getContext(), cells));
    }


    private class CalenderAdapter extends ArrayAdapter<Date> {
        private LayoutInflater mInflater;

        CalenderAdapter(@NonNull Context context, ArrayList<Date> dates) {
            super(context, R.layout.cell_layout, dates);
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Date date = getItem(position);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.cell_layout, parent, false);
            }
            if (date != null) {
                int day = date.getDate();
                ((TextView) convertView).setText(String.valueOf(day));
                convertView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), new SimpleDateFormat("yyyy年MM月dd", Locale.getDefault()
                        ).format(date), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //获取当月日期
            Date now = new Date();

            boolean isSameMonth = false;
            //判断是否是本月
            if (date.getMonth() == now.getMonth()) {
                isSameMonth = true;
            }

            if (isSameMonth) {
                ((MyTextView) convertView).setTextColor(Color.BLACK);
            } else {
                ((MyTextView) convertView).setTextColor(Color.GRAY);
            }
            //判断是否是今日
            if (date.getDate() == now.getDate() && date.getMonth() == now.getMonth() && date.getYear() == now.getYear()) {
                ((MyTextView) convertView).isToday = true;
                ((MyTextView) convertView).setTextColor(Color.RED);
            }
            return convertView;
        }
    }
}
