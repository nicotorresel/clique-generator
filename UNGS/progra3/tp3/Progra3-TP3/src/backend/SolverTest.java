package backend;

import static org.junit.Assert.*;

import org.junit.Test;

public class SolverTest {
	
	private Graph graph = Graph.readJSON("archivo.JSON");
	private Solver solver = new Solver(graph);
	private Solution solution = solver.solution();

	@Test
	public void solutionTest() {
		
		assertEquals(3, solution.getNodes().size());
	}
	
	@Test
	public void solutionContainsTest() {
		
		assertTrue(solution.containsNodeNumber(1));
		assertTrue(solution.containsNodeNumber(2));
		assertTrue(solution.containsNodeNumber(4));
	}
	
	@Test
	public void solutionNotContainsTest() {
		
		assertFalse(solution.containsNodeNumber(3));
		assertFalse(solution.containsNodeNumber(0));
		assertFalse(solution.containsNodeNumber(5));
		assertFalse(solution.containsNodeNumber(6));
	}
	
	@Test
	public void weightTest() {
		
		boolean expected = solution.getWeight() == 23.5;
		
		assertTrue(expected);
	}

}
