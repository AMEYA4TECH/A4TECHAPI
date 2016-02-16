package com.a4.product.postservice;

import com.a4.product.beans.ExternalAPIResponse;
import com.a4.product.beans.Product;



public interface PostService {

	
	public ExternalAPIResponse postProduct(String authToken,Product product );
}