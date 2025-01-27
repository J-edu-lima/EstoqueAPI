package com.jedu_lima.EstoqueAPI.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientApi {

	public String getDadosDaApi(String Url) throws IOException {

		URL url = new URL(Url);
		String line;

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);

		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuilder response = new StringBuilder();

		while ((line = bufferedReader.readLine()) != null) {
			response.append(line);
		}

		bufferedReader.close();
		return response.toString();
	}
}
