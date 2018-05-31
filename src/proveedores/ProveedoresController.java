package proveedores;

import departamentos.*;
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

public class ProveedoresController implements Initializable {
    SQL_Crear con_crear= new SQL_Crear();
    SQL_Actualizar con_actualizar= new SQL_Actualizar();
    SQL_Consultar con_consultar= new SQL_Consultar();
    SQL_Eliminar con_Eliminar= new SQL_Eliminar();
    int cve_anterior;
    
    @FXML
    private TextField nombre, clave,direccion,rfc;
    @FXML
    private Button btn_crear,btn_leer,btn_eliminar,btn_actualizar,btn_aceptar_crear,btn_aceptar_actualizar,btn_aceptar_eliminar,btn_seleccionar,btn_cancelar;
    @FXML
    private Label lbl1, lbl2,lbl3,lbl4;
    @FXML
    private ListView lista_;
    @FXML
    private void handleBotones(ActionEvent event) throws SQLException{
        if(event.getSource() == btn_crear){
            vista_consultar(false);
            vista_actualizar(false);
            vista_eliminar(false);
            vista_crear(true);
            limpiar();
        }else
            if(event.getSource() == btn_leer){
                vista_crear(false);
                vista_actualizar(false);
                vista_eliminar(false);
                vista_consultar(true);
                btn_cancelar.setVisible(false);
                busqueda();
                limpiar();
            }else
                if(event.getSource() == btn_actualizar){
                    vista_crear(false);
                    vista_consultar(false);
                    vista_eliminar(false);
                    vista_actualizar(true);
                    btn_cancelar.setVisible(false);
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
                    }else
                        if(event.getSource() == btn_seleccionar){
                            seleccionar();
                            btn_seleccionar.setVisible(false);
                            btn_cancelar.setVisible(true);
                            btn_aceptar_actualizar.setVisible(true);
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
                                limpiar();
                            }else
                                if(event.getSource() == btn_aceptar_crear){
                                    crear();
                                    limpiar();
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
        
        clave.setPromptText("0-9999");
        nombre.setPromptText("50 caracteres");
        rfc.setPromptText("13 caracteres");
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
        lbl3.setTextFill(color);
        lbl4.setTextFill(color);
    }
    
    private void vista_crear(boolean vista){
        lbl1.setVisible(vista);
        lbl2.setVisible(vista);
        lbl3.setVisible(vista);
        lbl4.setVisible(vista);
        nombre.setVisible(vista);
        clave.setVisible(vista);
        direccion.setVisible(vista);
        rfc.setVisible(vista);
        btn_aceptar_crear.setVisible(vista);
    }
    
    private void vista_consultar(boolean vista){
        lista_.setVisible(vista);
    }
    
    private void vista_actualizar(boolean vista){
        lbl1.setVisible(vista);
        lbl2.setVisible(vista);
        lbl3.setVisible(vista);
        lbl4.setVisible(vista);
        nombre.setVisible(vista);
        clave.setVisible(vista);
        direccion.setVisible(vista);
        rfc.setVisible(vista);
        btn_seleccionar.setVisible(vista);
        lista_.setVisible(vista);
        //btn_aceptar_actualizar.setVisible(vista);
    }
    
    private void vista_eliminar(boolean vista){
        lista_.setVisible(vista);
        
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
        lista_.getItems().clear();
        lista_.setItems(con_consultar.getProveedores());
    }
    
    private void seleccionar(){
        List<Proveedor> sval = lista_.getSelectionModel().getSelectedItems();
            for(int i=0;i<sval.size();i++){
                Proveedor Seleccionado = sval.get(i);
                try{
                    int cve_=Seleccionado.getCve_proveedor();
                    String nombre_=Seleccionado.getNombre_proveedor();
                    String direccion_=Seleccionado.getDireccion();
                    String rfc_=Seleccionado.getRFC();
                    clave.setText(""+cve_);
                    nombre.setText(nombre_);
                    direccion.setText(direccion_);
                    rfc.setText(rfc_);
                    cve_anterior = cve_;
                }
                catch(Exception e){
                    System.out.println(""+e);
                }
            }
    }
    
    private void actualizar(){
        int cve=Integer.parseInt(clave.getText());
        String name=nombre.getText();
        String dir_=direccion.getText();
        String rfc_=rfc.getText();

        if(con_actualizar.ActualizaProveedor(cve, name, dir_, rfc_,cve_anterior)){
            Alert m=new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("");
            m.setContentText(" actualizado correctamente");
            m.setGraphic(new ImageView(new Image("iconos/usuario.png")));
            m.show();
        }else{
            Alert m=new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("Proveedor");
            m.setContentText(" no pudo ser actualizado");
            m.setGraphic(new ImageView(new Image("iconos/usuario.png")));
            m.show();
        }
    }
    
    private void limpiar(){
        clave.setText("");
        nombre.setText("");
        direccion.setText("");
        rfc.setText("");
    }
    
    private void crear(){
        try {
            Proveedor nuevo= new Proveedor(
                Integer.parseInt(clave.getText()),
                nombre.getText(),
                direccion.getText(),
                rfc.getText()
            );
                
                if(con_crear.insertaProveedor(nuevo))
                {
                   Alert msg=new Alert(Alert.AlertType.INFORMATION);
                   msg.setTitle("Registro guardado");
                   msg.setContentText("guardado correctamente");
                   msg.setGraphic(new ImageView(new Image("iconos/pais.png")));
                   msg.show();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    
    private void eliminar(){
        List<Proveedor> sval = lista_.getSelectionModel().getSelectedItems();
            for(int i=0;i<sval.size();i++){
                Proveedor Seleccionado = sval.get(i);
                try{
                    Alert msg =new Alert(Alert.AlertType.CONFIRMATION);
                    msg.setTitle(("ELiminar"));
                    msg.setHeaderText("");
                    msg.setContentText("¿Seguro de eliminar al depaetamento seleccionado?\n\n"+Seleccionado.getCve_proveedor()+" "+Seleccionado.getNombre_proveedor());
                    msg.setGraphic(new ImageView(new Image("iconos/departamento.png")));
                    Optional<ButtonType> res= msg.showAndWait();
                    if(res.get()==ButtonType.OK)
                    {
                        if(con_Eliminar.eliminaProveedor(Seleccionado.getCve_proveedor())){
                            Alert ms =new Alert(Alert.AlertType.CONFIRMATION);
                            ms.setTitle(("Departamento"));
                            ms.setHeaderText("Eliminado");
                            ms.setContentText("Se ha eliminado el departamento "+ Seleccionado.getCve_proveedor()+ " "+Seleccionado.getNombre_proveedor() + " correctamente");
                            ms.setGraphic(new ImageView(new Image("iconos/departamento.png")));
                        }
                    }
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
    }
}