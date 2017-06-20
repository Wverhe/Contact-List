package com.core.objects.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Created by agaspari on 6/20/2017.
 */
public class CustomGridPane extends GridPane{
    private ColumnConstraints column;
    public CustomGridPane(){
        super();
        column = new ColumnConstraints();
        column.setHgrow(Priority.ALWAYS);
        setAlignment(Pos.TOP_CENTER);
        setVgap(5);
        setHgap(5);
        setPadding(new Insets(5));
    }

    public void addColumns(int numColumns){
        column.setPercentWidth(100/numColumns);
        for(int i = 0; i < numColumns; i++){
            getColumnConstraints().add(column);
        }
    }
}
