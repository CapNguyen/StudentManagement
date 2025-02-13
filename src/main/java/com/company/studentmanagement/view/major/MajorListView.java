package com.company.studentmanagement.view.major;

import com.company.studentmanagement.entity.Major;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "majors", layout = MainView.class)
@ViewController(id = "Major.list")
@ViewDescriptor(path = "major-list-view.xml")
@LookupComponent("majorsDataGrid")
@DialogMode(width = "64em")
public class MajorListView extends StandardListView<Major> {
}