package frontend_viewcontroller;

import backend_models.*;
import java.io.File;
import java.io.IOException;

/**
 * This class and its constructor is used to set up the backend,
 * i.e. the business logic behind the scenes that power the core
 * of your solution to the problem.
 * 
 * There should be no code here about painting graphics on the screen,
 * or code for handling user interaction.
 * 
 * User interface related code goes in other front-end classes.
 * 
 * @author ckcheng
 */
public class BackendModelSetup {

    public Scratchpad theScratchpad;

    public BackendModelSetup() throws IOException {
        theScratchpad = new Scratchpad(new File("Scratchpad.png").getAbsolutePath());
    }
}
