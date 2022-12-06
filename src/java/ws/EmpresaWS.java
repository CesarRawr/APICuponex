package ws;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojos.Catalogo;
import pojos.Empresa;
import pojos.Respuesta;
import pojos.Sucursal;

@Path("empresa")
public class EmpresaWS {
    
    @Context
    private UriInfo context;
    
    public EmpresaWS(){    
    }
    
    @Path("categorias")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catalogo> buscarCategorias(){
        List<Catalogo> result = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                result = conn.selectList("empresa.getAllCategorias");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return result;
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> buscarTodos(){
        List<Empresa> empresas = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                empresas = conn.selectList("empresa.getAllEmpresas");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return empresas;
    }
    
    @Path("byName/{nombre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa buscarByNombre(@PathParam("nombre") String nombre){
        Empresa empresaResultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                empresaResultado = conn.selectOne("empresa.getByNombre",nombre);
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }
        return empresaResultado;
    }
    
    @Path("byRFC/{rfc}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa buscarByRFC(@PathParam("rfc") String rfc){
        Empresa empresaResultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                empresaResultado = conn.selectOne("empresa.getByRFC",rfc);
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }
        return empresaResultado;
    }
    
    @Path("byRep/{representante}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa buscarByRepresentante(@PathParam("representante") String representante){
        Empresa empresaResultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                empresaResultado = conn.selectOne("empresa.getByRep",representante);
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }
        return empresaResultado;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrar(@FormParam("nombre")String nombre,
                            @FormParam("nombreCom") String nombreCom,
                            @FormParam("representante") String representante,
                            @FormParam("correo") String correo,
                            @FormParam("direccion") String direccion,
                            @FormParam("codigo_P") String codigo_P,
                            @FormParam("ciudad") String ciudad,
                            @FormParam("telefono") String telefono,
                            @FormParam("paginaW") String paginaW,
                            @FormParam("rfc") String rfc,
                            @FormParam("idCategoriaE") Integer idCategoriaE){
        
        Empresa empresaRegistro = new Empresa();
        empresaRegistro.setNombre(nombre);
        empresaRegistro.setNombreCom(nombreCom);
        empresaRegistro.setRepresentante(representante);
        empresaRegistro.setCorreo(correo);
        empresaRegistro.setDireccion(direccion);
        empresaRegistro.setCodigo_P(codigo_P);
        empresaRegistro.setCiudad(ciudad);
        empresaRegistro.setTelefono(telefono);
        empresaRegistro.setPaginaW(paginaW);
        empresaRegistro.setRfc(rfc);
        empresaRegistro.setIdCategoriaE(idCategoriaE);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.insert("empresa.registrar", empresaRegistro);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Registro de empresa ACEPTADO.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Registro de empresa DENEGADO."); 
                }
            } catch (Exception e){
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuestaWS.setError(true);
            respuestaWS.setMensaje("CONEXION CON EL SISTEMA PERDIDO");
        }
        return respuestaWS;
    }
    
    @Path("modificar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta modificar(
                            @FormParam("idEmpresa") Integer idEmpresa,
                            @FormParam("nombre")String nombre,
                            @FormParam("nombreCom") String nombreCom,
                            @FormParam("representante") String representante,
                            @FormParam("correo") String correo,
                            @FormParam("direccion") String direccion,
                            @FormParam("codigo_P") String codigo_P,
                            @FormParam("ciudad") String ciudad,
                            @FormParam("telefono") String telefono,
                            @FormParam("paginaW") String paginaW,
                            @FormParam("categoriaE") Integer idCategoriaE){
        
        Empresa empresaEdicion = new Empresa();
        empresaEdicion.setIdEmpresa(idEmpresa);
        empresaEdicion.setNombre(nombre);
        empresaEdicion.setNombreCom(nombreCom);
        empresaEdicion.setRepresentante(representante);
        empresaEdicion.setCorreo(correo);
        empresaEdicion.setDireccion(direccion);
        empresaEdicion.setCodigo_P(codigo_P);
        empresaEdicion.setCiudad(ciudad);
        empresaEdicion.setTelefono(telefono);
        empresaEdicion.setPaginaW(paginaW);
        empresaEdicion.setIdCategoriaE(idCategoriaE);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.update("empresa.modificar", empresaEdicion);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Actualización de información ACEPTADA.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Actualización de información DENEGADA."); 
                }
            } catch (Exception e){
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuestaWS.setError(true);
            respuestaWS.setMensaje("CONEXION CON EL SISTEMA PERDIDO");
        }
        return respuestaWS;
    }
    
    @Path("cambiarEstatus")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta cambiarEstatus(@FormParam("idEmpresa") Integer idEmpresa){
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.update("empresa.cambiarEstatus", idEmpresa);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Estatus de la empresa INACTIVA.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Identificardor de empresa DENEGADO."); 
                }
            } catch (Exception e){
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuestaWS.setError(true);
            respuestaWS.setMensaje("CONEXION CON EL SISTEMA PERDIDO");
        }
        return respuestaWS;
    }
    
    @Path("eliminar")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@FormParam("idEmpresa") Integer idEmpresa){
        List<Sucursal> sucursales = null;
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                sucursales = conexionBD.selectList("sucursal.getAllByEmpresa", idEmpresa);
                
                // Si no hay sucursales asignadas a la empresa
                if (sucursales.size() == 0) {
                    int resultadoMapper = conexionBD.delete("empresa.eliminar", idEmpresa);
                    conexionBD.commit();

                    if(resultadoMapper > 0){
                        respuestaWS.setError(false);
                        respuestaWS.setMensaje("Baja de empresa ACEPTADA.");
                    }else{
                        respuestaWS.setError(true);
                        respuestaWS.setMensaje("Identificador de empresa DENEGADO."); 
                    }
                } else {
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("La empresa tiene sucursales asignadas"); 
                }
            } catch (Exception e){
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuestaWS.setError(true);
            respuestaWS.setMensaje("CONEXION CON EL SISTEMA PERDIDO");
        }
        
        return respuestaWS;
    }
}
