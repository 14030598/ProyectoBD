package sueldos;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import SQL.SQL_Actualizar;
import SQL.SQL_Consultar;
import SQL.SQL_Crear;
import SQL.SQL_Eliminar;

public class SueldosController implements Initializable{
    SQL_Crear con_crear= new SQL_Crear();
    SQL_Actualizar con_actualizar= new SQL_Actualizar();
    SQL_Consultar con_consultar= new SQL_Consultar();
    SQL_Eliminar con_Eliminar= new SQL_Eliminar();
    int nivel_anterior;
    
    @FXML
    private TextField nivel_sdo,monto_sdo;
    @FXML
    private Button btn_crear,btn_leer,btn_eliminar,btn_actualizar,btn_aceptar_crear,btn_aceptar_actualizar,btn_aceptar_eliminar,btn_seleccionar,btn_cancelar;
    @FXML
    private Label lbl1, lbl2;
    @FXML
    private ListView lista_sueldos;
    @FXML
    private void handleBotones(ActionEvent event) throws SQLException{
        if(event.getSource() == btn_crear){
            vista_consultar(false);
            vista_actualizar(false);
            vista_eliminar(false);
            vista_crear(true);
            btn_aceptar_actualizar.setVisible(false);
            btn_cancelar.setVisible(false);
            limpiar();
        }else
            if(event.getSource() == btn_leer){
                vista_crear(false);
                vista_actualizar(false);
                vista_eliminar(false);
                vista_consultar(true);
                btn_cancelar.setVisible(false);
                btn_aceptar_actualizar.setVisible(false);
                busqueda();
                limpiar();
            }else
                if(event.getSource() == btn_actualizar){
                    vista_crear(false);
                    vista_consultar(false);
                    vista_eliminar(false);
                    vista_actualizar(true);
                    btn_cancelar.setVisible(false);
                    btn_aceptar_actualizar.setVisible(false);
                    limpiar();
                    busqueda();
                }else
                    if(event.getSource() == btn_eliminar){
                        vista_crear(false);
                        vista_consultar(false);
                        vista_actualizar(false);
                        vista_eliminar(true);
                        busqueda();
                        btn_cancelar.setVisible(false);
                        btn_aceptar_actualizar.setVisible(false);
                    }else
                        if(event.getSource() == btn_seleccionar){
                            seleccionar();
                            btn_seleccionar.setVisible(false);
                            btn_cancelar.setVisible(true);
                            btn_aceptar_actualizar.setVisible(true);
                            seleccionar();
                        }else
                        if(event.getSource() == btn_cancelar){
                            btn_seleccionar.setVisible(true);
                            btn_cancelar.setVisible(false);
                            btn_aceptar_actualizar.setVisible(false);
                            limpiar();
                        }else
                            if(event.getSource() == btn_aceptar_actualizar){
                                actualizar();
                                busqueda();
                                btn_aceptar_actualizar.setVisible(false);
                                btn_cancelar.setVisible(false);
                                limpiar();
                            }else
                                if(event.getSource() == btn_aceptar_crear){
                                    crear();
                                }else
                                    if(event.getSource() == btn_aceptar_eliminar){
                                        eliminar();
                                        busqueda();
                                    }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        color(Color.web("#D4AF37"));
        vista_crear(false);
        vista_consultar(false);
        vista_actualizar(false);
        vista_eliminar(false);
        btn_cancelar.setVisible(false);
        btn_aceptar_actualizar.setVisible(false);
        imagenes();
        nivel_sdo.setPromptText("Solo se permiten numeros");
        monto_sdo.setPromptText("Solo se permiten numeros");
    }
    
    private void color(Color color){
        btn_crear.setTextFill(color);
        btn_leer.setTextFill(color);
        btn_actualizar.setTextFill(color);
        btn_eliminar.setTextFill(color);
        
        btn_seleccionar.setTextFill(color);
        btn_cancelar.setTextFill(color);
        
        btn_aceptar_crear.setTextFill(color);
        btn_aceptar_actualizar.setTextFill(color);
        btn_aceptar_eliminar.setTextFill(color);
        
        lbl1.setTextFill(color);
        lbl2.setTextFill(color);
    }
    
    private void vista_crear(boolean vista){
        lbl1.setVisible(vista);
        lbl2.setVisible(vista);
        nivel_sdo.setVisible(vista);
        monto_sdo.setVisible(vista);
        btn_aceptar_crear.setVisible(vista);
    }
    
    private void vista_consultar(boolean vista){
        lista_sueldos.setVisible(vista);
    }
    
    private void vista_actualizar(boolean vista){
        lbl1.setVisible(vista);
        lbl2.setVisible(vista);
        nivel_sdo.setVisible(vista);
        monto_sdo.setVisible(vista);
        btn_seleccionar.setVisible(vista);
        lista_sueldos.setVisible(vista);
        //btn_aceptar_actualizar.setVisible(vista);
    }
    
