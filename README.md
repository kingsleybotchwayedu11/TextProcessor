# Text Processor with Java and JavaFX

**Text Processor** is a simple text editing application built using Java and JavaFX. It allows users to create, edit, and manipulate text documents. Features include text search, replace, undo, redo, zoom functionality, and file handling (open, save, save as).

## Features

- **Create a New File**: Clear the current content and start a new document.
- **Open File**: Open existing `.txt` files for editing.
- **Save File**: Save changes to the current file or save as a new file.
- **Undo & Redo**: Supports undo and redo actions to revert text modifications.
- **Text Search & Replace**: Search for specific text using simple or regular expressions and replace it.
- **Zoom**: Increase or decrease font size in the text area.
- **Word & Character Count**: Displays real-time word and character counts while typing.

## Technologies Used

- **Java**: The core programming language used to build the application.
- **JavaFX**: A GUI framework used for creating the user interface.
- **FXML**: Used to define the structure of the GUI in a separate XML file.
- **Maven**: Dependency management (if required).

## Installation

1. Clone the repository to your local machine.
2. Ensure you have Java 11 or higher installed on your system.
3. Import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
4. If using Maven, ensure dependencies are installed.
5. Run the `App` class to launch the application.

## Usage

- **New File**: Click the "New File" option in the menu to clear the current text and start fresh.
- **Open File**: Use the "Open" menu option to browse and open a `.txt` file from your computer.
- **Save File**: Save the current document with the "Save" option. If no previous file exists, it will prompt you to choose a location.
- **Undo and Redo**: Click the "Undo" and "Redo" buttons to revert or reapply changes.
- **Text Search and Replace**:
  - Use the "Find" text box to search for a word or phrase.
  - Choose between normal text search or regular expression search.
  - Use the "Replace" text box to replace text, with the option to replace one instance or all instances in the document.
- **Zoom**: Increase or decrease the font size using the zoom buttons (or by clicking the respective buttons to zoom in/out).
- **Word and Character Count**: Real-time word and character counts are displayed while typing in the `TextArea`.

## Code Overview

### Key Classes

1. **HomeController**: This class controls the main interactions and logic for the application. It handles:
   - File operations like opening, saving, and creating new files.
   - Undo and Redo operations using `UndoAndRedo` utility class.
   - Text editing functionalities like searching and replacing text.
   - Font size zoom in and zoom out functionality.
   - Real-time updates to word and character counts.

2. **TextState**: A utility class that manages the current state of the text, including:
   - Saving and loading text to/from files.
   - Keeping track of the current text and font size.
   - Undo and redo operations using a history stack.

3. **UndoAndRedo**: Handles storing and restoring text states for undo and redo functionality.

4. **App**: The main entry point for the application, initializing the JavaFX application window.

### HomeController Methods

- **newFileAction**: Clears the text area and resets the document state.
- **openMenuAction**: Opens a `.txt` file using a file chooser and loads its contents into the text area.
- **redoAction & undoAction**: These methods handle the redo and undo actions, updating the `TextArea` content accordingly.
- **zoomInAction & zoomOutAction**: Adjusts the font size in the `TextArea` by incrementing or decrementing the font size by 2.
- **saveAction**: Saves the current text either to an existing file or prompts the user to select a new location.
- **findAction & replaceAction**: Searches for text and optionally replaces it with new text, either one occurrence or all occurrences.
  
## Customization

- **Font**: The application allows the user to zoom in and out by adjusting the font size of the text in the `TextArea`. The font size is stored and maintained across interactions.
- **Search and Replace**: Supports both case-sensitive and case-insensitive search as well as regular expression-based searching.

## Screenshots

Include some example screenshots of the application's interface here.

## License

This project is open-source and available under the MIT License.

## Contribution

Feel free to fork the repository, create branches, and submit pull requests for any improvements or bug fixes.

---

### Example of the `HomeController` class:

```java
@FXML
void openMenuAction(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Text Files", "*.txt"));
    File selectedFile = fileChooser.showOpenDialog(App.displayTage);
    if (selectedFile != null) {
        Boolean isRead = TextState.readFromFile(selectedFile.getAbsolutePath());
        if (isRead) {
            textArea.setText(TextState.getCurrentText());
        }
    }
}
