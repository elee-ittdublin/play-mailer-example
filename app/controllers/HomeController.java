// Simple email example based on https://github.com/playframework/play-mailer

package controllers;

import org.apache.commons.mail.EmailAttachment;
import play.mvc.*;
import play.data.*;
import java.util.ArrayList;
import java.util.List;

import views.html.*;

import play.api.libs.mailer.MailerClient;
import play.libs.mailer.Email;
import javax.inject.Inject;
import java.io.File;

// Import models
import models.*;

import playconfig.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

     private final MailerClient mailer;

    // Declare a private FormFactory instance
    private FormFactory formFactory;

    //  Inject an instance of FormFactory and emailer into the controller via its constructor
    @Inject
    public HomeController(MailerClient mailer, FormFactory f) {
        this.mailer = mailer;
        this.formFactory = f;
    }


    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    // The action method which displays an empty form
    public Result contact() {

        // Create a form by wrapping the Product class
        // in a FormFactory form instance
        Form<Contact> contactForm = formFactory.form(Contact.class);

		// Pass the form instance to the form view template
        return ok(contact.render(contactForm));
    }

    public Result contactSubmit() {
        

        // Create a product form object (to hold submitted data)
        // 'Bind' the object to the submitted form (this copies the filled form)
        Form<Contact> contactForm = formFactory.form(Contact.class).bindFromRequest();

        // Check for errors (based on Product class annotations)
        if(contactForm.hasErrors()) {
            // Display the form again
            return badRequest(contact.render(contactForm));
        }

        // Extract the product from the form object
        Contact c = contactForm.get();

        // Send Email
        String cid = "1234";
        Email email = new Email()
            .setSubject("Contact Form: " + c.subject)
            .setFrom(c.getFrom())
            .addTo("The Recipient TO <recipient@company.com>")
            .setBodyText(c.message);
        
        String id = mailer.send(email);

		// Open a page to confirm the message was sent
		return ok(contactSubmitted.render(c));
    }

}
