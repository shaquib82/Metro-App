DELHI METRO APPLICATION

Description
MetroApp is a Java-based application designed to manage and navigate a metro system efficiently. It uses a custom heap implementation to optimize route planning, ensuring the shortest path in terms of both distance and time. The application supports various functionalities such as displaying the metro map, listing stations, and finding the shortest path between stations.

The idea is implemented using Graph and Heap data structures. The graph has nodes and edges. Nodes represent a metro station that will be containing certain information regarding that station like its name, its metro corridor, and the lines which it connects. Edges (the connection between two nodes) represent the distance between the two stations and the cost of each edge will be equal to the distance between the two of its connecting stations(nodes). 

By using different algorithms like Dijkstra, breadth-first search, depth-first search, etc, the shortest path between the source station and the destination station is determined, and accordingly, the fare is being calculated on the basis of the total distance between the two stations. Finally, the metro route between the two stations and the total fare is displayed.


Features

List All Stations: Display all stations in the metro map.
Show Metro Map: Visualize the entire metro network.
Shortest Distance: Calculate the shortest distance between two stations.
Shortest Time: Find the quickest route between two stations.
Shortest Path (Distance-wise): Get the shortest path based on distance.
Shortest Path (Time-wise): Get the shortest path based on travel time.

Installation

To get started with MetroApp, follow these steps:

Clone the Repository:

git clone https://github.com/shaquib82/MetroApp.git
cd MetroApp

Compile the Java Files:
javac -d bin src/*.java

Run the Application:
java -cp bin MetroDriver


Usage
Initialize the MetroApp:

Run the MetroDriver class to start the application.
The application will initialize the metro map and display a welcome message.
Navigate Through the Menu:

The main menu provides various options to interact with the metro map:
- List all stations
- Show the metro map
- Get the shortest distance between stations
- Get the shortest time to reach between stations
- Get the shortest path (distance-wise)
- Get the shortest path (time-wise)
- Exit the application

Example Commands:

To list all stations, choose option 1.
To find the shortest distance between two stations, choose option 3 and follow the prompts.


##  REQUIREMENTS

> The project can run on any online or offline Integrated Development Environment (IDE) like Eclipse, Netbeans, ideone.com, etc.
> You should have at least elementary knowledge of Java Programming language to work on the project.
> Knowledge of data structures like Graph and Heap and Algorithms like Dijkstra, BFS, DFS, etc is appreciated, however, it is not essential.



