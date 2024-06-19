package org.georgiydev.graph;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

// Вершины
public class Vertex {
    private final String name; // Имя вершины

    @Getter
    private final double heuristic; // Эвристическая значимость
    @Getter
    private final List<Edge> edges; // Список исходных рёбер

    public Vertex(String name, double heuristic) {
        this.name = name;
        this.heuristic = heuristic;
        this.edges = new ArrayList<>(); // Инициализация списка рёбер
    }

    public void addEdge(Vertex target, double weight) {
        edges.add(new Edge(this, target, weight)); // Добавление ребра в список исходных рёбер
    }

    @Override
    public String toString() {
        return name;
    }
}
