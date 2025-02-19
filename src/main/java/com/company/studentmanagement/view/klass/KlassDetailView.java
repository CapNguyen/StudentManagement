package com.company.studentmanagement.view.klass;

import com.company.studentmanagement.entity.*;
import com.company.studentmanagement.view.lecturer.LecturerDetailView;
import com.company.studentmanagement.view.main.MainView;
import com.company.studentmanagement.view.student.StudentDetailView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.multiselectcomboboxpicker.JmixMultiSelectComboBoxPicker;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Route(value = "klasses/:id", layout = MainView.class)
@ViewController(id = "Klass.detail")
@ViewDescriptor(path = "klass-detail-view.xml")
@EditedEntityContainer("klassDc")
public class KlassDetailView extends StandardDetailView<Klass> {
    @ViewComponent
    private CollectionLoader<Lecturer> lecturersDl;
    @ViewComponent
    private CollectionLoader<Student> studentsDl;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private CollectionContainer<Lecturer> lecturersDc;
    @ViewComponent
    private CollectionContainer<Student> studentsDc;
    @Autowired
    private Metadata metadata;
    @ViewComponent
    private DataGrid<Student> studentDataGrid;
    @ViewComponent
    private DataGrid<Lecturer> lecturerDataGrid;
    @Autowired
    private DialogWindows lecturerDialogWindows;
    @Autowired
    private DialogWindows studentDialogWindows;
    @Autowired
    private Dialogs dialogs;
    @ViewComponent
    private JmixMultiSelectComboBoxPicker<Lecturer> multiSelectComboBoxPickerLecturers;

    @Subscribe
    public void onInit(final InitEvent event) {

//        multiSelectComboBoxPickerLecturers.addValueChangeListener(e -> {
//            Set<Lecturer> selectedLecture = e.getValue();
//            selectedLecture.forEach(l -> {
//                lecturersDl.load();
//                lecturersDc.getMutableItems().addAll(selectedLecture);
//            });
//        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        var classEditing = getEditedEntity();
        lecturersDl.setParameter("classId", classEditing.getId());
        lecturersDl.load();
        studentsDl.setParameter("classId", classEditing.getId());
        studentsDl.load();
    }

