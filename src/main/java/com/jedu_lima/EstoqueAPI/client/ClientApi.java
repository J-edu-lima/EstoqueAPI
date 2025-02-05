package com.jedu_lima.EstoqueAPI.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

	public String postDadosParaApi(String Url, String json) throws IOException {

		URL url = new URL(Url);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		try (OutputStream os = connection.getOutputStream()) {
			byte[] input = json.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			return "Produto cadastrado com sucesso!";
		} else {
			return "Erro ao cadastrar produto: " + responseCode;
		}
	}

	public String deletarProduto(Long id) throws IOException {
		
		String url = "http://localhost:8080/v1/produto/" + id;
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("DELETE");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);

		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			return "Produto deletado com sucesso.";
		} else {
			return "Erro ao deletar produto.";
		}
	}
}