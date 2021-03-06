package ticket_se_incluye_en_producto;

public class Ticket_Se_Incluye_En_Producto {
    int cantidad;
    int no_ticket;
    int cve_producto;

    public Ticket_Se_Incluye_En_Producto(int cantidad, int no_ticket, int cve_producto) {
        this.cantidad = cantidad;
        this.no_ticket = no_ticket;
        this.cve_producto = cve_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNo_ticket() {
        return no_ticket;
    }

    public void setNo_ticket(int no_ticket) {
        this.no_ticket = no_ticket;
    }

    public int getCve_producto() {
        return cve_producto;
    }

    public void setCve_producto(int cve_producto) {
        this.cve_producto = cve_producto;
    }

    @Override
    public String toString() {
        return String.format("\t\t%30s", cantidad) + String.format("%70s", no_ticket) + String.format("%70s",cve_producto);
    }
    
    
}
