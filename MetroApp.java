import java.util.*;
import java.io.*;
public class MetroApp {

    public class Vertex{
        HashMap<String,Integer> adj;

        public Vertex(){
            adj = new HashMap<>();
        }
    }

    public enum Metric {
        DISTANCE,
        TIME;
    }

    public HashMap<String, Vertex> vertices = new HashMap<>();

    public MetroApp(){
        vertices = new HashMap<>();
    }

    // numVertex function provides a convenient way to obtain the count of vertices/stations in the graph
    public int numVertex(){
        return vertices.size();
    }

    // Adds a vertex
    public void addVertex (String name){
        Vertex vt = new Vertex();
        vertices.put(name,vt);
    }

    // Returns true if a vertex/station with the given name exists
    public boolean containsVertex(String name){
     return vertices.containsKey(name);
    }

// This method is used to remove a vertex/station from the graph along with all its connected edges.
    public void removeVertex(String name){

        Vertex vtx = vertices.get(name); // Get the vertex object associated with the given vertex name

        ArrayList<String> keys = new ArrayList<>(vtx.adj.keySet()); // Get the list of neighboring vertices

        for(String ks: keys){
            Vertex adjVertex = vertices.get(ks); // Get the neighbor vertex object
            adjVertex.adj.remove(name); // Remove the current vertex/station from the list of neighbors of the neighbor vertex/station
        }
       vertices.remove(name); // Remove the vertex/station from the graph
    }

// This method can be called to obtain the total number of edges present in the graph b/w stations/vertices
    public int numEdges(){

        ArrayList<String> keys = new ArrayList<>(vertices.keySet()); // Get a list of all vertex/station names in the graph
        int count =0;

        for(String ks : keys){
            Vertex vs = vertices.get(ks); // Get the Vertex object associated with the current vertex/station name
            count += vs.adj.size(); // Add the number of neighboring vertices (edges) of the current vertex to the count
        }
        return count/2;  // Since each edge is counted twice (once for each vertex it connects), divide the count by 2
    }

  //  This method can be called to check whether an edge exists between two vertices/stations in the graph.
    public boolean containsEdge(String name1, String name2){

        Vertex vt1 = vertices.get(name1); // Get the Vertex object associated with the first vertex
        Vertex vt2 = vertices.get(name2); // Get the Vertex object associated with the second vertex

        // Check if either of the vertices is null or if the second vertex is not a neighbor of the first vertex
        // Return false if any of the conditions are true, indicating that the edge does not exist
        if(vt1==null || vt2 ==null || !vt1.adj.containsKey(name2)) return false;
        return true;
    }

// This method is used to add an edge between two vertices/stations in the graph along with an associated weight/ distance
    public void addEdge(String name1, String name2, int value){

     Vertex vt1 = vertices.get(name1);
     Vertex vt2 = vertices.get(name2);

      // Check if either of the vertices is null or if an edge between them already exists
     if(vt1==null || vt2==null || vt1.adj.containsKey(name2)) return ;

     // Add an edge from the first vertex/station to the second vertex/station with the specified weight/distance
     vt1.adj.put(name2,value);
     // Add an edge from the second vertex/station to the first vertex/station with the specified weight/distance
     vt2.adj.put(name1,value);
    }

//  This method removes edge b/w two vertices/stations. The two stations are no longer directly connected
    public void removeEdge(String name1 , String name2){
        Vertex vt1 = vertices.get(name1);
        Vertex vt2 = vertices.get(name2);

        if(vt1==null || vt2==null || !vt1.adj.containsKey(name2)) return ;

        vt1.adj.remove(name2);
        vt2.adj.remove(name1);

    }

// This method is called on to display the graph's adjacency list representation
// This method showcases all the routes/edges that exists b/w stations/Vertices and is thus the map of the metro
    public void displayMap(){

        System.out.println("\t Delhi Metro Map");
        System.out.println("\t------------------");
        System.out.println("----------------------------------------------------\n");


        for(String ks : vertices.keySet()){

            String st = ks + " =>\n";
            Vertex vt = vertices.get(ks);

            for (String nb : vt.adj.keySet()){
                st = st + "\t" + nb + "\t";

                if (nb.length()<16) st += "\t";
                if (nb.length()<8) st += "\t";

                st += vt.adj.get(nb) + "\n";
            }
            System.out.println(st);
        }
        System.out.println("\t------------------");
        System.out.println("---------------------------------------------------\n");
    }

// This method displays all Vertex/ Stations that exists
    public void displayStations(){

        System.out.println("\n***********************************************************************\n");

        ArrayList<String> keys = new ArrayList<>(vertices.keySet());

            int i =1;

            for(String ks : keys){
                System.out.println(i + "." + ks);
            i++;
        }
        System.out.println("\n***********************************************************************\n");
    }

