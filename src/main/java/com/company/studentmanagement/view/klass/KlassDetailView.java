package com.company.studentmanagement.view.klass;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "klasses/:id", layout = MainView.class)
@ViewController(id = "Klass.detail")
@ViewDescriptor(path = "klass-detail-view.xml")
@EditedEntityContainer("klassDc")
public class KlassDetailView extends StandardDetailView<Klass> {
}