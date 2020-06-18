import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.collection.mutable.ListBuffer

@SerialVersionUID(123L)
case class Node ( vid: Long, grpid: Long, adj:Array[ Long ])
      extends Serializable {}


object Graph {
  def main(args: Array[ String ]) {
	val conf = new SparkConf().setAppName("Graph")
    conf.setMaster("local[2]")
    val sc = new SparkContext(conf)
    var graph = sc.textFile(args(0)).map( line => {var adj = new ListBuffer[Long]() 
													val a = line.split(",")
													for (i <- 1 until a.length) {
														adj += a(i).toLong
														}
														val adjlist = adj.toList
														(a(0).toLong,a(0).toLong,adjlist)
													} )
    
	
	
	var structure=graph.map(x=>(x._1,x))
	for(i<-1 to 5)
	{ 
		
		var graph1=graph.flatMap(map=>map match{case(a,b,c)=>(a,b)::(for(x <- c) yield (x,b))})
		var graph2=graph1.reduceByKey((a,b)=>(if(a>=b) b else a))
		graph=graph2.join(structure).map(x=>(x._2._2._1,x._2._1,x._2._2._3))
	}	
	
	println("oringnal======")
	graph.collect.foreach(println)
	println("end oringnal======")
	val op=graph.map(x=>(x._2,1)).reduceByKey(_+_).sortByKey()
	val op1=op.map({ case ((m,n))=> m+" "+n})
	val op2 =op1.collect().foreach(println)
	  
  }
}