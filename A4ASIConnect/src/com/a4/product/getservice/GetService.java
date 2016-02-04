package com.a4.product.getservice;

import com.a4.product.beans.Product;





public interface GetService {
	public Product doGet(String authToken,String xid);

}
