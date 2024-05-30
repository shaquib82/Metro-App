
import java.util.*;
import java.io.*;

public class MetroDriver {

    private static MetroApp ma;

    public static void init() throws IOException{
        ma = new MetroApp();
        ma.createMetroMap(ma);

        System.out.println("\n\t\t\t****WELCOME TO THE METRO APP*****");

    }

    public static void showMenu(){
        System.out.println("\t\t\t\t~~LIST OF ACTIONS~~\n\n");
        System.out.println("1. LIST ALL THE STATIONS IN THE MAP");
        System.out.println("2. SHOW THE METRO MAP");
        System.out.println("3. GET SHORTEST DISTANCE FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
        System.out.println("4. GET SHORTEST TIME TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
        System.out.println("5. GET SHORTEST PATH (DISTANCE WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
        System.out.println("6. GET SHORTEST PATH (TIME WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
        System.out.println("7. EXIT THE MENU");
        System.out.print("\nENTER YOUR CHOICE FROM THE ABOVE LIST (1 to 7) : ");
    }

    public static int getUserChoice(BufferedReader input ) throws IOException{

        int choice = -1;
        try{
            choice = Integer.parseInt(input.readLine());
        }
        catch (Exception e) {
            System.out.println("Error");
        }

        return choice;

    }


    public static void processChoice(int choice, BufferedReader input) throws IOException{
        switch(choice){

            case 1 :
                displayStations();
                break;
            case 2 :
                displayMap();
                break;
            case 3:
                getShortestDistance(input);
                break;
            case 4:
                getShortestTime(input);
                break;
            case 5:
                getShortestPathDistanceWise(input);
                break;
            case 6:
                getShortestPathTimeWise(input);
                break;
            case 7:
                System.exit(0);
                break;
            default:
                System.out.println("Please enter a valid option!");
                System.out.println("The options you can choose are from 1 to 7.");
        }
    }

    private static void displayStations() {
        ma.displayStations();
    }

    private static void displayMap() {
        ma.displayMap();
    }

    private static void getShortestDistance(BufferedReader input)  throws IOException{
        ArrayList<String> keys = new ArrayList<>(ma.vertices.keySet());
        String [] codes = ma.printCodeList();
        System.out.println("\n1. TO ENTER SERIAL NO. OF STATIONS\n2. TO ENTER CODE OF STATIONS\n3. TO ENTER NAME OF STATIONS\n");
        System.out.println("ENTER YOUR CHOICE:");
        int ch = Integer.valueOf(input.readLine());

        String st1 ="", st2="";
        System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
        if(ch==1){
            st1 = keys.get(Integer.parseInt(input.readLine())-1);
            st2 = keys.get(Integer.parseInt(input.readLine())-1);
        }
        else if(ch==2){
            String a, b;
            a = (input.readLine()).toUpperCase();
            st1 = getStationByCode(keys,codes,a);
            b = (input.readLine()).toUpperCase();
            st2 = getStationByCode(keys,codes,b);
        }
        else if(ch==3){
            st1 = input.readLine();
            st2 = input.readLine();
        }
        else{
            System.out.println("Invalid Choice");
            System.exit(0);
        }

        HashMap<String,Boolean> processed = new HashMap<>();

        if(!ma.containsVertex(st1) || !ma.containsVertex(st2) || !ma.hasPath(st1,st2,processed)){
            System.out.println("THE INPUTS ARE INVALID");
        }
        else{
            System.out.println("SHORTEST DISTANCE FROM " + st1 + " TO " + st2 + " IS " + ma.dijkstra(st1, st2, false) + "KM\n");
        }
    }

    private static void getShortestTime(BufferedReader input) throws IOException{
        ArrayList<String> keys = new ArrayList<>(ma.vertices.keySet());
        String [] codes = ma.printCodeList();

        System.out.println("\n1. TO ENTER SERIAL NO. OF STATIONS\n2. TO ENTER CODE OF STATIONS\n3. TO ENTER NAME OF STATIONS\n");
        System.out.println("ENTER YOUR CHOICE:");
        int ch = Integer.valueOf(input.readLine());

        String st1 ="", st2="";
        System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
        if(ch==1){
            st1 = keys.get(Integer.parseInt(input.readLine())-1);
            st2 = keys.get(Integer.parseInt(input.readLine())-1);
        }
        else if(ch==2){
            String a, b;
            a = (input.readLine()).toUpperCase();
            st1 = getStationByCode(keys,codes,a);
            b = (input.readLine()).toUpperCase();
            st2 = getStationByCode(keys,codes,b);
        }
        else if(ch==3){
            st1 = input.readLine();
            st2 = input.readLine();
        }
        else{
            System.out.println("Invalid Choice");
            System.exit(0);
        }

        System.out.println("SHORTEST TIME FROM (" + st1 + ") TO (" + st2 + ") IS " + ma.dijkstra(st1, st2, true) / 60 + " MINUTES\n\n");
    }

    private static void getShortestPathDistanceWise(BufferedReader input) throws IOException{

        ArrayList<String> keys = new ArrayList<>(ma.vertices.keySet());
        String [] codes = ma.printCodeList();

        System.out.println("\n1. TO ENTER SERIAL NO. OF STATIONS\n2. TO ENTER CODE OF STATIONS\n3. TO ENTER NAME OF STATIONS\n");
        System.out.println("ENTER YOUR CHOICE:");
        int ch = Integer.valueOf(input.readLine());

        String st1 ="", st2="";
        System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
        if(ch==1){
            st1 = keys.get(Integer.parseInt(input.readLine())-1);
            st2 = keys.get(Integer.parseInt(input.readLine())-1);
        }
        else if(ch==2){
            String a, b;
            a = (input.readLine()).toUpperCase();
            st1 = getStationByCode(keys,codes,a);
            b = (input.readLine()).toUpperCase();
            st2 = getStationByCode(keys,codes,b);
        }
        else if(ch==3){
            st1 = input.readLine();
            st2 = input.readLine();
        }
        else{
            System.out.println("Invalid Choice");
            System.exit(0);
        }

        HashMap<String, Boolean> processed = new HashMap<>();

        if(!ma.containsVertex(st1) || !ma.containsVertex(st2) || !ma.hasPath(st1,st2,processed)){
            System.out.println("THE INPUTS ARE INVALID");
        }
        else{
            ArrayList<String> str = new ArrayList<>(ma.getInterchanges(ma.getMinimumDistance(st1,st2)));
            int len = str.size();
            System.out.println("SOURCE STATION : " + st1);
            System.out.println("DESTINATION STATION : " + st2);
            System.out.println("DISTANCE : " + str.get(len - 1));
            System.out.println("NUMBER OF INTERCHANGES : " + str.get(len - 2));
            System.out.println("~~~~~~~~~~~~~");
            System.out.println("START  ==>  " + str.get(0) + "\t");
            for (int i = 1; i < len - 3; i++) {
                System.out.println(str.get(i));
            }
            System.out.print(str.get(len - 3) + "   ==>    END");
            System.out.println("\n~~~~~~~~~~~~~");
        }
    }

    private static void getShortestPathTimeWise(BufferedReader input) throws IOException{
        ArrayList<String> keys = new ArrayList<>(ma.vertices.keySet());
        String [] codes = ma.printCodeList();

        System.out.println("\n1. TO ENTER SERIAL NO. OF STATIONS\n2. TO ENTER CODE OF STATIONS\n3. TO ENTER NAME OF STATIONS\n");
        System.out.println("ENTER YOUR CHOICE:");
        int ch = Integer.valueOf(input.readLine());

        String st1 ="", st2="";
        System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
        if(ch==1){
            st1 = keys.get(Integer.parseInt(input.readLine())-1);
            st2 = keys.get(Integer.parseInt(input.readLine())-1);
        }
        else if(ch==2){
            String a, b;
            a = (input.readLine()).toUpperCase();
            st1 = getStationByCode(keys,codes,a);
            b = (input.readLine()).toUpperCase();
            st2 = getStationByCode(keys,codes,b);
        }
        else if(ch==3){
            st1 = input.readLine();
            st2 = input.readLine();
        }
        else{
            System.out.println("Invalid Choice");
            System.exit(0);
        }

        HashMap<String, Boolean> processed = new HashMap<>();
        if (!ma.containsVertex(st1) || !ma.containsVertex(st2) || !ma.hasPath(st1, st2, processed)) {
            System.out.println("THE INPUTS ARE INVALID");
        }
        else{
            ArrayList<String> str = ma.getInterchanges(ma.getMinimumTime(st1,st2));
            int len = str.size();
            System.out.println("SOURCE STATION : " + st1);
            System.out.println("DESTINATION STATION : " + st2);
            System.out.println("TIME : " + str.get(len - 1) + " MINUTES");
            System.out.println("NUMBER OF INTERCHANGES : " + str.get(len - 2));
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("START  ==>  " + str.get(0) + " ==>  ");

            for(int i =1;i<len-3;i++){
                System.out.println(str.get(i));
            }
            System.out.print(str.get(len - 3) + "   ==>    END");
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    private static String getStationByCode(ArrayList<String> keys, String [] codes, String code){

        for(int j =0; j<keys.size();j++){
            if(code.equalsIgnoreCase(codes[j])){
                return keys.get(j);
            }
        }
        return "";
    }


}