 //   This method is typically used to determine whether there is a path between two Stations in the map.
    public boolean hasPath(String name1, String name2, HashMap<String, Boolean> hm ){

        if(containsEdge(name1,name2)) return true; //If there is a direct edge between vname1 and vname2, return true
        hm.put(name1,true);

        Vertex vt = vertices.get(name1);

        ArrayList<String> keys = new ArrayList<>(vt.adj.keySet());
        // TRAVERSE THE NEIGHBOURS OF THE VERTEX
        for(String ks: keys){
            if(!hm.containsKey(ks)){
                if(hasPath(ks,name2,hm)) return true; // Recursively check if there is a path from ks to name2
            }
        }
        return false;   // If no path is found after traversing all neighbors, return false
    }

/*   The DijkstraPair class implements the Comparable interface,
     which allows instances of this class to be compared based on their cost*/
    private class DijkstraPair implements Comparable<DijkstraPair>{

        String name;
        String path; //the path taken to reach this station from the source station.
        int cost;

     /*
     The compareTo method is overridden in the DijkstraPair class because DijkstraPair
     implements the Comparable interface. The purpose of overriding compareTo is to define
     the natural ordering of DijkstraPair objects based on their cost attribute.
     This means that objects with lower costs will have higher priority.
      */
        // override
        public int compareTo(DijkstraPair o)
        {
            return o.cost - this.cost;
        }
    }


  /*  The dijkstra method is an implementation of Dijkstra's algorithm, which is used
    to find the shortest paths from a single source vertex to all other vertices. */

     public int dijkstra(String src, String des, boolean nan ){

        int val =0; // Final result representing the shortest distance or time from source to destination
        ArrayList<String> ans = new ArrayList<>(); //Intermediate result storing the sequence of vertices visited
         HashMap<String, DijkstraPair> map = new HashMap<>(); // Map to store DijkstraPair objects for each vertex
         Heap<DijkstraPair> heap = new Heap<>(); // Priority queue (min heap) for processing vertices based on their costs


         //  Populate the map and heap with initial DijkstraPair objects

         for(String key : vertices.keySet()){

             DijkstraPair dp = new DijkstraPair();
             dp.name = key;
             dp.cost = Integer.MAX_VALUE;

             if(key.equals(src)){
                 dp.cost =0; // Set the cost of the source vertex to 0
                 dp.path = key; // Set the path so far to the source vertex itself
             }
             heap.add(dp); // Add the DijkstraPair object to the heap
             map.put(key,dp); // Add the DijkstraPair object to the map for easy access
         }
         // Main loop: Perform Dijkstra's algorithm until the heap is empty

         while(!heap.isEmpty()){

             DijkstraPair dp2 = heap.remove(); // Extract the vertex with the minimum cost from the heap

             if(dp2.name.equals(des)){
                 val = dp2.cost;
                 break;
             }
             map.remove(dp2.name);
             ans.add(dp2.name);

             // Explore neighbors of the current vertex and update their costs if necessary
            Vertex vt = vertices.get(dp2.name);
            for(String nb : vt.adj.keySet()){
              if(map.containsKey(nb)){
                  int oc = map.get(nb).cost; // Current cost of the neighbor vertex
                  int nc; // New cost after relaxation

                  if(nan){
                      nc = dp2.cost + 120 + 40 * vt.adj.get(nb); // calculate new cost considering nan stations
                  }
                  else{
                      nc = dp2.cost + vt.adj.get(nb); // Calculate new cost without considering nan stations
                  }
                  // If the new cost is less than the current cost, update the DijkstraPair object and heap

                  if(nc<oc){
                      DijkstraPair dp3 = map.get(nb);
                      dp3.path = dp2.path+nb;
                      dp3.cost = nc;
                      heap.updatePriority(dp3);
                  }
              }
            }
         }
        return val;
     }


