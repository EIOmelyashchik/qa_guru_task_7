package tests;

import bussiness_objects.Student;
import helpers.TestResultHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.StudentRegistrationFormPage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

@ExtendWith(TestResultHelper.class)
@DisplayName("Student registration form tests")
public class StudentRegistrationFormTest extends TestBase {

    private Student student;
    private Map<String, String> expectedData;

    @BeforeEach
    void generateData() {
        student = StudentRegistrationFormData.generateStudent();
        expectedData = StudentRegistrationFormData.generateExpectedData(student);
    }

    @Test
    @Owner("omelyashchik")
    @Feature("Registration form")
    @DisplayName("Successful fill student registration form")
    void successfulFillStudentRegistrationForm() {
        StudentRegistrationFormPage studentRegistrationFormPage =
                open(StudentRegistrationFormPage.PAGE_URL, StudentRegistrationFormPage.class);

        studentRegistrationFormPage.setFirstName(student.getFirstName())
                .setLastName(student.getLastName())
                .setEmail(student.getEmail())
                .setGender(student.getGender())
                .setMobileNumber(student.getMobileNumber())
                .setBirthDate(student.getDateOfBirth())
                .setSubjects(student.getSubjects())
                .setHobbies(student.getHobbies())
                .uploadPicture(student.getPathToPicture())
                .setCurrentAddress(student.getAddress())
                .setState(student.getState())
                .setCity(student.getCity())
                .submit()
                .popupShouldAppear();

        SoftAssertions softly = new SoftAssertions();
        for (Map.Entry<String, String> data : expectedData.entrySet()) {
            String rowName = data.getKey();
            String expectedValueInTableRow = data.getValue();

            Allure.step("Check that row '" + rowName + "' contains value '" + expectedValueInTableRow + "'", () -> {
                String actualValueInTableRow = studentRegistrationFormPage.getValueInTableRow(rowName);
                softly.assertThat(actualValueInTableRow).as(rowName).isEqualTo(expectedValueInTableRow);
            });
        }
        softly.assertAll();
    }

    @Test
    @Owner("omelyashchik")
    @Feature("Registration form")
    @DisplayName("Failed fill student registration form")
    void failedFillStudentRegistrationForm() {
        StudentRegistrationFormPage studentRegistrationFormPage =
                open(StudentRegistrationFormPage.PAGE_URL, StudentRegistrationFormPage.class);

        studentRegistrationFormPage.setFirstName(student.getFirstName())
                //do not enter the last name for unsuccessful filling of the form
                //.setLastName(student.getLastName())
                .setEmail(student.getEmail())
                .setGender(student.getGender())
                .setMobileNumber(student.getMobileNumber())
                .setBirthDate(student.getDateOfBirth())
                .setSubjects(student.getSubjects())
                .setHobbies(student.getHobbies())
                .uploadPicture(student.getPathToPicture())
                .setCurrentAddress(student.getAddress())
                .setState(student.getState())
                .setCity(student.getCity())
                .submit()
                .popupShouldAppear();
    }
}
