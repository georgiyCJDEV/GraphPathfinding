package org.georgiydev.graph;

import lombok.Getter;

// Рёбра
public class Edge {
    private final Vertex source; // Начальная вершина ребра

    @Getter
    private final Vertex target; // Конечная вершина ребра
    @Getter
    private final double weight; // Вес ребра

    public Edge(Vertex source, Vertex target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
}