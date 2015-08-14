package com.usermanagement;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User implements Serializable {

   private static final long serialVersionUID = 1L;
   private int id;
   private String name;
   private String address;
   private String contactNumber;
   
   public User(){}
   
   public User(int id, String name, String address, String contactNum){
      this.id = id;
      this.name = name;
      this.address = address;
      this.contactNumber = contactNum;
   }

   public int getId() {
      return id;
   }

   @XmlElement
   public void setId(int id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   @XmlElement
   public void setName(String name) {
      this.name = name;
   }
   public String getAddress() {
      return address;
   }
   @XmlElement
   public void setAddress(String address) {
      this.address = address;
   }		
   public String getContactNumber() {
	      return contactNumber;
	   }
   @XmlElement
   public void setContactNumber(String contactNum) {
      this.contactNumber = contactNum;
   }		
}
