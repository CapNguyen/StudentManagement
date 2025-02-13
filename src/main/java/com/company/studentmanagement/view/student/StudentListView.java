package com.company.studentmanagement.view.student;

import com.company.studentmanagement.entity.Student;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.data.impl.EntityEventManager;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;


@Route(value = "students", layout = MainView.class)
@ViewController(id = "Student.list")
@ViewDescriptor(path = "student-list-view.xml")
@LookupComponent("studentsDataGrid")
@DialogMode(width = "64em")
public class StudentListView extends StandardListView<Student> {
}