package backend;

import java.util.ArrayList;

//interface que seria responsável pela entrada e saída de dados do aplicativo
//de forma independente do meio de persistência dos dados.
//interface teria os "contratos de entrada e saída de dados" e cada um dos objetos 
//"persistíveis" deveria implementar estes contratos (métodos) de acordo com suas necessidades
public interface Persistivel {
    //retornaria um boolean true caso seja salvo, false caso contrário.
    public boolean inserir();

    //retornaria um boolean true caso seja salvo, false caso contrário.
    public boolean remover();

    //retornaria um objeto que implementa "persistivel"
    //indiquei que parametroDeBusca de busca é um object pois pode ser uma string, integer etc
    public <T extends Persistivel> T buscar(Object parametroDeBusca);
    
    //retornaria em um ArrayList todos os objetos daquela classe que estivassem salvos
    public <T extends Persistivel> ArrayList<T> listar();

    //retornaria um boolean true caso seja alterado, false caso contrário.
    //indiquei que novaInfo é um object pois pode ser uma string, integer etc
    public boolean alterar(Object novaInfo);
}
