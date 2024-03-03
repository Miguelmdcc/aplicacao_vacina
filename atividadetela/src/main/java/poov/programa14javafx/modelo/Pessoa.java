package poov.programa14javafx.modelo;

import java.time.LocalDate;

public class Pessoa {
    private String nome;
    private Long codigo;
    private String cpf;
    private LocalDate dataNascimento;
    private Situacao situacao;

    public Pessoa() {
        nome = "";
        codigo = null;
        cpf = "";
        dataNascimento = null;
        situacao = Situacao.ATIVO;
    }

    public Pessoa(String nome, Long codigo, String cpf, LocalDate dataNascimento, Situacao situacao) {
        this.nome = nome;
        this.codigo = codigo;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        situacao = Situacao.ATIVO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
        result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (dataNascimento == null) {
            if (other.dataNascimento != null)
                return false;
        } else if (!dataNascimento.equals(other.dataNascimento))
            return false;
        if (situacao != other.situacao)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pessoa [nome=" + nome + ", codigo=" + codigo + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento
                + ", situacao=" + situacao + "]";
    }

}
