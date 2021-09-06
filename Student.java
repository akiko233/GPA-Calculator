package Assignment2;
import java.util.ArrayList;

/**
 * This is the Student class with the information of student ID, student name, course taken and final grade;
 */
public class Student {
    private String studentID;
    private String name;
    private ArrayList<Course> courseTaken;
    private ArrayList<Double> finalGrade;

    /**
     * This is a default constructor
     */
    public Student() {
        this.studentID = this.name = "";
        this.courseTaken = new ArrayList<>();
        this.finalGrade = new ArrayList<>();
    } // end of default Student constructor

    /**
     * This is a constructor with student ID, name and course taken
     *
     * @param studentId   is the student id in string
     * @param name        is the name of the student
     * @param courseTaken the course list that the student takes
     */
    public Student(String studentId, String name, ArrayList<Course> courseTaken) {
        this();
        this.studentID = studentId;
        this.name = name;
        this.courseTaken = courseTaken;
    } // end of Student Course

    /**
     * This is a method adding parallel array of final grade and
     *
     * @param grades
     * @param weights
     * @throws It throws InvalidTotalException in case of following reasons:
     *            1. course is not added
     *            1. the total weights is not 100
     *            2. either grades or weights is null
     *            3. the sizes of grades and weights are not match
     *            4. the final grade is not in range [0, 100]
     *            5. the sizes of grades and the assignments of the course are not match
     */
    public void addGrade(ArrayList<Double> grades, ArrayList<Integer> weights) {
        try {
            int totalWeight = 0;
            // check course
            if (getCourseTaken().size() == 0)
                throw new InvalidTotalException("Please add course first!");
            // check parameters
            if (grades == null || weights == null)
                throw new InvalidTotalException("Neither grades nor weight can be null!");
            // check if they are same size
            if (grades.size() != weights.size())
                throw new InvalidTotalException("The size of grades and weights should be the same!");
            // check if the grades size equals the assignment size
            if (grades.size() != getCourseTaken().get(getCourseTaken().size() - 1).getAssignment().size())
                throw new InvalidTotalException("The size of the grads should be the same as the size of assignments");

            double calculatedGrade = 0.0;
            for (int i = 0; i < grades.size(); i++) {
                int weight = weights.get(i);
                //calculatedGrade += Math.round(grades.get(i) * 10) * weight / 1000.0;
                calculatedGrade += grades.get(i) * weight / 100.0;
                totalWeight += weight;
            }
            calculatedGrade = Math.round(calculatedGrade * 10) / 10.0;


            // check if weight added up to 100
            if (totalWeight != 100)
                throw new InvalidTotalException("The total weight is too " + ((totalWeight < 100) ? "small" : "large"));

            // check if final grade is higher than 100 or less than 0
            if (calculatedGrade > 100 || calculatedGrade < 0)
                throw new InvalidTotalException("The final calculated grade should be in [0, 100]!");

            // append the calculated grade to finalGrade list
            getFinalGrade().add(calculatedGrade);
        } catch (InvalidTotalException ex) {
            ex.printStackTrace();
        } finally {
            // do nothing
        }

    } // end of addGrade

    /**
     * This methods converts the grade to grade point
     *
     * @param grade
     * @return
     */
    private static int gradeToGP(double grade){
        if (grade >= 90)
            return 9;
        else if (grade >= 80)
            return 8;
        else if (grade >= 75)
            return 7;
        else if (grade >= 70)
            return 6;
        else if (grade >= 65)
            return 5;
        else if (grade >= 60)
            return 4;
        else if (grade >= 55)
            return 3;
        else if (grade >= 50)
            return 2;
        else if (grade >= 47)
            return 1;
        else
            return 0;
    } // end of gradeToGP

    /**
     * This is a method get weighted GPA according the grade and the weight
     *
     * @return the grade point on average
     */
    public double weightedGPA() {
        // calculate the total grade
        double calculatedGradePoint = 0.0;
        double totalCredits = 0.0;
        for (int i = 0; i < getFinalGrade().size(); i++) {
            double grade = getFinalGrade().get(i);
            double credit = getCourseTaken().get(i).getCredit();
            totalCredits += credit;
            calculatedGradePoint += gradeToGP(grade) * credit;
        }
        // get the average
        return Math.round((totalCredits == 0.0) ? 0.0 : calculatedGradePoint * 10 / totalCredits ) / 10.0;

    } // end of weightedGPA

    /**
     * This is a method append course info
     *
     * @param course
     */
    public void addCourse(Course course) {
        if (this.courseTaken.indexOf(course) >= 0) {
            System.out.println(String.format(
                    "The course \"%s\" has been taken by %s",
                    course.getCode(),
                    studentID));
            return;
        }
        this.getCourseTaken().add(course);
    } // end of addCourse

    /**
     * This method gets the student ID
     *
     * @return ID of the student
     */
    public String getStudentID() {
        return studentID;
    } // end of getStudentID

    /**
     * This method gets the student name
     *
     * @return name of the student
     */
    public String getName() {
        return name;
    } // end of getName

    /**
     * This method gets the course list taken by the student
     *
     * @return
     */
    public ArrayList<Course> getCourseTaken() {
        return courseTaken;
    } // end of getCourseTaken

    /**
     * This method gets the final grade according the course list
     *
     * @return
     */
    public ArrayList<Double> getFinalGrade() {
        return finalGrade;
    } // end of getFinalGrade
} // end of Student
