package com.example.finalproject;

import com.example.finalproject.models.Student;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    public static Task<Void> addStudent(Student student) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students").push();
        student.setId(ref.getKey());
        return ref.setValue(student);
    }
}
