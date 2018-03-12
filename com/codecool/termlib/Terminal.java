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
    }

    public void clearScreen() {
        //System.out.println("HELLO WORLD!");
        command(CONTROL_CODE + CLEAR);
    }

    public void moveTo(Integer x, Integer y) {
    }

    public void setColor(Color color) {
    }

    public void setBgColor(Color color) {
    }

    public void setUnderline() {
    }

    public void moveCursor(Direction direction, Integer amount) {
    }

    public void setChar(char c) {
    }

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
