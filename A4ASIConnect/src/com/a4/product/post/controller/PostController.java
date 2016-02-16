package com.a4.product.post.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.a4.product.beans.ExternalAPIResponse;
import com.a4.product.beans.Product;
import com.a4.product.postservice.PostService;


@RestController
@RequestMapping("/product")
public class PostController {
	
	@Autowired
    private PostService  postService;
	
	private Logger              _LOGGER              = Logger.getLogger(getClass());
    // @Secured("ROLE_CUSTOMER")
    @RequestMapping(method = RequestMethod.POST, headers = "content-type=application/json", produces = { "application/json; charset=UTF-8" })
    public ResponseEntity<?> updateProduct(HttpEntity<Product> requestEntity, HttpServletRequest request,
            @RequestHeader("AuthToken") String authToken) throws Exception {
        if (_LOGGER.isDebugEnabled()) {
            _LOGGER.debug("calling service");
        }
        ExternalAPIResponse message = null;

        if (authToken == null) {
            return new ResponseEntity<ExternalAPIResponse>(message, null, HttpStatus.UNAUTHORIZED);
        } else if (requestEntity == null || requestEntity.getBody() == null) {
            message = new ExternalAPIResponse();
            message.setStatusCode(HttpStatus.BAD_REQUEST);
            message.setMessage("Invalid request, request body can't be null");
            return new ResponseEntity<ExternalAPIResponse>(message, null, HttpStatus.BAD_REQUEST);
        } else if (requestEntity.getBody().getExternalProductId() == null || requestEntity.getBody().getExternalProductId().isEmpty()) {
            // External ProductID required
            message = new ExternalAPIResponse();
            message.setMessage("Invalid request, ExternalProductId can't be null/empty");
            message.setStatusCode(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<ExternalAPIResponse>(message, null, HttpStatus.BAD_REQUEST);
        }
        try {
            //String batchId = request.getParameter("batchId");
            message = postService.postProduct(authToken, requestEntity.getBody());
            //(authToken, batchId, requestEntity.getBody(), getIPAddress(request));
            
            
            if(message==null){
            	  message = new ExternalAPIResponse();
                  message.setMessage("Kindly check the product json, something is wrong with data");
                  message.setStatusCode(HttpStatus.BAD_REQUEST);
                  return new ResponseEntity<ExternalAPIResponse>(message, null,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            _LOGGER.error(e.getMessage(), e);
           // throw e;
            
            message = new ExternalAPIResponse();
            message.setMessage("Kindly check the product json, something is wrong with data");
            message.setStatusCode(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<ExternalAPIResponse>(message, null,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ExternalAPIResponse>(message, null, message.getStatusCode());
    }
	public PostService getPostService() {
		return postService;
	}
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	

}
