package com.company.studentmanagement.view.klass;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;


@Route(value = "klasses", layout = MainView.class)
@ViewController(id = "Klass.list")
@ViewDescriptor(path = "klass-list-view.xml")
@LookupComponent("klassesDataGrid")
@DialogMode(width = "64em")
public class KlassListView extends StandardListView<Klass> {
    @ViewComponent
    private CollectionContainer<Klass> klassesDc;
    @ViewComponent
    private DataGrid<Klass> klassesDataGrid;


}