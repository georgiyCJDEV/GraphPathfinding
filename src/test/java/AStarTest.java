import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.georgiydev.algorithms.AStar;
import org.georgiydev.graph.*;

public class AStarTest {

    private AStar aStar;

    @BeforeEach
    public void setUp() {
        // Подготовка данных перед каждым тестом
        aStar = new AStar(); // Создание нового экземпляра алгоритма A*

        // Добавление вершин с эвристическими значениями
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
        aStar.addVertex("P", 5);
        aStar.addVertex("Q", 1);
        aStar.addVertex("R", 10);
        aStar.addVertex("S", 3);
        aStar.addVertex("T", 4);
        aStar.addVertex("U", 7);
        aStar.addVertex("V", 4);

        // Добавление ребер между вершинами с указанием весов
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
        aStar.addEdge("D", "L", 12);
        aStar.addEdge("H", "L", 6);
        aStar.addEdge("I", "M", 5);
        aStar.addEdge("J", "N", 3);
        aStar.addEdge("K", "O", 7);
        aStar.addEdge("P", "Q", 1);
        aStar.addEdge("Q", "S", 1);
        aStar.addEdge("S", "T", 1);
        aStar.addEdge("T", "V", 1);
        aStar.addEdge("V", "R", 1);
        aStar.addEdge("T", "U", 1);
        aStar.addEdge("U", "R", 1);
        aStar.addEdge("P", "R", 1);
    }

    @Test
    public void testFindPath() {
        // Тестирование поиска кратчайшего пути

        // Вызов метода поиска пути из вершины "A" в "M"
        List<Vertex> path = aStar.findPath("A", "L");

        // Проверка, что найденный путь не равен null
        assertNotNull(path, "Path should not be null");

        // Проверка, что размер найденного пути равен ожидаемому (5 вершины)
        assertEquals(5, path.size(), "Path should contain 5 vertices");

        // Проверка последовательности вершин в найденном пути
        assertEquals("A", path.get(0).toString());
        assertEquals("B", path.get(1).toString());
        assertEquals("D", path.get(2).toString());
        assertEquals("H", path.get(3).toString());
        assertEquals("L", path.get(4).toString());
    }

    @Test
    public void testTotalCost() {
        // Тестирование общей стоимости найденного пути

        // Вызов метода поиска пути из вершины "A" в "N"
        aStar.findPath("A", "N");

        // Получение вершины "M"
        Vertex goal = aStar.getGraph().get("N");

        // Проверка, что общая стоимость пути до вершины "N" равна ожидаемой (14.0)
        assertEquals(14.0, aStar.getTotalCost(goal), 0.01, "Total path cost should be 13.0");
    }

    @Test
    public void testNoPath() {
        // Тестирование случая отсутствия пути между вершинами

        // Добавление новых вершин "X" и "Y"
        aStar.addVertex("X", 1);
        aStar.addVertex("Y", 1);

        // Добавление ребра между вершинами "X" и "Y"
        aStar.addEdge("X", "Y", 1);

        // Вызов метода поиска пути из вершины "A" в "Y"
        List<Vertex> path = aStar.findPath("A", "Y");

        // Проверка, что путь не найден и результат равен null
        assertNull(path, "Path should be null since there is no connection to Y");
    }

    @Test
    public void testStartEqualsGoal() {
        // Тестирование случая, когда начальная и конечная вершины совпадают

        // Вызов метода поиска пути из вершины "A" в "A"
        List<Vertex> path = aStar.findPath("A", "A");

        // Проверка, что путь не равен null
        assertNotNull(path, "Path should not be null");

        // Проверка, что размер найденного пути равен 1 (только одна вершина)
        assertEquals(1, path.size(), "Path should contain only one vertex");

        // Проверка, что вершина в пути действительно "A"
        assertEquals("A", path.getFirst().toString());
    }

    @Test
    public void testInvalidStartOrGoal() {
        // Тестирование случая, когда заданы недопустимые начальная или конечная вершины

        // Проверка исключения IllegalArgumentException при попытке найти путь от "Z" до "A"
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            aStar.findPath("Z", "A");
        });
        assertEquals("Start or goal vertex not found", exception.getMessage());

        // Проверка исключения IllegalArgumentException при попытке найти путь от "A" до "Z"
        exception = assertThrows(IllegalArgumentException.class, () -> {
            aStar.findPath("A", "Z");
        });
        assertEquals("Start or goal vertex not found", exception.getMessage());
    }

    @Test
    public void testHeuristic() {
        // Тестирование поиска кратчайшего пути (вес рёбер равен единице)

        // Вызов метода поиска пути из вершины "P" в "V"
        List<Vertex> path = aStar.findPath("P", "V");

        // Проверка, что найденный путь не равен null
        assertNotNull(path, "Path should not be null");

        // Проверка, что размер найденного пути равен ожидаемому (5 вершин)
        assertEquals(5, path.size(), "Path should contain 5 vertices");

        // Проверка последовательности вершин в найденном пути
        assertEquals("P", path.get(0).toString());
        assertEquals("Q", path.get(1).toString());
        assertEquals("S", path.get(2).toString());
        assertEquals("T", path.get(3).toString());
        assertEquals("V", path.get(4).toString());

    }
}