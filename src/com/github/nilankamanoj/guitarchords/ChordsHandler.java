package com.github.nilankamanoj.guitarchords;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ChordsHandler {
    private  JSONArray chords;
    private SortedSet<String> artists;
    private SortedSet<String> songs;
    private Map<String,List<Integer>> artistsSongs;
    private Map<String,Integer> songIds;

    public ChordsHandler() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String filePath = new File("").getAbsolutePath();
        chords = (JSONArray) parser.parse(new FileReader(filePath.concat("/data.json")));
        artists= new TreeSet();
        songs= new TreeSet();
        artistsSongs= new HashMap();
        songIds=new HashMap();
        for (int i = 0; i < chords.size() ; i++) {
            JSONObject song = (JSONObject)chords.get(i);
            String artist = ((String)song.get("artist")).toLowerCase();
            String songName = ((String)song.get("song")).toLowerCase();

            artists.add(artist);
            songs.add(songName);
            if(artistsSongs.containsKey(artist)){
                artistsSongs.get(artist).add(i+1);
            }
            else {
                List songs = new ArrayList();
                songs.add(i+1);
                artistsSongs.put(artist,songs);
            }
            if(songName!=null)songIds.put(songName,i+1);
        }

    }

    public JSONObject getSongById(int id){
        return (JSONObject) chords.get(id-1);
    }

    public List getSongNamesByIds(List ids){
        List names = new ArrayList();
        for (int i = 0; i < ids.size(); i++) {
            JSONObject song = (JSONObject)chords.get((int)ids.get(i)-1);
            String name = (String)song.get("song");
            names.add(name);
        }
        return names;
    }

    public SortedSet getArtists(){
        return artists;
    }
    public SortedSet getSongs(){
        return songs;
    }

    public List getSongIds(String artist){
        return artistsSongs.get(artist);
    }
    public int getSongIdByName(String name){
        return songIds.get(name.toLowerCase());
    }

}
