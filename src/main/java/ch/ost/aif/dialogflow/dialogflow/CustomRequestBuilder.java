package ch.ost.aif.dialogflow.dialogflow;

import java.io.IOException;
import java.util.Map;

import ch.ost.aif.dialogflow.coingecko.CoinGeckoApi;
import ch.ost.aif.dialogflow.main.ApiIntegrator;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;

public class CustomRequestBuilder {

	// same as template
	public static void detectIntentTexts(String projectId, String text, String sessionId, String languageCode) throws IOException, ApiException {
		// Instantiates a client
		try (SessionsClient sessionsClient = SessionsClient.create()) {
			// Set the session name using the sessionId (UUID) and projectID (my-project-id)
			SessionName session = SessionName.of(projectId, sessionId);
			//System.out.println("Session Path: " + session.toString());

			// Detect intents for each text input

			// Set the text (hello) and language code (en-US) for the query
			TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

			// Build the query with the TextInput
			QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

			// Performs the detect intent request
			DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

			// Display the query result
			QueryResult queryResult = response.getQueryResult();

			// own code
			// get the intent as a String
			String intent = queryResult.getIntent().getDisplayName();

			// print the text answer
			System.out.println(queryResult.getFulfillmentText());
			// switch-case to treat different intents differently
			switch (intent) {
			case "Default Welcome Intent": // just checking that it is the welcome intent
				break;
			case "Goodbye":
				break;
			case "accountBalanceCheck":
				break;
			case "accountBalanceCheck_contextAccount":
				break;
			case "accountBalanceCheck_contextBalance":
				break;
			case "retrieveCryptos":
				break;
			case "accountTradeCrypto":
				if (queryResult.getAllRequiredParamsPresent()) {// only entered if all informations are available,

					String coinName = ApiIntegrator.getCoinNameFromQuery(queryResult);
					CoinGeckoApi coinGeckoAPI = new CoinGeckoApi();
					Map result = coinGeckoAPI.getCoinPrice(ApiIntegrator.convertCoinNameToApiParameter(coinName), ApiIntegrator.currency);

					System.out.println(ApiIntegrator.castResultIntoConsoleOutput(result));
				}
				break;
			case "account.trade.crypto - context: trading - date - yes":
				System.out.println(java.util.UUID.randomUUID());
				break;
			case "Default Fallback Intent":
				break;
			default:
				System.out.println(intent);
			}
		}
	}
}
