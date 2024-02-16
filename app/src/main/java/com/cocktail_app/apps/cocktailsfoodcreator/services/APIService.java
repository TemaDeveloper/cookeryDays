package com.cocktail_app.apps.cocktailsfoodcreator.services;


import com.cocktail_app.apps.cocktailsfoodcreator.models.ResponseData;
import com.cocktail_app.apps.cocktailsfoodcreator.models.ResponseDataMeals;
import com.cocktail_app.apps.cocktailsfoodcreator.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("filter.php?a=Alcoholic")
    Call<ResponseData> getAlcoCocktails();

    @POST("filter.php?a=Non_Alcoholic")
    Call<ResponseData> getNonAlcoCocktails();

    @POST("list.php?c=list")
    Call<ResponseDataMeals> getCategories();

    @FormUrlEncoded
    @POST("auth/signup.php")
    Call<ResponseBody> addUser(@Field("name") String name,
                               @Field("email") String email,
                               @Field("profileImg") String profileImg,
                               @Field("password") String password);


    @FormUrlEncoded
    @POST("auth/login.php")
    Call<ResponseBody> loginUser(@Field("email") String email,
                                 @Field("password") String password);


    @FormUrlEncoded
    @POST("auth/getUser.php")
    Call<User> getUser(@Field("email") String email);

    @FormUrlEncoded
    @POST("auth/userUpdate.php")
    Call<ResponseBody> setUser(@Field("id") int id,
                               @Field("name") String name,
                               @Field("email") String email,
                               @Field("image") String image);


}
