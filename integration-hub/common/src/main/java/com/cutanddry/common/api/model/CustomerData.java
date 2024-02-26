package com.cutanddry.common.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerData {
    @JsonProperty("company_id")
    private String companyId;
    @JsonProperty("verified_vendor_id")
    private String verifiedVendorId;
    @JsonProperty("customer_code")
    private String customerCode;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("erp_customer_id")
    private String erpCustomerId;
    @JsonProperty("customer_name")
    private String customerName;
    @JsonProperty("primary_contact_name")
    private String primaryContactName;
    @JsonProperty("primary_contact_mobile")
    private String primaryContactMobile;
    @JsonProperty("primary_contact_email")
    private String primaryContactEmail;
    @JsonProperty("ship_to_code")
    private String shipToCode;
    @JsonProperty("ship_to_street_address")
    private String shipToStreetAddress;
    @JsonProperty("ship_to_street_address2")
    private String shipToStreetAddress2;
    @JsonProperty("ship_to_city")
    private String shipToCity;
    @JsonProperty("ship_to_state")
    private String shipToState;
    @JsonProperty("ship_to_zip")
    private String shipToZip;
    @JsonProperty("billing_contact_name")
    private String billingContactName;
    @JsonProperty("billing_contact_email")
    private String billingContactEmail;
    @JsonProperty("billing_contact_mobile")
    private String billingContactMobile;
    @JsonProperty("billing_street_address")
    private String billingStreetAddress;
    @JsonProperty("billing_street_address2")
    private String billingStreetAddress2;
    @JsonProperty("billing_city")
    private String billingCity;
    @JsonProperty("billing_state")
    private String billingState;
    @JsonProperty("billing_zip")
    private String billingZip;
    @JsonProperty("active_status")
    private String activeStatus;
    @JsonProperty("price_level")
    private String priceLevel;
    @JsonProperty("customer_group_code")
    private String customerGroupCode;
    @JsonProperty("salesperson_name")
    private String salespersonName;
    @JsonProperty("salesperson_email")
    private String salespersonEmail;
    @JsonProperty("secondary_salesperson_name")
    private String secondarySalespersonName;
    @JsonProperty("secondary_salesperson_email")
    private String secondarySalespersonEmail;
    @JsonProperty("order_minimum")
    private String orderMinimum;
    @JsonProperty("route")
    private String route;
    @JsonProperty("primary_contact")
    private String primaryContact;
    @JsonProperty("billing_contact")
    private String billingContact;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("user_mobile")
    private String userMobile;
    @JsonProperty("user_role")
    private String userRole;
}
