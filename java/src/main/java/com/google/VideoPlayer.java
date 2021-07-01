package com.google;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  // Note: Not yet in lexicographic order
  public void showAllVideos() {
	  System.out.println("Here's a list of all available videos:");

	  String filePath = "src/main/resources/videos.txt";

	  try {
		    FileReader reader = new FileReader(filePath);
		    BufferedReader bufferedReader = new BufferedReader(reader);

		    String line = bufferedReader.readLine();

		    while (line != null) {
		    	String [] videoDetails = line.split(Pattern.quote("|").strip());

		    	if (videoDetails.length == 3) {
		    		String output = videoDetails[0] + " (" + videoDetails[1] + ") " + "[" + videoDetails[2] + "]";
		    		String indentedOutput = output.replaceAll("(?m)^", "\t");
		    		
		    		System.out.println(indentedOutput);
		    	}
		    	else if (videoDetails.length == 2) {
		    		String output = videoDetails[0] + " (" + videoDetails[1] + ") " + "[]";
		    		String indentedOutput = output.replaceAll("(?m)^", "\t");
		    		
		    		System.out.println(indentedOutput);
		    	}
		    	
		    	line = bufferedReader.readLine();
		    }
		    reader.close();

		   } catch (IOException e) {
		    e.printStackTrace();
		   }
  }
  
  Boolean videoPlaying = false;
  String currentlyPlaying = "";

  public void playVideo(String videoId) {	  
	  String filePath = "src/main/resources/videos.txt";

	  try {
		    FileReader reader = new FileReader(filePath);
		    BufferedReader bufferedReader = new BufferedReader(reader);

		    String line = bufferedReader.readLine();
		    Map<String, String> map = new HashMap<String, String>();

		    while (line != null) {
		    	String [] videoDetails = line.split(Pattern.quote("|"));
		    	
		    	String id = videoDetails[1].strip();
		    	String title = videoDetails[0].strip();
		    	
		    	map.put(id, title);
		    	
		    	line = bufferedReader.readLine();
		    }
		    
		    if (map.containsKey(videoId) && !videoPlaying) {
		    	videoPlaying = true;
		    	currentlyPlaying = map.get(videoId);
		    	System.out.println("Playing video: " + currentlyPlaying);
		    }
		    else if (map.containsKey(videoId) && videoPlaying) {
		    	System.out.println("Stopping video: " + currentlyPlaying);
		    	currentlyPlaying = map.get(videoId);
		    	System.out.println("Playing video: " + currentlyPlaying);
		    	paused = false;
		    }
		    else if (!map.containsKey(videoId)) {
		    	System.out.println("Cannot play video: Video does not exist");
		    }

		    reader.close();

		   } catch (IOException e) {
		    e.printStackTrace();
		   }
  }

  public void stopVideo() {
	  if (videoPlaying) {
		  System.out.println("Stopping video: " + currentlyPlaying);
		  videoPlaying = false;
	  }
	  else if (!videoPlaying) {
		  System.out.println("Cannot stop video: No video is currently playing");
	  }
  }

  // Note: Not taking into account case where no videos are available yet
  public void playRandomVideo() {
	  ArrayList<String> videoTitles = new ArrayList<String>();
	  
	  String filePath = "src/main/resources/videos.txt";

	  try {
		    FileReader reader = new FileReader(filePath);
		    BufferedReader bufferedReader = new BufferedReader(reader);

		    String line = bufferedReader.readLine();

		    while (line != null) {
		    	String [] videoDetails = line.split(Pattern.quote("|").strip());
		    	videoTitles.add(videoDetails[0]);
		    	
		    	line = bufferedReader.readLine();
		    }
		    reader.close();
		    
		    int max = videoTitles.size() - 1;
		    int min = 0;
		    
		    Random random = new Random();
		    int randomIndex = random.nextInt(max - min) + min;
		    
		    if (!videoPlaying) {
		    	currentlyPlaying = videoTitles.get(randomIndex);
		    	System.out.println("Playing video: " + currentlyPlaying);
		    	videoPlaying = true;
		    }
		    else if (videoPlaying) {
		    	System.out.println("Stopping video: " + currentlyPlaying);
		    	currentlyPlaying = videoTitles.get(randomIndex);
		    	System.out.println("Playing video: " + currentlyPlaying);
		    }
		    
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
  }

  Boolean paused = false;
  
  public void pauseVideo() {
	  if (videoPlaying && !paused) {
		  System.out.println("Pausing video: " + currentlyPlaying);
		  paused = true;
	  }
	  else if (videoPlaying && paused) {
		  System.out.println("Video already paused: " + currentlyPlaying);
	  }
	  else if (!videoPlaying) {
		  System.out.println("Cannot pause video: No video is currently playing");
	  }
  }

  public void continueVideo() {
	  if (videoPlaying && paused) {
		  System.out.println("Continuing video: " + currentlyPlaying);
		  paused = false;
	  }
	  else if (videoPlaying && !paused) {
		  System.out.println("Cannot continue video: Video is not paused");
	  }
	  else if (!videoPlaying) {
		  System.out.println("Cannot continue video: No video is currently playing");
	  }
  }

  public void showPlaying() {
	  String filePath = "src/main/resources/videos.txt";
	  
	  Map<String, String> map_videoTitletoID = new HashMap<String, String>();
	  Map<String, String> map_videoTitletoTags = new HashMap<String, String>();
	  
	  try {
		    FileReader reader = new FileReader(filePath);
		    BufferedReader bufferedReader = new BufferedReader(reader);

		    String line = bufferedReader.readLine();

		    while (line != null) {
		    	String [] videoDetails = line.split(Pattern.quote("|"));

		    	map_videoTitletoID.put(videoDetails[0].strip(), videoDetails[1].strip());
		    	if (videoDetails.length == 3) {
		    		map_videoTitletoTags.put(videoDetails[0].strip(), videoDetails[2].strip());
		    	}
		    	
		    	line = bufferedReader.readLine();
		    }
		    reader.close();

		   } catch (IOException e) {
		    e.printStackTrace();
		   }
	  
	  String currentlyPlayingID = map_videoTitletoID.get(currentlyPlaying);
	  String currentlyPlayingTags = map_videoTitletoTags.get(currentlyPlaying);
	  
	  if (videoPlaying && !paused) {
		  System.out.println("Currently playing: " + currentlyPlaying + " (" + currentlyPlayingID + ") " + "[" + currentlyPlayingTags + "]");
	  }
	  else if (videoPlaying && paused) {
		  System.out.println("Currently playing: " + currentlyPlaying + " (" + currentlyPlayingID + ") " + "[" + currentlyPlayingTags + "] " + "- PAUSED");
	  }
	  else if (!videoPlaying) {
		  System.out.println("No video is currently playing");
	  }
  }

  public void createPlaylist(String playlistName) {
    System.out.println("createPlaylist needs implementation");
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}