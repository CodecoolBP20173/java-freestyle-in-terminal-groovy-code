package com.codecool.game;

import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import com.codecool.termlib.Direction;

//a rectangle shaped thing represented with a multiarray of chars
public class Thing{
    char[][] cells; //character cells of the thing 
    public int sizeX, sizeY; // size of thing
    public int posX, posY; //position of top left cell of the thing in the global grid array

    //this constructor fills a thing with the data of importPix 
    Thing(char[][] importPix, int posX, int posY){
        sizeX = importPix[0].length;
        sizeY = importPix.length;
        this.posX = posX;
        this.posY = posY;
        cells = new char[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++){
                cells[y][x] = importPix[y][x];
            }
        }
    }

    //this constructor imports from a text file an ascii 'image'
    //the first line of file should be as long, as the whole pic will be
    Thing(String filePath, int posX, int posY) throws IOException {

        this.posX = posX;
        this.posY = posY;

        BufferedReader inputStream = null;
        List<List<Character>> importCells;
        List<Character> line;

        try {
            inputStream = new BufferedReader(new FileReader(filePath));
            importCells = new ArrayList<List<Character>>();
            line = new ArrayList<Character>();
            int c;
            while ((c = inputStream.read()) != -1) {
                if (c == '\n'){
                    importCells.add(line);
                    //System.out.println(line);
                    line = new ArrayList<Character>(); //clearing is not good, because 'line' points to the same object 
                }
                else{
                    line.add((char) c);
                }
            }
            //importCells.add(line);
            
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        sizeX = importCells.get(0).size();
        sizeY = importCells.size();

        System.out.println("sizeX: " + sizeX);
        System.out.println("sizeY: " + sizeY);

        cells = new char[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++){
                cells[y][x] = importCells.get(y).get(x);
            }
        }

    }

    private void _draw(String option){
        boolean onScreen, opaque;
        int gx, gy; //grid coordinates of the actual cell in the loop we operate on
        for (int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++){
                gx = posX + x;
                gy = posY + y; 
                onScreen = gy < Game.MAXROW && gy >= 0 && gx < Game.MAXCOL && gx >= 0 ;
                opaque = cells[y][x] != '~'; //this character signifies transparency
                if (onScreen && opaque){
                    if (option.equals("draw")){ 
                        Game.grid[gy][gx] = cells[y][x];
                    }
                    else if (option.equals("clear")){
                        Game.grid[gy][gx] = '\u0000';
                    }

                }
            }
        }
    }
    
    //update grid with the thing, "stamp" it on the grid
    //TODO: we should handle partly out of screen and collision here I think
    public void draw(){
        _draw("draw");
    }

    //clears the thing's rectangle in the grid, we use this method before moving 
    public void clear(){
        _draw("clear");
    }

    public void move(Direction dir){
        clear(); //clear the rectangle where we are now
        if (dir == Direction.FORWARD){
            posX++;
        }
        else if (dir == Direction.BACKWARD){
            posX--;
        }
        else if (dir == Direction.UP){
            posY--;
        }
        else if (dir == Direction.DOWN){
            posY++;
        }
        draw();
    }

    public void moveTo(int x, int y){
        clear(); //clear the rectangle where we are now
        posX = x;
        posY = y;
        draw();
    }
}