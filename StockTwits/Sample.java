// To use the java client library include in your classpath the file 'mashapeClient.jar'
// make sure to have the json library from http://www.json.org/java/index.html
// import the generated source file
// grab your developer key( you can find it in your dashboard: http://www.mashape.com/account/index )
// and relax!
import StockTwits;

import org.json.JSONObject;

import com.mashape.client.exceptions.MashapeClientException;
import com.mashape.client.http.response.MashapeResponse;

public class Sample {

	public static void main(String[] args) {
		// basic instantiation. TODO Put your authentication keys here.
		StockTwits client = new StockTwits("MASHAPE_KEY", "OAUTH_CONSUMER_KEY", "OAUTH_SECRET", "YOUR_CALLBACK_URL");

		
		// This API is protected by OAuth. For more info: https://www.mashape.com/docs/consume/java#oauth
		
		String redirectUrl = client.getOAuthUrl("Optional Scope").getBody().getString("url"); // Redirect the user to the redirectUrl
		client.authorize("OAUTH-ACCESS-TOKEN", "OAUTH-ACCESS-SECRET"); // Parse the tokens and then authorize the client object
		

		// A sample method call. These parameters are not properly filled in.
		// See StockTwits.java to find all method names and parameters.
		MashapeResponse<JSONObject> response = client.accountUpdate("FILL IN PARAMETERS HERE", ...);

		//now you can do something with the response.
		System.out.println("API Call returned a response code of " + response.getCode());
	}
}
