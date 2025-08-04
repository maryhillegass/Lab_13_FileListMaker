import java.util.ArrayList;
import java.util.Scanner;

public class ListMaker {


    public static void main(String[] args) {
        //make a looping menu until confirmed quit
        Scanner in = new Scanner(System.in);
        boolean done = false;
        //Create the array list
        ArrayList<String> inputList = new ArrayList<>();
        boolean needsToBeSaved = false;
        boolean loadedList = false;

        do
        {
            displayList(inputList);
            displayMenu();
            String menuInput = SafeInput.getRegExString(in, "Select a menu option", "[AaDdIiVvQqMmOoSsCc]");


            switch (menuInput.toLowerCase())
            {
                case "q":
                    if(needsToBeSaved){
                        //save or abandon before quitting
                    }else
                    done = SafeInput.getYNConfirm(in, "Are you sure you want to quit?");
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
                    if (inputList.size() == 0){
                        inputList.add(SafeInput.getNonZeroLenString(in,"What would you like to insert to list?"));
                        needsToBeSaved = true;
                        break;
                    }
                    int insLocation = SafeInput.getRangedInt(in, "What position in the list would you like to insert?", 1,inputList.size()) - 1;
                    inputList.add(insLocation, SafeInput.getNonZeroLenString(in, "What would you like to insert into the list?"));
                    needsToBeSaved = true;
                    break;

                case "v":
                    displayList(inputList);
                    break;

                case "m":
                    needsToBeSaved = true;
                    break;

                case "o":
                    if (needsToBeSaved){
                        //save or abandon before opening new list
                    }
                    //open new list
                    //record filename
                    loadedList = true;
                    needsToBeSaved = false;
                    break;

                case "c":
                    if (needsToBeSaved){
                        //save or abandon before opening new list
                    }
                    //clear list

                    needsToBeSaved = false;
                    break;

                case "s":
                    if(loadedList){
                        //same file name
                    }
                    else{
                        //ask for file name
                    }

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


}
