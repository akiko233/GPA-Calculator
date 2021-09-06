package Assignment2;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/* PLEASE DO NOT MODIFY A SINGLE STATEMENT IN THE TEXT BELOW.
READ THE FOLLOWING CAREFULLY AND FILL IN THE GAPS

I hereby declare that all the work that was required to 
solve the following problem including designing the algorithms
and writing the code below, is solely my own and that I received
no help in creating this solution and I have not discussed my solution 
with anybody. I affirm that I have read and understood
the Senate Policy on Academic honesty at 
https://secretariat-policies.info.yorku.ca/policies/academic-honesty-senate-policy-on/
and I am well aware of the seriousness of the matter and the penalties that I will face as a 
result of committing plagiarism in this assignment.

BY FILLING THE GAPS,YOU ARE SIGNING THE ABOVE STATEMENTS.

Full Name:
Student Number:
Course Section:
*/


/**
* This class generates a transcript for each student, whose information is in the text file.
* 
*
*/

public class Transcript {
	private ArrayList<Object> grade = new ArrayList<Object>();
	private File inputFile;
	private String outputFile;
	
	/**
	 * This the the constructor for Transcript class that 
	 * initializes its instance variables and call readFie private
	 * method to read the file and construct this.grade.
	 * @param inFile is the name of the input file.
	 * @param outFile is the name of the output file.
	 */
	public Transcript(String inFile, String outFile) {
		inputFile = new File(inFile);	
		outputFile = outFile;	
		grade = new ArrayList<Object>();
		this.readFile();
	}// end of Transcript constructor

	/** 
	 * This method reads a text file and add each line as 
	 * an entry of grade ArrayList.
	 * @exception It throws FileNotFoundException if the file is not found.
	 */
	private void readFile() {
		Scanner sc = null; 
		try {
			sc = new Scanner(inputFile);	
			while(sc.hasNextLine()){
				grade.add(sc.nextLine());
	        }      
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(sc != null)
				sc.close();
		}		
	} // end of readFile

	/**
	 * This method generate the formatted report of the student
	 *
	 * @param student
	 * @return
	 */
	private static String genReport(Student student) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(String.format("%s\t%s\n", student.getName(), student.getStudentID()))
				.append("--------------------\n");
		for (int i = 0; i < student.getCourseTaken().size(); i++) {
			buffer.append(String.format("%s:\t%5.1f\n",
					student.getCourseTaken().get(i).getCode(),
					student.getFinalGrade().get(i)));
		}
		buffer.append("--------------------\n")
				.append(String.format("GPA:\t%5.1f\n", student.weightedGPA()));
		return buffer.toString();
	} // end of printReport

	/**
	 * This method generates the student list according the lines stored in list grade
	 * @return
	 */
	public ArrayList<Student> buildStudentArray() {
		ArrayList<Student> students = new ArrayList<>();
		ArrayList<String> studentIds = new ArrayList<>();
		for (Object obj : grade) {
			String line = (String) obj;
			// if it is null continue
			if (line == null) continue;
			// it it is empty line continue
			line = line.trim();
			if (line.length() == 0) continue;

			// start parsing line
			// Sample EECS2030,3,1000,P10(90),P10(80),P30(60),E15(60),E15(44),E20(80),John
			String[] items = line.split("\\s*,\\s*");
			// check item count: there should be course code, credit, student id, student name
			// and at least one assignment
			if (items.length < 5) {
				// TODO log error
				continue;
			}

			// parse course info
			String courseCode = items[0];
			double courseCredits = Double.parseDouble(items[1]);

			// parse student info
			String studentID = items[2];
			String studentName = items[items.length - 1];

			// parse and create assignment of current line
			Pattern pattern = Pattern.compile("([PE])(\\d+)\\((\\d+)\\)");
			ArrayList<Integer> weights = new ArrayList<>();
			ArrayList<Double> grades = new ArrayList<>();
			ArrayList<Assessment> assessments = new ArrayList<>();
			for (int i = 3; i < items.length - 1; i++) {
				Matcher matcher = pattern.matcher(items[i]);
				if (matcher.find()) {
					char type = matcher.group(0).charAt(1);
					int weight = Integer.parseInt(matcher.group(2));
					Double grade = Double.parseDouble(matcher.group(3));

					// append to assessments list
					assessments.add(Assessment.getInstance(type, weight));
					// append to grades list
					grades.add(grade);
					// append to weights list
					weights.add(weight);
				} else {
					// TODO log error
				}
			}

			// create course
			Course course = new Course(courseCode, assessments, courseCredits);

			// find or create new student
			int index = studentIds.indexOf(studentID);
			if (index < 0) {
				index = students.size();
				// new student create one
				students.add(new Student(studentID, studentName, new ArrayList<>()));
				studentIds.add(studentID);
			} // end of creating new student

			Student student = students.get(index);
			student.addCourse(course);
			student.addGrade(grades, weights);
		}
		return students;
	} // enf of buildStudentArray

	/**
	 * This method print out the report of student list passed in
	 * @param students the student list
	 * @exception It throws IOExecption if the outputFile cannot be written
	 */
	public void printTranscript(ArrayList<Student> students){
		try (FileWriter fileWriter = new FileWriter(outputFile)) {
			for(Student student : students){
				fileWriter.write(genReport(student));
				fileWriter.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
} // end of Transcript

