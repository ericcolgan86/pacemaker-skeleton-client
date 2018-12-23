package controllers;

import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.Activity;
import models.Location;
import models.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


interface PacemakerInterface
{
  @GET("/users")
  Call<List<User>> getUsers();
  
  @POST("/users")
  Call<User> registerUser(@Body User User);
  
  @GET("/users/{id}/activities")
  Call<List<Activity>> getActivities(@Path("id") String id);

  @POST("/users/{id}/activities")
  Call<Activity> addActivity(@Path("id") String id, @Body Activity activity);
}

public class PacemakerAPI {
	
PacemakerInterface pacemakerInterface;

public PacemakerAPI(String url) {
    Gson gson = new GsonBuilder().create();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson)).build();
    pacemakerInterface = retrofit.create(PacemakerInterface.class);
  }

public Collection<User> getUsers() {
    Collection<User> users = null;
    try {
      Call<List<User>> call = pacemakerInterface.getUsers();
      Response<List<User>> response = call.execute();
      users = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return users;
  }

  public void deleteUsers() {
  }

  public User createUser(String firstName, String lastName, String email, String password) {
	    User returnedUser = null;
	    try {
	      Call<User> call = pacemakerInterface.registerUser(new User(firstName, lastName, email, password));
	      Response<User> response = call.execute();
	      returnedUser = response.body();    
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return returnedUser;
  }

  public Activity createActivity(String id, String type, String location, double distance) {
	    Activity returnedActivity = null;
	    try {
	      Call<Activity> call = pacemakerInterface.addActivity(id, new Activity(type, location, distance));
	      Response<Activity> response = call.execute();
	      returnedActivity = response.body();    
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return returnedActivity;
	  }

  public Collection<Activity> getActivities(String id) {
	    Collection<Activity> activities = null;
	    try {
	      Call<List<Activity>> call = pacemakerInterface.getActivities(id);
	      Response<List<Activity>> response = call.execute();
	      activities = response.body();
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return activities;
	  }


  public List<Activity> listActivities(String userId, String sortBy) {
    return null;
  }

  public void addLocation(String id, double latitude, double longitude) {
  }

  public User getUserByEmail(String email) {
	    Collection<User> users = getUsers();
	    User foundUser = null;
	    for (User user : users) {
	      if (user.email.equals(email)) {
	        foundUser = user;
	      }
	    }
	    return foundUser;
  }

  public User getUser(String id) {
    return null;
  }

  public User deleteUser(String id) {
    return null;
  }
}