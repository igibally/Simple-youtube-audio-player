package com.youtube.audio;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.samples.youtube.cmdline.Search;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Print a list of videos matching a search term.
 *
 * @author Jeremy Walker
 */
@Provider
@Path("serach")
public class MyLink {
	/**
	 * Define a global variable that identifies the name of a file that contains
	 * the developer's API key.
	 */
	private static final String PROPERTIES_FILENAME = "youtube.properties";
	private static final long NUMBER_OF_VIDEOS_RETURNED = 50;
	/**
	 * Define a global instance of a Youtube object, which will be used to make
	 * YouTube Data API requests.
	 */
	private static YouTube youtube;

	/**
	 * Initialize a YouTube object to search for videos on YouTube. Then display
	 * the name and thumbnail image of each video in the result set.
	 *
	 * @param args
	 *            command line args.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{keyword}")
	public Response getResult(@PathParam("keyword") String queryTerm) {
		// Read the developer key from the properties file.
		Properties properties = new Properties();
		Response  response = null;
		try {
			InputStream in = Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
			properties.load(in);

		} catch (IOException e) {
			System.err.println(
					"There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause() + " : " + e.getMessage());
			System.exit(1);
		}

		try {
			// This object is used to make YouTube Data API requests. The last
			// argument is required, but since we don't need anything
			// initialized when the HttpRequest is initialized, we override
			// the interface and provide a no-op function.
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {
				}
			}).setApplicationName("youtube-cmdline-search-sample").build();

			// Define the API request for retrieving search results.
			YouTube.Search.List search = youtube.search().list("id,snippet");

			// Set your developer key from the {{ Google Cloud Console }} for
			// non-authenticated requests. See:
			// {{ https://cloud.google.com/console }}
			String apiKey = properties.getProperty("youtube.apikey");
			search.setKey(apiKey);
			search.setQ(queryTerm);

			// Restrict the search results to only include videos. See:
			// https://developers.google.com/youtube/v3/docs/search/list#type
			search.setType("video");

			// To increase efficiency, only retrieve the fields that the
			// application uses.
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/medium/url)");
			search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

			// Call the API and print results.
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			if (searchResultList != null) {
				
				response = Response.status(Response.Status.OK).entity(searchResultList).build();
				//response = prettyPrint(searchResultList.iterator(), queryTerm);
			}
		} catch (GoogleJsonResponseException e) {
			System.err.println(
					"There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
		} catch (IOException e) {
			System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return response;

	}

	/*
	 * Prompt the user to enter a query term and return the user-specified term.
	 */
	private static String getInputQuery() throws IOException {

		String inputQuery = "";

		System.out.print("Please enter a search term: ");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		inputQuery = bReader.readLine();

		if (inputQuery.length() < 1) {
			// Use the string "YouTube Developers Live" as a default.
			inputQuery = "YouTube Developers Live";
		}
		return inputQuery;
	}

	/*
	 * Prints out all results in the Iterator. For each result, print the title,
	 * video ID, and thumbnail.
	 *
	 * @param iteratorSearchResults Iterator of SearchResults to print
	 *
	 * @param query Search query (String)
	 */
	private static String prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

		StringBuilder output = new StringBuilder();
		output.append("<h1>First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\"</h1>");
		output.append("<div id='continer' >");
		if (!iteratorSearchResults.hasNext()) {
			output.append("<div> There aren't any results for your query.</div>");
		}

		while (iteratorSearchResults.hasNext()) {
			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();
			// Confirm that the result represents a video. Otherwise, the
			// item will not contain a video ID.
			if (rId.getKind().equals("youtube#video")) {
				Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
				output.append(
						"<div class='item'  style='margin:10px 0px 0px 20px;border:1px solid red; background-color:#efefef;padding:5px'   >");
				output.append("<h4 class='item-title' style='padding:0px;margin:8px' ><span>"
						+ singleVideo.getSnippet().getTitle() + "</span></h4>");
				output.append("<a  id='item-link' style='dispaly:block' target='_blank' ");
				output.append("title= '" + singleVideo.getSnippet().getTitle() + "'");
				output.append("onclick=\"window.open('http://localhost:8080/simple-audio-webapp/webapi/myresource/"
						+ rId.getVideoId()
						+ "','_blank', 'toolbar=no,scrollbars=no,resizable=no,Location=no,toolbar=0,top=20,left=20,width=400,height=50;toolbar=no,titlebar=no');\"");
				output.append("href='javascript:void(0)'>");
				output.append("<img src='" + thumbnail.getUrl() + "'/>");
				output.append("</a>");
				output.append("</div>");
			}
		}
		output.append("</div>");
		return output.toString();
	}
}
