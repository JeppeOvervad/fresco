package dk.alexandra.fresco.suite.dummy.arithmetic;


import org.junit.Ignore;
import org.junit.Test;

import dk.alexandra.fresco.framework.network.NetworkingStrategy;
import dk.alexandra.fresco.framework.sce.evaluator.EvaluationStrategy;
import dk.alexandra.fresco.lib.arithmetic.BasicArithmeticTests;
import dk.alexandra.fresco.lib.arithmetic.ComparisonTests;
import dk.alexandra.fresco.lib.arithmetic.LogicTests;
import dk.alexandra.fresco.lib.arithmetic.MiMCTests;
import dk.alexandra.fresco.lib.arithmetic.SearchingTests;
import dk.alexandra.fresco.lib.arithmetic.SortingTests;
import dk.alexandra.fresco.lib.list.EliminateDuplicatesTests;
import dk.alexandra.fresco.lib.lp.LPBuildingBlockTests;
import dk.alexandra.fresco.lib.math.integer.binary.BinaryOperationsTests;
import dk.alexandra.fresco.lib.math.integer.division.DivisionTests;
import dk.alexandra.fresco.lib.math.integer.exp.ExponentiationTests;
import dk.alexandra.fresco.lib.math.integer.linalg.LinAlgTests;
import dk.alexandra.fresco.lib.math.integer.log.LogTests;
import dk.alexandra.fresco.lib.math.integer.min.MinTests;
import dk.alexandra.fresco.lib.math.integer.sqrt.SqrtTests;
import dk.alexandra.fresco.lib.math.integer.stat.StatisticsTests;

import dk.alexandra.fresco.lib.math.polynomial.PolynomialTests;
import dk.alexandra.fresco.lib.statistics.CreditRaterTest;
import dk.alexandra.fresco.lib.statistics.DEASolver.AnalysisType;
import dk.alexandra.fresco.lib.statistics.DEASolverTests;


public class TestDummyArithmeticProtocolSuite extends AbstractDummyArithmeticTest {

