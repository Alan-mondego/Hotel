//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import servicos.*;
import entidades.*;
import repositorio.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Inicialização do sistema diretamente no main
        Scanner sc = new Scanner(System.in);
        
        // Inicialização dos repositórios
        RepositorioFuncionarios repositorioFuncionarios = new RepositorioFuncionarios();
        RepositorioClientes repositorioClientes = new RepositorioClientes();
        RepositorioHospedagens repositorioHospedagens = new RepositorioHospedagens();
        RepositorioReservas repositorioReservas = new RepositorioReservas();
        RepositorioServicosAdicionais repositorioServicosAdicionais = new RepositorioServicosAdicionais();
        RepositorioReservasServico repositorioReservasServico = new RepositorioReservasServico();

        // Inicialização dos gerenciadores
        GerenciadorFuncionarios gerenciadorFuncionarios = new GerenciadorFuncionarios(repositorioFuncionarios);
        GerenciadorClientes gerenciadorClientes = new GerenciadorClientes(repositorioClientes);
        GerenciadorHospedagens gerenciadorHospedagens = new GerenciadorHospedagens(repositorioHospedagens);
        GerenciadorReservas gerenciadorReservas = new GerenciadorReservas(
            repositorioReservas, repositorioHospedagens, repositorioClientes);
        GerenciadorServicosAdicionais gerenciadorServicosAdicionais = new GerenciadorServicosAdicionais(
            repositorioServicosAdicionais, repositorioReservasServico);

        int opcaoUsuario;

        do {
            System.out.println("====BEM VINDO===");
            System.out.println("1-Cadastrar cliente");
            System.out.println("2-Cadastrar Funcionário");
            System.out.println("3-Cadastro e gerenciamento de hospedagens e serviços adicionais");
            System.out.println("4-Realização de reservas de hospedagem e serviços adicionais");
            System.out.println("5-Consulta de disponibilidade de hospedagem e serviços");
            System.out.println("6-Fazer check-out");
            System.out.println("7-Cancelamento de reservas");
            System.out.println("8-Realização de check-in");
            System.out.println("9-Relatórios");
            System.out.println("10-Listar Reservas Ativas");
            System.out.println("0-Sair");
            System.out.println("Escolha uma opção :");

            while (!sc.hasNextInt()) {
                System.out.println("Opção inválida!!");
                sc.nextLine();
                System.out.println("Insira uma opção");
            }
            opcaoUsuario = sc.nextInt();
            sc.nextLine();

            switch (opcaoUsuario) {
                case 1:
                    if (!gerenciadorFuncionarios.existeAlgumFuncionario()) {
                        System.out.println("Não há funcionários cadastrados no momento");
                        break;
                    }

                    boolean cadastroSucesso = false;
                    while (!cadastroSucesso) {
                        try {
                            System.out.println("---INSIRA SEU DADOS PARA CADASTRO---");
                            System.out.println("CPF: ");
                            String cpfCliente = sc.nextLine();
                            System.out.println("Nome: ");
                            String nomeCliente = sc.nextLine();
                            System.out.println("Telefone: ");
                            String telefoneCliente = sc.nextLine();

                            gerenciadorClientes.cadastrarCliente(nomeCliente, cpfCliente, telefoneCliente);
                            System.out.println("Cliente cadastrado com sucesso!");
                            cadastroSucesso = true;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Deseja tentar novamente? (S/N)");
                            String resposta = sc.nextLine();
                            if (!resposta.equalsIgnoreCase("S")) {
                                break;
                            }
                        }
                    }
                    break;

                case 2:
                    boolean cadastroFuncionarioSucesso = false;
                    while (!cadastroFuncionarioSucesso) {
                        try {
                            System.out.println("Informe seu nome:");
                            String nomeFuncionario = sc.nextLine();
                            System.out.println("Informe seu cargo:");
                            String cargoFuncionario = sc.nextLine();

                            gerenciadorFuncionarios.cadastrarFuncionario(nomeFuncionario, cargoFuncionario);
                            System.out.println("Funcionário cadastrado com sucesso!");
                            cadastroFuncionarioSucesso = true;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Deseja tentar novamente? (S/N)");
                            String resposta = sc.nextLine();
                            if (!resposta.equalsIgnoreCase("S")) {
                                break;
                            }
                        }
                    }
                    break;

                case 3:
                    int escolhaDeHospedagem = 0;
                    do {
                        System.out.println("----TIPOS DE HOSPEDAGEM E SERVIÇOS-----");
                        System.out.println("1-Cadastrar Quarto");
                        System.out.println("2-Cadastrar Apartamento");
                        System.out.println("3-Cadastrar Cabana");
                        System.out.println("4-Remover Hospedagem");
                        System.out.println("5-Gerenciar Serviços Adicionais");
                        System.out.println("0-Voltar ao menu principal");
                        System.out.println("Insira a opção desejada:");

                        while (!sc.hasNextInt()) {
                            sc.next();
                            System.out.println("Insira a opção desejada:");
                        }
                        escolhaDeHospedagem = sc.nextInt();
                        sc.nextLine();

                        switch (escolhaDeHospedagem) {
                            case 1:
                                boolean cadastroQuartoSucesso = false;
                                while (!cadastroQuartoSucesso) {
                                    try {
                                        System.out.println("Número do quarto:");
                                        String numeroQuarto = sc.nextLine();

                                        // Valores fixos para quarto
                                        int capacidadeQuarto = 4; // Capacidade fixa de 4 pessoas
                                        double diariaQuarto = 200.00; // Diária fixa de R$ 200

                                        gerenciadorHospedagens.cadastrarQuarto(numeroQuarto, capacidadeQuarto, diariaQuarto);
                                        System.out.println("Quarto cadastrado com sucesso!");
                                        System.out.println("Capacidade: " + capacidadeQuarto + " pessoas");
                                        System.out.println("Diária: R$ " + diariaQuarto);
                                        cadastroQuartoSucesso = true;
                                    } catch (Exception e) {
                                        System.out.println("Erro: " + e.getMessage());
                                        System.out.println("Deseja tentar novamente? (S/N)");
                                        String resposta = sc.nextLine();
                                        if (!resposta.equalsIgnoreCase("S")) {
                                            break;
                                        }
                                    }
                                }
                                break;

                            case 2:
                                boolean cadastroAptoSucesso = false;
                                while (!cadastroAptoSucesso) {
                                    try {
                                        System.out.println("Número do apartamento:");
                                        String numeroApto = sc.nextLine();
                                        System.out.println("Capacidade:");
                                        int capacidadeApto = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("Diária:");
                                        double diariaApto = sc.nextDouble();
                                        sc.nextLine();
                                        System.out.println("Número de quartos:");
                                        int numeroQuartos = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("Possui cozinha? (S/N):");
                                        boolean possuiCozinha = sc.nextLine().equalsIgnoreCase("S");
                                        System.out.println("Andar:");
                                        int andar = sc.nextInt();
                                        sc.nextLine();

                                        gerenciadorHospedagens.cadastrarApartamento(numeroApto, capacidadeApto, diariaApto, numeroQuartos, possuiCozinha, andar);
                                        System.out.println("\nApartamento cadastrado com sucesso!");
                                        System.out.println("=== DETALHES DO APARTAMENTO ===");
                                        System.out.println("Número: " + numeroApto);
                                        System.out.println("Capacidade: " + capacidadeApto + " pessoas");
                                        System.out.println("Diária: R$ " + String.format("%.2f", diariaApto));
                                        System.out.println("Número de quartos: " + numeroQuartos);
                                        System.out.println("Possui cozinha: " + (possuiCozinha ? "Sim" : "Não"));
                                        System.out.println("Andar: " + andar);
                                        System.out.println("----------------------------");
                                        cadastroAptoSucesso = true;
                                    } catch (Exception e) {
                                        System.out.println("Erro: " + e.getMessage());
                                        System.out.println("Deseja tentar novamente? (S/N)");
                                        String resposta = sc.nextLine();
                                        if (!resposta.equalsIgnoreCase("S")) {
                                            break;
                                        }
                                    }
                                }
                                break;

                            case 3:
                                boolean cadastroCabanaSucesso = false;
                                while (!cadastroCabanaSucesso) {
                                    try {
                                        System.out.println("Número da cabana:");
                                        String numeroCabana = sc.nextLine();
                                        System.out.println("Capacidade:");
                                        int capacidadeCabana = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("Diária:");
                                        double diariaCabana = sc.nextDouble();
                                        sc.nextLine();
                                        System.out.println("Possui lareira? (S/N):");
                                        boolean possuiLareira = sc.nextLine().equalsIgnoreCase("S");
                                        System.out.println("Possui vista para o mar? (S/N):");
                                        boolean possuiVistaMar = sc.nextLine().equalsIgnoreCase("S");

                                        gerenciadorHospedagens.cadastrarCabana(numeroCabana, capacidadeCabana, diariaCabana, possuiLareira, possuiVistaMar);
                                        System.out.println("\nCabana cadastrada com sucesso!");
                                        System.out.println("=== DETALHES DA CABANA ===");
                                        System.out.println("Número: " + numeroCabana);
                                        System.out.println("Capacidade: " + capacidadeCabana + " pessoas");
                                        System.out.println("Diária: R$ " + String.format("%.2f", diariaCabana));
                                        System.out.println("Possui lareira: " + (possuiLareira ? "Sim" : "Não"));
                                        System.out.println("Possui vista para o mar: " + (possuiVistaMar ? "Sim" : "Não"));
                                        System.out.println("----------------------------");
                                        cadastroCabanaSucesso = true;
                                    } catch (Exception e) {
                                        System.out.println("Erro: " + e.getMessage());
                                        System.out.println("Deseja tentar novamente? (S/N)");
                                        String resposta = sc.nextLine();
                                        if (!resposta.equalsIgnoreCase("S")) {
                                            break;
                                        }
                                    }
                                }
                                break;

                            case 4:
                                boolean removerHospedagemSucesso = false;
                                while (!removerHospedagemSucesso) {
                                    try {
                                        System.out.println("ID da hospedagem a remover:");
                                        String idHospedagem = sc.nextLine();
                                        gerenciadorHospedagens.removerHospedagem(idHospedagem);
                                        System.out.println("Hospedagem removida com sucesso!");
                                        removerHospedagemSucesso = true;
                                    } catch (Exception e) {
                                        System.out.println("Erro: " + e.getMessage());
                                        System.out.println("Deseja tentar novamente? (S/N)");
                                        String resposta = sc.nextLine();
                                        if (!resposta.equalsIgnoreCase("S")) {
                                            break;
                                        }
                                    }
                                }
                                break;

                            case 5:
                                int opcaoServico;
                                do {
                                    System.out.println("\n=== GERENCIAR SERVIÇOS ADICIONAIS ===");
                                    System.out.println("1-Cadastrar Serviço");
                                    System.out.println("2-Listar Serviços");
                                    System.out.println("3-Remover Serviço");
                                    System.out.println("0-Voltar");
                                    System.out.println("Escolha uma opção:");
                                    
                                    opcaoServico = sc.nextInt();
                                    sc.nextLine();

                                    switch (opcaoServico) {
                                        case 1:
                                            boolean cadastroServicoSucesso = false;
                                            while (!cadastroServicoSucesso) {
                                                try {
                                                    System.out.println("\n=== SERVIÇOS PRÉ-ESTABELECIDOS ===");
                                                    System.out.println("1-Passeio Turístico (R$ 150,00)");
                                                    System.out.println("2-Transfer Aeroporto (R$ 200,00)");
                                                    System.out.println("3-Transfer Rodoviária (R$ 150,00)");
                                                    System.out.println("4-Lavanderia (R$ 80,00)");
                                                    System.out.println("Escolha o serviço:");
                                                    
                                                    int escolhaServico = sc.nextInt();
                                                    sc.nextLine();
                                                    
                                                    String nome, id;
                                                    double preco;
                                                    ServicoAdicional.TipoServico tipo;
                                                    
                                                    switch (escolhaServico) {
                                                        case 1:
                                                            nome = "Passeio Turístico";
                                                            preco = 150.00;
                                                            tipo = ServicoAdicional.TipoServico.PASSEIO_TURISTICO;
                                                            id = "SERV001";
                                                            break;
                                                        case 2:
                                                            nome = "Transfer Aeroporto";
                                                            preco = 200.00;
                                                            tipo = ServicoAdicional.TipoServico.TRANSFER_AEROPORTO;
                                                            id = "SERV002";
                                                            break;
                                                        case 3:
                                                            nome = "Transfer Rodoviária";
                                                            preco = 150.00;
                                                            tipo = ServicoAdicional.TipoServico.TRANSFER_RODOVIARIA;
                                                            id = "SERV003";
                                                            break;
                                                        case 4:
                                                            nome = "Lavanderia";
                                                            preco = 80.00;
                                                            tipo = ServicoAdicional.TipoServico.LAVANDERIA;
                                                            id = "SERV004";
                                                            break;
                                                        default:
                                                            throw new IllegalArgumentException("Opção inválida!");
                                                    }
                                                    
                                                    ServicoAdicional servico = new ServicoAdicional(nome, "", preco, tipo);
                                                    servico.setId(id);
                                                    repositorioServicosAdicionais.salvar(servico);
                                                    System.out.println("Serviço cadastrado com sucesso!");
                                                    System.out.println("ID do serviço: " + id);
                                                    cadastroServicoSucesso = true;
                                                } catch (Exception e) {
                                                    System.out.println("Erro: " + e.getMessage());
                                                    System.out.println("Deseja tentar novamente? (S/N)");
                                                    String resposta = sc.nextLine();
                                                    if (!resposta.equalsIgnoreCase("S")) {
                                                        break;
                                                    }
                                                }
                                            }
                                            break;
                                        
                                        case 2:
                                            System.out.println("\nServiços disponíveis:");
                                            List<ServicoAdicional> servicos = gerenciadorServicosAdicionais.listarServicosDisponiveis();
                                            for (ServicoAdicional s : servicos) {
                                                System.out.println("\nID: " + s.getId());
                                                System.out.println("Nome: " + s.getNome());
                                                System.out.println("Preço: R$ " + String.format("%.2f", s.getPreco()));
                                                System.out.println("----------------------------");
                                            }
                                            break;
                                        
                                        case 3:
                                            boolean removerServicoSucesso = false;
                                            while (!removerServicoSucesso) {
                                                try {
                                                    System.out.println("ID do serviço a remover:");
                                                    String idServico = sc.nextLine();
                                                    gerenciadorServicosAdicionais.cancelarServico(idServico);
                                                    System.out.println("Serviço removido com sucesso!");
                                                    removerServicoSucesso = true;
                                                } catch (Exception e) {
                                                    System.out.println("Erro: " + e.getMessage());
                                                    System.out.println("Deseja tentar novamente? (S/N)");
                                                    String resposta = sc.nextLine();
                                                    if (!resposta.equalsIgnoreCase("S")) {
                                                        break;
                                                    }
                                                }
                                            }
                                            break;
                                        
                                        case 0:
                                            break;
                                        
                                        default:
                                            System.out.println("Opção inválida!");
                                    }
                                } while (opcaoServico != 0);
                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("Opção inválida! Tente novamente.");
                        }
                    } while (escolhaDeHospedagem != 0);
                    break;

                case 4:
                    System.out.println("--- REALIZAR RESERVA ---");
                    System.out.println("1-Reserva de Hospedagem");
                    System.out.println("2-Reserva de Serviço Adicional");
                    System.out.println("Escolha uma opção:");
                    int escolhaReserva = sc.nextInt();
                    sc.nextLine();

                    switch (escolhaReserva) {
                        case 1:
                            boolean reservaSucesso = false;
                            while (!reservaSucesso) {
                                try {
                                    System.out.println("Clientes cadastrados:");
                                    gerenciadorClientes.listarTodos();

                                    System.out.println("Informe o CPF do cliente:");
                                    String cpf = sc.nextLine();
                                    
                                    // Verificar se o cliente existe
                                    Cliente cliente = gerenciadorClientes.buscarPorCpf(cpf);
                                    if (cliente == null) {
                                        System.out.println("Cliente não encontrado. Retornando ao menu principal...");
                                        break;
                                    }
                                    
                                    System.out.println("\nCliente encontrado: " + cliente.getNome());
                                    System.out.println("\nHospedagens disponíveis:");
                                    List<Hospedagem> hospedagensDisponiveis = gerenciadorHospedagens.listarDisponiveis();
                                    if (hospedagensDisponiveis.isEmpty()) {
                                        throw new IllegalArgumentException("Não há hospedagens disponíveis no momento.");
                                    }
                                    
                                    for (Hospedagem h : hospedagensDisponiveis) {
                                        System.out.println("\nID: " + h.getIdentificacao());
                                        System.out.println("Tipo: " + h.getClass().getSimpleName());
                                        System.out.println("Capacidade: " + h.getCapacidadeMaxima() + " pessoas");
                                        System.out.println("Diária: R$ " + String.format("%.2f", h.getPrecoDiaria()));
                                        System.out.println("----------------------------");
                                    }

                                    System.out.println("\nInforme o ID da hospedagem que deseja reservar:");
                                    String idHospedagem = sc.nextLine();

                                    // Check-in
                                    System.out.println("\n--- Data de Check-in ---");
                                    System.out.println("Dia (1-31):");
                                    int diaCheckIn = sc.nextInt();
                                    if (diaCheckIn < 1 || diaCheckIn > 31) {
                                        throw new IllegalArgumentException("Dia inválido. Deve estar entre 1 e 31.");
                                    }

                                    System.out.println("Mês (1-12):");
                                    int mesCheckIn = sc.nextInt();
                                    if (mesCheckIn < 1 || mesCheckIn > 12) {
                                        throw new IllegalArgumentException("Mês inválido. Deve estar entre 1 e 12.");
                                    }

                                    System.out.println("Ano (2024 ou posterior):");
                                    int anoCheckIn = sc.nextInt();
                                    if (anoCheckIn < 2024) {
                                        throw new IllegalArgumentException("Ano inválido. Deve ser 2024 ou posterior.");
                                    }

                                    // Criar data de check-in para validação
                                    LocalDate dataCheckIn = LocalDate.of(anoCheckIn, mesCheckIn, diaCheckIn);
                                    LocalDate hoje = LocalDate.now();
                                    if (dataCheckIn.isBefore(hoje)) {
                                        throw new IllegalArgumentException("A data de check-in não pode ser anterior à data atual.");
                                    }

                                    // Check-out
                                    System.out.println("\n--- Data de Check-out ---");
                                    System.out.println("Dia (1-31):");
                                    int diaCheckOut = sc.nextInt();
                                    if (diaCheckOut < 1 || diaCheckOut > 31) {
                                        throw new IllegalArgumentException("Dia inválido. Deve estar entre 1 e 31.");
                                    }

                                    System.out.println("Mês (1-12):");
                                    int mesCheckOut = sc.nextInt();
                                    if (mesCheckOut < 1 || mesCheckOut > 12) {
                                        throw new IllegalArgumentException("Mês inválido. Deve estar entre 1 e 12.");
                                    }

                                    System.out.println("Ano (2024 ou posterior):");
                                    int anoCheckOut = sc.nextInt();
                                    if (anoCheckOut < 2024) {
                                        throw new IllegalArgumentException("Ano inválido. Deve ser 2024 ou posterior.");
                                    }
                                    sc.nextLine(); // Limpar o buffer

                                    // Criar data de check-out e validar
                                    LocalDate dataCheckOut = LocalDate.of(anoCheckOut, mesCheckOut, diaCheckOut);
                                    
                                    // Validar se check-out é depois do check-in
                                    if (dataCheckOut.isBefore(dataCheckIn) || dataCheckOut.isEqual(dataCheckIn)) {
                                        throw new IllegalArgumentException("A data de check-out deve ser pelo menos um dia após a data de check-in.");
                                    }

                                    // Converter para LocalDateTime com horários específicos
                                    LocalDateTime checkIn = dataCheckIn.atTime(14, 0);
                                    LocalDateTime checkOut = dataCheckOut.atTime(12, 0);

                                    Reserva reserva = gerenciadorReservas.criarReserva(cpf, idHospedagem, checkIn, checkOut);
                                    System.out.println("Reserva realizada com sucesso!");
                                    System.out.println("ID da reserva: " + reserva.getId());
                                    reservaSucesso = true;
                                } catch (Exception e) {
                                    System.out.println("Erro: " + e.getMessage());
                                    System.out.println("Deseja tentar novamente? (S/N)");
                                    String resposta = sc.nextLine();
                                    if (!resposta.equalsIgnoreCase("S")) {
                                        break;
                                    }
                                }
                            }
                            break;

                        case 2:
                            boolean servicoSucesso = false;
                            while (!servicoSucesso) {
                                try {
                                    System.out.println("\n=== RESERVA DE SERVIÇO ADICIONAL ===");
                                    
                                    System.out.println("\nServiços disponíveis:");
                                    gerenciadorServicosAdicionais.listarServicosDisponiveis();
                                    
                                    System.out.println("\nID do serviço:");
                                    String idServico = sc.nextLine();
                                    
                                    System.out.println("ID da reserva:");
                                    String idReserva = sc.nextLine();
                                    
                                    // Data do serviço
                                    System.out.println("\n--- Data do Serviço ---");
                                    System.out.println("Dia (1-31):");
                                    int diaServico = sc.nextInt();
                                    if (diaServico < 1 || diaServico > 31) {
                                        throw new IllegalArgumentException("Dia inválido. Deve estar entre 1 e 31.");
                                    }

                                    System.out.println("Mês (1-12):");
                                    int mesServico = sc.nextInt();
                                    if (mesServico < 1 || mesServico > 12) {
                                        throw new IllegalArgumentException("Mês inválido. Deve estar entre 1 e 12.");
                                    }

                                    System.out.println("Ano (2024 ou posterior):");
                                    int anoServico = sc.nextInt();
                                    if (anoServico < 2024) {
                                        throw new IllegalArgumentException("Ano inválido. Deve ser 2024 ou posterior.");
                                    }
                                    sc.nextLine(); // Limpar o buffer
                                    
                                    LocalDate dataServico = LocalDate.of(anoServico, mesServico, diaServico);
                                    LocalDateTime dataHora = dataServico.atTime(10, 0);
                                    
                                    Reserva reserva = gerenciadorReservas.buscarPorId(idReserva);
                                    if (reserva == null) {
                                        throw new IllegalArgumentException("Reserva não encontrada.");
                                    }
                                    
                                    gerenciadorServicosAdicionais.reservarServico(reserva, idServico, dataHora);
                                    System.out.println("Serviço reservado com sucesso!");
                                    servicoSucesso = true;
                                } catch (Exception e) {
                                    System.out.println("Erro: " + e.getMessage());
                                    System.out.println("Deseja tentar novamente? (S/N)");
                                    String resposta = sc.nextLine();
                                    if (!resposta.equalsIgnoreCase("S")) {
                                        break;
                                    }
                                }
                            }
                            break;

                        default:
                            System.out.println("Opção inválida!");
                    }
                    break;

                case 5:
                    System.out.println("--- CONSULTA DE DISPONIBILIDADE ---");
                    System.out.println("1-Consultar Hospedagens Disponíveis");
                    System.out.println("2-Consultar Serviços Disponíveis");
                    System.out.println("Escolha uma opção:");
                    int escolhaConsulta = sc.nextInt();
                    sc.nextLine();

                    switch (escolhaConsulta) {
                        case 1:
                            System.out.println("\nHospedagens disponíveis:");
                            gerenciadorHospedagens.listarDisponiveis();
                            break;
                        case 2:
                            System.out.println("\nServiços disponíveis:");
                            gerenciadorServicosAdicionais.listarServicosDisponiveis();
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                    break;

                case 6:
                    boolean checkOutSucesso = false;
                    while (!checkOutSucesso) {
                        try {
                            System.out.println("\n=== REALIZAR CHECK-OUT ===");
                            System.out.println("ID da reserva: ");
                            String reservaIdCheckOut = sc.nextLine();
                            
                            gerenciadorReservas.realizarCheckOut(reservaIdCheckOut);
                            System.out.println("Check-out realizado com sucesso!");
                            checkOutSucesso = true;
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Deseja tentar novamente? (S/N)");
                            String resposta = sc.nextLine();
                            if (!resposta.equalsIgnoreCase("S")) {
                                break;
                            }
                        }
                    }
                    break;

                case 7:
                    System.out.println("\n=== CANCELAR RESERVA ===");
                    System.out.print("ID da reserva: ");
                    String reservaIdCancelar = sc.nextLine();
                    
                    gerenciadorReservas.cancelarReserva(reservaIdCancelar);
                    System.out.println("Reserva cancelada com sucesso!");
                    break;

                case 8:
                    System.out.println("\n=== REALIZAR CHECK-IN ===");
                    System.out.print("ID da reserva: ");
                    String reservaIdCheckIn = sc.nextLine();
                    
                    gerenciadorReservas.realizarCheckIn(reservaIdCheckIn);
                    System.out.println("Check-in realizado com sucesso!");
                    break;

                case 9:
                    System.out.println("\n=== RELATÓRIOS ===");
                    System.out.println("1-Relatório de Reservas Ativas");
                    System.out.println("2-Relatório de Ocupação");
                    System.out.println("3-Relatório Financeiro");
                    System.out.println("4-Histórico de Cliente");
                    System.out.println("Escolha uma opção:");
                    int opcaoRelatorio = sc.nextInt();
                    sc.nextLine();

                    switch (opcaoRelatorio) {
                        case 1:
                            System.out.println("\nRelatório de Reservas Ativas:");
                            List<Reserva> reservasAtivas = gerenciadorReservas.listarReservasAtivas();
                            if (reservasAtivas.isEmpty()) {
                                System.out.println("Não há reservas ativas no momento.");
                            } else {
                                for (Reserva r : reservasAtivas) {
                                    System.out.println("\n=== DETALHES DA RESERVA ===");
                                    System.out.println("ID: " + r.getId());
                                    System.out.println("Cliente: " + r.getCliente().getNome());
                                    System.out.println("CPF: " + r.getCliente().getCpf());
                                    System.out.println("Telefone: " + r.getCliente().getTelefone());
                                    System.out.println("\nHospedagem: " + r.getHospedagem().getIdentificacao());
                                    System.out.println("Check-in: " + r.getDataCheckIn().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                                    System.out.println("Check-out: " + r.getDataCheckOut().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                                    System.out.println("Valor Total: R$ " + String.format("%.2f", r.getValorTotal()));
                                    System.out.println("Status: " + r.getStatus());
                                    System.out.println("----------------------------");
                                }
                            }
                            break;

                        case 2:
                            System.out.println("\nRelatório de Ocupação:");
                            List<Hospedagem> todasHospedagens = gerenciadorHospedagens.listarTodas();
                            long totalHospedagens = todasHospedagens.size();
                            long hospedagensOcupadas = todasHospedagens.stream()
                                .filter(h -> !h.isDisponivel())
                                .count();
                            
                            System.out.println("Total de Unidades: " + totalHospedagens);
                            System.out.println("Unidades Ocupadas: " + hospedagensOcupadas);
                            System.out.println("Unidades Disponíveis: " + (totalHospedagens - hospedagensOcupadas));
                            if (totalHospedagens > 0) {
                                double taxaOcupacao = (hospedagensOcupadas * 100.0) / totalHospedagens;
                                System.out.println("Taxa de Ocupação: " + String.format("%.2f%%", taxaOcupacao));
                            }
                            break;

                        case 3:
                            System.out.println("\nRelatório Financeiro:");
                            double totalReceita = 0.0;
                            int totalReservas = 0;
                            List<Reserva> todasReservas = gerenciadorReservas.listarReservasAtivas();
                            
                            for (Reserva r : todasReservas) {
                                totalReceita += r.getValorTotal();
                                totalReservas++;
                            }
                            
                            System.out.println("Total de Reservas: " + totalReservas);
                            System.out.println("Receita Total: R$ " + String.format("%.2f", totalReceita));
                            if (totalReservas > 0) {
                                System.out.println("Média por Reserva: R$ " + String.format("%.2f", totalReceita / totalReservas));
                            }
                            break;

                        case 4:
                            System.out.println("\nHistórico de Cliente");
                            System.out.println("Digite o CPF do cliente:");
                            String cpfHistorico = sc.nextLine();
                            
                            Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfHistorico);
                            if (cliente != null) {
                                System.out.println("\nCliente: " + cliente.getNome());
                                System.out.println("CPF: " + cliente.getCpf());
                                System.out.println("Telefone: " + cliente.getTelefone());
                                System.out.println("\nHistórico de Reservas:");
                                
                                List<Reserva> reservasCliente = gerenciadorReservas.buscarReservasPorCliente(cpfHistorico);
                                for (Reserva r : reservasCliente) {
                                    System.out.println("\nReserva ID: " + r.getId());
                                    System.out.println("Status: " + r.getStatus());
                                    System.out.println("Check-in: " + r.getDataCheckIn());
                                    System.out.println("Check-out: " + r.getDataCheckOut());
                                    System.out.println("Valor: R$ " + String.format("%.2f", r.getValorTotal()));
                                    System.out.println("----------------------------");
                                }
                            } else {
                                System.out.println("Cliente não encontrado.");
                            }
                            break;

                        default:
                            System.out.println("Opção inválida!");
                    }
                    break;

                case 10:
                    System.out.println("\nReservas ativas:");
                    gerenciadorReservas.listarReservasAtivas();
                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção Inválida");
            }
        } while (opcaoUsuario != 0);

        sc.close();
    }
}