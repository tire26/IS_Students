package com.example.is_students.mai.controller;

import com.example.is_students.mai.FXMLPage;
import com.example.is_students.mai.additionalClasses.TableData;
import com.example.is_students.mai.entity.*;
import com.example.is_students.mai.service.TeacherPageService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;


public class TeacherPageController {

    private User user;
    private Stage stage;
    private final TeacherPageService teacherPageService = new TeacherPageService();
    private final List<RefMark> FINAL_MARKS = teacherPageService.getRefMarks();
    private final List<AssessmentType> assessmentTypes = teacherPageService.getAssessmentTypes();
    @FXML
    private Pane seeSchedulePane;

    @FXML
    private Pane setMarksPane;

    @FXML
    private MenuButton groupMenuButton;

    @FXML
    private MenuButton subjectMenuButton;

    @FXML
    private MenuButton choseCommandMenuButton;

    @FXML
    private TableView<TableData> markTableView;

    @FXML
    private GridPane scheduleGridPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button exitButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button nextDay;

    @FXML
    private Button previousDay;

    @FXML
    private Label fioTextField;

    @FXML
    private TextField markTextField;

    @FXML
    private Label creditBookTextField;

    private TableColumn<TableData, String> markColumn = new TableColumn<>("Оценка");

    private TableColumn<TableData, String> creditBookTableColumn = new TableColumn<>("Номер зачётки");

    @FXML
    private Label message;

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

    public TeacherPageController() {
    }


    public void onExitAction() {
        if (stage == null) {
            initializeClassParameters();
        }
        FXMLPage.load("/pages/mainPage.fxml", 600, 300, stage, user);
    }

    public void onChoseSeeScheduleToday() {
        if (stage == null) {
            initializeClassParameters();
        }

        setVisibleSetMarksFunctionElements(false);
        setVisibleSeeScheduleFunctionElements(true);
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

    public void onChoseSetMarksAction() {

        setVisibleSetMarksFunctionElements(true);
        setVisibleSeeScheduleFunctionElements(false);

        if (stage == null) {
            initializeClassParameters();
        }

        subjectMenuButton.getItems().clear();
        List<Subject> lessons = teacherPageService.getSubjects(user);
        for (Subject lesson : lessons) {
            MenuItem menuItem = new MenuItem(lesson.getSubject());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(lesson);
            menuItem.setOnAction(event -> onChoseSubjectAction((Subject) menuItem.getUserData()));
            subjectMenuButton.getItems().add(menuItem);
        }
        choseCommandMenuButton.setText("Выставить оценки");
    }

    private void onChoseSubjectAction(Subject subject) {
        groupMenuButton.getItems().clear();
        List<Group> groups = teacherPageService.getGroups(user, subject);
        for (Group group : groups) {
            MenuItem menuItem = new MenuItem(group.getGroupNumber());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(group);
            menuItem.setOnAction(event -> {
                onChoseGroupAction((Group) menuItem.getUserData());
            });
            groupMenuButton.getItems().add(menuItem);
        }
        subjectMenuButton.setUserData(subject);
        subjectMenuButton.setText(subject.getSubject());
        groupMenuButton.setText("Группа");
    }

    private void onChoseGroupAction(Group group) {
        if (markTableView.getColumns().size() == 0) {
            initialize();
        }
        groupMenuButton.setUserData(group);
        groupMenuButton.setText(group.getGroupNumber());

        fillMarkTableView();
        setNoneField();
    }

    public void onSubmitAction() {
        String mark = markTextField.getText();
        RefMark refMark = createRefMark(mark);
        if (refMark != null) {
            Mark studentMark = (Mark) gridPane.getUserData();
            if (refMark.getMark().matches("\\d")) {
                studentMark.setAssessmentType(assessmentTypes.get(0));
            } else {
                studentMark.setAssessmentType(assessmentTypes.get(2));
            }
            teacherPageService.saveMark(studentMark, refMark);
        } else {
            message.setText("неправильно введена оценка");
        }
        fillMarkTableView();
    }

    public void seeSchedule(LocalDate currentDate) {
        StudentPageController.seeSchedule(currentDate, scheduleGridPane, currentDatePicker, teacherPageService.getLessons(user, currentDate), firstLessonInfo, secondLessonInfo, thirdLessonInfo, fourthLessonInfo, fifthLessonInfo, choseCommandMenuButton);
    }

    @FXML
    private void initialize() {
        markColumn.setCellValueFactory(cellData -> cellData.getValue().getMarkString());
        markColumn.setPrefWidth(markTableView.getPrefWidth() / 3);

        creditBookTableColumn.setCellValueFactory(cellData -> cellData.getValue().getCreditBookString());
        creditBookTableColumn.setPrefWidth(2 * markTableView.getPrefWidth() / 3);

        markTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showStudentDetail(newValue == null ? null : newValue.getMarkTableData()));
        markTableView.getColumns().addAll(creditBookTableColumn, markColumn);
    }

    private void showStudentDetail(Mark studentMark) {
        if (studentMark != null) {
            User currentUser = studentMark.getCreditBook().getStudent().getUser();
            gridPane.setUserData(studentMark);
            fioTextField.setText(currentUser.getName() + " " + currentUser.getFullname() + " " + currentUser.getSurname());
            creditBookTextField.setText(studentMark.getCreditBook().getCreditBookNumber());
            markTextField.setText(studentMark.getMark() == null ? null : studentMark.getMark().getMark());
        } else {
            setNoneField();
        }
    }

    private void setVisibleSetMarksFunctionElements(boolean visibleMarksElements) {
        markTableView.getColumns().clear();
        markTableView.setVisible(visibleMarksElements);
        markColumn.setVisible(visibleMarksElements);
        creditBookTableColumn.setVisible(visibleMarksElements);
        gridPane.setVisible(visibleMarksElements);
        gridPane.setVisible(visibleMarksElements);
        groupMenuButton.setVisible(visibleMarksElements);
        groupMenuButton.setText("Группа");
        subjectMenuButton.setVisible(visibleMarksElements);
        subjectMenuButton.setText("Предмет");
        submitButton.setVisible(visibleMarksElements);
        setMarksPane.setVisible(visibleMarksElements);
        setMarksPane.setDisable(!visibleMarksElements);
    }

    private void setVisibleSeeScheduleFunctionElements(boolean visibleMarksElements) {

        firstLesson.setText("Первая пара:");
        secondLesson.setText("Вторая пара:");
        thirdLesson.setText("Третья пара:");
        fourthLesson.setText("Четвертая пара:");
        fifthLesson.setText("Пятая пара:");

        nextDay.setVisible(visibleMarksElements);
        previousDay.setVisible(visibleMarksElements);
        seeSchedulePane.setVisible(visibleMarksElements);
        seeSchedulePane.setDisable(!visibleMarksElements);
    }

    private void initializeClassParameters() {
        stage = (Stage) exitButton.getScene().getWindow();
        user = (User) stage.getUserData();
    }

    private void fillMarkTableView() {
        List<TableData> tableDataList = teacherPageService.getCreditBooks((Group) groupMenuButton.getUserData(), (Subject) subjectMenuButton.getUserData());
        markTableView.setItems(FXCollections.observableList(tableDataList));
    }

    private RefMark createRefMark(String mark) {
        for (RefMark finalMark : FINAL_MARKS) {
            if (mark.equals(finalMark.getMark())) {
                return finalMark;
            }
        }
        return null;
    }

    private void setNoneField() {
        fioTextField.setText("");
        creditBookTextField.setText("");
        markTextField.setText("");
    }
}

