package sr.bludots.search;

/* Hier is een app die de shortest route/path gaat bijhouden van het startpunt naar de eindbestemming dmv een array.
 * Er zijn een aantal cities(vertices) met een weight(afstand) die directed geconnect zijn aan elkaar(edges)
 * en dmv van een dijkstra algoritme de shortest route gaat zoeken, opslaan en uit kan printen.
 */

class DistanceParent {              
                                    // items worden opgeslagen in de sPath array(sPath array= shortest path[])
    public int distancePar;         // distance van start naar vertex
    public int parentVert;          // current parent naar vertex

    public DistanceParent(int parentV, int distPar) {
        distancePar = distPar;
        parentVert = parentV;
    }
    
}
class Vertex{
public String stad; // stad (e.g. "Paramaribo")
public boolean isInTree;

public Vertex(String city){
stad = city;
isInTree = false;
}
} 

class Graph {
    private final int MAX_VERTS = 20;       // Max size 
    private final int INFINITY = 1000000;   // als een vertex infinity is, betekent t dat hij unreachable is
    private Vertex vertexList[];            // lijst met vertices
    private int adjMat[][];                 // adjacency matrix
    private int nVerts;                     // current aantal vertices
    private int nTree;                      // aantal vertices in tree
    private DistanceParent sPath[];         // array voor shortest-path data
    private int currentVert;                // current vertex
    private int startToCurrent;             // distance naar currentVert

    public Graph() {                           
        vertexList = new Vertex[MAX_VERTS];    
        adjMat = new int[MAX_VERTS][MAX_VERTS]; // adjacency matrix
        nVerts = 0;
        nTree = 0;

        for (int j = 0; j < MAX_VERTS; j++)         // zet adjacency
            for (int k = 0; k < MAX_VERTS; k++)     // matrix
                adjMat[j][k] = INFINITY;            // naar infinity
        sPath = new DistanceParent[MAX_VERTS];      // shortest paths

    }

    public void addVertex(String city){       //je add je vertices
        vertexList[nVerts++] = new Vertex(city);
    }

    public void addEdge(int start, int end, int weight){
        adjMat[start][end] = weight;     // (geeft aan dat je alleen van start naar end kan gaan en niet terug)
    }                                    // dus directed graph

    public void findShortestPath(int userStartTree) {   // zoek alle shortest paths
        int startTree = userStartTree;
      
        vertexList[startTree].isInTree = true;
        nTree = 1;                                 // if true, zet in tree

        // rij met distances transferen van adjMat naar sPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            sPath[j] = new DistanceParent(startTree, tempDist);
        }
        // als alle vertices al in the tree zijn
        while (nTree < nVerts) {
            int indexMin = getMin();                   // get minimum van sPath
            int minDist = sPath[indexMin].distancePar;
            if (minDist == INFINITY)                   // als allemaal infinite zijn
            {                                          // of in tree,
                System.out.println("There are unreachable vertices");
                break;                                 // sPath is dan compleet
            } else {                                   // reset currentVert naar closest vertex
                currentVert = indexMin;                
                startToCurrent = sPath[indexMin].distancePar;
                // minimum distance van startTree is
                // naar currentVert, en is startToCurrent
            }
            // zet current vertex in tree
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath_shortest();                    // update sPath[] array
        }                                              
        nTree = 0;                                      // delete tree
        for (int j = 0; j < nVerts; j++)
            vertexList[j].isInTree = false;
    }

    public int getMin() {                               // get entry van sPath met minimum distance
        int minDist = INFINITY;                         //  minimum
        int indexMin = 0;

        for (int j = 1; j < nVerts; j++) {              // voor elke vertex, als het in tree is en kleiner dan oude
            if (!vertexList[j].isInTree && sPath[j].distancePar < minDist) {
                minDist = sPath[j].distancePar;
                indexMin = j;                           // update minimum
            }
        }                                           
        return indexMin;                                // return index van minimum
    }

    public void adjust_sPath_shortest() {         
        int column = 1;                           // skip starting vertex

        while (column < nVerts)                   
        {
            // if this column vertex already in tree, skip it
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            // calculate distance van sPath entry
            // get edge van currentVert naar column
            int currentToFringe = adjMat[currentVert][column];

            // add distance van start
            int startToFringe = startToCurrent + currentToFringe;

            // get distance van current sPath entry
            int sPathDist = sPath[column].distancePar;

            // vergelijk distance van start met sPath entry
            if (startToFringe < sPathDist)                         // if shorter,
            {
                // update sPath
                sPath[column].parentVert = currentVert;
                sPath[column].distancePar = startToFringe;
            }
            column++;
        }                                                    
    }                                                        

    public void displayShortestPaths() {
        for (int j = 0; j < nVerts; j++)                          // display inhoud van sPath[]
        {
            System.out.print(vertexList[j].stad + "=");           // stad=
            if (sPath[j].distancePar == INFINITY)
                System.out.print("inf");                          // inf

            else
                System.out.print(sPath[j].distancePar);            // 500

            String parent = vertexList[sPath[j].parentVert].stad;
            System.out.println(" (" + parent + ") ");             // ("Paramaribo")

        }

        System.out.println("");
    }
}
