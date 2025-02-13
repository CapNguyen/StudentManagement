package com.company.studentmanagement.view.lecturer;

import com.company.studentmanagement.entity.Lecturer;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "lecturers", layout = MainView.class)
@ViewController(id = "Lecturer.list")
@ViewDescriptor(path = "lecturer-list-view.xml")
@LookupComponent("lecturersDataGrid")
@DialogMode(width = "64em")
public class LecturerListView extends StandardListView<Lecturer> {
}