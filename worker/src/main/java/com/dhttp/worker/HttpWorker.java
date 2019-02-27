package com.dhttp.worker;

import com.dhttp.data.model.HttpRecord;
import com.dhttp.data.model.RequestType;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
	private static final RestTemplate template = new RestTemplate();
	private static final Logger logger = LoggerFactory.getLogger(HttpWorker.class);
	private static final HttpClient client = HttpClientBuilder.create().build();

	public static void main(String[] args) {
		SpringApplication.run(HttpWorker.class, args);
	}

	@Value("${gateway.url}")
	private String gatewayUrl;

	@GetMapping(path = "/")
	public HttpRecord proxyRequest(@RequestParam String url, @RequestParam(required = false) RequestType type) throws Exception {
		type = (type == null) ? RequestType.GET : type;
		logger.debug("Executing {} request:{}", type, url);

		return executeRequest(type, new URL(url).toURI());
	}

	/**
	 * Execute http request and save to storage microservice
	 *
	 * @param type http request type
	 * @param uri  url to request
	 * @return HttpRecord
	 */
	private HttpRecord executeRequest(RequestType type, URI uri) throws IOException {
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

		String source = EntityUtils.toString(response.getEntity());
		try {
			return saveToStorage(type, uri.toString(), source);
		} catch (Exception ex) {
			throw new ErrorSavingToStorageException(ex);
		}
	}

	private HttpRecord saveToStorage(RequestType type, String url, String source) {
		ResponseEntity<HttpRecord> exchange = template.exchange(
				gatewayUrl + "?url={url}&type={type}",
				HttpMethod.POST,
				new HttpEntity<>(source, new HttpHeaders()),
				HttpRecord.class,
				url,
				type
		);
		if (exchange.getStatusCode().is2xxSuccessful()) {
			return exchange.getBody();
		}
		throw new ErrorSavingToStorageException(exchange.getStatusCodeValue());
	}
}
