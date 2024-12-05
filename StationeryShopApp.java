import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StationeryShopApp {
    public static void main(String[] args) {
        // Step 1: Define the available products, their prices, and stock quantities
        String[] productNames = {"Pens", "Pencils", "Notebooks", "Staplers", "Glue"};
        double[] productPrices = {5.0, 5.0, 50.0, 100.0, 15.0};
        int[] productStock = {100, 200, 50, 20, 30};
        double[] totalSales = {0.0}; // Store total sales in an array to allow updates in event listeners

        // Step 2: Set up the main application window
        JFrame window = new JFrame("Stationery Shop");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 300);
        window.setLayout(new BorderLayout());

        // Step 3: Create a display area for the product list and total sales
        JTextArea productDisplayArea = new JTextArea(formatProductList(productNames, productPrices, productStock, totalSales[0]));
        productDisplayArea.setEditable(false);
        window.add(new JScrollPane(productDisplayArea), BorderLayout.CENTER);

        // Step 4: Create input fields for product selection and quantity
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Purchase Section"));

        JLabel productLabel = new JLabel("Product Number:");
        JTextField productInputField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityInputField = new JTextField();

        JButton purchaseButton = new JButton("Purchase");
        JLabel resultMessageLabel = new JLabel("", SwingConstants.CENTER);

        inputPanel.add(productLabel);
        inputPanel.add(productInputField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityInputField);
        inputPanel.add(purchaseButton);

        window.add(inputPanel, BorderLayout.NORTH);
        window.add(resultMessageLabel, BorderLayout.SOUTH);

        // Step 5: Set up the purchase button to handle the logic
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get user input
                    int productIndex = Integer.parseInt(productInputField.getText()) - 1;
                    int quantity = Integer.parseInt(quantityInputField.getText());

                    // Process the purchase
                    String purchaseResult = processPurchase(productNames, productPrices, productStock, productIndex, quantity, totalSales);
                    resultMessageLabel.setText(purchaseResult);
                    productDisplayArea.setText(formatProductList(productNames, productPrices, productStock, totalSales[0]));
                } catch (NumberFormatException ex) {
                    // Handle invalid input
                    resultMessageLabel.setText("Please enter valid numbers!");
                }
            }
        });

        // Step 6: Show the window
        window.setVisible(true);
    }

    // Helper method: Format the product list for display
    private static String formatProductList(String[] names, double[] prices, int[] stock, double totalSales) {
        StringBuilder formattedList = new StringBuilder("Available Products:\n");
        formattedList.append("No.\tProduct\tPrice\tStock\n");
        formattedList.append("---------------------------------\n");

        // Loop through each product and add its details to the display string
        for (int i = 0; i < names.length; i++) {
            formattedList.append((i + 1)).append(".\t")
                    .append(names[i]).append("\t$")
                    .append(prices[i]).append("\t")
                    .append(stock[i]).append("\n");
        }

        formattedList.append("---------------------------------\n");
        formattedList.append("Total Sales: $").append(totalSales).append("\n");
        return formattedList.toString();
    }

    // Helper method: Process the purchase and update stock and sales
    private static String processPurchase(String[] names, double[] prices, int[] stock, int productIndex, int quantity, double[] totalSales) {
        if (productIndex < 0 || productIndex >= names.length) {
            return "Invalid product number!";
        }

        if (quantity <= 0) {
            return "Quantity must be greater than 0!";
        }

        if (quantity > stock[productIndex]) {
            return "Insufficient stock for " + names[productIndex] + "!";
        }

        // Calculate the subtotal and update sales and stock
        double subtotal = prices[productIndex] * quantity;
        totalSales[0] += subtotal;
        stock[productIndex] -= quantity;

        return "Successfully purchased " + quantity + " " + names[productIndex] + "(s) for $" + subtotal;
    }
}