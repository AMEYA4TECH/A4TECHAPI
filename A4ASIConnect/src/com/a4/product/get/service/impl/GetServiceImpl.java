package com.a4.product.get.service.impl;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.a4.product.beans.Product;
import com.a4.product.getservice.GetService;

public class GetServiceImpl implements GetService {
	Product product = null;
	private Logger _LOGGER = Logger.getLogger(getClass());
	private RestTemplate restTemplateClass;
	private String getApiURL;

	public String getGetApiURL() {
		return getApiURL;
	}

	public void setGetApiURL(String getApiURL) {
		this.getApiURL = getApiURL;
	}

	public RestTemplate getRestTemplateClass() {
		return restTemplateClass;
	}

	public void setRestTemplateClass(RestTemplate restTemplateClass) {
		this.restTemplateClass = restTemplateClass;
	}

	public Product doGet(String authToken, String xid) {
		String url = getApiURL + xid;

		Product product = null;
		try {

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			header.add("AuthToken", authToken);
			HttpEntity<String> requestEntity = new HttpEntity<String>(header);

			_LOGGER.info("Getting ASI Product - XID: " + xid);
			_LOGGER.info("With Authentication Token: " + authToken);

			ResponseEntity<Product> response = restTemplateClass.exchange(url,
					HttpMethod.GET, requestEntity, Product.class, xid);
			if (response != null && response.getBody() != null) {
				product = response.getBody();
			}

			_LOGGER.info("URL: " + url + " with Response Code: "
					+ response.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}
}
