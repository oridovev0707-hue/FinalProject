package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalproject.models.Assignment;
import com.example.finalproject.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainViewModel extends ViewModel {
    private String currUserId;
    private final MutableLiveData<Student> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Assignment>> assignmentsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Exception> exLiveData = new MutableLiveData<>();


    private ValueEventListener userListener;
    private ValueEventListener assignmentsListener;

    private FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              if(firebaseAuth.getCurrentUser() != null) {
                  currUserId = FirebaseAuth.getInstance().getUid();
                  userListener = Database.listenUser(userLiveData, exLiveData);
                  assignmentsListener = Database.listenAssignments(assignmentsLiveData);
              }
              else {
                  userLiveData.postValue(null);
                  removeUserListeners();
              }
        }
    };

    public MainViewModel() {
        FirebaseAuth.getInstance().addAuthStateListener(authListener);
    }

    public LiveData<Student> getUserLiveData() {
        return userLiveData;
    }
    public LiveData<List<Assignment>> getAssignmentsLiveData() {
        return assignmentsLiveData;
    }
    public LiveData<Exception> getExLiveData() {
        return exLiveData;
    }

    private void removeUserListeners() {
        if(userListener != null) {
            FirebaseDatabase.getInstance(Database.DB_URL)
                    .getReference("students")
                    .child(currUserId)
                    .removeEventListener(userListener);
            userListener = null;
        }
        if(assignmentsListener != null) {
            FirebaseDatabase.getInstance(Database.DB_URL)
                    .getReference("students")
                    .child(currUserId)
                    .child("assignments")
                    .removeEventListener(assignmentsListener);
            assignmentsListener = null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        removeUserListeners();
    }
}
