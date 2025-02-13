package com.company.studentmanagement.listener;

import com.company.studentmanagement.entity.Klass;
import com.company.studentmanagement.entity.Student;
import io.jmix.core.DataManager;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {
    @Autowired
    private DataManager dataManager;


//    @EventListener
//    public void onStudentSaving(EntitySavingEvent<Student> event) {
//        var student = event.getEntity();
//        var klass = student.getInClass();
//        if (klass != null) {
//            klass.setTotalStudent((klass.getTotalStudent() == null ? 0 : klass.getTotalStudent()) + 1);
//            dataManager.save(klass);
//        }
//    }


    @EventListener
    public void onStudentChangedBeforeCommit(EntityChangedEvent<Student> event) {
//        if (event.getType() == EntityChangedEvent.Type.DELETED) {
//            var classId = event.getChanges().getOldValue("inClass");
//
//            if (classId != null) {
//                Klass klass = dataManager.load(Klass.class)
//                        .id(classId)
//                        .optional()
//                        .orElse(null);
//
//                if (klass != null && klass.getTotalStudent() != null) {
//                    klass.setTotalStudent(Math.max(klass.getTotalStudent() - 1, 0));
//                    dataManager.save(klass);
//                }
//            }
//        }
//        if(event.getType()==EntityChangedEvent.Type.UPDATED){
        var oldClass = dataManager.load(Klass.class)
                .query("Select c From Klass c where c.id= :oldClassId")
                .parameter("oldClassId", event.getChanges().getOldValue("inClass"))
                .optional()
                .orElse(null);

        var newClass = dataManager.load(Klass.class)
                .query("SELECT s.inClass FROM Student s WHERE s.id = :studentId")
                .parameter("studentId", event.getEntityId())
                .optional()
                .orElse(null);
        if (oldClass != null) {
            oldClass.setTotalStudent(Math.max(0, oldClass.getTotalStudent() - 1));
            dataManager.save(oldClass);
        }
        if (newClass != null) {
            newClass.setTotalStudent(newClass.getTotalStudent() + 1);
            dataManager.save(newClass);
        }
    }
}
//}
