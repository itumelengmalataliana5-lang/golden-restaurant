package com.example.rastuarent;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestaurantController {

    // ---------- FOOD ----------
    @FXML private CheckBox chkFood1;
    @FXML private CheckBox chkFood2;
    @FXML private CheckBox chkFood3;

    @FXML private Spinner<Integer> qtyFood1;
    @FXML private Spinner<Integer> qtyFood2;
    @FXML private Spinner<Integer> qtyFood3;

    // ---------- DESSERT ----------
    @FXML private RadioButton rbDessert1;
    @FXML private RadioButton rbDessert2;
    @FXML private RadioButton rbDessert3;

    private ToggleGroup dessertGroup = new ToggleGroup();

    // ---------- DRINK ----------
    @FXML private ComboBox<String> comboDrinks;

    // ---------- PAYMENT ----------
    @FXML private TextField txtTotal;
    @FXML private TextField txtPaid;
    @FXML private TextField txtChange;

    // ================= INITIALIZE =================
    @FXML
    public void initialize() {

        // Radio buttons group
        rbDessert1.setToggleGroup(dessertGroup);
        rbDessert2.setToggleGroup(dessertGroup);
        rbDessert3.setToggleGroup(dessertGroup);

        // Show prices beside items
        chkFood1.setText("Rice & Beef (M50)");
        chkFood2.setText("Rice & Chicken (M60)");
        chkFood3.setText("Macaroni & Beef (M55)");

        rbDessert1.setText("Custard & Jelly (M15)");
        rbDessert2.setText("Chocolate Cake (M20)");
        rbDessert3.setText("Cupcake (M10)");

        comboDrinks.setItems(FXCollections.observableArrayList(
                "Water (M5)",
                "Soda (M10)",
                "Juice (M12)",
                "Tea (M8)",
                "Coffee (M15)"
        ));

        // Food quantity spinners
        qtyFood1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        qtyFood2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        qtyFood3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
    }

    // ================= CALCULATE TOTAL =================
    @FXML
    private void calculateTotal() {
        double total = 0;

        if (chkFood1.isSelected()) total += qtyFood1.getValue() * 50;
        if (chkFood2.isSelected()) total += qtyFood2.getValue() * 60;
        if (chkFood3.isSelected()) total += qtyFood3.getValue() * 55;

        Toggle selected = dessertGroup.getSelectedToggle();
        if (selected != null) {
            RadioButton rb = (RadioButton) selected;
            if (rb.getText().contains("15")) total += 15;
            else if (rb.getText().contains("20")) total += 20;
            else if (rb.getText().contains("10")) total += 10;
        }

        String drink = comboDrinks.getValue();
        if (drink != null) {
            if (drink.contains("M5")) total += 5;
            else if (drink.contains("M8")) total += 8;
            else if (drink.contains("M10")) total += 10;
            else if (drink.contains("M12")) total += 12;
            else if (drink.contains("M15")) total += 15;
        }

        txtTotal.setText(String.format("%.2f", total));
    }

    // ================= CALCULATE CHANGE + SAVE =================
    @FXML
    private void calculateChange() {
        try {
            double total = Double.parseDouble(txtTotal.getText());
            double paid = Double.parseDouble(txtPaid.getText());
            double change = paid - total;
            txtChange.setText(String.format("%.2f", change));

            // Build receipt and save
            String receipt = buildReceipt(total, paid, change);
            FileManager.saveTransaction(receipt);

            showAlert("Transaction saved successfully!");

        } catch (NumberFormatException e) {
            showAlert("Please enter valid numbers.");
        }
    }

    // ================= BUILD RECEIPT =================
    private String buildReceipt(double total, double paid, double change) {
        StringBuilder r = new StringBuilder();
        r.append("Items Sold:\n");

        if (chkFood1.isSelected())
            r.append("Rice & Beef x ").append(qtyFood1.getValue())
                    .append(" = M").append(qtyFood1.getValue() * 50).append("\n");

        if (chkFood2.isSelected())
            r.append("Rice & Chicken x ").append(qtyFood2.getValue())
                    .append(" = M").append(qtyFood2.getValue() * 60).append("\n");

        if (chkFood3.isSelected())
            r.append("Macaroni & Beef x ").append(qtyFood3.getValue())
                    .append(" = M").append(qtyFood3.getValue() * 55).append("\n");

        Toggle selected = dessertGroup.getSelectedToggle();
        if (selected != null)
            r.append(((RadioButton) selected).getText()).append("\n");

        if (comboDrinks.getValue() != null)
            r.append(comboDrinks.getValue()).append("\n");

        r.append("\nTOTAL: M").append(total)
                .append("\nPAID: M").append(paid)
                .append("\nCHANGE: M").append(change);

        return r.toString();
    }

    // ================= RESET SYSTEM =================
    @FXML
    private void resetSystem() {
        chkFood1.setSelected(false);
        chkFood2.setSelected(false);
        chkFood3.setSelected(false);

        qtyFood1.getValueFactory().setValue(0);
        qtyFood2.getValueFactory().setValue(0);
        qtyFood3.getValueFactory().setValue(0);

        dessertGroup.selectToggle(null);
        comboDrinks.setValue(null);

        txtTotal.clear();
        txtPaid.clear();
        txtChange.clear();
    }

    // ================= EXIT SYSTEM =================
    @FXML
    private void exitSystem() {
        Stage stage = (Stage) txtTotal.getScene().getWindow();
        stage.close();
    }

    // ================= ALERT =================
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void openFileManager() {
        Stage stage = new Stage();
        stage.setTitle("Sales Records");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        // Load sales.txt content
        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File("sales.txt"))) {
            StringBuilder sb = new StringBuilder();
            while(scanner.hasNextLine()){
                sb.append(scanner.nextLine()).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (java.io.FileNotFoundException e) {
            textArea.setText("No sales recorded yet.");
        }

        VBox root = new VBox(textArea);
        root.setSpacing(10);
        root.setStyle("-fx-padding: 10;");

        stage.setScene(new javafx.scene.Scene(root, 500, 400));
        stage.show();
    }
}