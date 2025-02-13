package com.company.studentmanagement.view.major;

import com.company.studentmanagement.entity.Major;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "majors/:id", layout = MainView.class)
@ViewController(id = "Major.detail")
@ViewDescriptor(path = "major-detail-view.xml")
@EditedEntityContainer("majorDc")
public class MajorDetailView extends StandardDetailView<Major> {
}