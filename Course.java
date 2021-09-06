package Assignment2;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This is Course class with course information of course code, assignment and creditß
 */
public class Course {
    private String code;
    private ArrayList<Assessment> assignment;
    private double credit;

    /**
     * This is a default constructor
     */
    public Course() {
        this.code = "";
        this.assignment = new ArrayList<>();
        this.credit = 0.0;
    } // end of Course default constructor

    /**
     * This is Course constructor with code, assignment and credit
     *
     * @param code
     * @param assignment
     * @param credit
     */
    public Course(String code, ArrayList<Assessment> assignment, double credit) {
        this();
        this.code = code;
        this.assignment = assignment;
        this.credit = credit;
    } // end of Course constructor

    /**
     * This is Course copy constructor
     *
     * @param course
     */
    public Course(Course course) {
        this(course.getCode(), course.getAssignment(), course.getCredit());
    } // end of Course copy constructor

    /**
     * This is an override equals method
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course course = (Course) obj;
        return Objects.equals(getCode(), course.getCode());
    } // end of equals

    /**
     * This method gets the course code
     *
     * @return code of the course
     */
    public String getCode() {
        return code;
    } // end of getCode

    /**
     * This method gets the credit of the course
     *
     * @return credit of the course
     */
    public double getCredit() {
        return credit;
    } // end of getCredit

    /**
     * This method gets the assignment list
     *
     * @return
     */
    public ArrayList<Assessment> getAssignment() {
        return assignment;
    } // end of getAssignment
} // end of Course