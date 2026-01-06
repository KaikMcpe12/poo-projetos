import javax.swing.JOptionPane;

import errors.ContaInexistenteException;
import errors.SaldoInsuficienteException;

import java.util.ArrayList;

public class AgenciaBancaria {
    static ArrayList<ContaBancaria> contasbancarias;

    public static ContaBancaria buscarConta(ArrayList<ContaBancaria> contas, String email, String senha) {
        // procura a conta com o email correspondente
        ContaBancaria conta = contas.stream()
                .filter(c -> c.getTitular().getEmail().equals(email))
                .findFirst()
                .orElse(null);

        // compara a senha fornecida com a senha da conta encontrada
        if (conta != null && Integer.toString(conta.getTitular().getSenha()).equals(senha)) {
            return conta;
        }

        return null;
    }

    // busca conta pelo numero da conta
    // caso não envie email e senha é chamado esse método
    // polimorfismo por sobrecarga (caso seja chamado com dois parâmetros ou um)
    public static ContaBancaria buscarConta(ArrayList<ContaBancaria> contas, String numeroConta) {
        // procura a conta com o email correspondente
        return contas.stream()
                .filter(c -> c.getNumeroConta().equals(numeroConta))
                .findFirst()
                .orElse(null);
    }

    public static String listContaBancarias() {
        String lista = "=== Contas Bancárias ===\n\n";

        for (ContaBancaria conta : contasbancarias) {
            // poderia fazer concatenação normal, mas com string format fica mais legível
            lista += String.format("Número: %s, Titular: %s, Email: %s\n",
                    conta.getNumeroConta(), conta.getTitular().getNome(), conta.getTitular().getEmail());
        }

        return lista;
    }

    public static void criarConta() {
        String nome, cpf, email;
        int senha;
        double saldoInicial = 0;
        try {
            nome = JOptionPane.showInputDialog("""
                === Acesso ao menu de Gerente ===
    
                Insira o nome do titular da conta:
            """);
            cpf = JOptionPane.showInputDialog("""
                === Acesso ao menu de Gerente ===
    
                Insira o CPF do titular da conta:
            """);
            email = JOptionPane.showInputDialog("""
                === Acesso ao menu de Gerente ===
    
                Insira o email do titular da conta:
            """);
            senha = Integer.parseInt(JOptionPane.showInputDialog("""
                === Acesso ao menu de Gerente ===
    
                Insira a senha do titular da conta:
            """));
            saldoInicial = Double.parseDouble(JOptionPane.showInputDialog("""
                === Acesso ao menu de Gerente ===
    
                Insira o saldo inicial da conta:
            """));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar conta, digite os valores corretamente" );
            criarConta();
            return;
        }

        if( nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha <= 0 || saldoInicial < 0 ) {
            JOptionPane.showMessageDialog(null, "Erro ao criar conta, digite os valores corretamente" );
            criarConta();
            return;
        }

        // confirmação dos dados antes de criar a conta
        int esc = Integer.parseInt(JOptionPane.showInputDialog("""
            === Acesso ao menu de Gerente ===

            Confirme os dados da conta:
            Nome: %s
            CPF: %s
            Email: %s
            Senha: %d
            Saldo Inicial: %.2f

            1 - Confirmar
            2 - Cancelar
            """.formatted(nome, cpf, email, senha, saldoInicial)));

        if (esc != 1) {
            JOptionPane.showMessageDialog(null, "Criação de conta cancelada.");
            return;
        }

        Pessoa titular = new Pessoa(nome, senha, cpf, email);
        ContaBancaria novaConta = new ContaBancaria(titular, saldoInicial);
        contasbancarias.add(novaConta);

        JOptionPane.showMessageDialog(null, "Conta criada com sucesso!\nNúmero da Conta: " + novaConta.getNumeroConta());
    }

