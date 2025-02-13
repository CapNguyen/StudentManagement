package com.company.studentmanagement.view.scoreboard;

import com.company.studentmanagement.entity.Scoreboard;
import com.company.studentmanagement.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "scoreboards", layout = MainView.class)
@ViewController(id = "Scoreboard.list")
@ViewDescriptor(path = "scoreboard-list-view.xml")
@LookupComponent("scoreboardsDataGrid")
@DialogMode(width = "64em")
public class ScoreboardListView extends StandardListView<Scoreboard> {

}