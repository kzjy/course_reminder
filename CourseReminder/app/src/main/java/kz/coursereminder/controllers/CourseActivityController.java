package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.Grade;
import kz.coursereminder.structure.Reminder;


public class CourseActivityController extends CourseControllers implements Serializable {

    private Course currentCourse;

    public CourseActivityController(Context context, String courseName) {
        super(context);
        this.currentCourse = courseManager.getSpecificCourse(courseName);
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    /**
     * Save an edit to the course name, info or notes
     *
     * @param input the edit to be saved
     * @param type  one of name, info or notes
     */
    public void saveEdits(String input, String type) {
        switch (type) {
            case ("name"):
                Course dummyCourse = new Course(input, "", 0);
                if (checkValidName(dummyCourse)) {
                    currentCourse.setName(input);
                }
                break;
            case ("info"):
                currentCourse.setInfo(input);
                break;
            case ("notes"):
                currentCourse.setNotes(input);
                break;
        }
        save();
        makeTosstEditSsved();
    }

    /**
     * Make Toast edit saved
     */
    private void makeTosstEditSsved() {
        Toast.makeText(context, "Edits saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Add a reminder to the current course
     *
     * @param reminder reminder to be added
     * @return whether addition is successful
     */
    public boolean addReminder(Reminder reminder) {
        boolean succesful = courseManager.addReminderToCourse(currentCourse, reminder);
        save();
        return succesful;
    }

    /**
     * Check stringInput for empty or 0 inputs
     *
     * @param stringInput string input of grade
     * @return whether it is valid
     */
    public boolean checkNotValidGradeInput(String[] stringInput) {
        for (int i = 0; i < stringInput.length; i++) {
            if (stringInput[i].equals("")) {
                return true;
            }
            if (i == 1) {
                if (Float.valueOf(stringInput[i]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes an assignment from course
     *
     * @param position position of assignment in list
     */
    public void removeAssignment(int position) {
        courseManager.removeReminderFromCourse(currentCourse, position,
                currentCourse.getReminders().get(position));
        save();
    }

    /**
     * Assign a grade to an assignment
     *
     * @param grade            grade to be added
     * @param positionSelected position in list of assignment
     */
    public void addGradeToAssignment(Grade grade, int positionSelected) {
        currentCourse.setReminderGrade(grade, positionSelected);
        save();
    }

    /**
     * Edit grade in completed assignments
     *
     * @param grade            new grade to be changed
     * @param positionSelected position of the assignment
     */
    public void editGrade(Grade grade, int positionSelected) {
        currentCourse.editReminderGrade(grade, positionSelected);
        save();
    }

    /**
     * Save the course manager
     */
    private void save() {
        fileManager.writeFile(CourseManager.COURSES, courseManager);
    }

    /**
     * Delete the current course and save its changes
     */
    public void deleteCurrentCourse() {
        courseManager.deleteCourse(currentCourse);
        save();
    }

    /**
     * Reads the most recent coursemanager file and updates the controller
     */
    public void updateController() {
        fileManager.loadFile(CourseManager.COURSES);
        this.courseManager = fileManager.getCourseManager();
        currentCourse = courseManager.getSpecificCourse(currentCourse.getName());
    }

    public int calculateSpinnerMinutesBefore(int position) {
        switch (position) {
            case 0:
                return 30;
            case 1:
                return 60;
            case 2:
                return 120;
            case 3:
                return 240;
            case 4:
                return 1440;
            default:
                return 30;
        }
    }



    public void addSpinnerOptions(List<String> notificationTime) {
        notificationTime.add("30 minutes");
        notificationTime.add("1 hour");
        notificationTime.add("2 hours");
        notificationTime.add("4 hours");
        notificationTime.add("1 day");
    }
}
