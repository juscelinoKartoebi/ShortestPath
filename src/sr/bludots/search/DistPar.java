package sr.bludots.search;

/* Hier is een app die de shortest route/path gaat bijhouden van het startpunt naar de eindbestemming dmv een array.
 * Er zijn een aantal cities(vertices) met een weight(afstand) die directed geconnect zijn aan elkaar(edges)
 * en dmv van een dijkstra algoritme de shortest route gaat zoeken, opslaan en uit kan printen.
 */

class DistanceParent {              // class of distance and parent
                                    // items stored in sPath array (sPath array= shortest path [])
    public int distancePar;         // distance from start to the vertex
    public int parentVert;          // current parent of the vertex

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
    private final int INFINITY = 1000000;   // if a vertex is infinity, it means it is unreachable
    private Vertex vertexList[];            // list of vertices
    private int adjMat[][];                 // adjacency matrix
    private int nVerts;                     // current number of vertices
    private int nTree;                      // number of vertices in tree
    private DistanceParent sPath[];         // array for shortest-path data
    private int currentVert;                // current vertex
    private int startToCurrent;             // distance to currentVert

    public Graph() {                           
        vertexList = new Vertex[MAX_VERTS];    
        adjMat = new int[MAX_VERTS][MAX_VERTS]; // adjacency matrix
        nVerts = 0;
        nTree = 0;

        for (int j = 0; j < MAX_VERTS; j++)         // set adjacency
            for (int k = 0; k < MAX_VERTS; k++)     // matrix
                adjMat[j][k] = INFINITY;            // to infinity
        sPath = new DistanceParent[MAX_VERTS];      // shortest paths

    }

    public void addVertex(String city){
        vertexList[nVerts++] = new Vertex(city);
    }

    public void addEdge(int start, int end, int weight){
        adjMat[start][end] = weight;     // (can only go one way, not backwards)
    }

    public void findShortestPath(int userStartTree) {   // find all shortest paths
        int startTree = userStartTree;
      
        vertexList[startTree].isInTree = true;
        nTree = 1;                                 // put it in tree

        // transfer row of distances from adjMat to sPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            sPath[j] = new DistanceParent(startTree, tempDist);
        }
        // until all vertices are in the tree
        while (nTree < nVerts) {
            int indexMin = getMin();                   // get minimum from sPath
            int minDist = sPath[indexMin].distancePar;
            if (minDist == INFINITY)                   // if all infinite
            {                                          // or in tree,
                System.out.println("There are unreachable vertices");
                break;                                 // sPath is complete
            } else {                                   // reset currentVert to closest vertex
                currentVert = indexMin;                
                startToCurrent = sPath[indexMin].distancePar;
                // minimum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // put current vertex in tree
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath_shortest();                    // update sPath[] array
        }                                               // end while(nTree<nVerts)
        displayShortestPaths();                                 // display sPath[] contents
        nTree = 0;                                      // clear tree
        for (int j = 0; j < nVerts; j++)
            vertexList[j].isInTree = false;
    }

    public int getMin() {                               // get entry from sPath with minimum distance
        int minDist = INFINITY;                         // assume minimum
        int indexMin = 0;

        for (int j = 1; j < nVerts; j++) {              // for each vertex, if it's in tree and smaller than old one
            if (!vertexList[j].isInTree && sPath[j].distancePar < minDist) {
                minDist = sPath[j].distancePar;
                indexMin = j;                           // update minimum
            }
        }                                               // end for
        return indexMin;                                // return index of minimum
    }

    public void adjust_sPath_shortest() {         // adjust values in shortest-path array sPath
        int column = 1;                           // skip starting vertex

        while (column < nVerts)                   // go across columns
        {
            // if this column vertex already in tree, skip it
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            // calculate distance for one sPath entry
            // get edge from currentVert to column
            int currentToFringe = adjMat[currentVert][column];

            // add distance from start
            int startToFringe = startToCurrent + currentToFringe;

            // get distance of current sPath entry
            int sPathDist = sPath[column].distancePar;

            // compare distance from start with sPath entry
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
        for (int j = 0; j < nVerts; j++)                          // display contents of sPath[]
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
