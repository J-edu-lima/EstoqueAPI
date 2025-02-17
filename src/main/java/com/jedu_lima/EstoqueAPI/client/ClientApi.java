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
			return "Cadastrado com sucesso!";
		} else {
			return "Erro ao cadastrar: " + responseCode;
		}
	}

	public String deleteDadosDaApi(String Url, Long id) throws IOException {

		String url = Url + "/" + id;
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("DELETE");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);

		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			return "Deletado com sucesso.";
		} else {
			return "Erro ao deletar produto.";
		}
	}

	public String putDadosParaApi(String url, String json) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			try (OutputStream os = con.getOutputStream()) {
				byte[] input = json.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			} else {
				System.out.println("Erro na atualização: " + responseCode);
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}