/** Resposta da questão a da Avaliação Individual sobre a implementação do projeto.
 * 
 * A implementação atual do nosso programa não favorece a implementação de outros meios 
 * de persistência de dados. Nosso projeto utiliza a persistência de dados em arquivos texto (.txt).
 * Para tal, foi criada uma classe "genérica" de funções de arquivos que contém métodos de manipulação
 * de arquivos que podem ser utilizadas pelas demais classes do programa. Outras classes (a maioria) implementam 
 * seus métodos de manipulação de dados chamando as funções de caráter geral implementadas em FunçõesArquivos.java.
 * Então, há um pouco de abstração do método geral (exemplo: salvar o dado), pois cada uma das classes (em sua maioria)
 * não lida diretamente com implementações básicas de manipulação de dados. Apenas chamam os métodos de FunçõesArquivos.java.
 * Então, poderia ser realizada uma alteração do programa para que fosse utilizado como meio de persistência de dados um banco
 * de dados, por exemplo. Os métodos presentes em FuncoesArquivos.java poderiam ser modificados a fim de salvar nesse novo meio
 * e com alterações em algumas partes do código a transição seria possível de se fazer.
 *  
 * No entanto, isso não é suficiente para atender adequadamente outros meios de persistência de dados. Mesmo sendo possível realizar a 
 * transição de um meio de persistência de dados para outro, isso seria bastante complexo e nada eficiente. Haveria necessidade de procurar 
 * inúmeros pontos de código para encontrar onde um meio está sendo utilizado para trocar por outro, reescrever inúmeros métodos, corrigir 
 * chamadas de métodos (sobretudo pela grande diferença e imcompatibilidade entre as assinaturas atuais e as necessárias para outro método 
 * - poderiam "sobrar" parâmetros, "faltar" parâmetros, retornos inapropriados para o novo meio utilizado etc), entre outros problemas que 
 * poderiam atrasar e dificultar a migração entre meios. Logo, o nível de abstração da nossa implementação não está apto para isso sem 
 * modificações.
 * 
 * Para corrigir este problema e realizar estas alterações na aplicação, poderia ser implementada uma interface "Persistivel". 
 * Ela ditaria os métodos ("contratos") que cada uma das classes passíveis de serem persistidas teriam que implementar para 
 * realizar suas operações básicas de entrada e saída de dados, como, por exemplo, salvar um objeto x em um meio apropriado para 
 * suas próprias necessidades - que pode inclusive ser diferente do meio utilizado para o objeto de outra classe da aplicação.
 * Dessa forma, ao migrar uma única classe para uma nova forma de persistência de dados ou até todas as classes do nosso projeto 
 * bastaria modificar as implementações dos métodos definidos pela interface "Persistivel". Com isso, a assinatura do método
 * permaneceria a mesma independente do meio de manipulação de dados escolhido e não seria necessário procurar e alterar inúmeras 
 * linhas de código para realizar esta migração, seria necessário modifciar a implementação do método desejado na classe desejada.
 * 
 * Exemplo prático. A aplicação MedAlerta possui classes PessoaFisica e PessoaJuridica implementadas atualmente para gravar os dados
 * em arquivo texto. Supondo que fosse necessário migrar para gravação de dados em um banco de dados x utilizando a interface persistível.
 * Nesse contexto, ambas as classes teriam um método "salvar", por exemplo, com sua assinatura definida pela interface então a implementação 
 * interna desse método em cada uma das classes poderia ser modifcicada sem alterar a execução do programa, o método seria alterado internamente
 * mas não refletiria na aplicação por estar bastante abstraído. Supondo também que cada uma das classes citadas no exemplo precisem salvar
 * seus dados em bancos de dados distintos. Isso poderia ser feito sem problema algum justamente porque cada classe implementa seu método salvar.
 * 
 * Podemos notar então que a implementação não proporciona fácil migração entre meios de persistência de dados. Nesse contexto, seria 
 * interessante implementar uma interface "Persistivel" que ditaria os métodos e suas respectivas assinatura s para que sejam utilizados
 * e implementados em todas as classes consideradas persistíveis, facilitando, assim, a legibilidade, abstração e eficiência na migração entre
 * distintos meios de persistência de dados.
 * 
 * Para responder a questão b, criei a interface Persistivel com a assinatura dos métodos que deveriam ser implementados pelas classes 
 * persistiveis.
 *  
 * 
 * */ 


package inicio;

import backend.gerenciamento.Gerenciador;
import backend.usuario.PessoaFisica;
import frontend.Inicio;
import frontend.Home;

public class MedAlerta {
    private static PessoaFisica pessoa = null;
    private static boolean emEspera = true;
    private static boolean usuarioPessoa = true; // quem esta logado é pessoa fisica ou juridica?

    //utilizado para devolver objeto pessoa ao main para que seja utilizado por outras threads
    public static void setFimDaEspera(boolean emEspera, PessoaFisica pessoa) {
        MedAlerta.pessoa = pessoa;
        MedAlerta.emEspera = emEspera;
    }

    //utilizado para devolver objeto pessoa ao main para que seja utilizado por outras threads
    public static void setFimDaEspera(boolean emEspera) {
        MedAlerta.emEspera = emEspera;
        MedAlerta.usuarioPessoa = false;
    }

    public static void main(String[] args) {
        // thread para tela principal do aplicativo
        Inicio telaInicial = new Inicio();
        Thread interfaceComUsuario = new Thread(telaInicial);
        interfaceComUsuario.start();

        while (emEspera) {
            try {
                Thread.sleep(2000); // dorme por 2 segundos
                System.out.println("Sistema em espera..."); //esperando login
            } catch (InterruptedException e) {}
        }

        // thread para iniciar a tela home
        if(usuarioPessoa){
            Home tela = new Home();
            tela.receber(MedAlerta.pessoa);
            tela.setVisible(true);
        }
    
        // thread para o gerenciador do aplicativo
        if(usuarioPessoa){
            Gerenciador.setPessoa(pessoa);
            Gerenciador g = new Gerenciador();
            Thread gerenciador = new Thread(g);
            gerenciador.start();
        }
    }
}
