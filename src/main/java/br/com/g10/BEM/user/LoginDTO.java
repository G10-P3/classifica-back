package br.com.g10.BEM.user;

public class LoginDTO {

    private String cpf;

    private String password;

    // Construtor
    public LoginDTO(UserModel user) {
        this.cpf = user.getCpf();
        this.password = user.getPassword();
    }

    // construtor vazio
    public LoginDTO() {
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

