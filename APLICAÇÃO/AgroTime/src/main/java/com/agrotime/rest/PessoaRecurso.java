package com.agrotime.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.agrotime.entidades.Pessoa;

@Path("/pessoa")
public class PessoaRecurso {
	
    // Jackson
    @GET
    @Produces({ MediaType.APPLICATION_JSON/* , MediaType.APPLICATION_XML */ })
    @Path("/all")
    public List<Pessoa> getPessoasJSONP() {
    	
        List<Pessoa> listP = Util.getInstance().getlPessoa();
 
        return listP;
    }
 
    // Jackson
    @GET
    @Produces({ MediaType.APPLICATION_JSON/* , MediaType.APPLICATION_XML */ })
    @Path("/{id}")
    public Pessoa getPessoasByIdJSONP(@PathParam("id") int id) {
 
        List<Pessoa> listP = Util.getInstance().getlPessoa(); 
 
        for (Pessoa p : listP) {
            if (id == p.getId()) {
 
                return p;
            }
 
        }
 
        return null;
    }
 
    @POST
    @Consumes({ MediaType.APPLICATION_JSON/* , MediaType.APPLICATION_XML */ })
    @Path("/salvar")
    public void salvarPessoasJSONP(Pessoa p) {
 
        p.setId(Util.getInstance().getId());
        Util.getInstance().getlPessoa().add(p);     
 
    }
 
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON/* , MediaType.APPLICATION_XML */ })
    @Path("{id}")
    public void updatePessoaJSONP(@PathParam("id") int id, Pessoa p) {  
 
        List<Pessoa> listP = Util.getInstance().getlPessoa();
 
        Pessoa pessoa = listP.get(id);
 
        pessoa.setNome(p.getNome());
        pessoa.setEmail(p.getEmail());
        pessoa.setIdade(p.getIdade());
        pessoa.setDataCorrente(p.getDataCorrente());
        pessoa.setNascimento(p.getNascimento());
 
    }
 
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON/* , MediaType.APPLICATION_XML */ })
    @Path("/{id}")
    public void deleteJSONP(@PathParam("id") int id) {
 
        System.out.println("Remover id: " + id);
 
        Util.getInstance().getlPessoa().remove(id);
    }
	
    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello World";
    }
}