    private void vista_eliminar(boolean vista){
        lista_sueldos.setVisible(vista);
        
        btn_aceptar_eliminar.setVisible(vista);
    }
    
    private void imagenes(){
        btn_crear.setGraphic(new ImageView(new Image("iconos/crear.png")));
        btn_crear.setContentDisplay(ContentDisplay.BOTTOM);
        btn_leer.setGraphic(new ImageView(new Image("iconos/consultar.png")));
        btn_leer.setContentDisplay(ContentDisplay.BOTTOM);
        btn_actualizar.setGraphic(new ImageView(new Image("iconos/actualizar.png")));
        btn_actualizar.setContentDisplay(ContentDisplay.BOTTOM);
        btn_eliminar.setGraphic(new ImageView(new Image("iconos/eliminar.png")));
        btn_eliminar.setContentDisplay(ContentDisplay.BOTTOM);
    }
    
    private void busqueda() throws SQLException{
        lista_sueldos.getItems().clear();
        lista_sueldos.setItems(con_consultar.getSueldo());
    }
    
    private void seleccionar(){
        List<Sueldo> sval = lista_sueldos.getSelectionModel().getSelectedItems();
            for(int i=0;i<sval.size();i++){
                Sueldo SueldoSeleccionado = sval.get(i);
                try{
                    int nivel=SueldoSeleccionado.getNivel();
                    int monto=SueldoSeleccionado.getMonto();
                    nivel_sdo.setText(""+nivel);
                    monto_sdo.setText(""+monto);
                    nivel_anterior = nivel;
                }
                catch(Exception e){
                    System.out.println(""+e);
                }
            }
    }
    
    private void actualizar(){
        int nvel=Integer.parseInt(nivel_sdo.getText());
        int mto=Integer.parseInt(monto_sdo.getText());

        if(con_actualizar.ActualizaSueldo(nvel, mto, nivel_anterior)){
            Alert m=new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("Sueldo");
            m.setContentText("Sueldo actualizado correctamente");
            m.setGraphic(new ImageView(new Image("iconos/sueldos.png")));
            m.show();
        }else{
            Alert m=new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("Sueldo");
            m.setContentText("Sueldo no pudo ser actualizado");
            m.setGraphic(new ImageView(new Image("iconos/sueldos.png")));
            m.show();
        }
    }
    
    private void limpiar(){
        nivel_sdo.setText("");
        monto_sdo.setText("");
    }
    
    private void crear(){
        try {
            Sueldo nuevosueldo= new Sueldo(   Integer.parseInt(nivel_sdo.getText()),  Integer.parseInt(monto_sdo.getText())    );
                if(con_crear.insertaSueldo(nuevosueldo)){
                   Alert msg=new Alert(Alert.AlertType.INFORMATION);
                   msg.setTitle("Registro guardado");
                   msg.setContentText("guardado correctamente");
                   msg.setGraphic(new ImageView(new Image("iconos/sueldos.png")));
                   msg.show();
                }
            } catch (Exception e) {
                nivel_sdo.setText("");
                Alert m=new Alert(Alert.AlertType.INFORMATION);
                m.setTitle("Sueldo");
                m.setContentText("Sueldo no pudo ser crea"+e);
                m.setGraphic(new ImageView(new Image("iconos/sueldos.png")));
                m.show();
                System.out.println(e);
            }
    }
    
    private void eliminar(){
        List<Sueldo> sval = lista_sueldos.getSelectionModel().getSelectedItems();
            for(int i=0;i<sval.size();i++){
                Sueldo SueldoSeleccionado = sval.get(i);
                try{
                    Alert msg =new Alert(Alert.AlertType.CONFIRMATION);
                    msg.setTitle(("ELiminar"));
                    msg.setHeaderText("Sueldo");
                    msg.setContentText("¿Seguro de eliminar el sueldo seleccionada?\n\n"+SueldoSeleccionado.getNivel()+" "+SueldoSeleccionado.getMonto());
                    msg.setGraphic(new ImageView(new Image("iconos/sueldos.png")));
                    Optional<ButtonType> res= msg.showAndWait();
                    if(res.get()==ButtonType.OK)
                    {
                        if(con_Eliminar.eliminaSueldo(SueldoSeleccionado.getNivel())){
                            Alert ms =new Alert(Alert.AlertType.CONFIRMATION);
                            ms.setTitle(("Sueldo"));
                            ms.setHeaderText("Eliminado");
                            ms.setContentText("Se ha eliminado el area "+ SueldoSeleccionado.getNivel()+ " "+SueldoSeleccionado.getMonto() + " correctamente");
                            ms.setGraphic(new ImageView(new Image("iconos/IconosMenu/empleado.png")));
                        }
                    }
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
    }
}