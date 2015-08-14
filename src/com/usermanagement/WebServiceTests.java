package com.usermanagement;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class WebServiceTests  {

   private Client client;
   private String REST_SERVICE_URL = "http://localhost:8080/VodafoneTest/rest/UserService/users";
   private static final String SUCCESS_RESULT="<result>success</result>";
   private static final String FAILURE_RESULT="<result>failure</result>";
   private static final String PASS = "PASS";
   private static final String FAIL = "!!!FAIL!!!";

   private void init(){
      this.client = ClientBuilder.newClient();
   }

   public static void main(String[] args){
      
	   WebServiceTests tester = new WebServiceTests();
      
      //initialize the tester
      tester.init();
      
      //test get all users Web Service Method
      tester.testGetAllUsers();
      
      //test get user Web Service Method 
      tester.testGetUser();
      
      //test get user Web Service Method and Verify Data
      tester.testGetUser1Details();
      
      //test update user Web Service Method
      tester.testUpdateUser();
      
      //test add user Web Service Method
      tester.testAddUser();
      
      //test delete user Web Service Method
      tester.testDeleteUser();
      
      //test delete not existing user
      tester.testDeleteUserFail();
   }
   
   //Test: Get list of all users
   //Test: Check the list is containing two existing users
   private void testGetAllUsers(){
      GenericType<List<User>> list = new GenericType<List<User>>() {};
      List<User> users = client
         .target(REST_SERVICE_URL)
         .request(MediaType.APPLICATION_XML)
         .get(list);
      String result = PASS;
      
      if(users.size() == 2 && users.isEmpty())
      {
         result = FAIL;
      }
      System.out.println("Test case name: testGetAllUsers and verifying number of users already existing, Result: " + result );
   }
   
   //Test: Get User of id 1
   //Test: Check if user is same as sample user
   private void testGetUser(){
      User sampleUser = new User();
      sampleUser.setId(1);

      User user = client
         .target(REST_SERVICE_URL)
         .path("/{userid}")
         .resolveTemplate("userid", 1)
         .request(MediaType.APPLICATION_XML)
         .get(User.class);
      String result = FAIL;
      if(sampleUser != null && sampleUser.getId() == user.getId()){
         result = PASS;
      }
      System.out.println("Test case name: testGetUser, Result: " + result );
   }
       
   //Test: Update User of id 1
   //Test: Check if result is success XML.
   private void testUpdateUser(){
      Form form = new Form();
      form.param("id", "1");
      form.param("name", "Mahesh");
      form.param("address", "Christchurch");
      form.param("contactNumber","+9198855117733");

      String callResult = client
         .target(REST_SERVICE_URL)
         .request(MediaType.APPLICATION_XML)
         .post(Entity.entity(form,
            MediaType.APPLICATION_FORM_URLENCODED_TYPE),
            String.class);
      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testUpdateUser, Result: " + result );
   }
   
   //Test: Get User of id 1
   //Test: Check if user 1 details are as expected.
   private void testGetUser1Details(){
	   
	   User user = client
         .target(REST_SERVICE_URL)
         .path("/{userid}")
         .resolveTemplate("userid", 1)
         .request(MediaType.APPLICATION_XML)
         .get(User.class);
	   
	   String result = FAIL;
	   
	   //Verify user data
	   if(user.getName().equals("Mahesh") &&
			   user.getContactNumber().equals("+9198855117733") &&
			   user.getAddress().equals("Christchurch")){
         result = PASS;
      }
      System.out.println("Test case name: testGetUser and verify User Data is as expected, Result: " + result );
   }
   
   //Test: Add User of id 3
   //Test: Check if result is success XML.
   private void testAddUser(){
      Form form = new Form();
      form.param("id", "3");
      form.param("name", "Bomms");
      form.param("address", "jurasic park");
      form.param("contactNumber", "+5444432432");

      String callResult = client
         .target(REST_SERVICE_URL)
         .request(MediaType.APPLICATION_XML)
         .put(Entity.entity(form,
            MediaType.APPLICATION_FORM_URLENCODED_TYPE),
            String.class);
      
      GenericType<List<User>> list = new GenericType<List<User>>() {};
      List<User> users = client
         .target(REST_SERVICE_URL)
         .request(MediaType.APPLICATION_XML)
         .get(list);
   
      String result = PASS;
      //Verify add is successful and getallusers is giving 3 
      if(!SUCCESS_RESULT.equals(callResult) && users.size() != 3){
         result = FAIL;
      }

      System.out.println("Test case name: testAddUser, Result: " + result );
   }
   
   //Test: Delete User of id 3
   //Test: Check if result is success XML.
   private void testDeleteUser(){
      String callResult = client
         .target(REST_SERVICE_URL)
         .path("/{userid}")
         .resolveTemplate("userid", 3)
         .request(MediaType.APPLICATION_XML)
         .delete(String.class);

      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testDeleteUser, Result: " + result );
   }
   
   //Test: Delete User of id 4 
   //Test: Check for an error message.
   private void testDeleteUserFail(){
	   String callResult = client
			   .target(REST_SERVICE_URL)
			   .path("/{userid}")
			   .resolveTemplate("userid", 4)
			   .request(MediaType.APPLICATION_XML)
			   .delete(String.class);
			   
	   String result = PASS;
	   if(!FAILURE_RESULT.equals(callResult)){
		   result = FAIL;
	   }
	   
	   System.out.print("Test case name: testDeleteUserFail, Result: "+ result );
   }
}