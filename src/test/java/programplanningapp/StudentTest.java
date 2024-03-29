package programplanningapp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StudentTest {
    private Student student;
    private Program program;
    private ArrayList<CourseAttempt> coursesOnTranscript;

    /**
     * Setup member variables for tests.
     */
    @Before
    public void setUp() {
        String name = "Comp Sci";
        ArrayList<String> requiredCoursesByCode = new ArrayList<>();
        program = new Program(name, requiredCoursesByCode);
        coursesOnTranscript = new ArrayList<>();
        String courseAttemptOneCode = "CIS*4450";
        Status courseAttemptOneStatus = Status.PLANNED;
        String courseAttemptOneSemester = "W20";
        CourseAttempt courseAttemptOne = new CourseAttempt(courseAttemptOneCode, courseAttemptOneStatus,
                courseAttemptOneSemester);
        coursesOnTranscript.add(courseAttemptOne);
        student = new Student();
    }

    /**
     * Test setCoursesOnTranscript().
     */
    @Test
    public void testSetCoursesOnTranscript() {
        student.setCoursesOnTranscript(coursesOnTranscript);

        assertEquals(student.getCoursesOnTranscript(), coursesOnTranscript);
    }

    /**
     * Test getCoursesOnTranscript().
     */
    @Test
    public void testGetCoursesOnTranscript() {
        student.setCoursesOnTranscript(coursesOnTranscript);

        assertEquals(student.getCoursesOnTranscript(), coursesOnTranscript);
    }

    /**
     * Test setProgram().
     */
    @Test
    public void testSetProgram() {
        student.setProgram(program);

        assertEquals(student.getProgram(), program);
    }

    /**
     * Test getProgram().
     */
    @Test
    public void testGetProgram() {
        student.setProgram(program);

        assertEquals(student.getProgram(), program);
    }
}
