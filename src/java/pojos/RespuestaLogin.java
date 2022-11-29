package pojos;

public class RespuestaLogin {
    
    private Boolean error;
    private String mensaje;
    private Integer idUsuarioSesion;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Integer idCtgEmpresa;
    private String ctgEmpresa;

    public RespuestaLogin() {
    }

    public RespuestaLogin(Boolean error, String mensaje, Integer idUsuarioSesion, String nombre, String apellidoPaterno, String apellidoMaterno, Integer idCtgEmpresa, String ctgEmpresa) {
        this.error = error;
        this.mensaje = mensaje;
        this.idUsuarioSesion = idUsuarioSesion;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idCtgEmpresa = idCtgEmpresa;
        this.ctgEmpresa = ctgEmpresa;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getIdUsuarioSesion() {
        return idUsuarioSesion;
    }

    public void setIdUsuarioSesion(Integer idUsuarioSesion) {
        this.idUsuarioSesion = idUsuarioSesion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Integer getIdCtgEmpresa() {
        return idCtgEmpresa;
    }

    public void setIdCtgEmpresa(Integer idCtgEmpresa) {
        this.idCtgEmpresa = idCtgEmpresa;
    }

    public String getCtgEmpresa() {
        return ctgEmpresa;
    }

    public void setCtgEmpresa(String ctgEmpresa) {
        this.ctgEmpresa = ctgEmpresa;
    }
    
    
}
