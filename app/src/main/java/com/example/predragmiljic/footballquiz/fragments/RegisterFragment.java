package com.example.predragmiljic.footballquiz.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.predragmiljic.footballquiz.model.Candidate;
import com.example.predragmiljic.footballquiz.R;
import com.example.predragmiljic.footballquiz.model.QuestionAnswers;
import com.example.predragmiljic.footballquiz.remote.Result;
import com.example.predragmiljic.footballquiz.viewModel.RegisterFragmentViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dmax.dialog.SpotsDialog;

public class RegisterFragment extends Fragment {

    private TextInputLayout layoutName, layoutLastName, layoutEmail;
    private TextInputEditText editTextName, editTextLastName, editTextEmail;
    private Button buttonStart;
    private RegisterFragmentViewModel viewModel;
    private AlertDialog startingQuizDialog;

    public RegisterFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        layoutName = v.findViewById(R.id.LayoutName);
        layoutLastName = v.findViewById(R.id.layoutLastName);
        layoutEmail = v.findViewById(R.id.layoutEmail);

        editTextName = v.findViewById(R.id.editTextName);
        editTextLastName = v.findViewById(R.id.editTextLastName);
        editTextEmail = v.findViewById(R.id.editTextEmail);

        buttonStart = v.findViewById(R.id.buttonStart);

        startingQuizDialog = new SpotsDialog(getActivity(), R.style.loadingDialog);

        buttonStart.setOnClickListener(v1 -> {

            //Clears all error messages
            layoutName.setError(null);
            layoutLastName.setError(null);
            layoutEmail.setError(null);

            layoutName.setError(viewModel.setError("name",Objects.requireNonNull(editTextName.getText()).toString()));
            layoutLastName.setError(viewModel.setError("last_name",Objects.requireNonNull(editTextLastName.getText()).toString()));
            layoutEmail.setError(viewModel.setError("email",Objects.requireNonNull(editTextEmail.getText()).toString()));

            //if all fields are valid, get 5 questions and save candidate to DB and switch to question fragment
            if (viewModel.isValidInput()) {
                buttonStart.setEnabled(false);
                startingQuizDialog.show();

                viewModel.getQuestionAnswers(new Result<List<QuestionAnswers>>() {
                    @Override
                    public void onSuccess(List<QuestionAnswers> resultQuestions) {
                        Candidate candidate = new Candidate(editTextName.getText().toString(), editTextLastName.getText().toString(), editTextEmail.getText().toString());
                        viewModel.postCandidate(candidate, new Result<Candidate>() {
                            @Override
                            public void onSuccess(Candidate result) {
                                startingQuizDialog.dismiss();
                                QuestionFragment questionFragment = new QuestionFragment();
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("questions", (ArrayList<? extends Parcelable>) resultQuestions);
                                bundle.putParcelable("candidate", viewModel.getCandidate());
                                questionFragment.setArguments(bundle);
                                FragmentManager manager = getFragmentManager();
                                if (manager != null) {
                                    manager.beginTransaction().replace(R.id.mainLayout, questionFragment, questionFragment.getTag()).commit();
                                }
                            }

                            @Override
                            public void onFailure(String error) {
                                startingQuizDialog.dismiss();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                buttonStart.setEnabled(true);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                        startingQuizDialog.dismiss();
                        buttonStart.setEnabled(true);
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(RegisterFragmentViewModel.class);
    }
}