  @Test
  public void test_Input_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestInput(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_OutputToTarget_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestOutputToSingleParty(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_AddPublicValue_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestAddPublicValue(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_MultAndAdd_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestSimpleMultAndAdd(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Sum_And_Output_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestSumAndMult(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  // Comparisons

  @Test
  public void test_MinInfFrac_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestMinInfFrac(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_MinInfFrac_SequentialBatched() throws Exception {
    runTest(new BasicArithmeticTests.TestMinInfFrac(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_compareLT_Sequential() throws Exception {
    runTest(new ComparisonTests.TestCompareLT(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_compareEQ_Sequential() throws Exception {
    runTest(new ComparisonTests.TestCompareEQ(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_isSorted() throws Exception {
    runTest(new SortingTests.TestIsSorted(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_compareAndSwap() throws Exception {
    runTest(new SortingTests.TestCompareAndSwap(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Sort() throws Exception {
    runTest(new SortingTests.TestSort(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET,
        2);
  }

  @Test
  public void test_Big_Sort() throws Exception {
    runTest(new SortingTests.TestBigSort(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_logic() throws Exception {
    runTest(new LogicTests.TestLogic(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET,
        2);
  }
  
  //Statistics
  

  
  //Creditrater
  @Test
  public void test_CreditRater_Single_Value_2_parties() throws Exception {
    int[] values = {2};
    int[][] intervals = {{1,3}};
    int[][] scores = {{10,100,1000}};
    runTest(new CreditRaterTest.TestCreditRater(values, intervals, scores), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_CreditRater_Single_Value_3_parties() throws Exception {
    int[] values = {2};
    int[][] intervals = {{1,3}};
    int[][] scores = {{10,100,1000}};
    runTest(new CreditRaterTest.TestCreditRater(values, intervals, scores), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 3);
  }
  
  @Test
  public void test_CreditRater_multi_Value_2_parties() throws Exception {
    int[] values = {2, 2, 2};
    int[][] intervals = {{1,3}, {0, 5}, {0, 1}};
    int[][] scores = {{10, 100, 1000}, {10,100, 1000}, {10, 100, 1000}};
    runTest(new CreditRaterTest.TestCreditRater(values, intervals, scores), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  //DEASolver
  @Test
  public void test_DEASolver_2_parties() throws Exception {
    runTest(new DEASolverTests.TestDEASolver(5, 2, 10, 1, AnalysisType.INPUT_EFFICIENCY), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Ignore //TODO needs a closer look
  @Test
  public void test_DEASolver_3_parties() throws Exception {
    runTest(new DEASolverTests.TestDEASolver(2, 2, 10, 1, AnalysisType.INPUT_EFFICIENCY), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 3);
  }
  
  @Ignore //TODO needs a closer look
  @Test
  public void test_DEASolver_multiple_queries_2_parties() throws Exception {
    runTest(new DEASolverTests.TestDEASolver(5, 2, 10, 2, AnalysisType.INPUT_EFFICIENCY), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  @Ignore //TODO needs a closer look
  @Test
  public void test_DEASolver_single_input_2_parties() throws Exception {
    runTest(new DEASolverTests.TestDEASolver(1, 2, 10, 1, AnalysisType.INPUT_EFFICIENCY), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_DEASolver_single_input_and_output_2_parties() throws Exception {
    runTest(new DEASolverTests.TestDEASolver(1, 1, 10, 1, AnalysisType.INPUT_EFFICIENCY), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_DEASolver_output_efficiency_2_parties() throws Exception {
    runTest(new DEASolverTests.TestDEASolver(5, 1, 10, 1, AnalysisType.OUTPUT_EFFICIENCY), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_DEASolver_multiple_queries__output_2_parties() throws Exception {
    runTest(new DEASolverTests.TestDEASolver(5, 2, 10, 2, AnalysisType.OUTPUT_EFFICIENCY), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  //lib.collections
  @Test
  public void test_Test_Is_Sorted() throws Exception {
    runTest(new SearchingTests.TestIsSorted(),EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 2);
  }

  @Ignore
  @Test //TODO @Kasper, Using the other modulus breaks the LP tests 
  public void test_MiMC_DifferentPlainTexts() throws Exception {
    runTest(new MiMCTests.TestMiMCDifferentPlainTexts(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 2);
  }

  @Ignore
  @Test //TODO @Kasper, Using the other modulus breaks the LP tests 
  public void test_MiMC_EncSameEnc() throws Exception {
    runTest(new MiMCTests.TestMiMCEncSameEnc(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 2);
  }
  
  @Ignore
  @Test //TODO @Kasper, Using the other modulus breaks the LP tests 
  public void test_MiMC_EncDec() throws Exception {
    runTest(new MiMCTests.TestMiMCEncDec(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 2);
  }
  
  @Ignore
  @Test //TODO @Kasper, Using the other modulus breaks the LP tests 
  public void test_MiMC_Deterministically() throws Exception {
    runTest(new MiMCTests.TestMiMCEncryptsDeterministically(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 2);
  }

  //lib.list
  @Test
  public void test_findDuplicatesOne() throws Exception {
    runTest(new EliminateDuplicatesTests.TestFindDuplicatesOne(),
        EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 2);
  }

  //lib.lp
 // @Ignore //check with Peter if we still want this
  @Test
  public void test_LPSolverEntering() throws Exception {
    runTest(new LPBuildingBlockTests.TestEnteringVariable(),
        EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_LPSolverBlandEntering() throws Exception {
    runTest(new LPBuildingBlockTests.TestBlandEnteringVariable(),
        EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 2);
  }
  

  //lib.math.integer.binary
  @Test
  public void test_Right_Shift() throws Exception {
    runTest(new BinaryOperationsTests.TestRightShift(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }  

  @Test
  public void test_Bit_Length() throws Exception {
    runTest(new BinaryOperationsTests.TestBitLength(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  // Math tests

  @Test
  public void test_euclidian_division() throws Exception {
    runTest(new DivisionTests.TestEuclidianDivision(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_euclidian_division_large_divisor() throws Exception {
    runTest(new DivisionTests.TestEuclidianDivisionLargeDivisor(), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_ss_division() throws Exception {
    runTest(new DivisionTests.TestSecretSharedDivision(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Exponentiation() throws Exception {
    runTest(new ExponentiationTests.TestExponentiation(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  

  @Test
  public void test_ExponentiationOpenExponent() throws Exception {
    runTest(new ExponentiationTests.TestExponentiationOpenExponent(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_ExponentiationOpenBase() throws Exception {
    runTest(new ExponentiationTests.TestExponentiationOpenBase(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_ExponentiationZeroExponent() throws Exception {
    runTest(new ExponentiationTests.TestExponentiationZeroExponent(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_InnerProductClosed() throws Exception {
    runTest(new LinAlgTests.TestInnerProductClosed(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_InnerProductNewAPI() throws Exception {
    runTest(new LinAlgTests.InnerProductNewApiTest(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_InnerProductOpen() throws Exception {
    runTest(new LinAlgTests.TestInnerProductOpen(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
  
  @Test
  public void test_Logarithm() throws Exception {
    runTest(new LogTests.TestLogarithm(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Minimum_Protocol_2_parties() throws Exception {
    runTest(new MinTests.TestMinimumProtocol(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Min_Inf_Frac_2_parties() throws Exception {
    runTest(new MinTests.TestMinInfFraction(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Min_Inf_Frac_Trivial_2_parties() throws Exception {
    runTest(new MinTests.TestMinInfFractionTrivial(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_sqrt() throws Exception {
    runTest(new SqrtTests.TestSquareRoot(), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Exiting_Variable_2_parties() throws Exception {        
      runTest(new StatisticsTests.TestStatistics(), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Exiting_Variable_3_parties() throws Exception {
    runTest(new StatisticsTests.TestStatistics(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 3);
  }
  
  
  @Test
  public void test_Exiting_Variable_No_Mean_2_parties() throws Exception {        
      runTest(new StatisticsTests.TestStatisticsNoMean(), EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Polynomial_Evaluator_2_parties() throws Exception {
    runTest(new PolynomialTests.TestPolynomialEvaluator(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }
}
