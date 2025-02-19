package com.company.studentmanagement.view.klass;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.entity.Lecturer;
import com.company.studentmanagement.entity.LecturerClass;
import com.company.studentmanagement.entity.Student;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


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
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private CollectionLoader<Klass> klassesDl;
    @Autowired
    private Notifications notifications;

    @Subscribe("klassesDataGrid.remove")
    public void onKlassesDataGridRemove(final ActionPerformedEvent event) {
        var classToRemove = dataManager.load(Klass.class)
                .id(klassesDataGrid.getSingleSelectedItem().getId())
                .optional()
                .orElse(null);
        if(classToRemove == null){
            return;
        }
        var studentsInClass = dataManager.load(Student.class)
                .query("Select s from Student s where s.inClass = :klass")
                .parameter("klass", classToRemove)
                .list();
        var lecturesInClass = dataManager.load(LecturerClass.class)
                .query("Select lc from LecturerClass lc where lc.klass = :classToRemove")
                .parameter("classToRemove", classToRemove)
                .list();
        studentsInClass.forEach(s -> s.setInClass(null));
        lecturesInClass.forEach(lc ->
            dataManager.remove(lc)
        );
        dataManager.saveAll(studentsInClass);
        dataManager.remove(classToRemove);
        notifications.create("Remove Successfully")
                .withType(Notifications.Type.SUCCESS)
                .withPosition(Notification.Position.TOP_END)
                .show();
        klassesDl.load();

    }


}