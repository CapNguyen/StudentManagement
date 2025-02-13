package com.company.studentmanagement.listener;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.entity.Student;
import io.jmix.core.DataManager;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {
    @Autowired
    private DataManager dataManager;


    @EventListener
    public void onStudentSaving(EntitySavingEvent<Student> event) {
        var student = event.getEntity();
        var klass = student.getInClass();
        if (klass != null) {
            klass.setTotalStudent((klass.getTotalStudent() == null ? 0 : klass.getTotalStudent()) + 1);
            dataManager.save(klass);
        }
    }


    @EventListener
    public void onStudentChangedBeforeCommit(EntityChangedEvent<Student> event) {
        if (event.getType() == EntityChangedEvent.Type.DELETED) {
            var studentId = event.getEntityId().getValue();
            Student student = dataManager.load(Student.class)
                    .id(studentId)
                    .optional()
                    .orElse(null);

            if (student != null && student.getInClass() != null) {
                Klass klass = student.getInClass();
                if (klass.getTotalStudent() != null) {
                    klass.setTotalStudent(Math.max(klass.getTotalStudent() - 1, 0));
                }
                dataManager.save(klass);
            }
        }
    }
}
