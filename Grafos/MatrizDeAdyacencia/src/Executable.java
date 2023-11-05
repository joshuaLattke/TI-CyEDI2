import java.util.*;

import MatrizDeAdyacencia.model.GraphsMatrixAdyacency;

public class Executable {

	private Scanner input;
	private GraphsMatrixAdyacency<String> graph;

	public Executable(){
		input = new Scanner(System.in);
	}

	public static void main(String[] args) {	
		Executable executable = new Executable();
		executable.menu();
    }
	
	public void menu() {
		boolean indicator=false;
		System.out.println("\nGrafos");
		System.out.println("\n escoja el tamaño de su grafo: ");
        int size = input.nextInt();
        graph = new GraphsMatrixAdyacency<>(size);
        
		while(!indicator){
			System.out.println("\nMenu Grafos");
			System.out.println("Escoja que desea hacer:");
			System.out.println("1. Añadir vertices");
			System.out.println("2. Añadir Aristas");
            System.out.println("3. Mostrar matriz de Adyacencia");
			System.out.println("4. Hacer recorrido BFS desde un vertice");
            System.out.println("5. Mirar si se es fuertemente conexo");
			System.out.println("10. Testing");
            
			System.out.println("0. Salir");
			int option = input.nextInt();
			input.nextLine();

			switch (option) {

				case 1:
					System.out.println("Introduzca el nombre del vertice");
					String vertex = input.nextLine();
                    graph.addVertex(vertex);
					
					
				break;

				case 2:
					int origin=0,destiny=0;
					System.out.println(graph.toString());
                    System.out.println("Introduzca el vertice de origen");

					try {
						origin = input.nextInt()-1;
					} catch (Exception e) {
						System.out.println("Se introdujo un dato erroneo");
						System.exit(0);
					}


					System.out.println("Introduzca el vertice de destino");


					try {
						destiny = input.nextInt()-1;
					} catch (Exception e) {
						System.out.println("Se introdujo un dato erroneo");
						System.exit(0);
					}
                    
                    graph.addEdge(origin, destiny);

				break;

				case 3:
					System.out.println(graph.showMatriz());
					
				break;

				case 4:
					int choice;
					System.out.println(graph.toString());
					System.out.println("Introduzca el vertice desde el que quiere hacer BFS Por numero");
					try {
						choice = input.nextInt()-1;
						System.out.println(graph.recorridoBFS(choice));
						
					} catch (Exception e) {
						System.out.println("Se introdujo un dato erroneo");
						System.exit(0);
					}
					
				break;

				case 5:
					if(graph.hardlyConnect()){
						System.out.println("el grafo es fuertemente conexo");
					}else{
						System.out.println("el grafo no es fuertemente conexo");
					}
				break;

				case 10:
					int choiceTest=0;
					System.out.println("Testing");
					System.out.println("1: Linea Simple de 5");
					System.out.println("2: Ciclo de 5 ");
					try {
						choiceTest = input.nextInt();
						
					} catch (Exception e) {
						System.out.println("Se introdujo un dato erroneo");
						System.exit(0);
					}
					switch (choiceTest) {
						case 1:
							testing1();
						break;
						

						case 2:
							testing2();
						break;


						default:
							System.out.println("Esa no es una opcion!");
						break;
					}
				break;

				case 0:
					indicator=true;
				break;


				default:
					System.out.println("Esa no es una opcion!");
				break;
			}
		}
	}

	private void testing2() {
		graph = new GraphsMatrixAdyacency<>(5);
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");

		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 4);
		graph.addEdge(4, 0);
	}

	private void testing1() {

		graph = new GraphsMatrixAdyacency<>(5);
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");

		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 4);
		
	}
}
 