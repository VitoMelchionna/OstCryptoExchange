package ch.ost.aif.dialogflow.dialogflow;

import java.io.IOException;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
//import com.google.protobuf.Struct; // TODO: remove this import in case that it won't be needed

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
			case "Default Fallback Intent":
				break;
			default: // mirrors the intent string and the payload as default (if the intent isn't
						// treated specially)
				System.out.println(intent);
				System.out.println(queryResult.getFulfillmentMessagesOrBuilderList());
			}

			// TODO: remove this code example in case that it won't be needed
			/*if (queryResult.getFulfillmentMessagesCount() > 1) {// only entered if all informations are available,
																	// thus the payload sent
					Struct struct = queryResult.getFulfillmentMessagesOrBuilder(1).getPayload();// get the custom
																								// payload

			}*/
		}
	}
}
