 package com.majoapps.propertyfinderdailyscan.business.service;

 import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
 import com.majoapps.propertyfinderdailyscan.utils.StringCheck;
 import com.sendgrid.Method;
 import com.sendgrid.Request;
 import com.sendgrid.Response;
 import com.sendgrid.SendGrid;
 import com.sendgrid.helpers.mail.Mail;
 import com.sendgrid.helpers.mail.objects.Content;
 import com.sendgrid.helpers.mail.objects.Email;
 import java.util.List;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.stereotype.Service;

 @Slf4j
 @Service
 public class EmailService implements IEmailService {

     @Override
     public void sendEmailNotification(List<PropertyListingDTO> propertyListings, String email) throws Exception {

         //Send to Send Grid
         if (propertyListings != null && propertyListings.size() > 0) {

             for (PropertyListingDTO propertyListing : propertyListings) {
                 String json = "Price: " + StringCheck.isNotNullOrEmpty(propertyListing.price, "") + "\n" +
                     "Zone: " + StringCheck.isNotNullOrEmpty(propertyListing.zone, "") + "\n" +
                     "FSR: " + propertyListing.floorSpaceRatio + "\n" +
                     "PricePerSquareMeter: " + propertyListing.pricePSM + "\n" +
                     "PriceInteger: " + propertyListing.priceInt + "\n" +
                     "Area: " + propertyListing.area + "\n" +
                     "Address: " + StringCheck.isNotNullOrEmpty(propertyListing.address, "") + "\n" +
                     "Domain URL: " + StringCheck.isNotNullOrEmpty(propertyListing.listingURL, "") + "\n" +
                     "Summary: " + StringCheck.isNotNullOrEmpty(propertyListing.summaryDescription, "");

                 Email from = new Email("noreply@majoapps.com");
                 String subject = "Re: Domain Trigger " + propertyListing.address;
                 Email to = new Email(StringCheck.isNotNullOrEmpty(email) ? email:"markncardamis@gmail.com");
                 Content content = new Content("text/plain", json);
                 Mail mail = new Mail(from, subject, to, content);

                 SendGrid sg = new SendGrid(System.getenv().get("SENDGRID_API"));
                 if (StringCheck.isNotNullOrEmpty(propertyListing.propertyId.toString())) {
                     sg.addRequestHeader("In-Reply-To", propertyListing.propertyId.toString());
                 }
                 Request request = new Request();

                 request.setMethod(Method.POST);
                 request.setEndpoint("mail/send");
                 request.setBody(mail.build());
                 Response response = sg.api(request);
                 log.debug("Email send status code {}", response.getStatusCode());
             }
         }
     }

 }
