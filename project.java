import java.util.*;

public class project {

    public static class Edge {  //edge class
        int src;
        int dest;
        double kilom;

        public Edge(int s, int d, double km) {
            this.src = s;
            this.dest = d;
            this.kilom = km;
        }
    }

    static void createGraph(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>(); //create AL at every i
        }
        
        graph[1].add(new Edge(1, 2, 1.8));
        graph[1].add(new Edge(1, 3, 1.7));
        graph[1].add(new Edge(1, 8, 2.4));

        graph[2].add(new Edge(2, 4, 1.4));
        graph[2].add(new Edge(2, 7, 4.13));

        graph[3].add(new Edge(3, 4, 1.8));
        graph[3].add(new Edge(3, 5, 1.5));

        graph[4].add(new Edge(4, 6, 1.3));

        graph[5].add(new Edge(5, 6, 1.6));
        graph[5].add(new Edge(5, 9, 2.9));
        graph[5].add(new Edge(5, 10, 0.25));

        graph[6].add(new Edge(6, 7, 1.1));

        graph[8].add(new Edge(8, 9, 1.1));

        graph[10].add(new Edge(10, 11, 0.45));
        graph[10].add(new Edge(10, 12, 0.9));
        graph[10].add(new Edge(10, 24, 0.65));

        graph[11].add(new Edge(11, 14, 1));
        graph[11].add(new Edge(11, 24, 0.4));

        graph[12].add(new Edge(12, 13, 0.75));
        graph[12].add(new Edge(12, 25, 0.27));

        graph[13].add(new Edge(13, 15, 4));
        graph[13].add(new Edge(13, 16, 0.6));
        graph[13].add(new Edge(13, 25, 0.6));

        graph[14].add(new Edge(14, 17, 0.65));

        graph[15].add(new Edge(15, 16, 3.7));
        graph[15].add(new Edge(15, 18, 1.1));
        graph[15].add(new Edge(15, 19, 1.1));

        graph[16].add(new Edge(16, 17, 0.6));
        graph[16].add(new Edge(16, 18, 1.29));
        graph[16].add(new Edge(16, 25, 0.6));

        graph[17].add(new Edge(17, 24, 0.7));
        graph[17].add(new Edge(17, 25, 0.65));

        graph[18].add(new Edge(18, 19, 1.3));
        graph[18].add(new Edge(18, 20, 0.6));
        graph[18].add(new Edge(18, 22, 0.62));

        graph[19].add(new Edge(19, 21, 0.65));

        graph[20].add(new Edge(20, 21, 0.35));
        graph[20].add(new Edge(20, 23, 0.4));

        graph[21].add(new Edge(21, 23, 0.35));

        graph[22].add(new Edge(22, 23, 1.25));

