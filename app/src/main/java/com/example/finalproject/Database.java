package com.example.finalproject;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.finalproject.models.Assignment;
import com.example.finalproject.models.Student;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static final String DB_URL = "https://finalproject-868ac-default-rtdb.europe-west1.firebasedatabase.app";
    public static ValueEventListener listenUser(MutableLiveData<Student> liveData, MutableLiveData<Exception> ex) {
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance(Database.DB_URL).getReference("students").child(userId);
        return userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                liveData.postValue(snapshot.getValue(Student.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ex.postValue(error.toException());
            }
        });
    }

    public static Task<Void> addStudent(Student student) {
        DatabaseReference ref = FirebaseDatabase.getInstance(Database.DB_URL)
                .getReference("students")
                .child(FirebaseAuth.getInstance().getUid());
        student.setId(ref.getKey());
        return ref.setValue(student);
    }

    public static Task<Assignment> addAssignment(Assignment a) {
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance(Database.DB_URL).getReference("students").child(userId);
        DatabaseReference assignRef = userRef.child("assignments").push();
        a.setId(assignRef.getKey());
        return assignRef.setValue(a)
                .continueWith(new Continuation<Void, Assignment>() {
                    @Override
                    public Assignment then(@NonNull Task<Void> task) throws Exception {
                        if(!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return a;
                    }
                });
    }

    public static Task<Assignment> updateAssignment(Assignment a) {
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance(Database.DB_URL).getReference("students").child(userId);
        DatabaseReference assignRef = userRef.child("assignments").child(a.getId());
        return assignRef.setValue(a).continueWith(result -> {
            if(!result.isSuccessful()) throw result.getException();
            return a;
        });
    }
    public static Task<Void> deleteAssignment(String assignmentId) {
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance(Database.DB_URL).getReference("students").child(userId);
        DatabaseReference assignRef = userRef.child("assignments").child(assignmentId);
        return assignRef.removeValue();
    }


    public static ValueEventListener listenAssignments(MutableLiveData<List<Assignment>> liveData) {
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance(Database.DB_URL).getReference("students").child(userId);
        DatabaseReference assignRef = userRef.child("assignments");

        return assignRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Assignment> assignments = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    assignments.add(ds.getValue(Assignment.class));
                }
                liveData.postValue(assignments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Database:listenAssignments", error.getMessage());
            }
        });
    }

}
