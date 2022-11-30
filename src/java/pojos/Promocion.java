package pojos;

public class Promocion {
    
    private Integer idPromocion;
    private String nPromocion;
    private String descripcion;
    private String fecha_In;
    private String fecha_Fn;
    private String restricciones;
    private Integer porcentDesc;
    private Integer costoProm;
    private String tipoProm;
    private Integer idCategoriaE;
    private Integer idEstatusP;
    private Integer idSucursal;

    public Promocion() {
    }

    public Promocion(String nPromocion, String descripcion, String fecha_In, String fecha_Fn, String restricciones, Integer porcentDesc, Integer costoProm, String tipoProm, Integer idCategoriaE, Integer idEstatusP, Integer idSucursal) {
        this.nPromocion = nPromocion;
        this.descripcion = descripcion;
        this.fecha_In = fecha_In;
        this.fecha_Fn = fecha_Fn;
        this.restricciones = restricciones;
        this.porcentDesc = porcentDesc;
        this.costoProm = costoProm;
        this.tipoProm = tipoProm;
        this.idCategoriaE = idCategoriaE;
        this.idEstatusP = idEstatusP;
        this.idSucursal = idSucursal;
    }

    public Promocion(Integer idPromocion, String nPromocion, String descripcion, String restricciones, Integer porcentDesc, Integer costoProm, String tipoProm) {
        this.idPromocion = idPromocion;
        this.nPromocion = nPromocion;
        this.descripcion = descripcion;
        this.restricciones = restricciones;
        this.porcentDesc = porcentDesc;
        this.costoProm = costoProm;
        this.tipoProm = tipoProm;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getnPromocion() {
        return nPromocion;
    }

    public void setnPromocion(String nPromocion) {
        this.nPromocion = nPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_In() {
        return fecha_In;
    }

    public void setFecha_In(String fecha_In) {
        this.fecha_In = fecha_In;
    }

    public String getFecha_Fn() {
        return fecha_Fn;
    }

    public void setFecha_Fn(String fecha_Fn) {
        this.fecha_Fn = fecha_Fn;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }

    public Integer getPorcentDesc() {
        return porcentDesc;
    }

    public void setPorcentDesc(Integer porcentDesc) {
        this.porcentDesc = porcentDesc;
    }

    public Integer getCostoProm() {
        return costoProm;
    }

    public void setCostoProm(Integer costoProm) {
        this.costoProm = costoProm;
    }

    public String getTipoProm() {
        return tipoProm;
    }

    public void setTipoProm(String tipoProm) {
        this.tipoProm = tipoProm;
    }

    public Integer getIdCategoriaE() {
        return idCategoriaE;
    }

    public void setIdCategoriaE(Integer idCategoriaE) {
        this.idCategoriaE = idCategoriaE;
    }

    public Integer getIdEstatusP() {
        return idEstatusP;
    }

    public void setIdEstatusP(Integer idEstatusP) {
        this.idEstatusP = idEstatusP;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }
}
