package com.github.nilankamanoj.guitarchords;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

public class Controller {
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblData1, lblData2, lblData3, lblData4;
    @FXML
    private MenuItem itmSong,itmArtist;
    @FXML
    private MenuButton menuOption;
    private AutoCompletionBinding currentBind;

    private ChordsHandler chordsHandler;
    private SortedSet artists,songs;
    private List songNames;

    {
        try {
            chordsHandler = new ChordsHandler();
            artists = chordsHandler.getArtists();
            songs = chordsHandler.getSongs();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClickSearch(){
        String searchTxt = txtSearch.getText();

        if (menuOption.getText().equals("song")) {
            if(songs.contains(searchTxt)) {
                int id = chordsHandler.getSongIdByName(searchTxt);
                setSong(id);
            }
        }
        else{
            if(artists.contains(searchTxt)) {
                songNames = chordsHandler.getSongNamesByIds(chordsHandler.getSongIds(searchTxt));
                setSongNames(songNames);

            }
        }

    }

    public void setSongNames(List songNames){
        String part1="",part2="", part3="";
        lblData1.setText("");
        lblData2.setText("");
        lblData3.setText("");
        lblData4.setText("");
        for (int i = 0; i <songNames.size() ; i++) {
            if(i<32){
                part1 = part1 + songNames.get(i) + "\n";
            }
            else if(i<64){
                part2 = part2 + songNames.get(i) + "\n";
            }
            else{
                part3 = part3 + songNames.get(i) + "\n";
            }
        }
        lblData1.setText(part1);
        lblData2.setText(part2);
        lblData3.setText(part3);

    }

    public void setSong(int id){
        songNames = null;
        JSONObject song = chordsHandler.getSongById(id);
        String data = (String)song.get("data");

        String[] lines = data.split("\n");
        String part1 = "",part2="",part3="", part4="";
        lblData1.setText("");
        lblData2.setText("");
        lblData3.setText("");
        lblData4.setText("");
        for (int i = 0; i <lines.length ; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                if((int)lines[i].charAt(j) > 127){
                    lines[i] = lines[i].substring(0,j) + " "+lines[i].substring(j+1);
                }
            }
            if(i<32){
                part1 = part1 + lines[i] + "\n";
            }
            else if(i<64){
                part2 = part2 + lines[i] + "\n";
            }
            else if(i<96){
                part3 = part3 + lines[i] + "\n";
            }
            else{
                part4 = part4 + lines[i] + "\n";
            }
        }
        lblData1.setText(part1);
        lblData2.setText(part2);
        lblData3.setText(part3);
        lblData4.setText(part4);
    }


    public void onSearchSong(ActionEvent actionEvent) {
        menuOption.setText("song");
        itmSong.setVisible(false);
        itmArtist.setVisible(true);
        if(currentBind!=null)currentBind.dispose();
        currentBind = TextFields.bindAutoCompletion(txtSearch,songs);

    }

    public void onSearchArtist(ActionEvent actionEvent) {
        menuOption.setText("artist");
        itmSong.setVisible(true);
        itmArtist.setVisible(false);
        if(currentBind!=null)currentBind.dispose();
        currentBind = TextFields.bindAutoCompletion(txtSearch,artists);

    }

    public void onClickSearchtxt(MouseEvent mouseEvent) {
        if(currentBind==null)currentBind = TextFields.bindAutoCompletion(txtSearch,songs);
    }

    public void onClickLBL1(MouseEvent mouseEvent) {
        int index = (int)mouseEvent.getY()/20;
        if(songNames!=null && index<songNames.size()){
            int songId = chordsHandler.getSongIdByName((String)songNames.get(index));
            setSong(songId);
        }
    }
    public void onClickLBL2(MouseEvent mouseEvent) {
        int index = 32 + (int)mouseEvent.getY()/20;
        if(songNames!=null && index<songNames.size()){
            int songId = chordsHandler.getSongIdByName((String)songNames.get(index));
            setSong(songId);
        }
    }
    public void onClickLBL3(MouseEvent mouseEvent) {
        int index = 64 + (int)mouseEvent.getY()/20;
        if(songNames!=null && index<songNames.size()){
            int songId = chordsHandler.getSongIdByName((String)songNames.get(index));
            setSong(songId);
        }
    }
    public void onClickLBL4(MouseEvent mouseEvent) {
        int index = 96 + (int)mouseEvent.getY()/20;
        if(songNames!=null && index<songNames.size()){
            int songId = chordsHandler.getSongIdByName((String)songNames.get(index));
            setSong(songId);
        }
    }
}
