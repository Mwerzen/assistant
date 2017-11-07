package com.mikewerzen.automation.assistant.client;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class Client
{
	public static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) throws Exception
	{
		if (args != null && args.length != 0)
		{
			StringBuilder text = new StringBuilder();
			for (String arg : args)
			{
				text.append(arg + " ");
			}
			String command = text.toString();
			executeCommand(command);
		}
		else
		{
			while (true)
			{
				System.out.print("Enter Command or 'exit': ");
				String command = System.console().readLine();

				if ("exit".equalsIgnoreCase(command))
					return;

				executeCommand(command);

			}
		}
	}

	private static void executeCommand(String command) throws URISyntaxException
	{
		Request request = new Request();
		request.text = command;
		request.encryption = "none";
		request.location = "desktop";

		HttpEntity<Request> entity = new HttpEntity<>(request, generateHeaders());
		Response response = restTemplate.postForObject(new URI(System.getenv("auto-url")), entity, Response.class);

		System.out.println("Short: " + response.shortResponse);
		System.out.println("Long: " + response.longResponse);
		System.out.println("Data: " + response.data);
	}

	private static String generateToken()
	{
		String secret = System.getenv("auto-token");
		long timestamp = System.currentTimeMillis();

		String token = DigestUtils.sha1Hex(secret + timestamp);

		return timestamp + "-" + token;
	}

	private static HttpHeaders generateHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set("token", generateToken());
		return headers;
	}
}