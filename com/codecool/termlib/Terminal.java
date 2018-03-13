package com.codecool.termlib;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Terminal {
    private static final String CONTROL_CODE = "\033[";
    private static final String CLEAR = "2J";
    private static final String MOVE = "H";
    private static final String STYLE = "m";

    public void resetStyle() {
        //<ESC>[{attr1};...;{attrn}m
        command(CONTROL_CODE + '[' + 0 + STYLE);
    }

    public void clearScreen() {
        command(CONTROL_CODE + CLEAR);
    }

    public void moveTo(Integer x, Integer y) {
        command(CONTROL_CODE + '[' + String.valueof(x) + ';' + String.valueOf(y) + MOVE);
    }

    public void setColor(Color color) {
        String code = getFGColorCode(color);
        command(CONTROL_CODE + '[' + code + STYLE);
    }

    public void setBgColor(Color color) {
        String code = getBGColorCode(color);
        command(CONTROL_CODE + '[' + code + STYLE);
    }

    public void setUnderline() {
        command(CONTROL_CODE + '[' + '4' + STYLE);
    }

    public void moveCursor(Direction direction, Integer amount) {
        command(CONTROL_CODE + '[' + String.valueOf(amount) + getDirectionCode(direction));
    }

    public void setChar(char c) {
        command(String.valueof(c));
    }

    private String getDirectionCode(Direction dir){
        if (dir == UP) return 'A';
        if (dir == DOWN) return 'B';
        if (dir == FORWARD) return 'C';
        if (dir == BACKWARD) return 'D'; 
    }

    private String getBGColorCode(Color color){
        String code = "47";
        switch (color){
            case BLACK: code = "40"; break;
            case RED: code = "41"; break;
            case GREEN: code = "42"; break;
            case YELLOW: code = "43"; break;
            case BLUE: code = "44"; break;
            case MAGENTA: code = "45"; break;
            case CYAN: code = "46"; break;
            case WHITE: code = "47";
        }
        return code;
    }

    private String getFGColorCode(Color color){
        String code = "37";
        switch (color){
            case BLACK: code = "30"; break;
            case RED: code = "31"; break;
            case GREEN: code = "32"; break;
            case YELLOW: code = "33"; break;
            case BLUE: code = "34"; break;
            case MAGENTA: code = "35"; break;
            case CYAN: code = "36"; break;
            case WHITE: code = "37";
        }
        return code;
    }

    // these two functions from: https://stackoverflow.com/questions/1410741/want-to-invoke-a-linux-shell-command-from-java
    private void command(String commandString) {
        ArrayList<String> output = getCommandString(commandString, ".");
        if (null == output)
            System.out.println("\n\n\t\tCOMMAND FAILED: " + commandString);
        else
            for (String line : output)
                System.out.println(line);
    }

    //cmdline: command to run, directory: where to run it
    private static ArrayList<String> getCommandString(final String cmdline, final String directory) {
        try {
            Process process = 
                new ProcessBuilder(new String[] {"bash", "-c", cmdline})
                    .redirectErrorStream(true)
                    .directory(new File(directory))
                    .start();

            ArrayList<String> output = new ArrayList<String>();
            BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            String line = null;
            while ( (line = br.readLine()) != null )
                output.add(line);

            //There should really be a timeout here.
            if (0 != process.waitFor())
                return null;

            return output;

        } catch (Exception e) {
            //Warning: doing this is no good in high quality applications.
            //Instead, present appropriate error messages to the user.
            //But it's perfectly fine for prototyping.

            return null;
        }
    }
}
