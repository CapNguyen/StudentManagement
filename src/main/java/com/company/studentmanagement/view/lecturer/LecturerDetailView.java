package com.company.studentmanagement.view.lecturer;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.entity.Lecturer;
import com.company.studentmanagement.entity.LecturerClass;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.component.multiselectcombobox.JmixMultiSelectComboBox;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Route(value = "lecturers/:id", layout = MainView.class)
@ViewController(id = "Lecturer.detail")
@ViewDescriptor(path = "lecturer-detail-view.xml")
@EditedEntityContainer("lecturerDc")
public class LecturerDetailView extends StandardDetailView<Lecturer> {
    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private JmixMultiSelectComboBox<Klass> classField;
    @ViewComponent
    private CollectionLoader<LecturerClass> lecturerClassesDl;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        Lecturer editingLecturer = getEditedEntity();
        lecturerClassesDl.setParameter("lid", editingLecturer.getId());
        lecturerClassesDl.load();

    }
//
//    @Subscribe(id = "generateButton", subject = "clickListener")
//    public void onGenerateButtonClick(final ClickEvent<JmixButton> event) {
//        Lecturer editingLecturer = getEditedEntity();
//        Set<Klass> klassSet = classField.getSelectedItems();
//        List<Klass> classes = dataManager.load(Klass.class)
//                .query("SELECT DISTINCT c FROM Klass c JOIN LecturerClass lc ON c.id = lc.klass.id WHERE lc.lecturer.id = :lecturerId")
//                .parameter("lecturerId", editingLecturer.getId())
//                .list();
//
//        if(!klassSet.isEmpty()){
//            for(Klass c : klassSet){
//                if(classes.contains(c)) continue;
//                LecturerClass lc = dataContext.create(LecturerClass.class);
//                lc.setLecturer(editingLecturer);
//                lc.setKlass(c);
//                dataContext.merge(lc);
//            }
//            dataContext.save();
//            lecturerClassesDl.load();
//        }
//    }
}