// package com.majoapps.propertyfinderdailyscan.business.service;

// import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
// import com.majoapps.propertyfinderdailyscan.utils.KeywordExists;
// import com.majoapps.propertyfinderdailyscan.utils.PriceMethods;
// import com.majoapps.propertyfinderdailyscan.utils.StringCheck;
// import java.util.List;

// //TODO remove static class and make dynamic load from database
// public class FilterProperties implements IFilterProperties {

//     @Override
//     public List<PropertyListingDTO> filterProperties(List<PropertyListingDTO> propertyListings) {
//         if (propertyListings != null && propertyListings.size() > 0) {
//             Integer priceInt;
//             KeywordExists keywordExists = new KeywordExists();
//             String[] postcodes = new String[]{"2785", "2784", "2783", "2782", "2780", "2779", "2778", "2777",
//                 "2776", "2774", "2773"};
//             String[] keywords = new String[]{"urgent", "reduced", "divorce", "builder", "motivated"};
//             //String[] keywords = new String[]{"urgent"};
//             try {
//                 for (PropertyListingDTO propertyListing : propertyListings) { //Iterate through all the listings
//                     priceInt = PriceMethods.stringToInteger(propertyListing.price);
//                     propertyListing.priceInteger = priceInt;
//                     propertyListing.pricePerSquareMeter = priceInt/Math.round(propertyListing.area);

//                     if (propertyListing.address.equals(propertyListing.planningPortalAddress)) {
//                         // Check if property price is smaller than landValue (if not null)
//                         if ((propertyListing.landValue != null) && (priceInt.compareTo(propertyListing.landValue) < 0)) {
//                             propertyListing.selectionReason =
//                                     StringCheck.concatWhenNotNull(propertyListing.selectionReason,
//                                             "Land Value " + propertyListing.landValue, ",\n");
//                             System.out.println("Land Value " + propertyListing.landValue + " " +
//                                     propertyListing.priceInteger + " " + propertyListing.listingURL);
//                         }
//                         if ((propertyListing.zone.equals("R1")) && (propertyListing.area > 1350)) {
//                             propertyListing.selectionReason =
//                                     StringCheck.concatWhenNotNull(propertyListing.selectionReason,
//                                             "R1, >1350m", ",\n");
//                             System.out.println(propertyListing.zone + " " + propertyListing.listingURL);
//                         }
//                         if ((propertyListing.zone.equals("R3")) && (propertyListing.area > 1350)) {
//                             propertyListing.selectionReason =
//                                     StringCheck.concatWhenNotNull(propertyListing.selectionReason,
//                                             "R3, >1350m", ",\n");
//                             System.out.println(propertyListing.zone + " " + propertyListing.listingURL);
//                         }
//                         if ((propertyListing.zone.equals("R4")) && (propertyListing.area > 400)) {
//                             propertyListing.selectionReason =
//                                     StringCheck.concatWhenNotNull(propertyListing.selectionReason,
//                                             "R4, >400m", ",\n");
//                             System.out.println(propertyListing.zone + " " + propertyListing.listingURL);
//                         }
//                         if ((propertyListing.zone.contains("B")) && (propertyListing.area > 400)) {
//                             propertyListing.selectionReason =
//                                     StringCheck.concatWhenNotNull(propertyListing.selectionReason,
//                                             "B, >400m", ",\n");
//                             System.out.println(propertyListing.zone + " " + propertyListing.listingURL);
//                         }
//                         if ((keywordExists.isKeywordPresent(propertyListing.summaryDescription, keywords)) &&
//                                 (keywordExists.isKeywordPresent(propertyListing.postCode, postcodes))) {
//                             propertyListing.selectionReason =
//                                     StringCheck.concatWhenNotNull(propertyListing.selectionReason,
//                                             "Keyword found: " + keywordExists.keywordPresent(propertyListing.summaryDescription, keywords),
//                                             ",\n");
//                             System.out.println("Keyword found: " + propertyListing.listingURL);
//                         }
//                     }
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }
//         return propertyListings;
//     }
// }
