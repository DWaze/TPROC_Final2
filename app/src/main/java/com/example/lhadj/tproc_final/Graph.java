package com.example.lhadj.tproc_final;

import java.util.ArrayList;
 
 class Graph{
    
 
    int V, C;
    Chemin chemin[];
	public int u;
	public int v;
    ArrayList<dist_com> dist;
 
    // Creates a graph with V vertices and E edges
    Graph(int v, int c){
        V = v;// nombre des points 
        C = c;// nombre des chemins
        chemin = new Chemin[c];
        for (int i=0; i<c; ++i){chemin[i] = new Chemin();}
            
    }
 
    // minimum dist entre src et touts les autres vertex
    void BellmanFord(Graph graph,int src)
    {
        int V = graph.V;// nombre des points
        int C = graph.C;// nombre des chemins
        dist = new ArrayList<dist_com>();


//        int point[] = new int[V];
        // Step 1: Initialization distances 
        for (int i=0; i<V; ++i){
            dist.add(new dist_com());
            dist.get(i).dist = Integer.MAX_VALUE;/*infinit*/
        }
        dist.get(src).dist = 0;
 
        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges

        for (int i=1; i<V; ++i){
        	
            for (int j=0; j<C; ++j)
            {
                u = graph.chemin[j].src;
                v = graph.chemin[j].dest;
                int weight = graph.chemin[j].poid;
                
                if (dist.get(u).dist!=Integer.MAX_VALUE &&  dist.get(u).dist+weight<dist.get(v).dist){
                	dist.get(v).dist=dist.get(u).dist+weight;
                    dist.get(v).src=u;
                }
            }
            
        }
       
 
       // traitment des negatives
        for (int j=0; j<C; ++j)
        {
            int u = graph.chemin[j].src;
            int v = graph.chemin[j].dest;
            int weight = graph.chemin[j].poid;
            if (dist.get(u).dist!=Integer.MAX_VALUE && dist.get(u).dist+weight<dist.get(v).dist)
              System.out.println("Graph a un cycle des poid negatif");
        }

    }
 
    
  
    void printDistance(ArrayList<dist_com> dist, int V)
    {
    	System.out.println("\nExecution:\n");

    	 System.out.println("| Vertex | Distance | Source");
        for (int i=0; i<V; ++i){
        	  
        	if(dist.get(i).dist==2147483647){
        		System.out.println("| "+i+"      | "+"INFINI"+"        ");
        	}else{
        		System.out.println("| "+i+"      | "+dist.get(i).dist+"       |   "+dist.get(i).src);
        	}
        }
    }
}