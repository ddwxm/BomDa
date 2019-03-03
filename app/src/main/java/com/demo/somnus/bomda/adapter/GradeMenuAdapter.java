package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Grade;
import com.demo.somnus.bomda.model.bean.GradeParent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Somnus on 2018/4/9.
 * 成绩二级适配器
 */

public class GradeMenuAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<GradeParent> parent = new ArrayList<>();
    private Map<String, List<String>> child = new HashMap<>();
    private int groupLayout;
    private int childrenLayout;

    public GradeMenuAdapter(Context context,List<GradeParent> parent,Map<String, List<String>> child,int groupLayout,int childrenLayout){
        this.context = context;
        this.parent = new ArrayList<>(parent);
        this.child = new HashMap<>(child);
        this.childrenLayout = childrenLayout;
        this.groupLayout = groupLayout;
    }

    @Override
    public int getGroupCount() {
        return parent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key = parent.get(groupPosition).getName();
        int size = child.get(key).size();
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = parent.get(groupPosition).getName();
        return (child.get(key).get(childPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(context).inflate(groupLayout,parent,false);
        } else {
            view = convertView;
        }
        GradeParent gradeParent = (GradeParent) getGroup(groupPosition);
        TextView textView1 = (TextView) view.findViewById(R.id.score_parent_tv1);
        TextView textView2 = (TextView) view.findViewById(R.id.score_parent_tv2);
        textView1.setText(gradeParent.getName());
        if (Integer.parseInt(gradeParent.getGrade()) >= 60){
            textView2.setText(gradeParent.getGrade());
            textView2.setTextColor(Color.GREEN);
        } else {
            textView2.setText(gradeParent.getGrade());
            textView2.setTextColor(Color.RED);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(context).inflate(childrenLayout,parent,false);
        } else {
            view = convertView;
        }
        String key = this.parent.get(groupPosition).getName();
        String info = child.get(key).get(childPosition);
        TextView textView = (TextView) view.findViewById(R.id.second_textview);
        textView.setText(info);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
