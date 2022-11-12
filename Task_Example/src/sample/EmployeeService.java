package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/* Service:  A non-visual component encapsulating the information required to perform some work on one or more
background threads.  Can be subclassed.  */
public class EmployeeService extends Service<ObservableList<String>>  {

    /* createTask():  Invoked after the Service is started on the JavaFX Application Thread.  */
    @Override
    protected Task<ObservableList<String>> createTask() {
        return new Task<ObservableList<String>>() {
            /* call() method is invoke when te task is executed.  */
            @Override
            protected ObservableList<String> call() throws Exception {
                String[] names = { "Tim Buchalka",
                        "Bill Rogers",
                        "Jack Jill",
                        "Joan Andrews",
                        "Mary Johnson",
                        "Bob McDonald" };

                ObservableList<String> employees = FXCollections.observableArrayList();

                /* Invoke updateProgress() 6 times.  */
                for(int i = 0; i < 6; i++) {
                    employees.add(names[i]);
                    /* Update the message depend on item added to employees.  */
                    updateMessage("Added " + names[i] + " to the list.  ");
                    /* Update progress to i + 1 each iteration, and the progress' maximum value is 6.  */
                    updateProgress(i + 1, 6);
                    /* Sleeps for 0.2 seconds each iteration.  */
                    Thread.sleep(200);
                }
                return employees;
            }
        };
    }

}
