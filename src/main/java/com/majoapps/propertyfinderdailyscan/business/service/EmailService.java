// package com.majoapps.propertyfinderdailyscan.business.service;

// import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
// import com.majoapps.propertyfinderdailyscan.utils.KeywordExists;
// import com.majoapps.propertyfinderdailyscan.utils.StringCheck;
// import com.sendgrid.Method;
// import com.sendgrid.Request;
// import com.sendgrid.Response;
// import com.sendgrid.SendGrid;
// import com.sendgrid.helpers.mail.Mail;
// import com.sendgrid.helpers.mail.objects.Content;
// import com.sendgrid.helpers.mail.objects.Email;
// import java.util.List;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// public class EmailService implements IEmailService {

//     @Override
//     public void sendEmailNotification(List<PropertyListingDTO> propertyListings) throws Exception {

//         //Send to Send Grid
//         if (propertyListings != null && propertyListings.size() > 0) {

//             String[] postcodes = new String[]{"2785", "2784", "2783", "2782", "2780", "2779", "2778", "2777",
//                     "2776", "2774", "2773"}; // Only email properties in Blue Mountains
//             KeywordExists keywordExists = new KeywordExists();

//             for (PropertyListingDTO propertyListing : propertyListings) {
//                 boolean blueMountains = (keywordExists.isKeywordPresent(propertyListing.postCode, postcodes));

//                 if (propertyListing.selectionReason != null && blueMountains) {
//                     String json = "Price: " + StringCheck.isNotNullOrEmpty(propertyListing.price, "") + "\n" +
//                             "Zone: " + StringCheck.isNotNullOrEmpty(propertyListing.zone, "") + "\n" +
//                             "FSR: " + propertyListing.fsr + "\n" +
//                             "PricePerSquareMeter: " + propertyListing.pricePerSquareMeter + "\n" +
//                             "PriceInteger: " + propertyListing.priceInteger + "\n" +
//                             "Area: " + propertyListing.area + "\n" +
//                             "Domain Listed Address: " + StringCheck.isNotNullOrEmpty(propertyListing.address, "") + "\n" +
//                             "Planning Portal Address: " + StringCheck.isNotNullOrEmpty(propertyListing.planningPortalAddress, "") + "\n" +
//                             "Domain URL: " + StringCheck.isNotNullOrEmpty(propertyListing.listingURL, "") + "\n" +
//                             "Land Checker URL: " + StringCheck.isNotNullOrEmpty(propertyListing.landCheckerURL, "") + "\n" +
//                             "Price Checker URL: " + StringCheck.isNotNullOrEmpty(propertyListing.priceCheckerURL, "") + "\n" +
//                             "Selection Reason: " + StringCheck.isNotNullOrEmpty(propertyListing.selectionReason, "") + "\n" +
//                             "Summary: " + StringCheck.isNotNullOrEmpty(propertyListing.summaryDescription, "");

//                     Email from = new Email("noreply@majoapps.com");
//                     String subject = "Re: Domain Trigger " + propertyListing.address;
//                     Email to = new Email("markncardamis@gmail.com");
//                     Content content = new Content("text/plain", json);
//                     Mail mail = new Mail(from, subject, to, content);

//                     SendGrid sg = new SendGrid(System.getenv().get("SENDGRID_API"));
//                     if (StringCheck.isNotNullOrEmpty(propertyListing.planningPortalPropId)) {
//                         sg.addRequestHeader("In-Reply-To",propertyListing.planningPortalPropId);
//                     }
//                     Request request = new Request();

//                     request.setMethod(Method.POST);
//                     request.setEndpoint("mail/send");
//                     request.setBody(mail.build());
//                     Response response = sg.api(request);
//                     log.debug("Email send status code {}", response.getStatusCode());
//                 }
//             }
//         }
//     }

// }
