package com.example.data;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import com.example.domain.Models.Employee;
import java.util.List;

public class NetworkApi {
    private static final String BASE_URL = "https://676201b546efb37323737500.mockapi.io/api/v1/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface EmployeeService {
        @GET("employees")
        Call<List<Employee>> getEmployees();
    }
}
