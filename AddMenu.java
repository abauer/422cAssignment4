package assignment4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Created by Anthony on 10/26/2016.
 */
public class AddMenu{

    GridPane g;

    public AddMenu() {
        g = new GridPane();
        g.setAlignment(Pos.CENTER);
        g.setHgap(4);   //10
        g.setVgap(10);
        g.setPadding(new Insets(25,25,25,25));  //5555

        ObservableList<String> options = FXCollections.observableArrayList(
                "Algae",
                "MyCritter1",
                "MyCritter2"
        );

        ComboBox comboBox = new ComboBox(options);

        Button btn = new Button("Create ");
        btn.setOnAction(e ->{try{
            System.out.println(comboBox.getValue());Critter.makeCritter((String)comboBox.getValue());}catch(Exception ex){}});
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        g.add(hbBtn, 1, 4);
        g.add(comboBox,1,0);

    }
}
