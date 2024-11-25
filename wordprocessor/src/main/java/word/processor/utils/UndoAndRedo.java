package word.processor.utils;
import java.util.Stack;

public class UndoAndRedo {

    //handles undo actions
    private static Stack<String> undo = new Stack<>(); // use to  store states that can be undo
    private static Stack<String> redo = new Stack<>(); //use to store states that can be redo


    public static void storeStates() {
        undo.push(TextState.getCurrentText());
    }

    public static boolean undoState () {
        //if undo is empty return
        if(undo.isEmpty()) 
            return false;
        redo.push(TextState.getCurrentText()); //save current state to redo stackk
        //fetch the last state from undo
        TextState.setCurrentText(undo.pop());
        return true;

    }

    public static boolean redoState () {
        //if redo stack is empty return
        if(redo.isEmpty()) 
            return false;
        //save current text state to the undo stack
        undo.push(TextState.getCurrentText());
        //pop the last state from the redo stack
        TextState.setCurrentText(redo.pop());
        return true;
    }
}