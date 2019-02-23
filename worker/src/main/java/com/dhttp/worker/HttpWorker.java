package com.dhttp.worker;

import com.dhttp.data.RequestType;
import com.dhttp.data.RequestWrapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Microservice to execute a http request and return the received http source
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class HttpWorker {
	private static final Logger logger = LoggerFactory.getLogger(HttpWorker.class);
	private static final HttpClient client = HttpClientBuilder.create().build();

	public static void main(String[] args) {
		SpringApplication.run(HttpWorker.class, args);
	}

	@GetMapping(path = "/", produces = {MediaType.APPLICATION_XML_VALUE})
	public RequestWrapper proxyRequest(@RequestParam String url, @RequestParam(required = false) RequestType type) {
		type = (type == null) ? RequestType.GET : type;
		logger.debug("Executing {} request:{}", type, url);

		try {
			return executeRequest(type, url);
		} catch (Exception e) {
			return RequestWrapper.createNew(type, url, e);
		}
	}

	private RequestWrapper executeRequest(RequestType type, String url) throws IOException {
		HttpResponse response;
		switch (type) {
			default:
			case GET:
				response = client.execute(new HttpGet(url));
				break;
			case POST:
				response = client.execute(new HttpPost(url));
				break;
			case PUT:
				response = client.execute(new HttpPut(url));
				break;
			case DELETE:
				response = client.execute(new HttpDelete(url));
				break;
		}
		return createWrapper(type, url, response);
	}

	private RequestWrapper createWrapper(RequestType type, String url, HttpResponse response) throws IOException {
		String source = EntityUtils.toString(response.getEntity());
		return RequestWrapper.createNew(type, url, response.getStatusLine().getStatusCode(), source);
	}
}
