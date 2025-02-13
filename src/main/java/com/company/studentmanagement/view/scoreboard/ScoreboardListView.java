package com.company.studentmanagement.view.scoreboard;

import com.company.studentmanagement.entity.Scoreboard;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.editor.DataGridEditor;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "scoreboards", layout = MainView.class)
@ViewController(id = "Scoreboard.list")
@ViewDescriptor(path = "scoreboard-list-view.xml")
@LookupComponent("scoreboardsDataGrid")
@DialogMode(width = "64em")
public class ScoreboardListView extends StandardListView<Scoreboard> {
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private DataGrid<Scoreboard> scoreboardsDataGrid;

    @Subscribe(id = "scoreboardsDc", target = Target.DATA_CONTAINER)
    public void onScoreboardsDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<Scoreboard> event) {
        dataManager.save(event.getItem());
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        DataGridEditor<Scoreboard> editor = scoreboardsDataGrid.getEditor();
        scoreboardsDataGrid.addItemDoubleClickListener(
                s -> {
                    editor.editItem(s.getItem());
                    Component editorComponent = s.getColumn().getEditorComponent();
                    if (editorComponent instanceof Focusable) {
                        ((Focusable) editorComponent).focus();
                    }
                });
    }


}