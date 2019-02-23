package com.dhttp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Gateway to combine all worker microservices
 */
@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
@EnableHystrixDashboard
public class GatewayServer {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServer.class, args);
	}

	@Bean
	public FallbackProvider zuulFallbackProvider() {
		return new FallbackProvider() {
			@Override
			public String getRoute() {
				return "*";
			}

			@Override
			public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
				return new ClientHttpResponse() {
					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_XML);
						return headers;
					}

					@Override
					public InputStream getBody() throws IOException {
						return new ByteArrayInputStream("<RequestWrapper><success>false</success><error>Service timed out</error><responseCode>-1</responseCode><url/><type/><source/></RequestWrapper>".getBytes());
					}

					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.GATEWAY_TIMEOUT;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return HttpStatus.GATEWAY_TIMEOUT.value();
					}

					@Override
					public String getStatusText() throws IOException {
						return HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase();
					}

					@Override
					public void close() {

					}
				};
			}
		};
	}
}
