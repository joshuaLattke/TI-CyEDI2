package MatrizDeAdyacencia.model;

import MatrizDeAdyacencia.model.Color;
import java.util.*;

public class GraphsMatrixAdyacency<T> {
    private int numedges;
    private boolean[][] matrizAdyacency;
    private ArrayList<T> vertex;
    

    public GraphsMatrixAdyacency(int numedges) {
        this.numedges = numedges;
        this.matrizAdyacency = new boolean[numedges][numedges];
        this.vertex = new ArrayList<>();
        
    }

    public void addVertex(T vertice) {
        try {
            vertex.add(vertice);
        } catch( Exception e) {
            System.out.println("Esta lleno");
        }
    }

    public void addEdge(int origin, int destiny) {
        if (origin >= 0 && origin < numedges && destiny >= 0 && destiny < numedges) {
            matrizAdyacency[origin][destiny] = true;
        } else {
            System.out.println("Los índices de los vértices no son válidos.");
        }
    }

    public String showMatriz(){
        String msg="";
        for (int i = 0; i < numedges; i++) {
            for (int j = 0; j < numedges; j++) {
                msg+=(matrizAdyacency[i][j] ? "1 " : "0 ");
            }
            msg+="\n";
        }

        return msg;
    }

    public String toString(){
        String msg="";
        
        for (int i=0;i< vertex.size();i++) {
            msg+=(i+1)+": "+vertex.get(i)+"\n";
        }

        return msg;
    }

    public boolean seeConex(int start, int destiny){
        boolean[] visited = new boolean[numedges];
        dfs(start, visited);
        return visited[destiny];
    }

    public boolean hardlyConnect(){
        for (int i = 0; i < vertex.size(); i++) {
            T tempStart =vertex.get(i);
            for (int j = 0; j < vertex.size(); j++) {
                T tempDestiny =vertex.get(i);
                if(i==j){
                }
                else{
                    if (!seeConex(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void dfs(int vertex, boolean[] visited) {
        visited[vertex] = true;
        
        for (int i = 0; i < numedges; i++) {
            if (matrizAdyacency[vertex][i] && !visited[i]) {
                dfs(i, visited);
            }
        }
    }

    
    public String recorridoBFS(int start) {
        String msg="";
        if (start < 0 || start >= numedges) {
            msg+="Índice de inicio no válido.";
            return msg;
        }


        boolean[] visited = new boolean[numedges];
        Queue<Integer> tail = new LinkedList<>();

        visited[start] = true;
        msg+="Recorrido BFS desde el vértice " + vertex.get(start) + ":";
        msg+=vertex.get(start) + " ";

        tail.add(start);

        while (!tail.isEmpty()) {
            int actualVertex = tail.poll();

            for (int i = 0; i < numedges; i++) {
                if (matrizAdyacency[actualVertex][i] && !visited[i]) {
                    visited[i] = true;
                    msg+=vertex.get(i) + " ";
                    tail.add(i);
                }
            }
        }

        return msg;
    }

}
