package Assignment2;
import org.junit.Assert;
import org.junit.Test;
import sun.swing.BakedArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestAssignment2 {
    @Test
    public void testAssessment_GetInstance(){
        Assert.assertNotNull(Assessment.getInstance('A', 1));
        Assert.assertNotNull(Assessment.getInstance('B', 0));
        Assert.assertNotNull(Assessment.getInstance('C', -1));
    }

    @Test
    public void testAssessment_Equals(){
        Assert.assertEquals(Assessment.getInstance('A', 1), Assessment.getInstance('A', 1));
        Assert.assertNotEquals(Assessment.getInstance('A', 1), Assessment.getInstance('B', 1));
        Assert.assertNotEquals(Assessment.getInstance('A', 1), Assessment.getInstance('A', 0));
    }

    @Test
    public  void testCourse_Ctor(){
        // test default ctor
        Course course1 = new Course();
        Assert.assertNotNull(course1);
        Assert.assertEquals("", course1.getCode());
        Assert.assertEquals(0, Double.compare(course1.getCredit(), 0.0));
        Assert.assertEquals(0, course1.getAssignment().size());

        // test constructor with code, assignment and credit
        String code1 = "code1";
        ArrayList<Assessment> assessments1 = new ArrayList<>();
        double credit1 = 0;
        course1 = new Course(code1, assessments1, credit1);
        Assert.assertNotNull(credit1);
        Assert.assertEquals(code1, course1.getCode());
        Assert.assertEquals(assessments1, course1.getAssignment());
        Assert.assertEquals(0, Double.compare(credit1, course1.getCredit()));

        // test copy constructor
        Course course2 = new Course(course1);
        Assert.assertEquals(course1, course2);
    }

    @Test
    public void testCourse_Equals(){
        Course course1 = new Course("A", new ArrayList<>(), 0);
        Course course2 = new Course("B", new ArrayList<>(), 0);
        Assert.assertEquals(false, course1.equals(course2));

        course2 = new Course("A", new ArrayList<>(), 0);
        Assert.assertEquals(true, course1.equals(course2));

        course2 = new Course("A", new ArrayList<>(), 1);
        Assert.assertEquals(true, course1.equals(course2));
        Assert.assertNotEquals(0, Double.compare(course1.getCredit(), course2.getCredit()));

        course2 = new Course("A", new ArrayList<Assessment>(){{add(Assessment.getInstance('A', 1));}}, 1);
        Assert.assertEquals(true, course1.equals(course2));
        Assert.assertNotEquals(course1.getAssignment(), course2.getAssignment());
    }

    @Test
    public  void testStudent_Ctor(){
        Student student1 = new Student();
        Assert.assertNotNull(student1);
        Assert.assertEquals("", student1.getStudentID());
        Assert.assertEquals("", student1.getName());
        Assert.assertEquals(0, student1.getCourseTaken().size());

        // test with student ID, name and course taken
        student1 = new Student("A", "B", new ArrayList<>());
        Assert.assertEquals("A", student1.getStudentID());
        Assert.assertEquals("B", student1.getName());
        Assert.assertEquals(0, student1.getCourseTaken().size());
        student1 = new Student("A", "B", new ArrayList<Course>(){{add(new Course());}});
        Assert.assertEquals(1, student1.getCourseTaken().size());
    }

    @Test
    public void testStudent_AddCourse(){
        Student student1 = new Student("ID1", "Name1", new ArrayList<>());
        Assert.assertEquals(0, student1.getCourseTaken().size());
        student1.addCourse(new Course("A", new ArrayList<>(), 1));
        Assert.assertEquals(1, student1.getCourseTaken().size());
        student1.addCourse(new Course("A", new ArrayList<>(), 2));
        Assert.assertEquals(1, student1.getCourseTaken().size());
        student1.addCourse(new Course("B", new ArrayList<>(), 1));
        Assert.assertEquals(2, student1.getCourseTaken().size());
    }

    @Test
    public void testStudent_AddGrade() {
        Student student1 = new Student("ID1", "Name1", new ArrayList<>());
        student1.addGrade(new ArrayList<>(), new ArrayList<>());
        Assert.assertEquals(0, Double.compare(0, student1.weightedGPA()));

        student1 = new Student("ID1", "Name1",
                new ArrayList<Course>() {{
                    add(new Course(
                            "C1",
                            new ArrayList<Assessment>() {{
                                add(Assessment.getInstance('A', 40));
                                add(Assessment.getInstance('B', 60));
                            }},
                            2));
                }});
        student1.addGrade(
                new ArrayList<Double>(){{add(80.0);add(90.0);}},
                new ArrayList<Integer>(){{add(40);add(60);}});
        Assert.assertEquals(new Double(8), new Double(student1.weightedGPA()));
    }

    @Test
    public void testTranscrip_Ctor(){
        Transcript transcript = new Transcript("infile", "outfile");
        Assert.assertNotNull(transcript);
        transcript.buildStudentArray();
    }

    @Test
    public void testTranscrip_BuildStudentArray(){
        Transcript transcript = new Transcript("input.txt", "outfile");
        ArrayList<Student> students = transcript.buildStudentArray();
        Assert.assertEquals(9, students.size());
    }

    @Test
    public void testTranscrip_printTranscript(){
        Transcript transcript = new Transcript("input.txt", "outfile.txt");
        transcript.printTranscript(transcript.buildStudentArray());

        // read expected lines
        ArrayList<String> expectedContent = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("expected_outfile.txt"))) {
            while(scanner.hasNextLine())
                expectedContent.add(scanner.nextLine());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail("Should not reach here!");
        }
        // read actual lines
        ArrayList<String> actualContent = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("outfile.txt"))) {
            while(scanner.hasNextLine())
                actualContent.add(scanner.nextLine());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail("Should not reach here!");
        }

        Assert.assertEquals(expectedContent.size(), actualContent.size());
        for(int i = 0; i< expectedContent.size(); i++)
            Assert.assertEquals("Current line: " + i, expectedContent.get(i), actualContent.get(i));
    }
}
