package main;

import controller.ProductoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.Pedido;
import model.Producto;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddDialogController implements Initializable {

    @FXML
    private Text fechaText;
    @FXML
    private DatePicker fechaDatePicker;
    @FXML
    private Text clienteText;
    @FXML
    private TextField clienteTextPicker;
    @FXML
    private Text estadoText;
    @FXML
    private ComboBox<String> estadoPicker;
    @FXML
    private Text productosText;
    @FXML
    private Text bocadillosText;
    @FXML
    private Spinner<Integer> pitufoMixtoNumberPicker;
    @FXML
    private Text pitufoMixtoText;
    @FXML
    private Spinner<Integer> pitufoBMNumberPicker;
    @FXML
    private Text pitufoBMText;
    @FXML
    private Text bebidasText;
    @FXML
    private Spinner<Integer> cocaNumberPicker;
    @FXML
    private Spinner<Integer> fantaNaranjaNumberPicker;
    @FXML
    private Spinner<Integer> pepsiNumberPicker;
    @FXML
    private Text cocaText;
    @FXML
    private Text fantaNaranjaText;
    @FXML
    private Text pepsiText;
    @FXML
    private Spinner<Integer> cacahuetesNumberPicker;
    @FXML
    private Spinner<Integer> altramucesNumberPicker;
    @FXML
    private Spinner<Integer> patatasNumberPicker;
    @FXML
    private Spinner<Integer> golosinasNumberPicker;
    @FXML
    private Spinner<Integer> pistachosNumberPicker;
    @FXML
    private Text cacahuetesText;
    @FXML
    private Text altramucesText;
    @FXML
    private Text patatasText;
    @FXML
    private Text golosinasText;
    @FXML
    private Text pistachosText;
    @FXML
    private Separator separator1;
    @FXML
    private Separator separator2;
    @FXML
    private DialogPane dialogAddDialog;
    @FXML
    private GridPane gridAddDialogPrincipal;
    @FXML
    private Separator separator21;
    @FXML
    private Separator separator11;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        fechaDatePicker.setValue(date.toLocalDate());

        estadoPicker.getItems().addAll("Pendiente", "Entregado");

        pitufoMixtoNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        pitufoBMNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        cocaNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        fantaNaranjaNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        pepsiNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        cacahuetesNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        altramucesNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        patatasNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        golosinasNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        pistachosNumberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
    }


    public Pedido procesarResultados() {
        LocalDate fecha = fechaDatePicker.getValue();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);
        String cliente = clienteTextPicker.getText();
        String estado = estadoPicker.getValue();
        HashMap<Producto, Integer> productos = conseguirListaProductos();
        Pedido pedido = new Pedido(fechaSQL, cliente, estado, productos);

        return pedido;
    }

    private HashMap<Producto, Integer> conseguirListaProductos() {
        HashMap<Producto, Integer> productos = new HashMap<>();
        int pitufoMixto = pitufoMixtoNumberPicker.getValue();
        if (pitufoMixto > 0) {
            Producto pitufoMixtoProducto;
            pitufoMixtoProducto = ProductoDAO.infoProducto(1);
            productos.put(pitufoMixtoProducto, pitufoMixto);
        }
        int pitufoBM = pitufoBMNumberPicker.getValue();
        if (pitufoBM > 0) {
            Producto pitufoBMProducto;
            pitufoBMProducto = ProductoDAO.infoProducto(3);
            productos.put(pitufoBMProducto, pitufoBM);
        }
        int coca = cocaNumberPicker.getValue();
        if (coca > 0) {
            Producto cocaProducto;
            cocaProducto = ProductoDAO.infoProducto(2);
            productos.put(cocaProducto, coca);
        }
        int fantaNaranja = fantaNaranjaNumberPicker.getValue();
        if (fantaNaranja > 0) {
            Producto fantaNaranjaProducto;
            fantaNaranjaProducto = ProductoDAO.infoProducto(5);
            productos.put(fantaNaranjaProducto, fantaNaranja);
        }
        int pepsi = pepsiNumberPicker.getValue();
        if (pepsi > 0) {
            Producto pepsiProducto;
            pepsiProducto = ProductoDAO.infoProducto(6);
            productos.put(pepsiProducto, pepsi);
        }
        int cacahuetes = cacahuetesNumberPicker.getValue();
        if (cacahuetes > 0) {
            Producto cacahuetesProducto;
            cacahuetesProducto = ProductoDAO.infoProducto(4);
            productos.put(cacahuetesProducto, cacahuetes);
        }
        int altramuces = altramucesNumberPicker.getValue();
        if (altramuces > 0) {
            Producto altramucesProducto;
            altramucesProducto = ProductoDAO.infoProducto(7);
            productos.put(altramucesProducto, altramuces);
        }
        int patatas = patatasNumberPicker.getValue();
        if (patatas > 0) {
            Producto patatasProducto;
            patatasProducto = ProductoDAO.infoProducto(8);
            productos.put(patatasProducto, patatas);
        }
        int golosinas = golosinasNumberPicker.getValue();
        if (golosinas > 0) {
            Producto golosinasProducto;
            golosinasProducto = ProductoDAO.infoProducto(9);
            productos.put(golosinasProducto, golosinas);
        }
        int pistachos = pistachosNumberPicker.getValue();
        if (pistachos > 0) {
            Producto pistachosProducto;
            pistachosProducto = ProductoDAO.infoProducto(10);
            productos.put(pistachosProducto, pistachos);
        }
        return productos;
    }

    public boolean isDiferente(Pedido pedido, Pedido editPedido) {
        return !pedido.getFecha().equals(editPedido.getFecha())
                || !pedido.getCliente().equals(editPedido.getCliente())
                || !pedido.getEstado().equals(editPedido.getEstado())
                || !pedido.getProductos().equals(editPedido.getProductos());
    }

    public void cargarDatos(Pedido pedido) {
        fechaDatePicker.setValue(pedido.getFecha().toLocalDate());
        clienteTextPicker.setText(pedido.getCliente());
        estadoPicker.setValue(pedido.getEstado());
        HashMap<Producto, Integer> productos = pedido.getHashProductos();
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidad = entry.getValue();
            switch (producto.getId()) {
                case 1:
                    pitufoMixtoNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 2:
                    cocaNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 3:
                    pitufoBMNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 4:
                    cacahuetesNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 5:
                    fantaNaranjaNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 6:
                    pepsiNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 7:
                    altramucesNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 8:
                    patatasNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 9:
                    golosinasNumberPicker.getValueFactory().setValue(cantidad);
                    break;
                case 10:
                    pistachosNumberPicker.getValueFactory().setValue(cantidad);
                    break;
            }
        }
    }
}
