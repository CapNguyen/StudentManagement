package com.company.studentmanagement.view.scoreboard;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.entity.Major;
import com.company.studentmanagement.entity.Scoreboard;
import com.company.studentmanagement.entity.Student;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;


@Route(value = "scoreboards", layout = MainView.class)
@ViewController(id = "Scoreboard.list")
@ViewDescriptor(path = "scoreboard-list-view.xml")
@LookupComponent("scoreboardsDataGrid")
@DialogMode(width = "64em")
public class ScoreboardListView extends StandardListView<Scoreboard> {
//    private List<Scoreboard> scoreboards;
//    @Autowired
//    private DataManager dataManager;
//    @ViewComponent
//    private CollectionLoader<Scoreboard> scoreboardsDl;
//
//    @Subscribe
//    public void onBeforeShow(final BeforeShowEvent event) {
//        List<Klass> classes = dataManager.load(Klass.class).all().list();
//        for (Klass c : classes) {
//            bindMajorToStudentInClass(c);
//        }
//        scoreboardsDl.load();
//    }
//
//    private void bindMajorToStudentInClass(Klass c) {
//        List<Student> students = dataManager.load(Student.class)
//                .query("SELECT s FROM Student s " +
//                        "Where s.inClass.id = :classId")
//                .parameter("classId", c.getId())
//                .list();
//        List<Major> majors = dataManager.load(Major.class)
//                .query("SELECT m FROM Major m " +
//                        "JOIN Lecturer l ON l.major = m " +
//                        "JOIN LecturerClass lc ON lc.lecturer = l " +
//                        "WHERE lc.klass.id = :classId")
//                .parameter("classId", c.getId())
//                .list();
//        for (Major m : majors) {
//            for (Student s : students) {
//                var sc = dataManager.create(Scoreboard.class);
//                sc.setMajor(m);
//                sc.setStudent(s);
//                dataManager.save(sc);
//            }
//        }
//    }

}