    public static void menuConta(ContaBancaria conta) {
        JOptionPane.showMessageDialog(null,
                String.format("""
                            Bem-vindo(a) ao Banco UFC.
                            Titular: %s
                            Numero da Conta: %s
                            Saldo Inicial: R$%.2f
                            """,
                        conta.getTitular().getNome(), conta.getNumeroConta(), conta.getSaldo()),
                "Conta Bancaria", JOptionPane.INFORMATION_MESSAGE);

        // loop principal para permitir múltiplas tentativas de saque
        String input;
        while (true) {
            try {
                input = JOptionPane.showInputDialog(null,
                        """
                            Digite
                            1 - Saque
                            2 - Deposito
                            3 - Transferir
                            4 - Dados da Conta
                            (Ou 'sair' para encerrar):
                        """,
                        "Menu de opções", JOptionPane.QUESTION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Erro ao ler a entrada.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (input == null || input.equalsIgnoreCase("sair")) {
                JOptionPane.showMessageDialog(null,
                        "Encerrando sessão da conta.",
                        "Saindo", JOptionPane.INFORMATION_MESSAGE);
                break; // encerra o loop e volta para o menu principal
            }

            try {
                switch (input) {
                    case "1" -> {
                        // pede o valor do saque e converte na mesma linha
                        double saque = Double.parseDouble(JOptionPane.showInputDialog("Insira o valor do saque"));

                        // tenta realizar a operação (pode lançar SaldoInsuficienteException ou IllegalArgumentException)
                        conta.sacar(saque);

                        // exibe o sucesso (só é executado se nenhuma exceção ocorrer)
                        JOptionPane.showMessageDialog(null,
                                String.format("Saque de R$%.2f realizado com sucesso.\nNovo Saldo: R$%.2f",
                                        saque, conta.getSaldo()),
                                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }

                    case "2" -> {
                        double deposito = Double.parseDouble(JOptionPane.showInputDialog("Insira o valor do deposito"));

                        conta.depositar(deposito);

                        JOptionPane.showMessageDialog(null, String.format("""
                                Deposito de %.2f realizado com sucesso.
                                Novo saldo: %.2f
                                """, deposito, conta.getSaldo()),
                                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }

                    case "3" -> {
                        Object[] opcoesContas = contasbancarias.toArray();

                        ContaBancaria contaSelecionada = (ContaBancaria) JOptionPane.showInputDialog(
                                null, // null
                                "Selecione a conta destino:", // mensagem
                                "Transferência", // titulo
                                JOptionPane.QUESTION_MESSAGE, // tipo de mensagem
                                null, // ícone padrão
                                opcoesContas, // opções
                                opcoesContas[0] // opção padrão
                        );

                        // nem precisa verificar se é null
                        // mas caso seja, lança a exceção
                        ContaBancaria destino = buscarConta(contasbancarias, contaSelecionada.getNumeroConta());

                        if (destino == null) {
                            throw new ContaInexistenteException("Conta de destino não encontrada!");
                        }

                        double valor = Double.parseDouble(JOptionPane.showInputDialog("Saldo da conta: " + conta.getSaldo()+ "\n\nInsira o valor que deseja transferir"));

                        conta.transferir(destino, valor);

                        JOptionPane.showMessageDialog(null, """
                                Transferência concluída!
                                De: %s
                                Para: %s
                                Valor: %.2f
                                """.formatted(conta.getTitular().getNome(), destino.getTitular().getNome(), valor));
                    }

                    case "4" -> {
                        JOptionPane.showMessageDialog(null,
                                """
                                        === Dados da Conta ===
                                        
                                        Titular: %s
                                        Email: %s
                                        Número da Conta: %s
                                        Saldo: R$%.2f
                                        """.formatted(conta.getTitular().getNome(),
                                        conta.getTitular().getEmail(),
                                        conta.getNumeroConta(),
                                        conta.getSaldo()),
                                "Dados da Conta", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } catch (SaldoInsuficienteException e) {
                // 1. tratamento da exceção de NEGÓCIO (saldo)
                JOptionPane.showMessageDialog(null,
                        "ERRO DE NEGÓCIO: Saque Recusado.\nDetalhes: " + e.getMessage(),
                        "Falha no Saque (Aviso)", JOptionPane.WARNING_MESSAGE);

            } catch (NumberFormatException e) {
                // 2. tratamento de erro de ENTRADA (usuário digitou texto, não número)
                JOptionPane.showMessageDialog(null,
                        "ERRO DE ENTRADA: Valor numérico inválido. Por favor, insira um número válido.",
                        "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                // 3. tratamento da exceção de VALIDAÇÃO (valor negativo/zero)
                JOptionPane.showMessageDialog(null,
                        "ERRO DE VALIDAÇÃO: " + e.getMessage(),
                        "Falha no Saque (Erro)", JOptionPane.ERROR_MESSAGE);

            } catch (ContaInexistenteException e) {
                JOptionPane.showMessageDialog(null, "ERRO DE VALIDAÇÃO: " + e.getMessage(),
                        "Conta Inexistente", JOptionPane.ERROR_MESSAGE);
            }
            
            // 3. tratamento de erro de ENTRADA (usuário digitou texto, não número)
            finally {
                // o finally será executado após CADA tentativa (try ou catch)
                // usamos para mostrar que o bloco de limpeza foi acionado
                JOptionPane.showMessageDialog(null, "Próximo passo: Aguardando nova operação.",
                        "Operação Concluída", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    public static void entrar() {
        int opt;
        do {
            // TODO: gerente precisa de conta com senha (apenas com senha)
            String menu = """
               Bem-vindo(a) ao Banco UFC.
                1 - GERENTE ( criar contas )
                2 - PESSOA FÍSICA ( acesso a sua conta)
                3 - SAIR DA AGÊNCIA
               """;

            try {
                opt = Integer.parseInt(JOptionPane.showInputDialog(menu));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opção inválida");
                entrar();
                return;
            }

            try {
                switch (opt) {
                    // é melhor usar -> pois agrupa o código, evitando erros de escopo
                    // não precisa usar break
                    case 1 -> {
                        // TODO: criar uma função para criar conta e verificação e caso os dados não sejam preechidos, pedir novamente (chamando a função recursivamente)
                        criarConta();
                    }
                    case 2 -> {
                        String email = JOptionPane.showInputDialog("=== Acesso ao menu de Pessoa Física ===\n\n" + "Insira o email da conta:");
                        String senha = JOptionPane.showInputDialog("=== Acesso ao menu de Pessoa Física ===\n\n" + "Insira a senha da conta:");
                        ContaBancaria conta = buscarConta(contasbancarias, email, senha);

                        if (conta == null) {
                            JOptionPane.showMessageDialog(null, "Conta não encontrada!");
                            break;
                        }

                        menuConta(conta);
                    }
                    // mensagem final de encerramento
                    case 3 -> JOptionPane.showMessageDialog(null, "Simulação encerrada. Obrigado por usar o sistema!",
                    "Fim do Programa", JOptionPane.INFORMATION_MESSAGE);

                    default -> JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                // tratamento de erro de entrada para números inválidos
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número válido.");
            }
        } while(opt != 3);
    }

    // TODO: método entrar() será a execução das regras iniciais (menu de gerente, pessoa e sair)
    // TODO: criar o método criarConta(), 
    // TODO: quando transferir pedir a senha
    public static void main(String[] args) {
        contasbancarias = new ArrayList<ContaBancaria>();

        entrar();
    }
}
