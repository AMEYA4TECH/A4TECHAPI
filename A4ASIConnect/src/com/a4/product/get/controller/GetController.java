package com.a4.product.get.controller;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.a4.product.beans.Product;
import com.a4.product.getservice.GetService;


@RestController
@RequestMapping("product")
public class GetController {

	@Autowired
	private GetService	getService;
	
	public GetService getGetService() {
		return getService;
	}

	public void setGetService(GetService getService) {
		this.getService = getService;
	}
	
	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
    @RequestMapping(value = "{xid}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8" , "application/xml; charset=UTF-8" })
    public ResponseEntity<Product> getProductJSON(@RequestHeader("AuthToken") String authToken, @PathVariable("xid") String xid)
            throws UnsupportedEncodingException,  Exception {
    	long totalTime = System.currentTimeMillis();

        Product productResponse = null;
        if (authToken == null) {
            return new ResponseEntity<Product>(productResponse, null, HttpStatus.UNAUTHORIZED);
        }
        
        try {
            productResponse = getService.doGet(authToken, xid);
            
        } catch (RuntimeException re) {
        	
        } 
        totalTime = System.currentTimeMillis() - totalTime;
        
        ResponseEntity<Product> response = new ResponseEntity<Product>(productResponse, null, HttpStatus.OK);

        _LOGGER.info("Successfully GET product " + xid + " in " + totalTime + " Ms");
        return response;
    }

}
