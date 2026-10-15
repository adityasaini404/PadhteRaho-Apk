package com.example.padhteraho.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.padhteraho.MainActivity;
import com.example.padhteraho.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginFragment extends Fragment {

    private TextInputEditText edtEmail, edtPassword;
    private MaterialButton btnLogin;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    public AdminLoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_admin_login,
                container,
                false);

        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        progressBar = view.findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        // Agar admin already login hai
        if (auth.getCurrentUser() != null) {

            ((MainActivity) requireActivity())
                    .openFragment(new AdminDashboardFragment(), null);

            return view;
        }

        btnLogin.setOnClickListener(v -> loginAdmin());

        return view;
    }

    private void loginAdmin() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Enter Email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Enter Password");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {

                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                    Toast.makeText(getContext(),
                            "Login Successful",
                            Toast.LENGTH_SHORT).show();

                    ((MainActivity) requireActivity())
                            .openFragment(new AdminDashboardFragment(), null);

                })
                .addOnFailureListener(e -> {

                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                    Toast.makeText(getContext(),
                            "Invalid Email or Password",
                            Toast.LENGTH_SHORT).show();

                });

    }

}