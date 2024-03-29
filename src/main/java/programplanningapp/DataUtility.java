package programplanningapp;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class DataUtility {
    private DataUtility() {
        //not called
    }

    /**
     * Get the filename of where the admin courses are currently saved.
     *
     * @return name of file containing saved courses.
     */
    public static String getSavedCoursesFilename() {
        String separator = System.getProperty("file.separator");

        String filename = "." + separator + "src" + separator + "main"
                + separator + "resources" + separator + "savedAdminCourses.csv";

        return filename;
    }

    /**
     * Get the stored admin courses.
     *
     * @return stored admin courses.
     */
    public static ArrayList<AdminCourse> getStoredAdminCourses() {
        ArrayList<AdminCourse> storedAdminCourses = new ArrayList<>();
        String filename;
        File savedCoursesFile;

        try {
            filename = getSavedCoursesFilename();

            savedCoursesFile = new File(filename);

            if (savedCoursesFile.exists()) {
                storedAdminCourses = CourseFileParser.parseFile(filename);
            }
        } catch (Exception e) {
            //TODO: Improve error handling here:
            System.out.println("Something went wrong trying to get saved courses.");
        }

        return storedAdminCourses;
    }

    /**
     * Update the stored admin courses.
     * If an admin course has already been stored, it will be overwritten.
     *
     * @param coursesToAdd admin courses to newly store
     * @return admin courses that are overwritten.
     */
    public static ArrayList<AdminCourse> updateStoredAdminCourses(ArrayList<AdminCourse> coursesToAdd) {
        ArrayList<AdminCourse> overwrittenCourses = new ArrayList<>();
        ArrayList<AdminCourse> savedCourses = getStoredAdminCourses();
        Boolean courseAlreadySaved;

        for (AdminCourse courseToAdd : coursesToAdd) {
            courseAlreadySaved = false;
            for (AdminCourse savedCourse : savedCourses) {
                if (courseToAdd.getCode().equals(savedCourse.getCode())) {
                    courseAlreadySaved = true;
                    overwrittenCourses.add(courseToAdd);
                    savedCourse.updateCourseInfo(courseToAdd);
                }
            }

            if (!courseAlreadySaved) {
                savedCourses.add(courseToAdd);
            }
        }

        writeCoursesToFile(savedCourses);

        return overwrittenCourses;
    }

    /**
     * Write courses to be saved to the file where they are stored.
     *
     * @param coursesToWrite courses to save
     */
    public static void writeCoursesToFile(ArrayList<AdminCourse> coursesToWrite) {
        String courseLine;
        PrintWriter myWriter = null;

        try {
            myWriter = new PrintWriter(new FileWriter(new File(getSavedCoursesFilename())));

            for (AdminCourse course : coursesToWrite) {
                courseLine = "";
                courseLine = courseLine + course.getCode() + ",";
                courseLine = courseLine + course.getCredits() + ",";
                courseLine = courseLine + course.getName() + ",";

                for (int i = 0; i < course.getPrerequisiteCodes().size(); i++) {
                    courseLine = courseLine + course.getPrerequisiteCodes().get(i);

                    if (i + 1 < course.getPrerequisiteCodes().size()) {
                        courseLine = courseLine + ":";
                    }
                }
                myWriter.println(courseLine);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            myWriter.close();
        }
    }

    /**
     * Get the name of the directory where the programs are currently saved.
     *
     * @return name of directory containing saved programs.
     */
    public static String getSavedProgramsDirectory() {
        String separator = System.getProperty("file.separator");

        String folderName = "." + separator + "src" + separator + "main"
                + separator + "resources" + separator + "savedPrograms";

        return folderName;
    }

    /**
     * Get the stored programs.
     *
     * @return stored programs.
     */
    public static ArrayList<Program> getStoredPrograms() {
        ArrayList<Program> storedPrograms = new ArrayList<>();
        File folder;
        String filename;
        ArrayList<String> filenames = new ArrayList<>();
        String separator = System.getProperty("file.separator");

        try {
            folder = new File(getSavedProgramsDirectory());

            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    filename = getSavedProgramsDirectory() + separator + listOfFiles[i].getName();
                    //System.out.println(filename);
                    filenames.add(filename);
                }
            }

            for (String nameOfFile : filenames) {
                storedPrograms.add(ProgramFileParser.parseFile(nameOfFile));
            }
        } catch (Exception e) {
            //TODO: Improve error handling here:
            System.out.println("Something went wrong trying to get saved programs.");
        }
        return storedPrograms;
    }

    /**
     * Update the stored programs.
     * If a program has already been stored, it will be overwritten.
     *
     * @param programToAdd program to newly store
     * @return programs that are overwritten.
     */
    public static ArrayList<Program> updateStoredPrograms(Program programToAdd) {
        ArrayList<Program> overwrittenPrograms = new ArrayList<>();
        ArrayList<Program> savedPrograms = getStoredPrograms();
        Boolean programAlreadySaved = false;

        for (Program savedProgram : savedPrograms) {
            if (programToAdd.getName().equals(savedProgram.getName())) {
                programAlreadySaved = true;
                overwrittenPrograms.add(programToAdd);
                savedProgram.setRequiredCoursesByCode(programToAdd.getRequiredCoursesByCode());
            }
        }

        if (!programAlreadySaved) {
            savedPrograms.add(programToAdd);
        }

        writeProgramsToFile(savedPrograms);

        return overwrittenPrograms;
    }

    /**
     * Write programs to be saved to the files where they are stored.
     *
     * @param programsToWrite programs to save
     */
    public static void writeProgramsToFile(ArrayList<Program> programsToWrite) {
        String programLine;
        PrintWriter myWriter = null;

        try {
            for (Program program : programsToWrite) {
                myWriter = new PrintWriter(new FileWriter(new File(getProgramFilename(program.getName()))));

                programLine = "";
                programLine = programLine + program.getName() + ",";

                for (int i = 0; i < program.getRequiredCoursesByCode().size(); i++) {
                    programLine = programLine + program.getRequiredCoursesByCode().get(i);

                    if (i + 1 < program.getRequiredCoursesByCode().size()) {
                        programLine = programLine + ",";
                    }
                }
                myWriter.println(programLine);
                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            myWriter.close();
        }
    }

    /**
     * Get the name of the file that corresponds with the program.
     *
     * @param programName name of program to get filename for
     * @return filename of program in storage
     */
    public static String getProgramFilename(String programName) {
        String separator = System.getProperty("file.separator");
        return getSavedProgramsDirectory() + separator + programName + ".csv";
    }
}
