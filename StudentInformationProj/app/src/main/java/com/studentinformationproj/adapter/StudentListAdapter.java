package com.studentinformationproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studentinformationproj.main.R;

import java.util.LinkedList;

/**
 * Created by Srisht on 13-09-2017.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolderAdapter>
        implements View.OnClickListener, RecyclerView.OnItemTouchListener{

    TextView tvStudentId,tvStudentIdValue,tvStudentName,tvStudentNameValue,tvStudentRoll,tvStudentRollValue,
             tvStudentClass,tvStudentClassValue,tvStudentSubject,tvStudentSubjectValue,tvStudentMarks,tvStudentMarksValue;
    LinkedList<StudentInfoAdapter> linkedList = new LinkedList<StudentInfoAdapter>();
    Context contextAdapter;

    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    public StudentListAdapter(Context context, LinkedList<StudentInfoAdapter> linkedListNew){
        this.linkedList = linkedListNew;
        this.contextAdapter = context;
    }

    public StudentListAdapter(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public ViewHolderAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCartRecycler = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_recycleviewdata, parent, false);
        ViewHolderAdapter viewHolerAdapter = new ViewHolderAdapter(viewCartRecycler);
        return viewHolerAdapter;
    }

    @Override
    public void onBindViewHolder(ViewHolderAdapter holder, final int position) {

        tvStudentIdValue.setText(linkedList.get(position).getStID());
        tvStudentNameValue.setText(linkedList.get(position).getStName());
        tvStudentRollValue.setText(linkedList.get(position).getStRoll());
        tvStudentClassValue.setText(linkedList.get(position).getStClass());
        tvStudentSubjectValue.setText(linkedList.get(position).getStSubject());
        tvStudentMarksValue.setText(linkedList.get(position).getStMarks());

    }

    @Override
    public int getItemCount() {
        return linkedList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onClick(View v) {
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class ViewHolderAdapter extends RecyclerView.ViewHolder{
        public ViewHolderAdapter(View itemView) {
            super(itemView);
            tvStudentId = (TextView) itemView.findViewById(R.id.tvId);
            tvStudentIdValue = (TextView) itemView.findViewById(R.id.tvIdValue);
            tvStudentName = (TextView) itemView.findViewById(R.id.tvName);
            tvStudentNameValue = (TextView) itemView.findViewById(R.id.tvNameValue);
            tvStudentRoll = (TextView) itemView.findViewById(R.id.tvRoll);
            tvStudentRollValue = (TextView) itemView.findViewById(R.id.tvRollValue);
            tvStudentClass = (TextView) itemView.findViewById(R.id.tvClass);
            tvStudentClassValue = (TextView) itemView.findViewById(R.id.tvClassValue);
            tvStudentSubject = (TextView) itemView.findViewById(R.id.tvSubject);
            tvStudentSubjectValue = (TextView) itemView.findViewById(R.id.tvSubjectValue);
            tvStudentMarks = (TextView) itemView.findViewById(R.id.tvMarks);
            tvStudentMarksValue = (TextView) itemView.findViewById(R.id.tvMarksValue);
        }
    }

    public LinkedList<StudentInfoAdapter> getCurrentStudentList(){
        return linkedList;
    }
}
