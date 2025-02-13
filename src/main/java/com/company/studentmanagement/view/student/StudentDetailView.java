package com.company.studentmanagement.view.student;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.entity.Student;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "students/:id", layout = MainView.class)
@ViewController(id = "Student.detail")
@ViewDescriptor(path = "student-detail-view.xml")
@EditedEntityContainer("studentDc")
public class StudentDetailView extends StandardDetailView<Student> {
    private Klass currentClass;
    @ViewComponent
    private EntityComboBox<Klass> classField;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private DataContext dataContext;

//    @Subscribe("classField")
//    public void onClassFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Klass>, Klass> event) {
//            var selectedClass = event.getValue();
//        if (currentClass == null || currentClass.getId() == selectedClass.getId()) {
//            return;
//        }
//        Klass oldClass = dataManager.load(Klass.class)
//                .query("SELECT c FROM Klass c WHERE c.id = :classId")
//                .parameter("classId", currentClass.getId())
//                .optional()
//                .orElse(null);
//        Klass newClass = dataManager.load(Klass.class)
//                .query("SELECT c FROM Klass c WHERE c.id = :classId")
//                .parameter("classId", selectedClass.getId())
//                .optional()
//                .orElse(null);
//
//        boolean updated = false;
//
//        if (oldClass != null) {
//            oldClass.setTotalStudent(oldClass.getTotalStudent() - 1);
//            updated = true;
//        }
//
//        if (newClass != null) {
//            newClass.setTotalStudent(newClass.getTotalStudent() + 1);
//            updated = true;
//        }
//
//        if (updated) {
//            dataManager.save(oldClass, newClass);
//        }
//        currentClass = selectedClass;
//    }


    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        var student = getEditedEntity();
        currentClass = student.getInClass();
    }

}