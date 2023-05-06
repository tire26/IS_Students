package com.example.is_students.mai.controller;

import com.example.is_students.mai.FXMLPage;
import com.example.is_students.mai.additionalClasses.ConverterCyrrilicToEnglish;
import com.example.is_students.mai.additionalClasses.PasswordGenerator;
import com.example.is_students.mai.additionalClasses.UserTableData;
import com.example.is_students.mai.entity.*;
import com.example.is_students.mai.service.AdministratorPageService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class AdministratorPageController {

    private User user;
    private Stage stage;
    private final AdministratorPageService administratorPageService = new AdministratorPageService();
    private final PasswordGenerator passwordGenerator;
    private final String STUDENT_TYPE = "Студент", TEACHER_TYPE = "Преподаватель";
    private final int COUNT_OF_LESSONS = 5;
    private final List<LessonsTime> lessonsTime = administratorPageService.getLessonsTime();

    @FXML
    private MenuButton choseCommandMenuButton;

    @FXML
    private MenuButton courseMenuButton;

    @FXML
    private Button createGroupButton;

    @FXML
    private Pane createGroupPane;

    @FXML
    private Button createUserButton;

    @FXML
    private Button createUserButtonCreate;

    @FXML
    private Pane createUserPane;

    @FXML
    private Label creditBookLabel;

    @FXML
    private Label creditBookLabelCreate;

    @FXML
    private TextField creditBookTextField;

    @FXML
    private TextField creditBookTextFieldCreate;

    @FXML
    private DatePicker currentDatePicker;

    @FXML
    private TextField documentTextField;

    @FXML
    private TextField documentTextFieldCreate;

    @FXML
    private Button exitButton;

    @FXML
    private MenuButton facultyMenuButton;

    @FXML
    private Label fifthLesson;

    @FXML
    private MenuButton fifthLessonRoom;

    @FXML
    private MenuButton fifthLessonSubject;

    @FXML
    private MenuButton fifthLessonTeacher;

    @FXML
    private TextField fioTextField;

    @FXML
    private TextField fioTextFieldCreate;

    @FXML
    private Label firstLesson;

    @FXML
    private MenuButton firstLessonRoom;

    @FXML
    private MenuButton firstLessonSubject;

    @FXML
    private MenuButton firstLessonTeacher;


    @FXML
    private Label fourthLesson;

    @FXML
    private MenuButton fourthLessonSubject;

    @FXML
    private MenuButton fourthLessonTeacher;


    @FXML
    private MenuButton fourthLessonRoom;

    @FXML
    private GridPane groupInfoGridPane;

    @FXML
    private TextField groupNumberText;

    @FXML
    private Label groupStudentLabel;

    @FXML
    private Label groupStudentLabelCreate;

    @FXML
    private MenuButton groupStudentMenuButton;

    @FXML
    private MenuButton groupStudentMenuButtonCreate;

    @FXML
    private Text message;

    @FXML
    private Text messageCreateUserText;

    @FXML
    private Text messageGroupCreate;

    @FXML
    private Button nextDay;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField phoneNumberTextFieldCreate;

    @FXML
    private Button previousDay;

    @FXML
    private GridPane scheduleGridPane;

    @FXML
    private MenuButton scheduleGroupMenuButton;

    @FXML
    private Pane schedulePane;

    @FXML
    private Label secondLesson;

    @FXML
    private MenuButton secondLessonRoom;

    @FXML
    private MenuButton secondLessonSubject;

    @FXML
    private MenuButton secondLessonTeacher;


    @FXML
    private Label statusStudentLabel;

    @FXML
    private Label statusStudentLabelCreate;

    @FXML
    private MenuButton statusStudentMenuButton;

    @FXML
    private MenuButton statusStudentMenuButtonCreate;

    @FXML
    private Label thirdLesson;

    @FXML
    private MenuButton thirdLessonRoom;

    @FXML
    private MenuButton thirdLessonSubject;

    @FXML
    private MenuButton thirdLessonTeacher;


    @FXML
    private TableView<UserTableData> userDataTableView;

    @FXML
    private GridPane userInfoCreateGridPane;

    @FXML
    private GridPane userInfoGridPane;

    @FXML
    private MenuButton userTypeMenuButton;

    @FXML
    private MenuButton userTypeMenuButtonCreate;

    @FXML
    private Pane workWithUserPane;

    @FXML
    private Button confirmScheduleChangesButton;


    public AdministratorPageController() {
        passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
    }


    public void onWorkWithSchedule() {
        if (stage == null) {
            initializeClassParameters();
        }

        setVisibleWorkWithSchedule(true);
        setVisibleWorkWithUserActionElements(false);
        setVisibleCreateGroupActionElements(false);
        setVisibleCreateUserElements(false);

        List<Group> groupList = administratorPageService.getGroups();
        for (Group group : groupList) {
            MenuItem menuItem = new MenuItem(group.getGroupNumber());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(group);
            menuItem.setOnAction(event -> onChoseGroupActionSchedule((Group) menuItem.getUserData()));
            scheduleGroupMenuButton.getItems().add(menuItem);
        }
    }


    public void onWorkWithUsers() {
        if (stage == null) {
            initializeClassParameters();
        }
        setVisibleWorkWithSchedule(false);
        setVisibleWorkWithUserActionElements(true);
        setVisibleCreateGroupActionElements(false);
        setVisibleCreateUserElements(false);
    }

    public void onCreateGroupAction() {
        if (stage == null) {
            initializeClassParameters();
        }
        setVisibleWorkWithSchedule(false);
        setVisibleCreateUserElements(false);
        setVisibleWorkWithUserActionElements(false);
        setVisibleCreateGroupActionElements(true);

        List<Course> courseList = administratorPageService.getCourses();
        List<Faculty> facultyList = administratorPageService.getFaculty();
        for (Course course : courseList) {
            MenuItem menuItem = new MenuItem(course.getCourse());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(course);
            menuItem.setOnAction(event -> onChoseCourseAction((Course) menuItem.getUserData()));
            courseMenuButton.getItems().add(menuItem);
        }
        for (Faculty faculty : facultyList) {
            MenuItem menuItem = new MenuItem(faculty.getFaculty());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(faculty);
            menuItem.setOnAction(event -> onChoseFacultyAction((Faculty) menuItem.getUserData()));
            facultyMenuButton.getItems().add(menuItem);
        }
    }

    public void onCreateUserAction() {
        if (stage == null) {
            initializeClassParameters();
        }
        setVisibleWorkWithSchedule(false);
        setVisibleCreateUserElements(true);
        setVisibleWorkWithUserActionElements(false);
        setVisibleCreateGroupActionElements(false);
    }

    public void onCreateGroupButton() {
        Group newGroup = new Group();
        newGroup.setCourse((Course) courseMenuButton.getUserData());
        newGroup.setFaculty((Faculty) facultyMenuButton.getUserData());
        newGroup.setGroupNumber(groupNumberText.getText());
        administratorPageService.saveGroup(newGroup);

        setCreateGroupFieldDefault();
        messageGroupCreate.setText("Сохранение успешно");
        messageGroupCreate.setStyle("-fx-text-fill: #00FF00");
        messageGroupCreate.setVisible(true);
    }

    public void onCreateUserButtonAction() {
        User newUser = new User();
        String[] toArrayFIO = fioTextFieldCreate.getText().split(" ");
        String name = toArrayFIO.length < 1 ? "" : toArrayFIO[0];
        String fullName = toArrayFIO.length < 2 ? "" : toArrayFIO[1];
        String surname = toArrayFIO.length < 3 ? "" : toArrayFIO[2];
        newUser.setName(name);
        newUser.setFullname(fullName);
        newUser.setSurname(surname);
        newUser.setUserTypeId(2L);
        newUser.setLogin(ConverterCyrrilicToEnglish.convert(name + fullName + surname));
        newUser.setPassword(passwordGenerator.generate(10));
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber(phoneNumberTextFieldCreate.getText());

        Document document = new Document();
        document.setDocumentNumber(documentTextFieldCreate.getText());

        if (userTypeMenuButtonCreate.getText().equals(STUDENT_TYPE)) {
            Student newStudent = new Student();
            newStudent.setGroup((Group) groupStudentMenuButtonCreate.getUserData());
            newStudent.setStatus((Status) statusStudentMenuButtonCreate.getUserData());
            CreditBook newCreditBook = new CreditBook();
            newCreditBook.setCreditBookNumber(creditBookTextFieldCreate.getText());
            administratorPageService.saveStudent(newUser, phoneNumber, document, newStudent, newCreditBook);
        } else {
            administratorPageService.saveTeacher(newUser, phoneNumber, document);
        }
        setCreateUserFieldDefault();
        messageCreateUserText.setText("Сохранение успешно");
        messageCreateUserText.setStyle("-fx-text-fill: #00FF00");
        messageCreateUserText.setVisible(true);
    }

    public void onChoseSeeScheduleNextDay() {
        LocalDate newDate = (LocalDate) schedulePane.getUserData();
        newDate = newDate.plusDays(1);
        schedulePane.setUserData(newDate);
        setDisableWorkWithScheduleElements(true);
        fillSchedule(newDate);
        setTeachersItems(newDate);
        setDisableWorkWithScheduleElements(false);
    }

    public void onChoseSeeSchedulePreviousDay() {
        LocalDate newDate = (LocalDate) schedulePane.getUserData();
        newDate = newDate.minusDays(1);
        schedulePane.setUserData(newDate);
        setDisableWorkWithScheduleElements(true);
        fillSchedule(newDate);
        setTeachersItems(newDate);
        setDisableWorkWithScheduleElements(false);
    }

    public void onChoseSeeScheduleChosenDay() {
        LocalDate newDate = currentDatePicker.getValue();
        schedulePane.setUserData(newDate);
        setDisableWorkWithScheduleElements(true);
        fillSchedule(newDate);
        setTeachersItems(newDate);
        setDisableWorkWithScheduleElements(false);
    }

    private void onChoseGroupActionSchedule(Group userData) {
        scheduleGroupMenuButton.setUserData(userData);
        scheduleGroupMenuButton.setText(userData.getGroupNumber());
        onChoseSeeScheduleToday();
    }

    private void onChoseSeeScheduleToday() {
        LocalDate currentDate = LocalDate.now();
        schedulePane.setUserData(currentDate);
        fillSchedule(currentDate);
        setTeachersItems(currentDate);
    }

    public void confirmSchedule() {

        Lesson firstLesson = new Lesson();
        if (schedulePane.getUserData() != null && firstLessonTeacher.getUserData() != null && scheduleGroupMenuButton.getUserData() != null && firstLessonSubject.getUserData() != null && firstLessonRoom.getUserData() != null) {
            firstLesson.setGroup((Group) scheduleGroupMenuButton.getUserData());
            firstLesson.setUser((User) firstLessonTeacher.getUserData());
            firstLesson.setTime(Date.valueOf((LocalDate) schedulePane.getUserData()));
            firstLesson.setLessonsTime(lessonsTime.get(0));
            firstLesson.setSubject((Subject) firstLessonSubject.getUserData());
            firstLesson.setClassroom((Classroom) firstLessonRoom.getUserData());
            firstLesson.setLessonId(this.firstLesson.getUserData() == null ? null : (Long) this.firstLesson.getUserData());
            administratorPageService.saveOrUpdateLesson(firstLesson);
        }

        Lesson secondLesson = new Lesson();
        if (schedulePane.getUserData() != null && secondLessonTeacher.getUserData() != null && scheduleGroupMenuButton.getUserData() != null && secondLessonSubject.getUserData() != null && secondLessonRoom.getUserData() != null) {
            secondLesson.setGroup((Group) scheduleGroupMenuButton.getUserData());
            secondLesson.setUser((User) secondLessonTeacher.getUserData());
            secondLesson.setTime(Date.valueOf((LocalDate) schedulePane.getUserData()));
            secondLesson.setLessonsTime(lessonsTime.get(0));
            secondLesson.setSubject((Subject) secondLessonSubject.getUserData());
            secondLesson.setClassroom((Classroom) secondLessonRoom.getUserData());
            secondLesson.setLessonId(this.secondLesson.getUserData() == null ? null : (Long) this.secondLesson.getUserData());
            administratorPageService.saveOrUpdateLesson(secondLesson);
        }

        Lesson thirdLesson = new Lesson();
        if (schedulePane.getUserData() != null && thirdLessonTeacher.getUserData() != null && scheduleGroupMenuButton.getUserData() != null && thirdLessonSubject.getUserData() != null && thirdLessonRoom.getUserData() != null) {
            thirdLesson.setGroup((Group) scheduleGroupMenuButton.getUserData());
            thirdLesson.setUser((User) thirdLessonTeacher.getUserData());
            thirdLesson.setTime(Date.valueOf((LocalDate) schedulePane.getUserData()));
            thirdLesson.setLessonsTime(lessonsTime.get(0));
            thirdLesson.setSubject((Subject) thirdLessonSubject.getUserData());
            thirdLesson.setClassroom((Classroom) thirdLessonRoom.getUserData());
            thirdLesson.setLessonId(this.thirdLesson.getUserData() == null ? null : (Long) this.thirdLesson.getUserData());
            administratorPageService.saveOrUpdateLesson(thirdLesson);
        }

        Lesson fourthLesson = new Lesson();
        if (schedulePane.getUserData() != null && fourthLessonTeacher.getUserData() != null && scheduleGroupMenuButton.getUserData() != null && fourthLessonSubject.getUserData() != null && fourthLessonRoom.getUserData() != null) {
            fourthLesson.setGroup((Group) scheduleGroupMenuButton.getUserData());
            fourthLesson.setUser((User) fourthLessonTeacher.getUserData());
            fourthLesson.setTime(Date.valueOf((LocalDate) schedulePane.getUserData()));
            fourthLesson.setLessonsTime(lessonsTime.get(0));
            fourthLesson.setSubject((Subject) fourthLessonSubject.getUserData());
            fourthLesson.setClassroom((Classroom) fourthLessonRoom.getUserData());
            fourthLesson.setLessonId(this.fourthLesson.getUserData() == null ? null : (Long) this.fourthLesson.getUserData());
            administratorPageService.saveOrUpdateLesson(fourthLesson);
        }

        Lesson fifthLesson = new Lesson();
        if (schedulePane.getUserData() != null && fifthLessonTeacher.getUserData() != null && scheduleGroupMenuButton.getUserData() != null && fifthLessonSubject.getUserData() != null && fifthLessonRoom.getUserData() != null) {
            fifthLesson.setGroup((Group) scheduleGroupMenuButton.getUserData());
            fifthLesson.setUser((User) fifthLessonTeacher.getUserData());
            fifthLesson.setTime(Date.valueOf((LocalDate) schedulePane.getUserData()));
            fifthLesson.setLessonsTime(lessonsTime.get(0));
            fifthLesson.setSubject((Subject) fifthLessonSubject.getUserData());
            fifthLesson.setClassroom((Classroom) fifthLessonRoom.getUserData());
            fifthLesson.setLessonId(this.fifthLesson.getUserData() == null ? null : (Long) this.fifthLesson.getUserData());
            administratorPageService.saveOrUpdateLesson(fifthLesson);
        }
        fillSchedule((LocalDate) schedulePane.getUserData());
        setTeachersItems((LocalDate) schedulePane.getUserData());
    }


    public void onExitAction() {
        FXMLPage.load("/pages/mainPage.fxml", 600, 300, stage, user);
    }

    public void setTypeTeacher() {
        userTypeMenuButton.setText(TEACHER_TYPE);
        initialize(userTypeMenuButton.getText());


        setVisibleStudentsGridPaneElements(false);
        fillTeachersTableView();
    }

    public void setTypeStudent() {
        userTypeMenuButton.setText(STUDENT_TYPE);
        initialize(userTypeMenuButton.getText());

        setVisibleStudentsGridPaneElements(true);
        fillStudentTableView();
    }

    public void setTypeStudentCreate() {
        List<Status> statusList = administratorPageService.getStatuses();
        List<Group> groupList = administratorPageService.getGroups();
        for (Status status : statusList) {
            MenuItem menuItem = new MenuItem(status.getStatus());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(status);
            menuItem.setOnAction(event -> onChoseStatusAction((Status) menuItem.getUserData()));
            statusStudentMenuButtonCreate.getItems().add(menuItem);
        }
        for (Group group : groupList) {
            MenuItem menuItem = new MenuItem(group.getGroupNumber());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(group);
            menuItem.setOnAction(event -> onChoseGroupAction((Group) menuItem.getUserData()));
            groupStudentMenuButton.getItems().add(menuItem);
        }
        userTypeMenuButtonCreate.setText(STUDENT_TYPE);
        setVisibleCreateStudentElements(true);
    }

    public void setTypeTeacherCreate() {
        userTypeMenuButtonCreate.setText(TEACHER_TYPE);
        setVisibleCreateStudentElements(false);
    }

    private void fillTeachersTableView() {
        List<UserTableData> tableDataList = administratorPageService.getTeachers();
        userDataTableView.setItems(FXCollections.observableList(tableDataList));
    }

    private void fillStudentTableView() {
        List<UserTableData> tableDataList = administratorPageService.getStudents();
        userDataTableView.setItems(FXCollections.observableList(tableDataList));
    }

    public void fillSchedule(LocalDate currentDate) {
        schedulePane.setUserData(currentDate);
        currentDatePicker.setValue(currentDate);
        List<Lesson> lessons = administratorPageService.getLessons((Group) scheduleGroupMenuButton.getUserData(), currentDate);

        Lesson firstLesson = lessons.size() < 1 ? null : lessons.get(0);
        if (firstLesson != null) {
            firstLessonSubject.setText(firstLesson.getSubject().getSubject());
            firstLessonTeacher.setText(firstLesson.getUser().getName() + " " + firstLesson.getUser().getFullname() + " " + firstLesson.getUser().getSurname());
            firstLessonRoom.setText(firstLesson.getClassroom().getClassroom());
            this.firstLesson.setUserData(firstLesson.getLessonId());
        } else {
            firstLessonSubject.setText("");
            firstLessonTeacher.setText("");
            firstLessonRoom.setText("");
            this.firstLesson.setUserData(null);
        }

        Lesson secondLesson = lessons.size() < 2 ? null : lessons.get(1);
        if (secondLesson != null) {
            secondLessonSubject.setText(secondLesson.getSubject().getSubject());
            secondLessonTeacher.setText(secondLesson.getUser().getName() + " " + secondLesson.getUser().getFullname() + " " + secondLesson.getUser().getSurname());
            secondLessonRoom.setText(secondLesson.getClassroom().getClassroom());
            this.secondLesson.setUserData(secondLesson.getLessonId());
        } else {
            secondLessonSubject.setText("");
            secondLessonTeacher.setText("");
            secondLessonRoom.setText("");
            this.secondLesson.setUserData(null);
        }

        Lesson thirdLesson = lessons.size() < 3 ? null : lessons.get(2);
        if (thirdLesson != null) {
            thirdLessonSubject.setText(thirdLesson.getSubject().getSubject());
            thirdLessonTeacher.setText(thirdLesson.getUser().getName() + " " + thirdLesson.getUser().getFullname() + " " + thirdLesson.getUser().getSurname());
            thirdLessonRoom.setText(thirdLesson.getClassroom().getClassroom());
            this.thirdLesson.setUserData(thirdLesson.getLessonId());
        } else {
            thirdLessonSubject.setText("");
            thirdLessonTeacher.setText("");
            thirdLessonRoom.setText("");
            this.thirdLesson.setUserData(null);
        }

        Lesson fourthLesson = lessons.size() < 4 ? null : lessons.get(3);
        if (fourthLesson != null) {
            fourthLessonSubject.setText(fourthLesson.getSubject().getSubject());
            fourthLessonTeacher.setText(fourthLesson.getUser().getName() + " " + fourthLesson.getUser().getFullname() + " " + fourthLesson.getUser().getSurname());
            fourthLessonRoom.setText(fourthLesson.getClassroom().getClassroom());
            this.fourthLesson.setUserData(fourthLesson.getLessonId());
        } else {
            fourthLessonSubject.setText("");
            fourthLessonTeacher.setText("");
            fourthLessonRoom.setText("");
            this.fourthLesson.setUserData(null);
        }

        Lesson fifthLesson = lessons.size() < 5 ? null : lessons.get(4);
        if (fifthLesson != null) {
            fifthLessonSubject.setText(fifthLesson.getSubject().getSubject());
            fifthLessonTeacher.setText(fifthLesson.getUser().getName() + " " + fifthLesson.getUser().getFullname() + " " + fifthLesson.getUser().getSurname());
            fifthLessonRoom.setText(fifthLesson.getClassroom().getClassroom());
            this.fifthLesson.setUserData(fifthLesson.getLessonId());
        } else {
            fifthLessonSubject.setText("");
            fifthLessonTeacher.setText("");
            fifthLessonRoom.setText("");
            this.fifthLesson.setUserData(null);
        }

        choseCommandMenuButton.setText("Увидеть расписание");
    }

    @FXML
    private void initialize(String userType) {
        if (userType.equals(TEACHER_TYPE)) {
            userDataTableView.getSelectionModel().selectedItemProperty().removeListener(
                    (observable, oldValue, newValue) -> showStudentDetail(newValue));
            userDataTableView.getColumns().clear();
            TableColumn<UserTableData, String> fioColumn = new TableColumn<>("ФИО");
            fioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getName() + " " + cellData.getValue().getUser().getFullname() + " " + cellData.getValue().getUser().getSurname()));
            fioColumn.setPrefWidth(2 * userDataTableView.getPrefWidth() / 6);

            TableColumn<UserTableData, String> phoneColumn = new TableColumn<>("Телефон");
            phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber() == null ? null : cellData.getValue().getPhoneNumber().getPhoneNumber()));
            phoneColumn.setPrefWidth(2 * userDataTableView.getPrefWidth() / 6);

            TableColumn<UserTableData, String> documentColumn = new TableColumn<>("Номер документа");
            documentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument() == null ? null : cellData.getValue().getDocument().getDocumentNumber()));
            documentColumn.setPrefWidth(2 * userDataTableView.getPrefWidth() / 6);


            userDataTableView.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showTeacherDetail(newValue));
            userDataTableView.getColumns().addAll(fioColumn, phoneColumn, documentColumn);
        } else if (userType.equals(STUDENT_TYPE)) {
            userDataTableView.getSelectionModel().selectedItemProperty().removeListener(
                    (observable, oldValue, newValue) -> showTeacherDetail(newValue));
            userDataTableView.getColumns().clear();
            TableColumn<UserTableData, String> fioColumn = new TableColumn<>("ФИО");
            fioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getName() + " " + cellData.getValue().getUser().getFullname() + " " + cellData.getValue().getUser().getSurname()));
            fioColumn.setPrefWidth(2 * userDataTableView.getPrefWidth() / 12);

            TableColumn<UserTableData, String> phoneColumn = new TableColumn<>("Телефон");
            phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber() == null ? null : cellData.getValue().getPhoneNumber().getPhoneNumber()));
            phoneColumn.setPrefWidth(2 * userDataTableView.getPrefWidth() / 12);

            TableColumn<UserTableData, String> documentColumn = new TableColumn<>("Номер документа");
            phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument() == null ? null : cellData.getValue().getDocument().getDocumentNumber()));
            documentColumn.setPrefWidth(2 * userDataTableView.getPrefWidth() / 12);

            TableColumn<UserTableData, String> creditBookNumber = new TableColumn<>("Номер зачётной книжки");
            creditBookNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreditBook() == null ? null : cellData.getValue().getCreditBook().getCreditBookNumber()));
            creditBookNumber.setPrefWidth(2 * userDataTableView.getPrefWidth() / 12);

            TableColumn<UserTableData, String> groupNumber = new TableColumn<>("Номер группы");
            groupNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGroup() == null ? null : cellData.getValue().getGroup().getGroupNumber()));
            groupNumber.setPrefWidth(2 * userDataTableView.getPrefWidth() / 12);

            TableColumn<UserTableData, String> studentStatus = new TableColumn<>("Статус обучающегося");
            studentStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus() == null ? null : cellData.getValue().getStatus().getStatus()));
            studentStatus.setPrefWidth(2 * userDataTableView.getPrefWidth() / 12);
            userDataTableView.getColumns().addAll(fioColumn, phoneColumn, documentColumn, creditBookNumber, groupNumber, studentStatus);

            userDataTableView.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showStudentDetail(newValue));
        }
    }

    private void showStudentDetail(UserTableData newValue) {
        fioTextField.setText(newValue.getUser().getName() + " " + newValue.getUser().getFullname() + " " + newValue.getUser().getSurname());
        phoneNumberTextField.setText(newValue.getPhoneNumber() == null ? null : newValue.getPhoneNumber().getPhoneNumber());
        documentTextField.setText(newValue.getDocument() == null ? null : newValue.getDocument().getDocumentNumber());
        creditBookTextField.setText(newValue.getCreditBook() == null ? null : newValue.getCreditBook().getCreditBookNumber());
        groupStudentMenuButton.setText(newValue.getGroup() == null ? null : newValue.getGroup().getGroupNumber());
        statusStudentMenuButton.setText(newValue.getStatus() == null ? null : newValue.getStatus().getStatus());
    }

    private void showTeacherDetail(UserTableData newValue) {

        fioTextField.setText(newValue.getUser().getName() + " " + newValue.getUser().getFullname() + " " + newValue.getUser().getSurname());
        phoneNumberTextField.setText(newValue.getPhoneNumber() == null ? null : newValue.getPhoneNumber().getPhoneNumber());
        documentTextField.setText(newValue.getDocument() == null ? null : newValue.getDocument().getDocumentNumber());
    }

    private void initializeClassParameters() {
        stage = (Stage) exitButton.getScene().getWindow();
        user = (User) stage.getUserData();
    }

    private void setVisibleWorkWithSchedule(boolean visible) {
        schedulePane.setVisible(visible);
        schedulePane.setDisable(!visible);

    }

    private void setDisableWorkWithScheduleElements(boolean disable) {
        scheduleGridPane.setDisable(disable);
        previousDay.setDisable(disable);
        nextDay.setDisable(disable);
        confirmScheduleChangesButton.setDisable(disable);
        currentDatePicker.setDisable(disable);
    }

    private void setVisibleWorkWithUserActionElements(boolean visible) {
        workWithUserPane.setVisible(visible);
        workWithUserPane.setDisable(!visible);
    }

    private void setVisibleCreateGroupActionElements(boolean visible) {
        createGroupPane.setVisible(visible);
        createGroupPane.setDisable(!visible);
    }

    private void setVisibleCreateUserElements(boolean visible) {
        createUserPane.setVisible(visible);
        createUserPane.setDisable(!visible);

        setVisibleCreateStudentElements(!visible);
    }

    private void setVisibleCreateStudentElements(boolean visible) {

        creditBookTextFieldCreate.setVisible(visible);
        creditBookLabelCreate.setVisible(visible);

        groupStudentMenuButtonCreate.setVisible(visible);
        groupStudentLabelCreate.setVisible(visible);

        statusStudentLabelCreate.setVisible(visible);
        statusStudentMenuButtonCreate.setVisible(visible);
    }

    private void setVisibleStudentsGridPaneElements(boolean visible) {
        creditBookLabel.setVisible(visible);
        creditBookTextField.setVisible(visible);

        groupStudentMenuButton.setVisible(visible);
        groupStudentLabel.setVisible(visible);

        statusStudentLabel.setVisible(visible);
        statusStudentMenuButton.setVisible(visible);
    }

    private void setCreateGroupFieldDefault() {
        groupNumberText.setText("");
        courseMenuButton.setText("курс");
        facultyMenuButton.setText("факультет");
        messageGroupCreate.setText("");
    }

    private void setCreateUserFieldDefault() {
        groupStudentMenuButtonCreate.setText("группа");
        userTypeMenuButtonCreate.setText("тип пользователя");
        statusStudentMenuButtonCreate.setText("статус");
        creditBookTextFieldCreate.setText("");
        fioTextFieldCreate.setText("");
        phoneNumberTextFieldCreate.setText("");
        documentTextFieldCreate.setText("");
        messageCreateUserText.setText("");
    }


    private void setTeachersItems(LocalDate currentDate) {
        List<User> availableTeachersFirstLesson = administratorPageService.getAvailableTeachers(currentDate, 1);
        firstLessonTeacher.getItems().clear();
        for (User element : availableTeachersFirstLesson) {
            MenuItem menuItem = new MenuItem(element.getName() + " " + element.getFullname() + " " + element.getSurname());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(element);
            menuItem.setOnAction(event -> onChoseTeacherAction((User) menuItem.getUserData(), firstLessonTeacher, firstLessonSubject, firstLessonRoom, lessonsTime.get(0)));
            firstLessonTeacher.getItems().add(menuItem);
        }

        List<User> availableTeachersSecondLesson = administratorPageService.getAvailableTeachers(currentDate, 2);
        secondLessonTeacher.getItems().clear();
        for (User element : availableTeachersSecondLesson) {
            MenuItem menuItem = new MenuItem(element.getName() + " " + element.getFullname() + " " + element.getSurname());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(element);
            menuItem.setOnAction(event -> onChoseTeacherAction((User) menuItem.getUserData(), secondLessonTeacher, secondLessonSubject, secondLessonRoom, lessonsTime.get(1)));
            secondLessonTeacher.getItems().add(menuItem);
        }

        List<User> availableTeachersThirdLesson = administratorPageService.getAvailableTeachers(currentDate, 3);
        thirdLessonTeacher.getItems().clear();
        for (User element : availableTeachersThirdLesson) {
            MenuItem menuItem = new MenuItem(element.getName() + " " + element.getFullname() + " " + element.getSurname());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(element);
            menuItem.setOnAction(event -> onChoseTeacherAction((User) menuItem.getUserData(), thirdLessonTeacher, thirdLessonSubject, thirdLessonRoom, lessonsTime.get(2)));
            thirdLessonTeacher.getItems().add(menuItem);
        }

        List<User> availableTeachersFourthLesson = administratorPageService.getAvailableTeachers(currentDate, 4);
        fourthLessonTeacher.getItems().clear();
        for (User element : availableTeachersFourthLesson) {
            MenuItem menuItem = new MenuItem(element.getName() + " " + element.getFullname() + " " + element.getSurname());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(element);
            menuItem.setOnAction(event -> onChoseTeacherAction((User) menuItem.getUserData(), fourthLessonTeacher, fourthLessonSubject, fourthLessonRoom, lessonsTime.get(3)));
            fourthLessonTeacher.getItems().add(menuItem);
        }

        List<User> availableTeachersFifthLesson = administratorPageService.getAvailableTeachers(currentDate, 5);
        fifthLessonTeacher.getItems().clear();
        for (User element : availableTeachersFifthLesson) {
            MenuItem menuItem = new MenuItem(element.getName() + " " + element.getFullname() + " " + element.getSurname());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(element);
            menuItem.setOnAction(event -> onChoseTeacherAction((User) menuItem.getUserData(), fifthLessonTeacher, fifthLessonSubject, fifthLessonRoom, lessonsTime.get(4)));
            fifthLessonTeacher.getItems().add(menuItem);
        }

    }

    private void onChoseTeacherAction(User userData, MenuButton currentLessonTeacher, MenuButton currentLessonSubject, MenuButton currentLessonRoom, LessonsTime lessonsTime) {
        currentLessonTeacher.setUserData(userData);
        currentLessonTeacher.setText(userData.getName() + " " + userData.getFullname() + " " + userData.getSurname());
        List<Subject> availableSubjects = administratorPageService.getSubjects();
        currentLessonSubject.getItems().clear();
        currentLessonSubject.setText("");
        for (Subject element : availableSubjects) {
            MenuItem menuItem = new MenuItem(element.getSubject());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(element);
            menuItem.setOnAction(event -> onChoseSubjectAction((Subject) menuItem.getUserData(), currentLessonSubject));
            currentLessonSubject.getItems().add(menuItem);
        }

        List<Classroom> availableClassRooms = administratorPageService.getAvailableRooms((LocalDate) schedulePane.getUserData(), lessonsTime.getId());
        currentLessonRoom.getItems().clear();
        currentLessonRoom.setText("");
        for (Classroom element : availableClassRooms) {
            MenuItem menuItem = new MenuItem(element.getClassroom());
            menuItem.setMnemonicParsing(false);
            menuItem.setUserData(element);
            menuItem.setOnAction(event -> onChoseRoomAction((Classroom) menuItem.getUserData(), currentLessonRoom));
            currentLessonRoom.getItems().add(menuItem);
        }
    }

    private void onChoseRoomAction(Classroom userData, MenuButton currentLessonRoom) {
        currentLessonRoom.setUserData(userData);
        currentLessonRoom.setText(userData.getClassroom());
    }

    private void onChoseSubjectAction(Subject userData, MenuButton currentLessonSubject) {
        currentLessonSubject.setUserData(userData);
        currentLessonSubject.setText(userData.getSubject());
    }

    private void onChoseFacultyAction(Faculty userData) {
        facultyMenuButton.setUserData(userData);
        facultyMenuButton.setText(userData.getFaculty());
    }

    private void onChoseCourseAction(Course userData) {
        courseMenuButton.setUserData(userData);
        courseMenuButton.setText(userData.getCourse());
    }

    private void onChoseGroupAction(Group userData) {
        groupStudentMenuButtonCreate.setUserData(userData);
        groupStudentMenuButtonCreate.setText(userData.getGroupNumber());
    }

    private void onChoseStatusAction(Status userData) {
        statusStudentMenuButtonCreate.setUserData(userData);
        statusStudentMenuButtonCreate.setText(userData.getStatus());
    }
}
