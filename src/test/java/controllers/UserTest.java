package controllers;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.User;
import static models.Fixtures.users;

public class UserTest {

  PacemakerAPI pacemaker = new PacemakerAPI("http://localhost:7000");
  User homer = new User("homer", "simpson", "homer@simpson.com", "secret");

  @Before
  public void setup() {
    pacemaker.deleteUsers();
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testCreateUser() {
    User user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
    assertEquals(user, homer);
    User user2 = pacemaker.getUserByEmail(homer.email);
    assertEquals(user2, homer);
  }

  @Test
  public void testCreateUsers() {
    users.forEach(
        user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
    Collection<User> returnedUsers = pacemaker.getUsers();
    assertEquals(users.size(), returnedUsers.size());
  }
  
  @Test
  public void testFollowAndListUser() {
    users.forEach(
        user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
    User bartNoMates = pacemaker.getUserByEmail("bart@simpson.com");
    User lisa = pacemaker.getUserByEmail("lisa@simpson.com");
    
    pacemaker.addFriend(bartNoMates.id, lisa.email);
    
    Collection<User> friends = pacemaker.listFriends(bartNoMates.id);
    List<User> friendsList = new ArrayList<User>(friends);
    
    assertEquals(friendsList.size(), 1);  
  }
  
}