     private class Pair{
         String name;
         String path;
         int min_dis;
         int min_time;
     }

   /* This method performs a depth-first traversal of the metro network, finding the shortest distance path
    between two given stations. It utilizes a stack for traversal and backtracking and a HashMap to keep
    track of processed stations to avoid revisiting them. */

    public String getMinimumDistanceOrTime(String src, String dest, Metric metric){

        int min = Integer.MAX_VALUE;
        String ans = "";

        HashMap<String, Boolean > processed = new HashMap<>();

        Stack<Pair> st = new Stack<>();

         // create a new pair
        Pair sp = new Pair();

        sp.name = src;
        sp.path = src + "  ";
        sp.min_dis=0;
        sp.min_time=0;

       // put the new pair in queue
        st.push(sp);

        while(!st.isEmpty()){

            Pair sp2 = st.pop();

            if(processed.containsKey(sp2.name)) continue;
            processed.put(sp2.name,true);

            // if there exists a direct edge b/w removed pair and destination vertex
            if(sp2.name.equals(dest)){
                int temp;

                if(metric==Metric.DISTANCE){
                    temp = sp2.min_dis;
                }
                else{
                    temp= sp2.min_time;
                }

                if(temp<min){
                    ans= sp2.path;
                    min = temp;
                }
                continue;
            }

            Vertex vt = vertices.get(sp2.name);

            for(String nb : vt.adj.keySet()){
                // process only unprocessed neighbours
                if(!processed.containsKey(nb)){
                   Pair np = new Pair();
                   np.name = nb;
                   np.path = sp2.path+nb+"  ";
                   np.min_dis = sp2.min_dis + vt.adj.get(nb);
                   np.min_time = sp2.min_time+ 120 + 40*vt.adj.get(nb);
                   st.push(np);
                }
            }
        }

        if(metric==Metric.TIME){
            Double minutes = Math.ceil((double)min / 60);
            ans += Double.toString(minutes);
        }
        else{
            ans += Integer.toString(min);
        }

        return ans;
     }

     public ArrayList<String> getInterchanges(String str){

         ArrayList<String> arr = new ArrayList<>();
         String [] res = str.split("  ");
         arr.add(res[0]); // Add the starting station to the result
         int count =0;  // Initialize interchange count

         for(int i =1;i<res.length-1;i++){
             // Iterate through each station in the path
             int index = res[i].indexOf('~');
             String st = res[i].substring((index+1)); // Extract the line code from the station details

             if(st.length()==2){
                 // If the line code has length 2, it indicates a transfer station
                 String prev = res[i-1].substring(res[i-1].indexOf('~')+1); // Line code of the previous station
                 String next = res[i+1].substring(res[i+1].indexOf('~')+1); // Line code of the next station

                 if(prev.equals(next)){
                     // If the previous and next stations are on the same line, it's not an interchange
                     arr.add(res[i]);
                 }
                 else{
                     // If the previous and next stations are on different lines, it's an interchange
                     arr.add(res[i]+" ==> "+ res[i+1]); // Add the interchange details to the result
//                     i++; // Skip the next station since it's already included in the interchange
                     count++; // Increment interchange count
                 }
             }
         else{
                 // If the line code has length other than 2, it's not an interchange
             arr.add(res[i]);
             }
         }

         arr.add(Integer.toString(count));
         arr.add(res[res.length-1]);
         return arr;
     }