    @Subscribe(id = "saveAndCloseButton", subject = "clickListener")
    public void onSaveAndCloseButtonClick(final ClickEvent<JmixButton> event) {
        var classes = dataManager.load(Klass.class).all().list()
                .stream()
                .map(c -> c.getId())
                .toList();
        var isEditing = classes.contains(this.getEditedEntity().getId());
        if (isEditing) {
            dialogs.createOptionDialog()
                    .withHeader("Confirmation")
                    .withText("Do you want to save the changes?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e -> {
                                        var nonDuplicatedLecturerList = lecturersDc.getItems().stream().distinct().toList();
                                        var nonDuplicatedStudentList = studentsDc.getItems().stream().distinct().toList();
                                        UpdateLecturers(nonDuplicatedLecturerList, getEditedEntity());
                                        UpdateStudents(nonDuplicatedStudentList, getEditedEntity());
                                        notifications.create("Update Successfully")
                                                .withType(Notifications.Type.SUCCESS)
                                                .withPosition(Notification.Position.TOP_END)
                                                .show();
                                        close(StandardOutcome.DISCARD);

                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .open();
        } else {
            var name = getEditedEntity().getName();
            var school = getEditedEntity().getSchool();
            var lecturers = lecturersDc.getMutableItems().stream().distinct().toList();
            var students = studentsDc.getMutableItems().stream().distinct().toList();
            var totalStudents = students.size();
            createClass(name, totalStudents, school, lecturers, students);
            notifications.create("Create Successfully")
                    .withType(Notifications.Type.SUCCESS)
                    .withPosition(Notification.Position.TOP_END)
                    .show();
            close(StandardOutcome.DISCARD);
        }
    }

    public void createClass(String name, Integer totalStudent, Department school,
                            List<Lecturer> lecturers, List<Student> students) {
        Klass newClass = dataManager.create(Klass.class);
        newClass.setName(name);
        newClass.setTotalStudent(totalStudent);
        newClass.setSchool(school);
        dataManager.save(newClass);

        List<LecturerClass> lecturerClasses = new ArrayList<>();
        lecturers.forEach(l -> {
            LecturerClass lecturerClass = dataManager.create(LecturerClass.class);
            lecturerClass.setLecturer(l);
            lecturerClass.setKlass(newClass);
            lecturerClasses.add(lecturerClass);
        });
        newClass.setLecturerClasses(lecturerClasses);

        students.forEach(s -> {
            s.setInClass(newClass);
            dataManager.save(s);
        });

        dataManager.save(newClass);
    }


    private void UpdateStudents(List<Student> studentList, Klass currentClass) {
        var dbStudentList = dataManager.load(Student.class)
                .query("Select s from Student s Join Fetch Klass where s.inClass=:currentClass")
                .parameter("currentClass", currentClass)
                .list();

        var dbStudentsId = dbStudentList.stream().map(s -> s.getId()).toList();
        //Get added Students
        var addStudents = studentList.stream()
                .filter(s -> !dbStudentsId.contains(s.getId()))
                .toList();
        //Get removed Students
        var removedStudents = dbStudentList.stream()
                .filter(s -> !(studentList.stream().map(st -> st.getId()).toList()).contains(s.getId()))
                .toList();
        //Get updated students
        var updatedStudents = studentList.stream()
                .filter(newStu -> {
                    var isExistedInDB = dbStudentsId.contains(newStu.getId());
                    var isUpdated = dbStudentList.stream().anyMatch(oldStu -> oldStu.getId().equals(newStu.getId()) && !oldStu.equals(newStu));
                    return isExistedInDB && isUpdated;
                })
                .toList();
        //save students to DB with inClass reference
        addStudents.forEach(s -> {
            dataManager.save(s);
            currentClass.setTotalStudent(currentClass.getTotalStudent() + 1);
            dataManager.save(currentClass);
        });
        updatedStudents.forEach(dataManager::save);
        removedStudents.forEach(s -> {
            dataManager.remove(s);
            currentClass.setTotalStudent(currentClass.getTotalStudent() - 1);
            dataManager.save(currentClass);
        });
    }

    private void UpdateLecturers(List<Lecturer> lecturerList, Klass currentClass) {
        //Get In-DB Lecturers
        var dbLecturers = dataManager.load(Lecturer.class)
                .query("Select l from Lecturer l " +
                        "Join LecturerClass lc On lc.lecturer.id=l.id " +
                        "where lc.klass.id= :classId")
                .parameter("classId", currentClass.getId())
                .list();
        var dbLecturersId = dbLecturers.stream().map(l -> l.getId()).toList();

        var addedLecturers = lecturerList.stream()
                .filter(newLec ->
                        !(dbLecturers.stream()
                                .map(l -> l.getId())
                                .toList())
                                .contains(newLec.getId()))
                .toList();
        var removedLecturers = dbLecturers.stream()
                .filter(oldLec ->
                        !(lecturerList.stream()
                                .map(l -> l.getId())
                                .toList())
                                .contains(oldLec.getId()))
                .toList();
        var updatedLecturers = lecturerList.stream()
                .filter(newLec ->
                {
                    var checkIfLecExistInDB = dbLecturersId.contains(newLec.getId()); //Avoid newly added Lec which has null ID
                    var checkIfLecIsModified = dbLecturers.stream().anyMatch(oldLec -> oldLec.getId().equals(newLec.getId()) && !oldLec.equals(newLec));
                    return checkIfLecExistInDB && checkIfLecIsModified;
                })
                .toList();

        addedLecturers.forEach(newLec -> {
            dataManager.save(newLec);
            var lecturerClass = dataManager.create(LecturerClass.class);
            lecturerClass.setLecturer(newLec);
            lecturerClass.setKlass(currentClass);
            dataManager.save(lecturerClass);
        });
        updatedLecturers.forEach(dataManager::save);
        removedLecturers.forEach(l -> {
            var lecturerClasses = dataManager.load(LecturerClass.class)
                    .query("SELECT lc FROM LecturerClass lc WHERE lc.lecturer.id = :lecId")
                    .parameter("lecId", l.getId())
                    .list();
            lecturerClasses.forEach(dataManager::remove);
            dataManager.remove(l);
        });
    }

    @Subscribe(id = "createLecturerButton", subject = "clickListener")
    public void onCreateLecturerButtonClick(final ClickEvent<JmixButton> event) {
        DialogWindow<LecturerDetailView> lecturerDialog =
                lecturerDialogWindows.detail(this, Lecturer.class)
                        .withViewClass(LecturerDetailView.class)
                        .newEntity()
                        .withInitializer(l -> l.setId(UUID.randomUUID()))
                        .withParentDataContext(dataContext)
                        .build();

        lecturerDialog.addAfterCloseListener(e -> {
            if (e.closedWith(StandardOutcome.SAVE)) {
                lecturersDc.getMutableItems().add(e.getView().getEditedEntity());
//                notifications.show("Check Lecturer");
            }

        });
        lecturerDialog.open();
    }

    @Subscribe(id = "createStudentButton", subject = "clickListener")
    public void onCreateStudentButtonClick(final ClickEvent<JmixButton> event) {
        var studentDialog =
                studentDialogWindows.detail(this, Student.class)
                        .withViewClass(StudentDetailView.class)
                        .newEntity()
                        .withInitializer(s -> {
                            s.setId(UUID.randomUUID());
                            s.setInClass(this.getEditedEntity());
                        })
                        .withParentDataContext(dataContext)
                        .build();
        studentDialog.addAfterCloseListener(e -> {
            if (e.closedWith(StandardOutcome.SAVE)) {
                studentsDc.getMutableItems().add(e.getView().getEditedEntity());
//                notifications.show("Check Student");
            }
        });
        studentDialog.open();
    }

    @Subscribe("studentDataGrid.edit")
    public void onStudentDataGridEdit(final ActionPerformedEvent event) {
        var selectedStudent = studentDataGrid.getSingleSelectedItem();
        if (selectedStudent == null) {
            notifications.show("No student selected!");
            return;
        }
        var studentDialog =
                studentDialogWindows.detail(this, Student.class)
                        .withViewClass(StudentDetailView.class)
                        .editEntity(selectedStudent)
                        .withParentDataContext(dataContext)
                        .build();
        studentDialog.addAfterCloseListener(e -> {
            if (e.closedWith(StandardOutcome.SAVE)) {
                var updatedStudent = e.getView().getEditedEntity();
                var students = studentsDc.getMutableItems();
                for (int i = 0; i < students.size(); i++) {
                    if (students.get(i).getId().equals(updatedStudent.getId())) {
                        students.set(i, updatedStudent);
                        break;
                    }
                }
//                notifications.show("Check Edit Student");
            }
        });
        studentDialog.open();
    }

    @Subscribe("lecturerDataGrid.edit")
    public void onLecturerDataGridEdit(final ActionPerformedEvent event) {
        var selectedLecturer = lecturerDataGrid.getSingleSelectedItem();
        if (selectedLecturer == null) {
            notifications.show("No Lecture selected!");
            return;
        }
        DialogWindow<LecturerDetailView> lecturerDialog =
                lecturerDialogWindows.detail(this, Lecturer.class)
                        .withViewClass(LecturerDetailView.class)
                        .editEntity(selectedLecturer)
                        .withParentDataContext(dataContext)
                        .build();

        lecturerDialog.addAfterCloseListener(e -> {
            if (e.closedWith(StandardOutcome.SAVE)) {
                var updatedLecturer = e.getView().getEditedEntity();
                var lecturers = lecturersDc.getMutableItems();
                for (int i = 0; i < lecturers.size(); i++) {
                    if (lecturers.get(i).getId().equals(updatedLecturer.getId())) {
                        lecturers.set(i, updatedLecturer);
                        break;
                    }
                }
//                notifications.show("Check Edit Lec");
            }
        });
        lecturerDialog.open();
    }

    @Subscribe("lecturerDataGrid.remove")
    public void onLecturerDataGridRemove(final ActionPerformedEvent event) {
        var selectedLecturer = lecturerDataGrid.getSingleSelectedItem();

        if (selectedLecturer == null) {
            notifications.show("Please select a lecturer to delete.");
            return;
        }

        dialogs.createOptionDialog()
                .withHeader("Confirm Deletion")
                .withText("Are you sure you want to delete this lecturer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> {
                                    lecturersDc.getMutableItems().remove(selectedLecturer);
//                                    notifications.show("Deleted");
                                }),
                        new DialogAction(DialogAction.Type.NO))
                .open();
    }

    @Subscribe("studentDataGrid.remove")
    public void onStudentDataGridRemove(final ActionPerformedEvent event) {
        var selectedStudent = studentDataGrid.getSingleSelectedItem();

        if (selectedStudent == null) {
            notifications.show("Please select a student to delete.");
            return;
        }

        dialogs.createOptionDialog()
                .withHeader("Confirm Deletion")
                .withText("Are you sure you want to delete this student?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> {
                                    studentsDc.getMutableItems().remove(selectedStudent);
//                                    notifications.show("Deleted");
                                }),
                        new DialogAction(DialogAction.Type.NO))
                .open();
    }
}




