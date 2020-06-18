# Graph-processing
Graph-processing using Spark in Scala

This project takes an undirected graph as input from a text file where each line represents a vertex and the vertices it is connected to. for ex a line in the input like:
1,2,3,4,5,6,7 
represents that vertex with ID 1 is connected to vertices with ID's 2,3,4,5,6 and 7.

The project finds connected components in the undirected graph and prints the size of these connected components. A connected component of a graph is a subgraph of the graph in which there is a path from any two vertices in the subgraph.
The project is using Spark framework in Scala.
A graph is represented as RDD[ ( Long, Long, List[Long] ) ], where the first Long is the graph node ID, the second Long is the group that this vertex belongs to and the List[Long] is the adjacent list (the IDs of the neighbors)

Following is the pseudo-code of how the project works:
var graph = /* read the graph from args(0); the group of a graph node is set to the node ID */

for (i <- 1 to 5)
   graph = graph.flatMap{ /* associate each adjacent neighbor with the node group number + the node itself with its group number*/ }
                .reduceByKey( /* get the min group of each node */ )
                .join( /* join with the original graph */ )
                .map{ /* reconstruct the graph topology */ }

/* finally, print the group sizes */
