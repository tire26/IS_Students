package com.example.is_students.mai.controller;

import com.example.is_students.mai.FXMLPage;
import com.example.is_students.mai.entity.*;
import com.example.is_students.mai.service.StudentPageService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class StudentPageController {

    private User user;
    private Stage stage;
    private final StudentPageService studentPageService = new StudentPageService();

    @FXML
    private MenuButton choseCommandMenuButton;

    @FXML
    private Pane seeMarksPane;

    @FXML
    private Pane seeSelfInfo;

    @FXML
    private Pane seeSchedulePane;

    @FXML
    private GridPane scheduleGridPane;

    @FXML
    private GridPane selfInfoGridPane;

    @FXML
    private TableView<Mark> markTableView;

    @FXML
    private TableColumn<Mark, String> assessmentTypeColumn = new TableColumn<>("Тип оценивания");

    @FXML
    private TableColumn<Mark, String> subjectColumn = new TableColumn<>("Предмет");

    @FXML
    private TableColumn<Mark, String> markColumn = new TableColumn<>("Оценка");

    @FXML
    private Label firstLesson;

    @FXML
    private Label firstLessonInfo;

    @FXML
    private Label thirdLessonInfo;

    @FXML
    private Label thirdLesson;

    @FXML
    private Label fifthLesson;

    @FXML
    private Label fifthLessonInfo;

    @FXML
    private Label fourthLesson;

    @FXML
    private Label fourthLessonInfo;

    @FXML
    private Label secondLesson;

    @FXML
    private Label secondLessonInfo;

    @FXML
    private DatePicker currentDatePicker;

    @FXML
    private Button nextDay;

    @FXML
    private Button previousDay;

    @FXML
    private Button exitButton;

    @FXML
    private Label fioLabel;

    @FXML
    private Label documentLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label creditBookLabel;

    @FXML
    private Label groupNumberLabel;

    @FXML Label facultieLabel;

    @FXML Label courseLabel;

    @FXML Label statusLabel;

    @FXML
    public void onExitAction() {
        FXMLPage.load("/pages/mainPage.fxml", 600, 300, stage, user);
    }

    public StudentPageController() {

    }

    public void onChoseSeeSelfInfo() {
        if (stage == null) {
            initializeClassParameters();
        }
        setVisibleSeeSelfInfoActionElements(true);
        setVisibleSeeMarksActionElements(false);
        setVisibleSeeScheduleActionElements(false);

        fioLabel.setText(user.getName() + " " + user.getFullname() + " " + user.getSurname());

        Group group = studentPageService.getGroup(user);
        groupNumberLabel.setText(group == null ? null : group.getGroupNumber());

        List<PhoneNumber> phoneNumbers = studentPageService.getPhoneNumber(user);
        StringBuilder result = new StringBuilder();
        if (phoneNumbers.size() != 0) {
            result.append(phoneNumbers.get(0).getPhoneNumber());
            for (int i = 1; i < phoneNumbers.size(); i++) {
                result.append(phoneNumbers.get(i).getPhoneNumber()).append(";");
            }
        }
        phoneNumberLabel.setText(result.toString());
        result = new StringBuilder();

        List<Document> documents = studentPageService.getDocuments(user);
        if (documents.size() != 0) {
            result.append(documents.get(0).getDocumentNumber());
            for (int i = 1; i < documents.size(); i++) {
                result.append(";");
                result.append(documents.get(i).getDocumentNumber());
            }
        }
        documentLabel.setText(result.toString());
        result = new StringBuilder();

        CreditBook creditBook = studentPageService.getCreditBook(user);
        creditBookLabel.setText(creditBook.getCreditBookNumber());

        Faculty faculty = studentPageService.getFaculty(user);
        facultieLabel.setText(faculty.getFaculty());

        Course course = studentPageService.getCourse(user);
        courseLabel.setText(course.getCourse());

        Status status = studentPageService.getStatus(user);
        statusLabel.setText(status.getStatus());
        choseCommandMenuButton.setText("Увидеть информацию о себе");
    }

    public void onChoseSeeScheduleToday() {
        if (stage == null) {
            initializeClassParameters();
        }
        setVisibleSeeSelfInfoActionElements(false);
        setVisibleSeeMarksActionElements(false);
        setVisibleSeeScheduleActionElements(true);

        LocalDate currentDate = LocalDate.now();
        seeSchedule(currentDate);
    }


    public void onChoseSeeScheduleNextDay() {
        LocalDate newDate = (LocalDate) scheduleGridPane.getUserData();
        newDate = newDate.plusDays(1);
        scheduleGridPane.setUserData(newDate);
        seeSchedule(newDate);
    }

    public void onChoseSeeSchedulePreviousDay() {
        LocalDate newDate = (LocalDate) scheduleGridPane.getUserData();
        newDate = newDate.minusDays(1);
        scheduleGridPane.setUserData(newDate);
        seeSchedule(newDate);
    }

    public void onChoseSeeScheduleChosenDay() {
        LocalDate newDate = currentDatePicker.getValue();
        scheduleGridPane.setUserData(newDate);
        seeSchedule(newDate);
    }

    @FXML
    public void showMarks() {
        if (stage == null) {
            initializeClassParameters();
        }
        setVisibleSeeSelfInfoActionElements(false);
        setVisibleSeeScheduleActionElements(false);
        setVisibleSeeMarksActionElements(true);

        if (markTableView.getColumns().size() == 0) {
            initialize();
        }
        if (stage == null) {
            initializeClassParameters();
        }

        List<Mark> marks = studentPageService.findMarksOfUser(this.user);
        markTableView.setItems(FXCollections.observableList(marks));
        choseCommandMenuButton.setText("Увидеть оценки");
    }

    public void seeSchedule(LocalDate currentDate) {
        seeSchedule(currentDate, scheduleGridPane, currentDatePicker, studentPageService.getLessons(user, currentDate), firstLessonInfo, secondLessonInfo, thirdLessonInfo, fourthLessonInfo, fifthLessonInfo, choseCommandMenuButton);
    }

    public static void seeSchedule(LocalDate currentDate, GridPane scheduleGridPane, DatePicker currentDatePicker, List<Lesson> lessons, Label firstLessonInfo, Label secondLessonInfo, Label thirdLessonInfo, Label fourthLessonInfo, Label fifthLessonInfo, MenuButton choseCommandMenuButton) {
        scheduleGridPane.setUserData(currentDate);
        currentDatePicker.setValue(currentDate);

        firstLessonInfo.setText(lessons.size() == 0 || lessons.get(0) == null ? "Занятий нет" : lessons.get(0).getTime().toString() + ": Аудитория" + lessons.get(0).getClassroom().getClassroom());
        secondLessonInfo.setText(lessons.size() <= 1 || lessons.get(1) == null ? "Занятий нет" : lessons.get(1).getTime().toString() + ": Аудитория" + lessons.get(1).getClassroom().getClassroom());
        thirdLessonInfo.setText(lessons.size() <= 2 || lessons.get(2) == null ? "Занятий нет" : lessons.get(2).getTime().toString() + " :Аудитория" + lessons.get(2).getClassroom().getClassroom());
        fourthLessonInfo.setText(lessons.size() <= 3 || lessons.get(3) == null ? "Занятий нет" : lessons.get(3).getTime().toString() + " :Аудитория" + lessons.get(3).getClassroom().getClassroom());
        fifthLessonInfo.setText(lessons.size() <= 4 || lessons.get(4) == null ? "Занятий нет" : lessons.get(4).getTime().toString() + " :Аудитория" + lessons.get(4).getClassroom().getClassroom());
        choseCommandMenuButton.setText("Увидеть расписание");
    }

    @FXML
    private void initialize() {
        assessmentTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssessmentType().getAssessment()));
        assessmentTypeColumn.setPrefWidth(2 * markTableView.getPrefWidth() / 5);

        subjectColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubject().getSubject()));
        subjectColumn.setPrefWidth(2 * markTableView.getPrefWidth() / 5);

        markColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMark().getMark()));
        markColumn.setPrefWidth(markTableView.getPrefWidth() / 5);

        markTableView.getColumns().addAll(assessmentTypeColumn, subjectColumn, markColumn);

    }

    private void setVisibleSeeMarksActionElements(boolean visibleMarksElements) {
        seeMarksPane.setDisable(!visibleMarksElements);
        seeMarksPane.setVisible(visibleMarksElements);
        markTableView.getColumns().clear();
        markTableView.setVisible(visibleMarksElements);
    }

    private void setVisibleSeeScheduleActionElements(boolean visibleMarksElements) {
        seeSchedulePane.setDisable(!visibleMarksElements);
        seeSchedulePane.setVisible(visibleMarksElements);
        firstLesson.setText("Первая пара:");
        secondLesson.setText("Вторая пара:");
        thirdLesson.setText("Третья пара:");
        fourthLesson.setText("Четвертая пара:");
        fifthLesson.setText("Пятая пара:");

        nextDay.setVisible(visibleMarksElements);
        previousDay.setVisible(visibleMarksElements);
    }

    private void setVisibleSeeSelfInfoActionElements(boolean visibleMarksElements) {
        seeSelfInfo.setDisable(!visibleMarksElements);
        seeSelfInfo.setVisible(visibleMarksElements);

    }

    private void initializeClassParameters() {
        stage = (Stage) exitButton.getScene().getWindow();
        user = (User) stage.getUserData();
    }

}
