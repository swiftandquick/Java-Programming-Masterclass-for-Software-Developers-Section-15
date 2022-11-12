package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

public class Controller {

    /* Task is just a unit of work in the abstract sense.  I have tasks of some type, and then I  have mechanisms to
    perform those tasks in an asynchronous way.  */
    private Task<ObservableList<String>> task;

    @FXML
    private ListView listView;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    private Service<ObservableList<String>> service;


    public void initialize() {
        /* Set service to an EmployeeService object.  */
        service = new EmployeeService();

        /*
        // The onRunning event handler is called whenever the Task state transitions to the RUNNING state.
        service.setOnRunning(new EventHandler<WorkerStateEvent>() {
            // handle() A method use to handle events.
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                // In the RUNNING state, set progressBar and progressLabel to visible.
                progressBar.setVisible(true);
                progressLabel.setVisible(true);
            }
        });

        // The onSucceeded event handler is called whenever the Task state transitions to the SUCCEEDED state.
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                // After service is finish executing (SUCCEEDED state), set progressBar and progressLabel to invisible.
                progressBar.setVisible(false);
                progressLabel.setVisible(false);
            }
        });

        // At the start progressBar and progressLabel are invisible.
        progressBar.setVisible(false);
        progressLabel.setVisible(false);
        */

        /* Bind progressBar's progress property to task (task in EmployeeService class)'s progress property.  */
        progressBar.progressProperty().bind(service.progressProperty());

        /* Binds progressLabel's text property to task's message property.  */
        progressLabel.textProperty().bind(service.messageProperty());

        /* itemsProperty():  The underlying data model for the ListView.
        * bind():  Here, we bind tghe listView items property to the task's value property.
        * valueProperty():  Gets the ReadOnlyObjectProperty representing the value.  */
        listView.itemsProperty().bind(service.valueProperty());

        /* Bind progressBar's visible property to service's running property.  If service is running, progressBar will
        be visible.  */
        progressBar.visibleProperty().bind(service.runningProperty());
        /* Same as above.  */
        progressLabel.visibleProperty().bind(service.runningProperty());
    }


    @FXML
    public void buttonPressed() {
        /* Gets the value of the property state, check if the finish state is SUCCEEDED (successfully run).  */
        if(service.getState() == Service.State.SUCCEEDED) {
            service.reset(); // Reset the service.
            service.start(); // Start the service.
        }
        /* If the service is not executed yet...  */
        else if(service.getState() == Service.State.READY) {
            service.start();
        }
    }
}
