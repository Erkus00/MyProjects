package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import model.Pedido;
import model.Producto;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class EstadisticaController implements Initializable {
    @FXML
    private TabPane tabPrincipal;
    @FXML
    private Tab panaderiaTab;
    @FXML
    private BorderPane panaderiaBorderPane;
    @FXML
    private BarChart panaderiaChart;
    @FXML
    private CategoryAxis panaderiaChartX;
    @FXML
    private NumberAxis panaderiaChartY;
    @FXML
    private Tab bebidasTab;
    @FXML
    private BorderPane bebidasBorderPane;
    @FXML
    private BarChart bebidasChart;
    @FXML
    private CategoryAxis bebidasChartX;
    @FXML
    private NumberAxis bebidasChartY;
    @FXML
    private Tab otrosTab;
    @FXML
    private BorderPane otrosBorderPane;
    @FXML
    private BarChart otrosChart;
    @FXML
    private CategoryAxis otrosChartX;
    @FXML
    private NumberAxis otrosChartY;
    @FXML
    private DialogPane dialogPrincipalDatos;
    @FXML
    private TextField datosDialogText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AtomicInteger pitufoBM = new AtomicInteger();
        AtomicInteger pitufoMixto = new AtomicInteger();
        AtomicInteger coca = new AtomicInteger();
        AtomicInteger fantaNaranja = new AtomicInteger();
        AtomicInteger pepsi = new AtomicInteger();
        AtomicInteger cacahuetes = new AtomicInteger();
        AtomicInteger altramuces = new AtomicInteger();
        AtomicInteger golosinas = new AtomicInteger();
        AtomicInteger patatas = new AtomicInteger();
        AtomicInteger pistachos = new AtomicInteger();

        ArrayList<Pedido> pedidos = PrincipalController.getAllPedidos();

        for (int i = 0; i < pedidos.size(); i++) {
            HashMap<Producto,Integer> product = pedidos.get(i).getHashProductos();

            product.forEach((producto, integer) -> {
                int id = producto.getId();
                switch (id) {
                    case 1 -> pitufoMixto.addAndGet(integer);
                    case 2 -> coca.addAndGet(integer);
                    case 3 -> pitufoBM.addAndGet(integer);
                    case 4 -> cacahuetes.addAndGet(integer);
                    case 5 -> fantaNaranja.addAndGet(integer);
                    case 6 -> pepsi.addAndGet(integer);
                    case 7 -> altramuces.addAndGet(integer);
                    case 8 -> patatas.addAndGet(integer);
                    case 9 -> golosinas.addAndGet(integer);
                    case 10 -> pistachos.addAndGet(integer);
                }

            });
        }

        XYChart.Series set1 = new XYChart.Series<>();

        set1.getData().add(new XYChart.Data("Pitufo Mixto", pitufoMixto));
        set1.getData().add(new XYChart.Data("Pitufo Bacon Mayo", pitufoBM));
        panaderiaChart.getData().addAll(set1);

        XYChart.Series set2 = new XYChart.Series<>();
        set2.getData().add(new XYChart.Data("Coca Cola", coca));
        set2.getData().add(new XYChart.Data("Fanta Naranja", fantaNaranja));
        set2.getData().add(new XYChart.Data("Pepsi", pepsi));
        bebidasChart.getData().addAll(set2);

        XYChart.Series set3 = new XYChart.Series<>();
        set3.getData().add(new XYChart.Data("Cacahuetes", cacahuetes));
        set3.getData().add(new XYChart.Data("Altramuces", altramuces));
        set3.getData().add(new XYChart.Data("Golosinas", golosinas));
        set3.getData().add(new XYChart.Data("Patatas", patatas));
        set3.getData().add(new XYChart.Data("Pistachos", pistachos));
        otrosChart.getData().addAll(set3);

    }

    public void cargarDatosEstadisticosPanaderia(ArrayList<Pedido> pedidos) {


    }

}
