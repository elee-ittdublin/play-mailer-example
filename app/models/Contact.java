package models;

import play.data.validation.Constraints;

// Entity extends Model
public class Contact {
  
  public String firstName;
  public String lastName;
  public String telephone;
  public String email;
  public String subject;
  public String message;
  
  public Contact() {
  }

  public Contact(String first, String last, String tel, String mail, String sub, String msg) {
    this.firstName = first;
    this.lastName = last;
    this.telephone = tel;
    this.email = mail;
    this.subject = sub;
    this.message = msg;
  }

  public String getFrom()
  {
      return this.firstName + " " + this.lastName + " FROM <" + this.email + ">";
  }

}