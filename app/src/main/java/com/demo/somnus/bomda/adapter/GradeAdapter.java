package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Activities;
import com.demo.somnus.bomda.model.bean.Grade;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.b.V;

/**
 * Created by Somnus on 2018/4/7.
 * 成绩Recyclerview适配器
 */

public class GradeAdapter extends RecyclerView.Adapter{
    private RecyclerView recyclerView;
    private List<Grade> grades = new ArrayList<>();
    private Context context;

    public GradeAdapter(Context context, RecyclerView rv) {
        this.context = context;
        this.recyclerView = rv;
    }

    public void setGradeList(List<Grade> grades) {
        this.grades = new ArrayList<>(grades);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_grade, parent, false);
        return new GradeItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GradeItemViewHolder) {
            Grade grade = grades.get(position);
            ((GradeItemViewHolder) holder).setGrade(grade);
        }
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    public class GradeItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_grade_course_name, item_grade_course_score;
        private TextView item_grade_time,item_grade_type,item_grade_college,item_grade_credits,item_grade_gpa;
        private TextView item_grade_examination,item_grade_reset_txt;
        private RelativeLayout item_grade_examination_re,item_grade_reset;
        private ImageView item_grade_isUnfold;
        private LinearLayout item_grade_info;
        private Grade grade;
        private boolean isUnfold = false;

        public GradeItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view) {
            item_grade_course_name = (TextView) view.findViewById(R.id.item_grade_course_name);
            item_grade_course_score = (TextView) view.findViewById(R.id.item_grade_course_score);
            item_grade_time = (TextView) view.findViewById(R.id.item_grade_time);
            item_grade_type = (TextView) view.findViewById(R.id.item_grade_type);
            item_grade_college = (TextView) view.findViewById(R.id.item_grade_college);
            item_grade_credits = (TextView) view.findViewById(R.id.item_grade_credits);
            item_grade_gpa = (TextView) view.findViewById(R.id.item_grade_gpa);
            item_grade_examination = (TextView) view.findViewById(R.id.item_grade_examination);
            item_grade_reset_txt = (TextView) view.findViewById(R.id.item_grade_reset_txt);
            item_grade_isUnfold = (ImageView) view.findViewById(R.id.item_grade_isUnfold);
            item_grade_examination_re = (RelativeLayout) view.findViewById(R.id.item_grade_examination_re);
            item_grade_reset = (RelativeLayout) view.findViewById(R.id.item_grade_reset);
            item_grade_info = (LinearLayout) view.findViewById(R.id.item_grade_info);
            item_grade_info.setVisibility(View.GONE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isUnfold){
                        isUnfold = false;
                        item_grade_info.setVisibility(View.GONE);
                        item_grade_isUnfold.setImageResource(R.drawable.ic_unfold_black_24dp);
                    } else {
                        isUnfold = true;
                        item_grade_info.setVisibility(View.VISIBLE);
                        item_grade_isUnfold.setImageResource(R.drawable.ic_packup_black_24dp);
                    }
                }
            });
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
            item_grade_course_name.setText(grade.getName());
            if (Integer.parseInt(grade.getGrade()) >= 60){
                item_grade_course_score.setText(grade.getGrade());
                item_grade_course_score.setTextColor(Color.GREEN);
            } else {
                item_grade_course_score.setText(grade.getGrade());
                item_grade_course_score.setTextColor(Color.RED);
            }
            item_grade_time.setText(grade.getYear() + "第" + grade.getSemester() + "学期");
            item_grade_type.setText(grade.getType());
            item_grade_college.setText(grade.getCollege());
            item_grade_credits.setText(grade.getCredits());
            item_grade_gpa.setText(grade.getGpa());
            if (grade.getExamination().length()!= 0){
                item_grade_examination.setText(grade.getExamination());
            } else {
                item_grade_examination_re.setVisibility(View.GONE);
            }
            if (grade.getReset().length()!= 0){
                item_grade_reset_txt.setText(grade.getGrade());
            } else {
                item_grade_reset.setVisibility(View.GONE);
            }

        }

        public Grade getGrade() {
            return grade;
        }

    }
}