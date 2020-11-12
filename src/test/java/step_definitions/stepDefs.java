package step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.sl.In;
import org.json.JSONObject;
import org.springframework.web.util.UriBuilder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class stepDefs {
    String name;
    String state;
    String shipType;

    int quantityToAdd;
    String itemName;
    Double itemPrice;

    @Given("The user's name is {string}")
    public void getStuff(String n) {
        this.name = n;
    }

    @And("The user lives in {string}")
    public void getState(String s) {
        this.state = s;
    }

    @And("The user chooses {string} shipping")
    public void getShipping(String s) {
        if (s.contains(" "))
            s = s.replace(" ", "_");
        this.shipType = s;
    }

    @And("The user buys {int} {string} for {double}")
    public void addItems(int quantity, String itemName, Double price) {
        this.quantityToAdd = quantity;
        this.itemName = itemName;
        this.itemPrice = price;
        itemPOST();
    }

    @Then("The total cost is {double}")
    public void testCalculation(double expectedCost) {
        try {
            String URLToUse = String.format
                ("http://localhost:8080/shoppingcart/totalprice?state=%s&shipping=%s",
                    this.state, this.shipType.toUpperCase());

            URL url = new URL(URLToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();

            JSONObject results = new JSONObject(result.toString());

            Double actualPrice = results.getDouble("total");
            testCheckout();
            assertEquals(expectedCost, actualPrice);
        }
        catch (Exception e) {e.printStackTrace();};
    }

    @Then("The user checks out")
    public void testCheckout() {
        try {
            String URLToUse = String.format
                    ("http://localhost:8080/shoppingcart/checkout?customer-name=%s&state=%s&shipping=%s",
                            this.name, this.state, this.shipType.toUpperCase());

            URL url = new URL(URLToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/html");

            StringBuilder result = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();

            //Only testing if checkout worked.
            assertEquals("ok", result.toString());
        }
        catch (Exception e) {e.printStackTrace();};
    }

    public void itemPOST () {
        String jsonBodyToPost = String.format("{\"name\" : \"%s\",\"unitPrice\":%f,\"quantity\":%d}",
                this.itemName, this.itemPrice, this.quantityToAdd);
        try {
            //URL Creation
            URL url = new URL("http://localhost:8080/shoppingcart/items");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-Type", "application/json");
            conn.setDoOutput(true);

            //Send the body
            OutputStream outStream = conn.getOutputStream();
            byte[] input = jsonBodyToPost.getBytes("UTF-8");
            outStream.write(input);
            outStream.flush();
            outStream.close();

            int test = conn.getResponseCode();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
