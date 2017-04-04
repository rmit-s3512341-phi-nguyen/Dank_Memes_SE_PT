package core.controller;

import core.model.Availability;
import core.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by louie on 31/03/2017.
 */
public class AvailabilityController {

    private static final Logger log = LogManager.getLogger(AvailabilityController.class.getName());

    @FXML
    private Button btnSaveTimes;

    @FXML
    private Label lblLoginError;

    @FXML
    private Button btnBackToBusinessScreen;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private Label empNameDisplay;

    @FXML
    private CheckBox monMorning;
    @FXML
    private CheckBox tueMorning;
    @FXML
    private CheckBox wedMorning;
    @FXML
    private CheckBox thurMorning;
    @FXML
    private CheckBox friMorning;

    @FXML
    private CheckBox monAfternoon;
    @FXML
    private CheckBox tueAfternoon;
    @FXML
    private CheckBox wedAfternoon;
    @FXML
    private CheckBox thurAfternoon;
    @FXML
    private CheckBox friAfternoon;

    @FXML
    private CheckBox monEvening;
    @FXML
    private CheckBox tueEvening;
    @FXML
    private CheckBox wedEvening;
    @FXML
    private CheckBox thurEvening;
    @FXML
    private CheckBox friEvening;

    @FXML
    private CheckBox satMorning;

    @FXML
    private CheckBox sunMorning;
    @FXML
    private CheckBox satAfternoon;
    @FXML
    private CheckBox sunAfternoon;
    @FXML
    private CheckBox satEvening;
    @FXML
    private CheckBox sunEvening;


    /*
    @FXML
    public void initialize() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();

        cal.setTime(today);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        today = cal.getTime();

        MondayDate.setText(new SimpleDateFormat("dd/MM").format(today));

        cal.add(Calendar.DAY_OF_WEEK, 6 );
        today = cal.getTime();

        SundayDate.setText(new SimpleDateFormat("dd/MM").format(today));
    }
*/

    private static final char AVAILABLE = '1';
    private static final char UNAVAILABLE = '0';
    private static final int EXISTS = 1;
    private String dayAvailability = "";
    private int empID = -1;

    /** Saves availability to the currently selected employee */
    public Boolean btnSaveTimes(ActionEvent event) throws IOException {

        log.debug("Save times button clicked");

        if(empID < 1){
            lblLoginError.setText("No Employee selected");
            log.debug("Save times clicked with no employee loaded");
            return false;
            /* no emp loaded */
        }
        else {
            Availability availability = new Availability();

            log.debug("Calling the method to set availability for each day");

            setMonday();
            setTuesday();
            setWednesday();
            setThursday();
            setFriday();
            setSaturday();
            setSunday();

            log.debug("Calling addAvailability method, leaving controller...");
            if (!availability.addAvailability(dayAvailability,empID)) {
                log.debug("Returned to controller, add availability failed.");
                return false;
            }
            log.debug("Returned to controller, availability added.");
            lblLoginError.setText("Success!!");
            dayAvailability = "";
            return true;
        }
    }


    /** loads in an employee to alter there availability  */
    public void loadEmp(ActionEvent event) throws IOException {
        log.debug("Load employee button clicked");
        String empName;
        char first;

        empName = txtEmployeeName.getCharacters().toString();

        if(empName.isEmpty()){
            log.debug("No employee entered");
            lblLoginError.setText("Employee does not exist");
        }
        else {

        /* Chaptalize first char */
            first = Character.toUpperCase(empName.charAt(0));
            empName = first + empName.substring(1);

            try {
                log.debug("Calling findEmployee method, leaving controller...");
                if ((empID = Employee.findEmployee(empName)) >= EXISTS) {
                    log.debug("Returned to controller, employee found and loaded: " + empID);
                    empNameDisplay.setText(empName);
                    lblLoginError.setText("");
                }
                log.debug("Returned to controller, failed to find and load employee");
            } catch (SQLException e) {
                log.error("SQL ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /*TODO if emp already has availability load in to checkboxes */

    /** Helpers */
    /** sets data for sunday */
    private void setSunday() {
        log.debug("Inside setSunday method");

        set(sunMorning.isSelected());
        set(sunAfternoon.isSelected());
        set(sunEvening.isSelected());
    }
    /** sets data for saturday */
    private void setSaturday() {
        log.debug("Inside setSaturday method");

        set(satMorning.isSelected());
        set(satAfternoon.isSelected());
        set(satEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for friday */
    private void setFriday() {
        log.debug("Inside setFriday method");

        set(friMorning.isSelected());
        set(friAfternoon.isSelected());
        set(friEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for thursday */
    private void setThursday() {
        log.debug("Inside setThursday method");
        set(thurMorning.isSelected());
        set(thurAfternoon.isSelected());
        set(thurEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for wednesday */
    private void setWednesday() {
        log.debug("Inside setWednesday method");
        set(wedMorning.isSelected());
        set(wedAfternoon.isSelected());
        set(wedEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for tuesday */
    private void setTuesday() {
        log.debug("Inside setTuesday method");
        set(tueMorning.isSelected());
        set(tueAfternoon.isSelected());
        set(tueEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for monday */
    private void setMonday() {
        log.debug("Inside setMonday method");
        set(monMorning.isSelected());
        set(monAfternoon.isSelected());
        set(monEvening.isSelected());
        dayAvailability += ',';
    }
    /** Sets a shifts availability e.g 8am - 12pm */
    private void set(Boolean available){
        if(available) {
            dayAvailability += AVAILABLE;
        }else{
            dayAvailability += UNAVAILABLE;
        }
    }

    @FXML
    public void btnBackToBusinessScreen(ActionEvent event) throws IOException {
        log.debug("Back (to business home screen) button clicked");
        log.debug("Loading business home screen, switching controller to Main controller...");
        Parent business_parent = FXMLLoader.load(getClass().getResource("../view/BusinessHome.fxml"));
        Scene business_scene = new Scene (business_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(business_scene);
        primaryStage.show();
    }

}
