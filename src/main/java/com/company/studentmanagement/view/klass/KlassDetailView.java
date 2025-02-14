package com.company.studentmanagement.view.klass;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.entity.Lecturer;
import com.company.studentmanagement.entity.LecturerClass;
import com.company.studentmanagement.entity.Student;
import com.company.studentmanagement.view.lecturer.LecturerDetailView;
import com.company.studentmanagement.view.main.MainView;
import com.company.studentmanagement.view.student.StudentDetailView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    private DialogWindows lecturerDialogWindows;
    @Autowired
    private DialogWindows studentDialogWindows;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        var classEditing = getEditedEntity();
        lecturersDl.setParameter("classId", classEditing.getId());
        lecturersDl.load();
        studentsDl.setParameter("classId", classEditing.getId());
        studentsDl.load();
    }

    @Subscribe(id = "createLecturerButton", subject = "clickListener")
    public void onCreateLecturerButtonClick(final ClickEvent<JmixButton> event) {
        LecturerClass lc = dataManager.create(LecturerClass.class);
        lc.setKlass(getEditedEntity());
        lecturerDialogWindows.detail(this, Lecturer.class)
                .withViewClass(LecturerDetailView.class)
                .newEntity()
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                        Lecturer l = afterCloseEvent.getView().getEditedEntity();
                        lc.setLecturer(l);
                        dataManager.save(lc);
                        lecturersDl.load();
                    }
                })
                .open();

    }

    @Subscribe(id = "createStudentButton", subject = "clickListener")
    public void onCreateStudentButtonClick(final ClickEvent<JmixButton> event) {
        studentDialogWindows.detail(this, Student.class)
                .withViewClass(StudentDetailView.class)
                .newEntity().open();
        studentsDl.load();

    }

}