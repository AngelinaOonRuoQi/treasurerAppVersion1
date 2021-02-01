package com.example.treasurerappversion1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<StudentName> mStudentList;


    public static class StudentViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public CardView cardView;
        RelativeLayout rlBackground;
        TextView student_paid;
        //Method 1 - Toggle Method, only two condition
//        Boolean isPaid = false;

        //Method 2 - Lets say we have three condition to switch
//        String number = "0";


        public StudentViewHolder(final View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.student_name);
            cardView=itemView.findViewById(R.id.student_card);
            rlBackground = itemView.findViewById(R.id.card_background);
            student_paid = itemView.findViewById(R.id.student_paid);


            itemView.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {


                //Method 1 - Using Toggle Method:
                    // --------------------------------------------------------------
//                    if(!isPaid){
//                        student_paid.setText("paid");
//                        student_paid.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorGreen));
                    //Another way to set Color
////                        student_paid.setTextColor(Color.parseColor("#008000"));
//                    } else {
//                        student_paid.setText("unpaid");
//                        student_paid.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorBlack));
                    //Another way to set Color
////                        student_paid.setTextColor(Color.parseColor("#000000"));
//                    }
//                    isPaid = !isPaid;
                    // --------------------------------------------------------------

                    //End of Method 1


                    //Method 2
                    //---------------------------------------------------------------------
//                    if(number.equals("0")){
//                        student_paid.setText("1");
//                        number = "1";
//                    } else if(number.equals("1")){
//                        student_paid.setText("2");
//                        number = "2";
//                    } else if(number.equals("2")){
//                        student_paid.setText("0");
//                        number = "0";
//                    }
                    //---------------------------------------------------------------------
                    //End of Method 2


                }

            });
        }
    }

    public StudentAdapter(ArrayList<StudentName> studentList){
        mStudentList=studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_cart_items_layout,parent,false);
        StudentViewHolder studentViewHolder=new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentName current=mStudentList.get(position);
        holder.name.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }
}
