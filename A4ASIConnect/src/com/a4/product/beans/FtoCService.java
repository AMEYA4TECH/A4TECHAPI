package com.a4.product.beans;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
 
@Path("/ftocservice")
public class FtoCService {
 
	  @GET
	  @Produces("application/json")
	  public void convertFtoC() throws JSONException {
 
		  try {
			  
				Client client = Client.create();
				WebResource webResource2 = client.resource(" http://stage-espupdates.asicentral.com/api/api/v1/Login?asi_number=68507&username=zahmed68507&password=password2&grant_type=password&app_code=VELO&app_version=1.0.0");
				
				ClientResponse response2 = webResource2.accept("application/json").get(ClientResponse.class);
				if (response2.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response2.getStatus());
				}
	 
				String output2 = response2.getEntity(String.class);
				System.out.println("\n============getFtoCResponse============");
				System.out.println(output2);
	 
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
 
	  @Path("{f}")
	  @GET
	  @Produces("application/json")
	  public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius =  (f - 32)*5/9; 
		jsonObject.put("F Value", f); 
		jsonObject.put("C Value", celsius);
 
		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
}