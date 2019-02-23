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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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

		URI uri;
		try {
			uri = new URL(url).toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			return RequestWrapper.createNew(type, url, e);
		}

		try {
			return executeRequest(type, uri);
		} catch (Exception e) {
			return RequestWrapper.createNew(type, url, e);
		}
	}

	private RequestWrapper executeRequest(RequestType type, URI uri) throws IOException {
		HttpResponse response;
		switch (type) {
			default:
			case GET:
				response = client.execute(new HttpGet(uri));
				break;
			case POST:
				response = client.execute(new HttpPost(uri));
				break;
			case PUT:
				response = client.execute(new HttpPut(uri));
				break;
			case DELETE:
				response = client.execute(new HttpDelete(uri));
				break;
		}
		return createWrapper(type, uri, response);
	}

	private RequestWrapper createWrapper(RequestType type, URI url, HttpResponse response) throws IOException {
		String source = EntityUtils.toString(response.getEntity());
		return RequestWrapper.createNew(type, url.toString(), response.getStatusLine().getStatusCode(), source);
	}
}
