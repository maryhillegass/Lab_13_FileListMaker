import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ListMaker {
    static String currentFile;
    static boolean loadedList = false;


    public static void main(String[] args) {
        //make a looping menu until confirmed quit
        Scanner in = new Scanner(System.in);
        boolean done = false;
        //Create the array list
        ArrayList<String> inputList = new ArrayList<>();
        boolean needsToBeSaved = false;


        do
        {
            displayList(inputList);
            displayMenu();
            String menuInput = SafeInput.getRegExString(in, "Select a menu option", "[AaDdIiVvQqMmOoSsCc]");


            switch (menuInput.toLowerCase())
            {
                case "q":
                    done = SafeInput.getYNConfirm(in, "Are you sure you want to quit?");
                    if(done && needsToBeSaved){
                        if (SafeInput.getYNConfirm(in, "Do you want to save before quitting?")){
                            if(!loadedList){
                                //ask file name
                                currentFile = SafeInput.getNonZeroLenString(in, "Enter a datafile name") + ".txt";
                            }
                            saveFile(inputList,currentFile);
                            needsToBeSaved = false;
                        }
                    }
                    break;

                case "a":
                    inputList.add(SafeInput.getNonZeroLenString(in,"What would you like to add to the end of the list?"));
                    needsToBeSaved = true;
                    break;

                case "d":
                    if (inputList.size() == 0) {
                        System.out.println("There is nothing to delete.");
                        break;
                    }
                    int delLocation = SafeInput.getRangedInt(in, "What position in the list would you like to delete?", 1,inputList.size()) - 1;
                    inputList.remove(delLocation);
                    needsToBeSaved = true;
                    break;

                case "i":
                    if (inputList.size() <= 0){
                        inputList.add(SafeInput.getNonZeroLenString(in,"What would you like to insert to list?"));

                    }else {
                        int insLocation = SafeInput.getRangedInt(in, "What position in the list would you like to insert?", 1, inputList.size()+1) - 1;
                        inputList.add(insLocation, SafeInput.getNonZeroLenString(in, "What would you like to insert into the list?"));
                    }
                    needsToBeSaved = true;
                    break;

                case "v":
                    displayList(inputList);
                    break;

                case "m":
                    if(inputList.size() <= 1){
                        System.out.println("There are not enough items to move in this list.");
                        break;
                    }else{
                        int orgLoc = SafeInput.getRangedInt(in, "What position in the list would you like to move?", 1,inputList.size()) - 1;
                        int newLoc = SafeInput.getRangedInt(in, "What position for the new location?", 1,inputList.size()) - 1;
                        String moving = inputList.get(orgLoc);
                        inputList.remove(orgLoc);
                        inputList.add(newLoc, moving);
                    }
                    needsToBeSaved = true;
                    break;

                case "o":
                    /*if (needsToBeSaved){
                        //save or abandon before opening new list
                        break;
                    }*/
                    if (needsToBeSaved){
                        if (SafeInput.getYNConfirm(in, "Do you want to save before opening a new list?")){
                            if(!loadedList){
                                //ask file name
                                currentFile = SafeInput.getNonZeroLenString(in, "Enter a datafile name") + ".txt";
                            }
                            saveFile(inputList,currentFile);
                            needsToBeSaved = false;
                        }
                    }
                    //open new list

                    //clear list first
                    inputList.clear();
                    inputList = openFile();
                    //record filename
                    loadedList = true;
                    needsToBeSaved = false;
                    break;

                case "c":
                    if (needsToBeSaved){
                        //save or abandon before opening new list
                        if (SafeInput.getYNConfirm(in, "Do you want to save before clearing the list?")){
                            if(!loadedList){
                                //ask file name
                                currentFile = SafeInput.getNonZeroLenString(in, "Enter a datafile name") + ".txt";
                            }
                            saveFile(inputList,currentFile);
                            needsToBeSaved = false;
                        }
                    }
                    //clear list
                    inputList.clear();
                    loadedList = false;
                    needsToBeSaved = false;
                    break;

                case "s":
                    if(!loadedList){
                        //ask file name
                        currentFile = SafeInput.getNonZeroLenString(in, "Enter a datafile name") + ".txt";
                        loadedList = true;
                    }
                    saveFile(inputList,currentFile);
                    needsToBeSaved = false;
                    break;

            }

        } while (!done);
    }


    /*
    Displays the numbered array list
     */
    private static void displayList(ArrayList<String> arrayList){
        System.out.println("Here is your list:");
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println((i+1) + "\t" + arrayList.get(i));
        }
        System.out.println();
    }

    /*
    Displays the menu
     */
    private static void displayMenu(){
        System.out.printf("What would you like to do?" +
                "\nA - Add an item to the list" +
                "\nD – Delete an item from the list" +
                "\nI – Insert an item into the list" +
                "\nM – Move an item" +
                "\nV – View (i.e. display) the list" +
                "\nO – Open a list file from disk" +
                "\nS – Save the current list file to disk" +
                "\nC – Clear removes all the elements from the current list" +
                "\nQ – Quit the program\n");
    }

    /**
     * Loads a list from a file
     */
    private static ArrayList<String> openFile()
    {
        //JFileChooser
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        ArrayList<String> lines = new ArrayList<>();

        try
        {
            //Getting the working directory
            File workingDirectory = new File(System.getProperty("user.dir"));

            //putting the chooser by default in the wd
            chooser.setCurrentDirectory(workingDirectory);

            //All the work goes in the if statement in case that user doesn't pick a file
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                //actually setting the file to selected file
                selectedFile = chooser.getSelectedFile();
                //getting the file path
                Path file = selectedFile.toPath();

                //I'm not sure that I understand the logic here.
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader((new InputStreamReader(in)));

                //Onto reading the file start at line 0
                int line = 0;
                while (reader.ready())
                {
                    //read the line
                    rec = reader.readLine();
                    //add to the array list
                    lines.add(rec);
                    //increment the lines counter
                    line++;
                    //print the read line to the screen
                    //System.out.printf("\nLine %4d %-60s ", line, rec);
                }
                reader.close();

                //Summary
                currentFile = selectedFile.getName();
                System.out.println("\nRead file: " + selectedFile.getName());
                System.out.printf("File has %d lines.\n", line);

            }
            else //not file selected and dialog closed
            {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
            return lines;
        }   //end of try
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
            return lines;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return lines;
        }


    }



    /**
     * Saves the list to a file
     */

    private static void saveFile(ArrayList<String> arrayList, String fileName)
    {
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName);

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!
            for(String a : arrayList)
            {
                writer.write(a, 0, a.length());
                writer.newLine();
            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
