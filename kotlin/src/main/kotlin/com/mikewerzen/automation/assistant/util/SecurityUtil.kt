package com.mikewerzen.automation.assistant.util


import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service

@Service
public class SecurityUtil
{
	val logger = LoggerFactory.getLogger(javaClass)

	@Value("\${webservice.secret}")
	lateinit private var secret: String

	@Value("\${webservice.millis.range}")
	private var allowedTimeRange: Long? = null

	@Value("\${environment}")
	private var environment : String? = null

	public fun generateToken(): String
	{
		val timestamp = System.currentTimeMillis().toString()
		val token = DigestUtils.sha1Hex(secret + timestamp)
		return timestamp + "-" + token;
	}

	public fun validateToken(token: String)
	{
		var parts = token.split("-")

		val timestamp = NumberUtils.toLong(parts[0]);
		val timeDiff = Math.abs(System.currentTimeMillis() - timestamp);

		val token = parts[1]
		val computedToken = DigestUtils.sha1Hex(secret + timestamp)

		logger.info("Token: " + token + " ComputedToken: " + computedToken)
		logger.info("Time: " + timestamp + " CurrTime: " + System.currentTimeMillis() + " Diff: " + timeDiff);

		if (!computedToken.equals(token) || timeDiff > allowedTimeRange!!)
		{
			if(environment.equals("nonprod"))
			{
				logger.info("Login Failed, Continuing in Dev");
				return;
			}
			throw RuntimeException("Could not Authenticate");
		}

	}

	public fun generateSecureHttpHeaders(): HttpHeaders
	{
		var headers = HttpHeaders();
		headers.set("token", generateToken());
		return headers;
	}
}