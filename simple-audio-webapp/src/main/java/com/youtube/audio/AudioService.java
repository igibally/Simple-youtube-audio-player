package com.youtube.audio;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;


public class AudioService {
	public  String getUrl(String id,String path){
		String url ="";
		String videoUrl = "https://www.youtube.com/watch?v="+id;
		// Destination directory
		//String directory = System.getProperty("user.home");
		// Build request
		YoutubeDLRequest request = new YoutubeDLRequest(videoUrl);
		//request.setOption("output", "%(id)s");	// --output "%(id)s"
		//request.setOption("retries", 10);		// --retries 10
		request.setOption("ignore-errors");		// --ignore-errors	
		request.setOption("max-downloads",1);
		request.setOption("ignore-config");
		request.setOption("skip-download");
		request.setOption("geo-bypass");
		request.setOption("youtube-skip-dash-manifest");
		request.setOption("restrict-filenames");
		request.setOption("extract-audio");
		request.setOption("get-url");
		// Make request and return response
		String stdOut ="";
		YoutubeDLResponse response;
	try {
			System.out.println("this is my real path");
			System.out.println(path);
			 YoutubeDL.setExecutablePath(path);
			response = YoutubeDL.execute(request);
			stdOut= response.getOut(); // Executable output
		} catch (Exception e) {
			// TODO Auto-generated catch block
		 e.printStackTrace();
		}
		return  stdOut;
	} 
}
