package assignment4;

import static org.junit.Assert.*;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import assignment4.Critter.TestCritter;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Object.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class A4InterpreterTest{

	private static String CLI_INTEGRATION_TESTS_INOUTS = "src/test/data/cli_integration_inouts/";
	private static  ByteArrayOutputStream outContent;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDefaultParams();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Before
	public void setUp() throws Exception {
		TestCritter.clearWorld();
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws Exception {
	}

		/*
	 * 1. ParseMakeLargeCritter
	 */
	
	@Test(timeout=1000) 
	public void ParseMakeLargeCritter(){
		// Test for make and show command
		// Expects entire board to be filled with critters
		
		setDefaultParams();
		String fileFolder = "make_large_critter";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
		Params.world_width = 20;
		Params.world_height = 20;

		Main.main(inputs);
		outContent = Main.testOutputString;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder +"/expected_output.txt") );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text = cleanString(scanner.useDelimiter("\\A").next().trim());
		String output = cleanString(outContent.toString());
		scanner.close();
		assertEquals(text, output);
	}
	
	
	/*
	 * 2. ParseMakeSmallCritter
	 */
	
	@Test(timeout=1000) 
	public void ParseMakeSmallCritter(){
		
		 //Test: ParseMakeSmallCritter
		 //Test for make and show command
		 //Expects board to contain one critter
		 
		setDefaultParams();
		Params.world_width = 1;
		Params.world_height = 1;
		
		String fileFolder = "make_small_critter";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
		
		
	
		Main.main(inputs);
		outContent = Main.testOutputString;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder +"/expected_output.txt") );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text = cleanString(scanner.useDelimiter("\\A").next().trim());
		String output = cleanString(outContent.toString());
		scanner.close();
		assertEquals(text, output);
	}
	
	
	/*
	 * 3. ParseInvalidInput
	 */
	@Test(timeout=1000) 
	public void ParseInvalidInput(){
		
		 //Test: ParseInvalidInput
		 //Test for Invalid Inputs
		 //Expects invalid command to be output
		 
		setDefaultParams();
		String fileFolder = "invalid_inputs";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
		Main.main(inputs);
		outContent = Main.testOutputString;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		String text = cleanString(scanner.useDelimiter("\\A").next().trim());
		String output = cleanString(outContent.toString());
		scanner.close();
		//assertThat(text, containsString(output));
		assertEquals(text,output);
	}
	
	/*
	 * 4. KillCritters
	 */
	@Test(timeout=1000) 
	public void KillCritters(){
		/*
		 * Test: killCritters
		 * Test for make critter and stats, and step
		 * Creates large number of make critters and compare stats after 100 steps
		 * Expects all Critters to be dead
		 */
		
		Params.start_energy = 1;
		
		String fileFolder = "kill_all_critter";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
		Main.main(inputs);
		outContent = Main.testOutputString;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		String text = cleanString(scanner.useDelimiter("\\A").next().trim());
		String output = cleanString(outContent.toString());
		scanner.close();
		//assertThat(text, containsString(output));
		assertEquals(text,output);
	}	
		
	/*
	 * 5. ParseSeed
	 */
	@Test(timeout=1000) 
	public void ParseSeed(){
		/*
		 * Test: ParseSeed
		 * Test for make and seed
		 * Expects a manual creation of make critter on the game board
		 * Critter will always be in the same spot
		 */
		
		setDefaultParams();
		
		String fileFolder = "seed_test";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
		Main.main(inputs);
		outContent = Main.testOutputString;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		String text = cleanString(scanner.useDelimiter("\\A").next().trim());
		String output = cleanString(outContent.toString());
		scanner.close();
		assertEquals(text, output);
	}
	
	
	/*
	 * 6. ParseErrors
	 */
		@Test(timeout=1000) 
	public void ParseErrors(){
		
		/*
		 * Test: ParseErrors
		 * Test for errors within valid inputs
		 * Expects errors to be printed
		 */
		
		setDefaultParams();
		String fileFolder = "error_processing";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
		Main.main(inputs);
		outContent = Main.testOutputString;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		String text = cleanString(scanner.useDelimiter("\\A").next().trim());
		String output = cleanString(outContent.toString());
		scanner.close();
		//assertThat(text, containsString(output));
		assertEquals(text,output);
	}
	
	@Rule
	  public final ExpectedException exception = ExpectedException.none();
	
	/*
	 * 7. InvalidCritter
	 */
	
	@Test(timeout=1000)
	public void InvalidCritter(){
		
		/*
		 * Test: InvalidCritter
		 * Expects error in creating critters
		 */
		setDefaultParams();
		String fileFolder = "invalid_make";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
		
		Main.main(inputs);
		outContent = Main.testOutputString;
		
		
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		
		String text = cleanString(scanner.useDelimiter("\\A").next().trim());
		String output = cleanString(outContent.toString());
		scanner.close();
		//assertThat(text, containsString(output));
		assertEquals(text,output);


	}

	/*
	 * 8. parseCraigStats
	 */
	@Test(timeout=1000)
	public void parseCraigStats(){
		/*
		 * Test for Craig Stats to be run accurately when Craig is created
		 * Expects String to match expected Craig and Algae solution
		 */

		setDefaultParams();
		String[] result = statsParserHelper();
		String text = result[0];
		String output = result[1];
		text = cleanString(text);
		output = cleanString(output);
		//assertThat(text, containsString(output));
		assertEquals(text,output);
		
	}
	
	/*
	 * 9. parseCraigStats
	 */
	@Test(timeout=1000)
	public void parseStats(){
		/*
		 * Test for Craig Stats to be run accurately when Craig is created
		 * Expects String to match expected Craig and Algae solution
		 * If not match, then compare to see if they at least print Critter
		 */
		setDefaultParams();
		String[] result = statsParserHelper();
		String text = result[0];
		String output = result[1];
		
		
		if(text.contains(output))
		{
			assertThat(text,containsString(output));
			return;
		}
		
		String fileFolder = "parse_stats";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
		
		Main.main(inputs);
		outContent = Main.testOutputString;
		
		
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		
		text = cleanString(scanner.useDelimiter("\\A").next().trim());
		output = cleanString(outContent.toString()).trim();
		scanner.close();
		//assertThat(text, containsString(output));
		assertEquals(text,output);
		
	}
		/*
	 * statsParserHelper
	 * Helper method to parse Craig Stats
	 */
	
	public String[] statsParserHelper(){
		String fileFolder = "craig_stats";
		String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};
		
				
		
		Main.main(inputs);
		outContent = Main.testOutputString;
		
		
		Scanner scanner = null;
		try {
			scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		
		String text = scanner.useDelimiter("\\A").next().trim();
		String output = cleanString(outContent.toString());
		scanner.close();
		
		String[] result = {text,output};
		return result;
		
	}	
	/*
	 * setDefaultParams()
	 */
	
	static void setDefaultParams()
	{
		Params.world_width = 20;
		Params.world_height = 20;
		Params.walk_energy_cost = 2;
		Params.run_energy_cost = 5;
		Params.rest_energy_cost = 1;
		Params.min_reproduce_energy = 20;
		Params.refresh_algae_count = (int)Math.max(1, Params.world_width*Params.world_height/1000);

		Params.photosynthesis_energy_amount = 1;
		Params.start_energy = 100;
		
		
	}	
	String cleanString(String input)
	{
		String lineSep = System.getProperty("line.separator");
		input = input.replaceAll("critter>", "")
					.replaceAll("\r\n", "\n")
					 .replaceAll("critters>", "")
					 .replaceAll(lineSep + "\\s+","\n")
					 .replaceAll(lineSep, "\n")
					 .replaceAll("(?m)^[ \t]*\r?\n","")
			         .trim();
		return input;
	}
	
}
