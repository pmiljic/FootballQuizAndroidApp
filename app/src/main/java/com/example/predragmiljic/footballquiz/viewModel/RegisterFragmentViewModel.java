package com.example.predragmiljic.footballquiz.viewModel;

import android.text.TextUtils;

import com.example.predragmiljic.footballquiz.model.Candidate;
import com.example.predragmiljic.footballquiz.model.QuestionAnswers;
import com.example.predragmiljic.footballquiz.remote.APIService;
import com.example.predragmiljic.footballquiz.remote.APIUtils;
import com.example.predragmiljic.footballquiz.remote.Result;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragmentViewModel extends ViewModel {
    private APIService apiService = APIUtils.getAPIService();
    private Candidate candidate;
    private List<QuestionAnswers> questionAnswers;
    private boolean isValidInput;

    public void postCandidate(Candidate c, Result<Candidate> result) {
        apiService.postCandidate(c).enqueue(new Callback<Candidate>() {
            @Override
            public void onResponse(@NonNull Call<Candidate> call, @NonNull Response<Candidate> response) {
                if (response.isSuccessful()) {
                    candidate = response.body();
                    result.onSuccess(response.body());
                } else {
                    result.onFailure("User with this email already played fquiz.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Candidate> call, @NonNull Throwable t) {
                result.onFailure("Failed to connect to web server");
            }
        });
    }

    public void getQuestionAnswers(Result<List<QuestionAnswers>> result) {

        if (questionAnswers == null) {
            apiService.getQuestions().enqueue(new Callback<List<QuestionAnswers>>() {
                @Override
                public void onResponse(@NonNull Call<List<QuestionAnswers>> call, @NonNull Response<List<QuestionAnswers>> response) {
                    if (response.isSuccessful()) {
                        questionAnswers = response.body();
                        result.onSuccess(response.body());
                    } else {
                        result.onFailure("Failed to connect to web server");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<QuestionAnswers>> call, @NonNull Throwable t) {
                    result.onFailure("Failed to connect to web server");
                }
            });
        }
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public boolean isValidInput() {
        return isValidInput;
    }

    private boolean isEmptyInput(String input) {
        return !TextUtils.isEmpty(input);
    }

    public String setError(String etName, String input) {
        String error = null;
        if (!isEmptyInput(input)) {
            if (etName.equalsIgnoreCase("name")) {
                error = "Name is required!";
                isValidInput = false;
            } else if (etName.equalsIgnoreCase("last_name")) {
                error = "Last name is required!";
                isValidInput = false;
            } else if (etName.equalsIgnoreCase("email")) {
                error = "Email is required!";
                isValidInput = false;
            }
        } else {
            isValidInput = true;
            if (etName.equalsIgnoreCase("email")) {
                if (!isEmailValid(input)) {
                    error = "Email is not valid!";
                    isValidInput = false;
                }
            }
        }
        return error;
    }

    //Checks email structure
    private boolean isEmailValid(String email) {
        String expression = "^[\\w-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}