        graph[24].add(new Edge(24, 25, 0.65));
    }

    public static boolean haspath(ArrayList<Edge>[] graph,int src,int dest,boolean vis[]){
        if(src==dest){
            return true;
        }
        vis[src] = true;
        for(int i=0; i<graph[src].size(); i++){
            Edge e = graph[src].get(i);
            if(!vis[e.dest] && haspath(graph,e.dest,dest,vis)){
                return true ;
            }
        }
        return false;
    }

    public static void printpath(ArrayList<Edge>[] graph,int src,int dest,String path){
        if(src==dest){
            System.out.println(path+dest); //path + dest_node
            return;
        }

        for(int i=0; i<graph[src].size(); i++){ //neigh of src
            Edge e = graph[src].get(i);
            printpath(graph, e.dest, dest, path+src+"->");
        }
    }

    static class pair implements Comparable<pair>{
        int node; 
        double path;

        public pair(int node,double path){
            this.node=node;
            this.path=path;
        }

        @Override
        public int compareTo(pair p2){
            return Double.compare(this.path, p2.path); //ascending sorting
        }
    }

    public static void dijkstra(ArrayList<Edge>[] graph, int src, int dest){
        double dist[] = new double[graph.length]; //dist[i]=src to i ka distance

        int parent[] = new int[graph.length]; // To store shortest path
        Arrays.fill(parent, -1);

        for(int i=0; i<graph.length; i++){
            if(i != src){
                dist[i] = Double.MAX_VALUE; //+infinite except 0
            }
        }

        boolean vis[] = new boolean[graph.length];
        PriorityQueue<pair> pq = new PriorityQueue<>();
        pq.add(new pair(src, 0.0));
        //loop
        while(!pq.isEmpty()){
            pair curr = pq.remove(); 
            if(!vis[curr.node]){
                vis[curr.node] = true;
                //curr node neighbour
                for(int i=0; i<graph[curr.node].size(); i++){ 
                    Edge e = graph[curr.node].get(i);
                    int u = e.src;
                    int v = e.dest;
                    double dis = e.kilom;
                    if(dist[u]+dis < dist[v]){
                        dist[v] = dist[u]+dis;
                        pq.add(new pair(v, dist[v]));
                        parent[v] = curr.node; //store the predecor
                    }
                }
            }
        }

        
        System.out.println("\nshortest distances from AIIMS:");
        for (int i = 1; i < dist.length; i++) {
            System.out.println("distance to " + i + " : " + dist[i]+" km");
        }

        
        System.out.println("\nshortest path from AIIMS to Connaught place:");
        printShortestPath(parent, dest, dist);
    }

    
    public static void printShortestPath(int parent[], int dest, double dist[]) {
        if (parent[dest] == -1) {
            System.out.println("no path exists");
            return;
        }

        ArrayList<Integer> path = new ArrayList<>();
        int curr = dest;

        while (curr != -1) {
            path.add(curr);
            curr = parent[curr];
        }

        Collections.reverse(path);
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i)+"->");
        }
        System.out.println();
        System.out.println("which is : "+dist[dest]+" km");
    }

    public static void handleTraffic(ArrayList<Edge>[] graph, HashMap<String, Double> trafficMap, double threshold) {
        for (int i = 0; i < graph.length; i++) {
            graph[i].removeIf(edge -> trafficMap.getOrDefault(edge.src + "-" + edge.dest, 0.0) > threshold);
        }
    }

    public static void handleRoadClosure(ArrayList<Edge>[] graph, int src, int dest) {
        graph[src].removeIf(edge -> edge.dest == dest);
    }

    public static void calInd(ArrayList<Edge>[] graph, int indeg[]){
        for(int i=0; i<graph.length; i++){ //'i' is vertex
            for(int j=0; j<graph[i].size(); j++){  //'j' is dest 
                Edge e = graph[i].get(j);
                indeg[e.dest]++;
            }
        }

        int maxIndex = 0, maxValue = 0;
        for (int i = 0; i < indeg.length; i++) {
            if (indeg[i] > maxValue) {
                maxValue = indeg[i];
                maxIndex = i;
            }
        }
        System.out.println("central hub is "+maxIndex+" with "+maxValue+" incoming edge");
    }

    public static double bfalgo(ArrayList<Edge>[] graph, int src, int dest){
        double dist[] = new double[graph.length]; //dist[i]->src to i ka distance

        int parent[] = new int[graph.length]; // to store shortest path
        Arrays.fill(parent, -1);

        for(int i=0; i<graph.length; i++){
            if(i != src){
                dist[i] = Double.MAX_VALUE; //+infinite except 0
            }
        }
        //algo
        int V = graph.length;
        //O(V*E)
        for(int i=0; i<V-1; i++){  //O(V)
            //edge - O(E)
            for(int j=0; j<graph.length; j++){
                for(int k=0; k<graph[j].size(); k++){
                    Edge e = graph[j].get(k);
                    int u = e.src;
                    int v = e.dest;
                    double dis = e.kilom;
                    //relaxation step
                    if(dist[u] + dis < dist[v]){
                        dist[v] = dist[u] + dis;
                        parent[v] = u; //store the predecor
                    }
                }
            }
        }
        System.out.println("\nshortest path from ambulance"+src+" to "+dest);
        printShortestPath(parent, dest, dist);
        return dist[dest];
    }

    public static void handlewind(ArrayList<Edge>[] graph, HashMap<String, Double> windMap, double threshold) {
        for (int i = 0; i < graph.length; i++) {
            graph[i].removeIf(edge -> windMap.getOrDefault(edge.src + "-" + edge.dest, 0.0) > threshold);
        }
    }

    public static void removeVertex(ArrayList<Edge>[] graph, int vertex) {
        graph[vertex].clear(); // remove all outgoing edges
        for (int i = 0; i < graph.length; i++) {
            graph[i].removeIf(edge -> edge.dest == vertex); // remove all incoming edges
        }
    }

    public static void handleterran(ArrayList<Edge>[] graph, HashMap<String, Double> terranMap) {
        for (int i = 0; i < graph.length; i++) {
            graph[i].removeIf(edge -> terranMap.getOrDefault(edge.src + "-" + edge.dest, 0.0) == 999);
        }
    }

    public static void main(String[] args) {
        
        int v = 26; // my total vertices + 1(for AL)

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[v]; /*AL of edgearray
        defining graph*/
        createGraph(graph);
  
        //handle traffic
        //keys->"src-dest"&values-traffic levels.
        HashMap<String, Double> trafficMap = new HashMap<>();
        trafficMap.put("1-2", 8.0); //this remove greater than 5
        trafficMap.put("1-3", 2.0);
        trafficMap.put("5-10", 4.0);
        trafficMap.put("10-12", 1.0);

        double trafficThreshold = 5.0;
        handleTraffic(graph, trafficMap, trafficThreshold);

        // handle road closure
        handleRoadClosure(graph, 13, 16); 

        System.out.println("path available from AIIMS to Connaught place:");
        System.out.println(haspath(graph, 1, 23, new boolean[v]));

        System.out.println("\nall path from AIIMS to Connaught place:");
        printpath(graph, 1, 23, " ");

        dijkstra(graph, 1, 23);

        //central hub
        //most in-degree vertex
        System.out.println();
        int indeg[] = new int[graph.length] ;
        calInd(graph, indeg);

        //...mult. ambulance dispatch using bellman ford algo.
        System.out.println();
        System.out.println("....mult. ambulance dispatch....");
        double d1 = bfalgo(graph, 15, 23);
        double d2 = bfalgo(graph, 16, 23);
        double d3 = bfalgo(graph, 21, 23);

        double minDist = Math.min(d1, Math.min(d2, d3));
        if (minDist == d1) System.out.println("**ambulance from 15 reached first in " + d1 + " km**");
        if (minDist == d2) System.out.println("**ambulance from 16 reached first in " + d2 + " km**");
        if (minDist == d3) System.out.println("**ambulance from 21 reached first in " + d3 + " km**");
        
        //...25 as a danger pron area
        System.out.println("\n....25 as a danger pron area....");
        bfalgo(graph, 11, 25);

        //...drone route from 18 to 23 which also see wind, weather, geographic cond.
        System.out.println("\n....rescueDrone route...");

        //wind condi
        System.out.println("\n1.drone optimal path after see wind condi ");
        HashMap<String, Double> windMap = new HashMap<>();
        windMap.put("22-23", 8.0); //this remove greater than 5
        windMap.put("20-23", 2.0);
        windMap.put("21-23", 4.0);

        double windThreshold = 5.0;
        handlewind(graph, windMap, windThreshold);

        //wheather condi
        int vrtx = 20; 
        System.out.println("2.removing vertex due to Rain/Storm");
        removeVertex(graph, vrtx);

        //geographic condi
        System.out.println("3.removing edge due to geographical terrain");
        HashMap<String, Double> terranMap = new HashMap<>();
        terranMap.put("21-23", 999.0);
        handleterran(graph, terranMap);

        bfalgo(graph, 18, 23);


    }
}
