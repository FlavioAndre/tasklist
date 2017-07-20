package com.tasklist.data;

public class Task {
    
import javax.persistence.*;

/**
 * Task entity.
 *
 * @author Flávio André
 */
@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", sequenceName = "id")
    private Long id;

    private String titulo;

    private String status;

    private String descricao;
    
    private Date criacao;
    
    private Date edicao;
    
    private Date remocao;
    
    private Date conclusao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo(){
        return this.titulo;
    }
   
    public void setTitulo(String titulo){
       this.titulo = titulo;
    }

     public String getStatus(){
        return this.status;
    }
   
    public void setStatus(String status){
       this.status = status;
    }

    public String getDescricao(){
        return this.descricao;
    }
   
    public void setDescricao(String descricao){
       this.descricao = descricao;
    }

    public Date getCriacao(){
        return this.criacao;
    }
   
    public void setCriacao(Date criacao){
       this.criacao = criacao;
    }

    public Date getEdicao(){
        return this.edicao;
    }
   
    public void setEdicao(Date edicao){
       this.edicao = edicao;
    }

    public Date getRemocao(){
        return this.remocao;
    }
   
    public void setRemocao(Date remocao){
       this.remocao = remocao;
    }

    public Date getConclusao(){
        return this.conclusao;
    }
   
    public void setConclusao(Date conclusao){
       this.conclusao = conclusao;
    }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Task task = (Task) o;

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
