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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface PacemakerInterface {
  @GET("/users")
  Call<List<User>> getUsers();
  
  @POST("/users")
  Call<User> registerUser(@Body User User);

  @GET("/users/{id}/activities")
  Call<List<Activity>> getActivities(@Path("id") String id);

  @POST("/users/{id}/activities")
  Call<Activity> addActivity(@Path("id") String id, @Body Activity activity);
  
  @GET("/users/{id}/activities/{activityid}")
  Call<Activity> getActivity(@Path("id") String id, @Path("activityid") String activityId);  
  
  @POST("/users/{id}/activities/{activityid}/locations")
  Call<Location> addLocation(@Path("id") String id, @Path("activityid") String activityId, @Body Location location);
  
  @DELETE("/users")
  Call<User> deleteUsers();

  @DELETE("/users/{id}")
  Call<User> deleteUser(@Path("id") String id);

  @GET("/users/{id}")
  Call<User> getUser(@Path("id") String id);
  
  @DELETE("/users/{id}/activities")
  Call<String> deleteActivities(@Path("id") String id);
  
  @POST("/users/{id}/friends/{friendemail}")
  Call<User> addFriend(@Path("id") String id, @Path("friendemail") String friendEmail);
  
  @GET("/users/{id}/friends/getfriends")
  Call<List<User>> getFriends(@Path("id") String id);  
  
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
  
  public void deleteActivities(String id) {
      try {
        Call<String> call = pacemakerInterface.deleteActivities(id);
        call.execute();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
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

  public Activity getActivity(String userId, String activityId) {
	   Activity activity = null;
	    try {
	      Call<Activity> call = pacemakerInterface.getActivity(userId, activityId);
	      Response<Activity> response = call.execute();
	      activity = response.body();
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return activity;
	  }
  
  public User addFriend(String userId, String friendEmail) {
	    User user = null;
	    try {
	      Call<User> call = pacemakerInterface.addFriend(userId, friendEmail);
	      Response<User> response = call.execute();
	      user = response.body();
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return user;
	  }
  
  public Collection<User> listFriends(String userId) {
	    Collection<User> users = null;
	    try {
	      Call<List<User>> call = pacemakerInterface.getFriends(userId);
	      Response<List<User>> response = call.execute();
	      users = response.body();
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return users;
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

  public void addLocation(String id, String activityId, double latitude, double longitude) {
	    try {
	      Call<Location> call = pacemakerInterface.addLocation(id, activityId, new Location(latitude, longitude));
	      call.execute();
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
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
	    User user = null;
	    try {
	      Call<User> call = pacemakerInterface.getUser(id);
	      Response<User> response = call.execute();
	      user = response.body();
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return user;
	  }

	  public void deleteUsers() {
	    try {
	      Call<User> call = pacemakerInterface.deleteUsers();
	      call.execute();
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	  }

	  public User deleteUser(String id) {
	    User user = null;
	    try {
	      Call<User> call = pacemakerInterface.deleteUser(id);
	      Response<User> response = call.execute();
	      user = response.body();
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return user;
	  }
  
  
}
