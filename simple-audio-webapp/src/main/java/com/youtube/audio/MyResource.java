package com.youtube.audio;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * Root resource (exposed at "myresource" path)
 */
@Provider
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{audioId}")
    public String getIt(@PathParam("audioId")String id,
    		@Context ServletContext servletContext) {
    		String path = servletContext.getRealPath("\\WEB-INF\\youtube-dl.exe").replace("\\", "\\\\");
    		AudioService audiSrv =new AudioService();
    	    String mp3_url= audiSrv.getUrl(id,path);
    	System.out.println(mp3_url);
    	StringBuilder response= new StringBuilder("<audio controls id='myVideo'>");
    	response.append("<source src='"+mp3_url+"' type=\"audio/mp3\">");
    	response.append("<source src=src='"+mp3_url+"' type=\"audio/mp3\">");
    	response.append("Your browser does not support the audio element");
    	response.append("</audio>");
    	response.append("<script>");
    	response.append("var vid = document.getElementById(\"myVideo\");");
    	response.append("vid.onloadeddata = function(){vid.play();}"); 
    	response.append("</script>");
    	return response.toString();
    	
    	
        
    }
}
