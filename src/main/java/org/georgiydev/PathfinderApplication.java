package org.georgiydev;
import lombok.extern.log4j.Log4j;
import  org.georgiydev.algorithms.*;
import org.georgiydev.graph.*;

import java.util.*;

@Log4j
public class PathfinderApplication {
    public static void main(String[] args) {
        AStar aStar = new AStar();
        // Пример задания вершин и ребер
        aStar.addVertex("A", 10);
        aStar.addVertex("B", 8);
        aStar.addVertex("C", 5);
        aStar.addVertex("D", 7);
        aStar.addVertex("E", 3);
        aStar.addVertex("F", 6);
        aStar.addVertex("G", 5);
        aStar.addVertex("H", 9);
        aStar.addVertex("I", 4);
        aStar.addVertex("J", 2);
        aStar.addVertex("K", 8);
        aStar.addVertex("L", 6);
        aStar.addVertex("M", 4);
        aStar.addVertex("N", 3);
        aStar.addVertex("O", 7);

        aStar.addEdge("A", "B", 1);
        aStar.addEdge("A", "C", 4);
        aStar.addEdge("B", "D", 2);
        aStar.addEdge("B", "E", 5);
        aStar.addEdge("C", "F", 3);
        aStar.addEdge("C", "G", 6);
        aStar.addEdge("D", "H", 3);
        aStar.addEdge("E", "I", 2);
        aStar.addEdge("F", "J", 4);
        aStar.addEdge("G", "K", 1);
        aStar.addEdge("H", "L", 6);
        aStar.addEdge("I", "M", 5);
        aStar.addEdge("J", "N", 3);
        aStar.addEdge("K", "O", 7);

        // Задание начальной и конечной вершин
        String start = "A";
        String goal = "M";

        // Поиск пути
        List<Vertex> path = aStar.findPath(start, goal);

        // Вывод результата
        if (path != null) {
            log.debug("Path found: " + path);
            log.debug("Total path cost: " + aStar.getTotalCost(aStar.getGraph().get(goal)));
        } else {
            log.error("No path found");
        }
    }
}