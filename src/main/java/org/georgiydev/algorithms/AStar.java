package org.georgiydev.algorithms;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.georgiydev.graph.*;

import java.util.*;


  // Класс, реализующий алгоритм A*
@Log4j
public class AStar {
      @Getter private final Map<String, Vertex> graph; // Граф в виде мапы
      private final Map<Vertex, Vertex> cameFrom; // Мапа для отслеживания пути
      private final Map<Vertex, Double> gScore; // Оценка стоимости пути от начальной вершины
      private final Map<Vertex, Double> fScore; // Оценка стоимости пути с учетом эвристики
      private final PriorityQueue<Vertex> openSet; // Открытый набор для обработки вершин

      // Конструктор для инициализации необходимых структур данных
      public AStar() {
          graph = new HashMap<>();
          cameFrom = new HashMap<>();
          gScore = new HashMap<>();
          fScore = new HashMap<>();
          openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get)); // Очередь с приоритетом на основе fScore
      }

      // Метод для добавления вершины в граф
      public void addVertex(String name, double heuristic) {
          graph.put(name, new Vertex(name, heuristic));
      }

      // Метод для добавления ребра в граф
      public void addEdge(String from, String to, double weight) {
          Vertex fromVertex = graph.get(from);
          Vertex toVertex = graph.get(to);
          if (fromVertex != null && toVertex != null) {
              fromVertex.addEdge(toVertex, weight); // Добавление ребра к начальной вершине
          }
      }

      // Метод для поиска кратчайшего пути с помощью A*
      public List<Vertex> findPath(String startName, String goalName) {
          Vertex start = graph.get(startName); // Получение начальной вершины
          Vertex goal = graph.get(goalName); // Получение конечной вершины

          if (start == null || goal == null) {
              throw new IllegalArgumentException("Start or goal vertex not found"); // Проверка наличия вершин
          }

          gScore.put(start, 0.0); // Начальная стоимость пути равна 0
          fScore.put(start, start.getHeuristic()); // Стоимость пути с учетом эвристики
          openSet.add(start); // Добавление начальной вершины в открытый набор

          log.debug("Starting A* algorithm...");
          log.debug("Start: " + start);
          log.debug("Goal: " + goal);

          while (!openSet.isEmpty()) { // Пока есть вершины для обработки
              Vertex current = openSet.poll(); // Извлечение вершины с наименьшей стоимостью
              log.debug("Visiting: " + current);

              if (current.equals(goal)) { // Если достигнута конечная вершина
                  return reconstructPath(current); // Восстановление пути
              }

              for (Edge edge : current.getEdges()) { // Для каждого ребра текущей вершины
                  Vertex neighbor = edge.getTarget(); // Получение соседней вершины
                  double tentativeGScore = gScore.get(current) + edge.getWeight(); // Расчёт новой стоимости пути
                  log.debug("Checking edge from " + current + " to " + neighbor + " with weight " + edge.getWeight());

                  if (tentativeGScore < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) { // Если найден лучший путь
                      log.debug("Updating path to " + neighbor + ". Previous gScore: " + gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY) + ", new gScore: " + tentativeGScore);
                      cameFrom.put(neighbor, current); // Обновление пути
                      gScore.put(neighbor, tentativeGScore); // Обновление стоимости пути
                      fScore.put(neighbor, tentativeGScore + neighbor.getHeuristic()); // Обновление оценки пути

                      if (!openSet.contains(neighbor)) { // Если соседняя вершина не в открытом наборе
                          openSet.add(neighbor); // Добавление в открытый набор
                          log.debug("Added " + neighbor + " to open set.");
                      }
                  }
              }
          }
          log.error("No path found to goal: " + goal); // Если путь не найден
          return null;
      }

      // Метод для восстановления пути от начальной до конечной вершины
      private List<Vertex> reconstructPath(Vertex current) {
          List<Vertex> totalPath = new ArrayList<>();
          double totalCost = gScore.get(current); // Общая стоимость пути
          while (cameFrom.containsKey(current)) { // Пока есть путь к текущей вершине
              totalPath.add(current); // Добавление вершины в путь
              current = cameFrom.get(current); // Переход к предыдущей вершине
          }
          totalPath.add(current); // Добавление начальной вершины
          Collections.reverse(totalPath); // Переворачивание пути
          log.debug("Total path cost: " + totalCost); // Вывод общей стоимости пути
          log.debug("Path: " + totalPath); // Вывод самого пути
          return totalPath;
      }

      // Метод для получения общей стоимости пути до конечной вершины
      public double getTotalCost(Vertex goal) {
          return gScore.getOrDefault(goal, Double.POSITIVE_INFINITY);
      }
  }