package com.company.studentmanagement.view.scoreboard;

import com.company.studentmanagement.entity.Scoreboard;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "scoreboards/:id", layout = MainView.class)
@ViewController(id = "Scoreboard.detail")
@ViewDescriptor(path = "scoreboard-detail-view.xml")
@EditedEntityContainer("scoreboardDc")
public class ScoreboardDetailView extends StandardDetailView<Scoreboard> {
}