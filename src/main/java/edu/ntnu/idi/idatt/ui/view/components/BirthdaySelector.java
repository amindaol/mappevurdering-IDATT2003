package edu.ntnu.idi.idatt.ui.view.components;

import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


/**
 * A reusable component for selecting a player's birthday.
 */
public class BirthdaySelector extends VBox {

  private final Label label;
  private final DatePicker datePicker;

  /**
   * Constructs a BirthdaySelector with default prompt text.
   */
  public BirthdaySelector() {
    Label label = new Label("Birthday:");
    DatePicker datePicker = new DatePicker();
    datePicker.setPromptText("Select birthday");

    this.getChildren().addAll(label, datePicker);
    this.setSpacing(5);

    this.label = label;
    this.datePicker = datePicker;
  }

  /**
   * Returns the selected birthday.
   *
   * @return LocalDate or null if not selected
   */
  public LocalDate getBirthday() {
    return datePicker.getValue();
  }

  /**
   * Sets the birthday value (e.g. for prefill or testing).
   *
   * @param date the birthday to set
   */
  public void setBirthday(LocalDate date) {
    datePicker.setValue(date);
  }

  /**
   * Returns the internal DatePicker (optional).
   */
  public DatePicker getDatePicker() {
    return datePicker;
  }


}
