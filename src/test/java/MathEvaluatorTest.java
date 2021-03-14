package test.java;

import main.java.MathEvaluator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathEvaluatorTest {

    private final MathEvaluator evaluator = new MathEvaluator();

    @Test
    public void additionTest() throws Exception{
        assertEquals(1d, evaluator.evaluate("0.5 + 0.5"));
        assertEquals(1.5d, evaluator.evaluate("1.0 + 0.5"));
        assertEquals(1.5d, evaluator.evaluate("1 + 0.5"));
        assertEquals(10d, evaluator.evaluate("  9 + 0.5 +    0.5"));
        assertEquals(11d, evaluator.evaluate("0.5 + 0.5 + 9 + 1"));
    }

    @Test
    public void subtractionTest() throws Exception{
        assertEquals(0d, evaluator.evaluate("0.5 - 0.5"));
        assertEquals(1d, evaluator.evaluate("0.5 - -0.5"));
        assertEquals(1.5d, evaluator.evaluate("  2- 0.5"));
        assertEquals(0.5d, evaluator.evaluate("1 - 0.5"));
        assertEquals(8d, evaluator.evaluate("  (9 - 0.5) -  0.5"));
    }

    @Test
    public void multiplicationTest() throws Exception{
        assertEquals(0.25d, evaluator.evaluate("0.5 * 0.5"));
        assertEquals(1d, evaluator.evaluate("  2* 0.5"));
        assertEquals(20d, evaluator.evaluate("10 * 2"));
        assertEquals(42d, evaluator.evaluate("  3 * (7 * 2)"));
    }

    @Test
    public void divisionTest() throws Exception{
        assertEquals(4d/3, evaluator.evaluate("4/3"));
        assertEquals(10d, evaluator.evaluate("  100 / 10.0"));
        assertEquals(5d/2, evaluator.evaluate("5.0/2"));
        assertEquals(6d, evaluator.evaluate("  12/(4/2)"));
    }

    @Test
    public void bracketsTest() throws Exception{
        assertEquals(-10d, evaluator.evaluate("2 * (3 + 2) * -1"));
        assertEquals(10d, evaluator.evaluate("(50 + 50) * 2 * (3 + 2) / 100"));
        assertEquals(14d, evaluator.evaluate("2 * (6 + 3) * 7 / 9"));
        assertEquals(37d, evaluator.evaluate("2 + 7 * ( 3 + 4/2)"));
    }
}
