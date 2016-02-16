package com.a4.product.post.service.impl;



import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;





import com.a4.product.beans.ExternalAPIResponse;
import com.a4.product.beans.Product;
import com.a4.product.postservice.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.*;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PostServiceImpl implements PostService{
	    @Autowired
		Product product2 ;
		  
	    private Logger              _LOGGER              = Logger.getLogger(getClass());
		
	    
		private RestTemplate        restTemplateClass;
		private String postApiURL;
		ExternalAPIResponse extRespose=null;
		  
		  
		public Product getProduct2() {
			return product2;
		}

		public void setProduct2(Product product2) {
			this.product2 = product2;
		}

		
		public ExternalAPIResponse postProduct(String authToken, Product product1) {
			
			
			try {
	            
	            
	        	HttpHeaders headers = new HttpHeaders();
	        	headers.add("AuthToken", authToken);
	        	//headers.setContentType(MediaType.APPLICATION_JSON);
	        	headers .add("Content-Type", "application/json ; charset=utf-8");
	            product2=product1;
	            ObjectMapper mapper1 = new ObjectMapper();
	           _LOGGER.info("Product Data : " + mapper1.writeValueAsString(product2));
	            HttpEntity<Product> requestEntity = new HttpEntity<Product>(product2, headers);
	            ResponseEntity<ExternalAPIResponse> response = restTemplateClass.exchange(postApiURL, HttpMethod.POST, requestEntity, ExternalAPIResponse.class);
	            _LOGGER.info("Result : " + response);
	            return getExternalAPIResponse("Product Saved successfully", HttpStatus.OK, null);
	        } catch (Exception hce) {
	            _LOGGER.error("Exception while posting product to Radar API", hce);
	            return null;
	        } 
	    }
		private ExternalAPIResponse getExternalAPIResponse(String message, HttpStatus statusCode, Set<String> additionalInfo) {
	        ExternalAPIResponse response = new ExternalAPIResponse();
	        if (statusCode != null && message != null && message.toLowerCase().startsWith("{\"Message\":\"Not Valid".toLowerCase())) {
	            response.setMessage("Product saved successfully but not active");
	            response.setStatusCode(HttpStatus.OK);
	        } else {
	            response.setStatusCode(statusCode);
	            response.setMessage(message);
	        }
	        response.setAdditionalInfo(additionalInfo);

	        return response;
	    }
		public RestTemplate getRestTemplateClass() {
			return restTemplateClass;
		}

		public void setRestTemplateClass(RestTemplate restTemplateClass) {
			this.restTemplateClass = restTemplateClass;
		}

		public String getPostApiURL() {
			return postApiURL;
		}

		public void setPostApiURL(String postApiURL) {
			this.postApiURL = postApiURL;
		}

		public ExternalAPIResponse getExtRespose() {
			return extRespose;
		}

		public void setExtRespose(ExternalAPIResponse extRespose) {
			this.extRespose = extRespose;
		}

		
	}