    public void createMetroMap(MetroApp g) {

        g.addVertex("Noida Sector 62~B");
        g.addVertex("Botanical Garden~B");
        g.addVertex("Yamuna Bank~B");
        g.addVertex("Rajiv Chowk~BY");
        g.addVertex("Vaishali~B");
        g.addVertex("Moti Nagar~B");
        g.addVertex("Janak Puri West~BO");
        g.addVertex("Dwarka Sector 21~B");
        g.addVertex("Huda City Center~Y");
        g.addVertex("Saket~Y");
        g.addVertex("Vishwavidyalaya~Y");
        g.addVertex("Chandni Chowk~Y");
        g.addVertex("New Delhi~YO");
        g.addVertex("AIIMS~Y");
        g.addVertex("Shivaji Stadium~O");
        g.addVertex("DDS Campus~O");
        g.addVertex("IGI Airport~O");
        g.addVertex("Rajouri Garden~BP");
        g.addVertex("Netaji Subhash Place~PR");
        g.addVertex("Punjabi Bagh West~P");

        g.addEdge("Noida Sector 62~B", "Botanical Garden~B", 8);
        g.addEdge("Botanical Garden~B", "Yamuna Bank~B", 10);
        g.addEdge("Yamuna Bank~B", "Vaishali~B", 8);
        g.addEdge("Yamuna Bank~B", "Rajiv Chowk~BY", 6);
        g.addEdge("Rajiv Chowk~BY", "Moti Nagar~B", 9);
        g.addEdge("Moti Nagar~B", "Janak Puri West~BO", 7);
        g.addEdge("Janak Puri West~BO", "Dwarka Sector 21~B", 6);
        g.addEdge("Huda City Center~Y", "Saket~Y", 15);
        g.addEdge("Saket~Y", "AIIMS~Y", 6);
        g.addEdge("AIIMS~Y", "Rajiv Chowk~BY", 7);
        g.addEdge("Rajiv Chowk~BY", "New Delhi~YO", 1);
        g.addEdge("New Delhi~YO", "Chandni Chowk~Y", 2);
        g.addEdge("Chandni Chowk~Y", "Vishwavidyalaya~Y", 5);
        g.addEdge("New Delhi~YO", "Shivaji Stadium~O", 2);
        g.addEdge("Shivaji Stadium~O", "DDS Campus~O", 7);
        g.addEdge("DDS Campus~O", "IGI Airport~O", 8);
        g.addEdge("Moti Nagar~B", "Rajouri Garden~BP", 2);
        g.addEdge("Punjabi Bagh West~P", "Rajouri Garden~BP", 2);
        g.addEdge("Punjabi Bagh West~P", "Netaji Subhash Place~PR", 3);
    }
    
    
    
    
    public  String [] printCodeList(){

        System.out.println("List of station along with their codes:\n");
        ArrayList <String> keys = new ArrayList<>(vertices.keySet());

        int i =1,j=0,m=1;
        StringTokenizer st;
        String temp ="";
        String [] codes = new String[keys.size()]; // Array to store the generated station codes
        char c;

        for(String key : keys){
            // Initialize StringTokenizer to split the station name into tokens

        st = new StringTokenizer(key);
          codes[i-1] = ""; // Initialize the code for the current station
          j=0; // Reset the character index

            // Process each token in the station name
            while(st.hasMoreTokens()) {
              temp = st.nextToken(); // Get the next token
              c = temp.charAt(0);  // Get the first character of the token

                // If the character is a digit (0-9), add it to the code.
              while (c > 47 && c < 58) {

                  codes[i-1] += c;
                  j++;
                  c = temp.charAt(j);
              }
                // If the character is a letter, add it to the code
              if ((c < 48 || c > 57) && c < 123) {
                  codes[i - 1] += c;
              }
          }
            // Ensure the generated code has at least 2 characters
              if(codes[i-1].length()<2) codes[i-1]+= Character.toUpperCase(temp.charAt(1));

            // Print the station index and name along with the generated code
                  System.out.print(i + ". " + key + "\t");
            // Adjust the number of tabs based on the length of the station name for proper alignment
                  if(key.length()<(22-m)) System.out.print("\t");
                  if(key.length()<(14-m)) System.out.print("\t");
                  if(key.length()<(6-m)) System.out.print("\t");
                  System.out.println(codes[i-1]);
                  i++;

            // Adjust the tab spacing control variable if the index reaches a power of 10 (10, 100, 1000, etc.)
                  if(i==(int)Math.pow(10,m)) m++;
          }

          return codes;
        }


    public static void main(String[] args) throws IOException {

        MetroDriver.init();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        while(true){
        MetroDriver.showMenu();
        int choice = MetroDriver.getUserChoice(input);
            System.out.print("\n***********************************************************\n");

            if (choice == 6) {
                System.exit(0);
            }
            MetroDriver.processChoice(choice,input);
        }
    }


}
