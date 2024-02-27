package com.cutanddry.filesync;

import com.cutanddry.common.api.model.CustomerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class CsvMapper {
    private static final Logger logger = LoggerFactory.getLogger(CsvMapper.class);

    public static List<CustomerData> mapCsvToCustomerDataFromS3(InputStream contentStream) {
        List<CustomerData> customerDataList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(contentStream))) {

            // Assuming CSV has a header
            String header = bufferedReader.readLine();
            if (header != null) {
                List<String> headers = Arrays.asList(header.split(","));

                // Process the CSV content directly
                bufferedReader.lines()
                    .forEach(line -> {
                        CustomerData customerData = mapLineToCustomerData(line.split(","), headers);
                        customerDataList.add(customerData);

                    });
            }

        } catch (IOException e) {
            logger.error("Error during processing CSV content from S3: {}", e.getMessage(), e);
        }

        return customerDataList;
    }

    private static CustomerData mapLineToCustomerData(String[] line, List<String> headers) {
        CustomerData customerData = new CustomerData();

        for (int i = 0; i < Math.min(headers.size(), line.length); i++) {
            String header = headers.get(i);
            String value = line[i];
            customerData.setVerifiedVendorId("46017666");//handle later

            // Map CSV columns to CustomerData fields dynamically based on headers
            switch (header.trim()) {
                case "CustomerCode":
                    customerData.setCustomerCode(value);
                    break;
                case "CustomerName":
                    customerData.setCustomerName(value);
                    break;
                case "CustomerPriContact":
                    customerData.setPrimaryContactName(value);
                    break;
                case "CustomerPriPhone":
                    customerData.setPrimaryContactMobile(value);
                    break;
                case "CustomerPriEmail":
                    customerData.setPrimaryContactEmail(value);
                    break;
                case "CustomerAddress1":
                    customerData.setShipToStreetAddress(value);
                    break;
                case "CustomerAddress2":
                    customerData.setShipToStreetAddress2(value);
                    break;
                case "CustomerCity":
                    customerData.setShipToCity(value);
                    break;
                case "CustomerState":
                    customerData.setShipToState(value);
                    break;
                case "CustomerZip":
                    customerData.setShipToZip(value);
                    break;
                case "BillToName":
                    customerData.setBillingContactName(value);
                    break;
                case "BillToEmail":
                    customerData.setBillingContactEmail(value);
                    break;
                case "BillToPhone":
                    customerData.setBillingContactMobile(value);
                    break;
                case "BillToAddress1":
                    customerData.setBillingStreetAddress(value);
                    break;
                case "BillToCity":
                    customerData.setBillingCity(value);
                    break;
                case "BillToState":
                    customerData.setBillingState(value);
                    break;
                case "BillToZip":
                    customerData.setBillingZip(value);
                    break;
                case "PriceLevel":
                    customerData.setPriceLevel(value);
                    break;
                case "GroupCode":
                    customerData.setCustomerGroupCode(value);
                    break;
                case "SalesRepName":
                    customerData.setSalespersonName(value);
                    break;
                case "SalesRepEmail":
                    customerData.setSalespersonEmail(value);
                    break;
                case "MinimumOrder":
                    customerData.setOrderMinimum(value);
                    break;
                case "MondayRoute":
                    customerData.setRoute(value);
                    break;
                // Add cases for other fields

                default:
                    // Handle unknown headers or ignore them
                    break;
            }
        }

        return customerData;
    }

    private static Map<String, String> getFieldMapping() {
        // Define the mapping between CSV headers and CustomerData fields
        Map<String, String> fieldMapping = new HashMap<>();
        fieldMapping.put("CompanyID", "company_id");
        fieldMapping.put("CustomerCode", "customer_code");
        fieldMapping.put("CustomerID", "customer_id");
        fieldMapping.put("ERPCustomerID", "erp_customer_id");
        fieldMapping.put("CustomerName", "customer_name");
        fieldMapping.put("PrimaryContactName", "primary_contact_name");
        fieldMapping.put("PrimaryContactMobile", "primary_contact_mobile");
        fieldMapping.put("PrimaryContactEmail", "primary_contact_email");
        fieldMapping.put("ShipToCode", "ship_to_code");
        fieldMapping.put("ShipToStreetAddress", "ship_to_street_address");
        fieldMapping.put("ShipToStreetAddress2", "ship_to_street_address2");
        fieldMapping.put("ShipToCity", "ship_to_city");
        fieldMapping.put("ShipToState", "ship_to_state");
        fieldMapping.put("ShipToZip", "ship_to_zip");
        fieldMapping.put("BillingContactName", "billing_contact_name");
        fieldMapping.put("BillingContactEmail", "billing_contact_email");
        fieldMapping.put("BillingContactMobile", "billing_contact_mobile");
        fieldMapping.put("BillingStreetAddress", "billing_street_address");
        fieldMapping.put("BillingStreetAddress2", "billing_street_address2");
        fieldMapping.put("BillingCity", "billing_city");
        fieldMapping.put("BillingState", "billing_state");
        fieldMapping.put("BillingZip", "billing_zip");
        fieldMapping.put("ActiveStatus", "active_status");
        fieldMapping.put("PriceLevel", "price_level");
        fieldMapping.put("CustomerGroupCode", "customer_group_code");
        fieldMapping.put("SalespersonName", "salesperson_name");
        fieldMapping.put("SalespersonEmail", "salesperson_email");
        fieldMapping.put("SecondarySalespersonName", "secondary_salesperson_name");
        fieldMapping.put("SecondarySalespersonEmail", "secondary_salesperson_email");
        fieldMapping.put("OrderMinimum", "order_minimum");
        fieldMapping.put("Route", "route");
        fieldMapping.put("PrimaryContact", "primary_contact");
        fieldMapping.put("BillingContact", "billing_contact");
        fieldMapping.put("UserName", "user_name");
        fieldMapping.put("UserEmail", "user_email");
        fieldMapping.put("UserMobile", "user_mobile");
        fieldMapping.put("UserRole", "user_role");
        return fieldMapping;
    }


}
