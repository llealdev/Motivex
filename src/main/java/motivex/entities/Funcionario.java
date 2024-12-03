package motivex.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String rg;
    private String cpf;
    
    @Column(name = "numero_celular")
    private String numeroCelular;

    @ManyToOne
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargoFuncionario;

    public Funcionario() {
    }

    public Funcionario(int id, String nome, String rg, String cpf, String numeroCelular, Usuario usuario, Cargo cargoFuncionario) {
        this.id = id;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.numeroCelular = numeroCelular;
        this.usuario = usuario;
        this.cargoFuncionario = cargoFuncionario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cargo getCargoFuncionario() {
        return cargoFuncionario;
    }

    public void setCargoFuncionario(Cargo cargoFuncionario) {
        this.cargoFuncionario = cargoFuncionario;
    }

    
}

