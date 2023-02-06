package apiTests;

import java.util.Date;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class E2E_Tests {

    public static void main(String[] args) {
  
        String baseUrl = "https://x8ki-letl-twmt.n7.xano.io/api:gHPd8le5";

        RestAssured.baseURI = baseUrl;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        long currentTime = new Date().getTime();
       Response response = request.body("{\r\n"
       		+ "\"coin1\": \"INR\",\r\n"
       		+ "\"coin2\": \"USDT\",\r\n"
       		+ "\"coin1Amount\": 300,\r\n"
       		+ "\"coin2Amount\": 2\r\n"
       		+ "}")
    		.post("/transaction");
        Assert.assertEquals(201,response.getStatusCode());
        String responseString = response.asString();
        String sentCoin = JsonPath.from(responseString).get("sentCoin");
        Assert.assertEquals("INR", sentCoin);
        String receivedCoin=JsonPath.from(responseString).get("receivedCoin");
        Assert.assertEquals("USDT",receivedCoin );
        String sentCoinAmount=JsonPath.from(responseString).get("sentCoinAmount");
        Assert.assertEquals("300",sentCoinAmount);
        String receivedCoinAmount=JsonPath.from("responseString").get("receivedCoinAmount");
        Assert.assertEquals("2",receivedCoinAmount);
        
        String id=JsonPath.from(receivedCoinAmount).get("id");
        
        request.header("Content-Type", "application/json");
        response = request.get("/transaction/"+id);
        
         sentCoin=JsonPath.from(responseString).get("sentCoin");
        Assert.assertEquals("INR",sentCoin );
        receivedCoin=JsonPath.from(responseString).get("receivedCoin");
        Assert.assertEquals("USDT",receivedCoin );
        
        sentCoinAmount=JsonPath.from(responseString).get("sentCoinAmount");
        Assert.assertEquals("300",sentCoinAmount );
        
        receivedCoinAmount=JsonPath.from(responseString).get("receivedCoinAmount");
        Assert.assertEquals("2",receivedCoinAmount );
        
       String receivedCoinMarketPrice=JsonPath.from(responseString).get("receivedCoinMarketPrice");
        Assert.assertEquals("e",receivedCoinMarketPrice );
        
        long created_at=JsonPath.from(responseString).getLong("created_at");
        
        Assert.assertTrue(currentTime+600 < created_at );
        
        
    }
}
