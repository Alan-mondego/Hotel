//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import entidades.*;
import servicos.*;
import repositorio.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.ArrayList;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    private static RepositorioClientes repositorioClientes;
    private static RepositorioHospedagens repositorioHospedagens;
    private static RepositorioReservas repositorioReservas;
    private static RepositorioFuncionarios repositorioFuncionarios;
    private static RepositorioServicosAdicionais repositorioServicosAdicionais;
    private static RepositorioReservasServico repositorioReservasServico;
    
    private static GerenciadorClientes gerenciadorClientes;
    private static GerenciadorHospedagens gerenciadorHospedagens;
    private static GerenciadorReservas gerenciadorReservas;
    private static GerenciadorFuncionarios gerenciadorFuncionarios;
    private static GerenciadorServicosAdicionais gerenciadorServicosAdicionais;

    public static void main(String[] args) {
        inicializarSistema();
        
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            try {
                switch (opcao) {
                    case 1:
                        cadastrarCliente();
                        break;
                    case 2:
                        cadastrarFuncionario();
                        break;
                    case 3:
                        menuHospedagensEServicos();
                        break;
                    case 4:
                        realizarReserva();
                        break;
                    case 5:
                        consultarDisponibilidade();
                        break;
                    case 6:
                        cancelarReserva();
                        break;
                    case 7:
                        realizarCheckIn();
                        break;
                    case 8:
                        realizarCheckOut();
                        break;
                    case 9:
                        menuRelatorios();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
            
            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        } while (opcao != 0);
        
        scanner.close();
    }

    private static void inicializarSistema() {
        repositorioClientes = new RepositorioClientes();
        repositorioHospedagens = new RepositorioHospedagens();
        repositorioReservas = new RepositorioReservas();
        repositorioFuncionarios = new RepositorioFuncionarios();
        repositorioServicosAdicionais = new RepositorioServicosAdicionais();
        repositorioReservasServico = new RepositorioReservasServico();

        gerenciadorClientes = new GerenciadorClientes(repositorioClientes);
        gerenciadorHospedagens = new GerenciadorHospedagens(repositorioHospedagens);
        gerenciadorReservas = new GerenciadorReservas(
            repositorioReservas, repositorioHospedagens, repositorioClientes);
        gerenciadorFuncionarios = new GerenciadorFuncionarios(repositorioFuncionarios);
        gerenciadorServicosAdicionais = new GerenciadorServicosAdicionais(
            repositorioServicosAdicionais, repositorioReservasServico);
    }

    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE HOSPEDAGEM ===");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Cadastrar Funcionário");
        System.out.println("3. Gerenciar Hospedagens e Serviços");
        System.out.println("4. Realizar Reserva");
        System.out.println("5. Consultar Disponibilidade");
        System.out.println("6. Cancelar Reserva");
        System.out.println("7. Realizar Check-in");
        System.out.println("8. Realizar Check-out");
        System.out.println("9. Relatórios");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarFuncionario() {
        System.out.println("\n=== CADASTRO DE FUNCIONÁRIO ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.println("Cargo:");
        System.out.println("1. RECEPCIONISTA");
        System.out.println("2. GERENTE");
        System.out.println("3. ADMINISTRADOR");
        System.out.print("Escolha: ");
        int cargoEscolha = scanner.nextInt();
        scanner.nextLine();
        
        String cargo;
        switch (cargoEscolha) {
            case 1:
                cargo = "RECEPCIONISTA";
                break;
            case 2:
                cargo = "GERENTE";
                break;
            case 3:
                cargo = "ADMINISTRADOR";
                break;
            default:
                cargo = "RECEPCIONISTA";
        }
        
        gerenciadorFuncionarios.cadastrarFuncionario(nome, cargo);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void cadastrarCliente() {
        if (!gerenciadorFuncionarios.existeAlgumFuncionario()) {
            System.out.println("Não é possível cadastrar clientes sem ter pelo menos um funcionário cadastrado!");
            System.out.println("Por favor, cadastre um funcionário primeiro.");
            return;
        }

        System.out.println("\n=== CADASTRO DE CLIENTE ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        gerenciadorClientes.cadastrarCliente(nome, cpf, telefone);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void menuHospedagensEServicos() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE HOSPEDAGENS E SERVIÇOS ===");
            System.out.println("1. Cadastrar Quarto");
            System.out.println("2. Cadastrar Cabana");
            System.out.println("3. Cadastrar Apartamento");
            System.out.println("4. Listar Todas as Hospedagens");
            System.out.println("5. Gerenciar Serviços Adicionais");
            System.out.println("0. Voltar");
            
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        cadastrarQuarto();
                        break;
                    case 2:
                        cadastrarCabana();
                        break;
                    case 3:
                        cadastrarApartamento();
                        break;
                    case 4:
                        listarHospedagens();
                        break;
                    case 5:
                        menuServicosAdicionais();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }

    private static void cadastrarQuarto() {
        System.out.println("\n=== CADASTRO DE QUARTO ===");
        System.out.print("Número do quarto: ");
        String identificacao = scanner.nextLine();
        
        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Preço da diária: R$ ");
        BigDecimal preco = scanner.nextBigDecimal();
        scanner.nextLine();
        
        System.out.println("Tipo do quarto:");
        System.out.println("1. STANDARD");
        System.out.println("2. LUXO");
        System.out.println("3. SUITE_PRESIDENCIAL");
        System.out.print("Escolha: ");
        int tipoEscolha = scanner.nextInt();
        scanner.nextLine();
        
        Quarto.TipoQuarto tipo = Quarto.TipoQuarto.values()[tipoEscolha - 1];
        
        gerenciadorHospedagens.cadastrarQuarto(identificacao, capacidade, preco, tipo);
        System.out.println("Quarto cadastrado com sucesso!");
    }

    private static void cadastrarCabana() {
        System.out.println("\n=== CADASTRO DE CABANA ===");
        System.out.print("Identificação da cabana: ");
        String identificacao = scanner.nextLine();
        
        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Preço da diária: R$ ");
        BigDecimal preco = scanner.nextBigDecimal();
        scanner.nextLine();
        
        System.out.print("Possui lareira? (S/N): ");
        boolean possuiLareira = scanner.next().equalsIgnoreCase("S");
        scanner.nextLine();
        
        System.out.print("Possui vista para o mar? (S/N): ");
        boolean possuiVistaMar = scanner.next().equalsIgnoreCase("S");
        scanner.nextLine();
        
        gerenciadorHospedagens.cadastrarCabana(identificacao, capacidade, preco, 
                                              possuiLareira, possuiVistaMar);
        System.out.println("Cabana cadastrada com sucesso!");
    }

    private static void cadastrarApartamento() {
        System.out.println("\n=== CADASTRO DE APARTAMENTO ===");
        System.out.print("Número do apartamento: ");
        String identificacao = scanner.nextLine();
        
        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Preço da diária: R$ ");
        BigDecimal preco = scanner.nextBigDecimal();
        scanner.nextLine();
        
        System.out.print("Número de quartos: ");
        int numeroQuartos = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Possui cozinha? (S/N): ");
        boolean possuiCozinha = scanner.next().equalsIgnoreCase("S");
        scanner.nextLine();
        
        System.out.print("Andar: ");
        int andar = scanner.nextInt();
        scanner.nextLine();
        
        gerenciadorHospedagens.cadastrarApartamento(identificacao, capacidade, preco,
                                                   numeroQuartos, possuiCozinha, andar);
        System.out.println("Apartamento cadastrado com sucesso!");
    }

    private static void listarHospedagens() {
        System.out.println("\n=== LISTA DE HOSPEDAGENS ===");
        List<Hospedagem> hospedagens = gerenciadorHospedagens.listarTodas();
        
        if (hospedagens.isEmpty()) {
            System.out.println("Nenhuma hospedagem cadastrada.");
            return;
        }

        for (Hospedagem h : hospedagens) {
            System.out.println("\nIdentificação: " + h.getIdentificacao());
            System.out.println("Tipo: " + h.getClass().getSimpleName());
            System.out.println("Capacidade: " + h.getCapacidadeMaxima() + " pessoas");
            System.out.println("Preço: R$ " + h.getPrecoDiaria() + "/dia");
            System.out.println("Disponível: " + (h.isDisponivel() ? "Sim" : "Não"));
            System.out.println("--------------------");
        }
    }

    private static void realizarReserva() {
        System.out.println("\n=== REALIZAR RESERVA ===");
        
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Identificação da hospedagem: ");
        String idHospedagem = scanner.nextLine();
        
        System.out.print("Data de check-in (dd/MM/yyyy): ");
        String dataCheckIn = scanner.nextLine();
        LocalDateTime checkIn = LocalDateTime.parse(dataCheckIn + " 14:00", formatter);
        
        System.out.print("Data de check-out (dd/MM/yyyy): ");
        String dataCheckOut = scanner.nextLine();
        LocalDateTime checkOut = LocalDateTime.parse(dataCheckOut + " 12:00", formatter);
        
        try {
            Reserva reserva = gerenciadorReservas.criarReserva(cpf, idHospedagem, checkIn, checkOut);
            System.out.println("\nReserva criada com sucesso!");
            System.out.println("Número da Reserva: " + reserva.getId());
            System.out.println("Check-in: " + checkIn.format(formatter) + " (14:00)");
            System.out.println("Check-out: " + checkOut.format(formatter) + " (12:00)");
        } catch (Exception e) {
            System.out.println("Erro ao criar reserva: " + e.getMessage());
        }
    }

    private static void consultarDisponibilidade() {
        System.out.println("\n=== HOSPEDAGENS DISPONÍVEIS ===");
        List<Hospedagem> disponiveis = gerenciadorHospedagens.listarDisponiveis();
        
        if (disponiveis.isEmpty()) {
            System.out.println("Nenhuma hospedagem disponível no momento.");
            return;
        }

        for (Hospedagem h : disponiveis) {
            System.out.println("\nIdentificação: " + h.getIdentificacao());
            System.out.println("Tipo: " + h.getClass().getSimpleName());
            System.out.println("Capacidade: " + h.getCapacidadeMaxima() + " pessoas");
            System.out.println("Preço: R$ " + h.getPrecoDiaria() + "/dia");
            System.out.println("--------------------");
        }
    }

    private static void cancelarReserva() {
        System.out.println("\n=== CANCELAR RESERVA ===");
        System.out.print("ID da reserva: ");
        String reservaId = scanner.nextLine();
        
        gerenciadorReservas.cancelarReserva(reservaId);
        System.out.println("Reserva cancelada com sucesso!");
    }

    private static void realizarCheckIn() {
        System.out.println("\n=== REALIZAR CHECK-IN ===");
        System.out.print("ID da reserva: ");
        String reservaId = scanner.nextLine();
        
        gerenciadorReservas.realizarCheckIn(reservaId);
        System.out.println("Check-in realizado com sucesso!");
    }

    private static void realizarCheckOut() {
        System.out.println("\n=== REALIZAR CHECK-OUT ===");
        System.out.print("ID da reserva: ");
        String reservaId = scanner.nextLine();
        
        gerenciadorReservas.realizarCheckOut(reservaId);
        System.out.println("Check-out realizado com sucesso!");
    }

    private static void menuServicosAdicionais() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE SERVIÇOS ADICIONAIS ===");
            System.out.println("1. Cadastrar Novo Serviço");
            System.out.println("2. Listar Serviços Disponíveis");
            System.out.println("3. Reservar Serviço");
            System.out.println("4. Realizar Serviço");
            System.out.println("5. Cancelar Serviço");
            System.out.println("6. Listar Serviços Agendados");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        cadastrarServicoAdicional();
                        break;
                    case 2:
                        listarServicosDisponiveis();
                        break;
                    case 3:
                        reservarServico();
                        break;
                    case 4:
                        realizarServico();
                        break;
                    case 5:
                        cancelarServico();
                        break;
                    case 6:
                        listarServicosAgendados();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void cadastrarServicoAdicional() {
        System.out.println("\n=== CADASTRO DE SERVIÇO ADICIONAL ===");
        System.out.print("Nome do serviço: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Preço: R$ ");
        BigDecimal preco = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.println("Tipo do serviço:");
        System.out.println("1. Passeio Turístico");
        System.out.println("2. Transfer Aeroporto");
        System.out.println("3. Transfer Rodoviária");
        System.out.println("4. Lavanderia");
        System.out.print("Escolha: ");
        int tipoEscolha = scanner.nextInt();
        scanner.nextLine();

        ServicoAdicional.TipoServico tipo;
        switch (tipoEscolha) {
            case 1: tipo = ServicoAdicional.TipoServico.PASSEIO_TURISTICO; break;
            case 2: tipo = ServicoAdicional.TipoServico.TRANSFER_AEROPORTO; break;
            case 3: tipo = ServicoAdicional.TipoServico.TRANSFER_RODOVIARIA; break;
            case 4: tipo = ServicoAdicional.TipoServico.LAVANDERIA; break;
            default: throw new IllegalArgumentException("Tipo de serviço inválido");
        }

        try {
            gerenciadorServicosAdicionais.cadastrarServico(nome, descricao, preco, tipo);
            System.out.println("Serviço cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar serviço: " + e.getMessage());
        }
    }

    private static void listarServicosDisponiveis() {
        System.out.println("\n=== SERVIÇOS DISPONÍVEIS ===");
        List<ServicoAdicional> servicos = gerenciadorServicosAdicionais.listarServicosDisponiveis();
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço disponível.");
            return;
        }

        for (ServicoAdicional servico : servicos) {
            System.out.println("\nID: " + servico.getId());
            System.out.println("Nome: " + servico.getNome());
            System.out.println("Descrição: " + servico.getDescricao());
            System.out.println("Preço: R$ " + servico.getPreco());
            System.out.println("Tipo: " + servico.getTipo());
        }
    }

    private static void reservarServico() {
        System.out.println("\n=== RESERVA DE SERVIÇO ===");
        
        System.out.print("ID da Reserva de Hospedagem: ");
        String idReserva = scanner.nextLine();
        
        System.out.print("ID do Serviço: ");
        String idServico = scanner.nextLine();
        
        System.out.print("Data (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine();
        // Define horário padrão como 10:00
        LocalDateTime dataHora = LocalDateTime.parse(dataStr + " 10:00", formatter);

        try {
            Reserva reserva = gerenciadorReservas.buscarPorId(idReserva);
            if (reserva == null) {
                throw new IllegalArgumentException("Reserva não encontrada");
            }

            ReservaServico reservaServico = gerenciadorServicosAdicionais.reservarServico(reserva, idServico, dataHora);
            System.out.println("Serviço reservado com sucesso! ID: " + reservaServico.getId());
        } catch (Exception e) {
            System.out.println("Erro ao reservar serviço: " + e.getMessage());
        }
    }

    private static void realizarServico() {
        System.out.println("\n=== REALIZAR SERVIÇO ===");
        System.out.print("ID da Reserva do Serviço: ");
        String id = scanner.nextLine();

        try {
            gerenciadorServicosAdicionais.realizarServico(id);
            System.out.println("Serviço realizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao realizar serviço: " + e.getMessage());
        }
    }

    private static void cancelarServico() {
        System.out.println("\n=== CANCELAR SERVIÇO ===");
        System.out.print("ID da Reserva do Serviço: ");
        String id = scanner.nextLine();

        try {
            gerenciadorServicosAdicionais.cancelarServico(id);
            System.out.println("Serviço cancelado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar serviço: " + e.getMessage());
        }
    }

    private static void listarServicosAgendados() {
        System.out.println("\n=== SERVIÇOS AGENDADOS ===");
        List<ReservaServico> servicos = gerenciadorServicosAdicionais.listarServicosAgendados();
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço agendado.");
            return;
        }

        for (ReservaServico reservaServico : servicos) {
            System.out.println("\nID da Reserva do Serviço: " + reservaServico.getId());
            System.out.println("Serviço: " + reservaServico.getServico().getNome());
            System.out.println("Cliente: " + reservaServico.getReserva().getCliente().getNome());
            System.out.println("Data: " + reservaServico.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Valor: R$ " + reservaServico.getValorTotal());
        }
    }

    private static void menuRelatorios() {
        int opcao;
        do {
            System.out.println("\n=== RELATÓRIOS ===");
            System.out.println("1. Relatório de Reservas Ativas");
            System.out.println("2. Relatório de Ocupação");
            System.out.println("3. Relatório de Cancelamentos");
            System.out.println("4. Relatório Financeiro");
            System.out.println("5. Histórico de Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        gerarRelatorioReservasAtivas();
                        break;
                    case 2:
                        gerarRelatorioOcupacao();
                        break;
                    case 3:
                        gerarRelatorioCancelamentos();
                        break;
                    case 4:
                        gerarRelatorioFinanceiro();
                        break;
                    case 5:
                        gerarHistoricoCliente();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }

    private static void gerarRelatorioReservasAtivas() {
        System.out.println("\n=== RELATÓRIO DE RESERVAS ATIVAS ===");
        List<Reserva> reservasAtivas = new ArrayList<>();
        reservasAtivas.addAll(gerenciadorReservas.listarReservasAtivas());
        reservasAtivas.addAll(gerenciadorReservas.listarReservasPendentes());
        
        if (reservasAtivas.isEmpty()) {
            System.out.println("Não há reservas ativas no momento.");
            return;
        }

        // Organizar por tipo de hospedagem
        Map<String, List<Reserva>> reservasPorTipo = reservasAtivas.stream()
            .collect(Collectors.groupingBy(r -> r.getHospedagem().getClass().getSimpleName()));

        for (Map.Entry<String, List<Reserva>> entry : reservasPorTipo.entrySet()) {
            System.out.println("\nTipo de Hospedagem: " + entry.getKey());
            
            // Ordenar por data de check-in
            List<Reserva> reservasOrdenadas = entry.getValue().stream()
                .sorted(Comparator.comparing(Reserva::getDataCheckIn))
                .collect(Collectors.toList());

            for (Reserva reserva : reservasOrdenadas) {
                System.out.println("\nReserva ID: " + reserva.getId());
                System.out.println("Cliente: " + reserva.getCliente().getNome());
                System.out.println("Status: " + reserva.getStatus());
                System.out.println("Check-in: " + reserva.getDataCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("Check-out: " + reserva.getDataCheckOut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("Valor: R$ " + reserva.getValorTotal());
                System.out.println("--------------------");
            }
        }
    }

    private static void gerarRelatorioOcupacao() {
        System.out.println("\n=== RELATÓRIO DE OCUPAÇÃO ===");
        System.out.print("Data inicial (dd/MM/yyyy): ");
        String dataInicialStr = scanner.nextLine();
        System.out.print("Data final (dd/MM/yyyy): ");
        String dataFinalStr = scanner.nextLine();

        LocalDateTime dataInicial = LocalDateTime.parse(dataInicialStr + " 00:00", formatter);
        LocalDateTime dataFinal = LocalDateTime.parse(dataFinalStr + " 23:59", formatter);

        List<Hospedagem> todasHospedagens = gerenciadorHospedagens.listarTodas();
        List<Reserva> reservasPeriodo = gerenciadorReservas.listarReservasAtivas();

        int totalUnidades = todasHospedagens.size();
        if (totalUnidades == 0) {
            System.out.println("Não há unidades de hospedagem cadastradas.");
            return;
        }

        long unidadesOcupadas = reservasPeriodo.stream()
            .filter(r -> !r.getDataCheckIn().isAfter(dataFinal) && !r.getDataCheckOut().isBefore(dataInicial))
            .count();

        double taxaOcupacao = (double) unidadesOcupadas / totalUnidades * 100;

        System.out.println("\nPeríodo: " + dataInicialStr + " a " + dataFinalStr);
        System.out.println("Total de Unidades: " + totalUnidades);
        System.out.println("Unidades Ocupadas: " + unidadesOcupadas);
        System.out.printf("Taxa de Ocupação: %.2f%%\n", taxaOcupacao);
    }

    private static void gerarRelatorioCancelamentos() {
        System.out.println("\n=== RELATÓRIO DE CANCELAMENTOS ===");
        List<Reserva> reservasCanceladas = gerenciadorReservas.listarReservasCanceladas();

        if (reservasCanceladas.isEmpty()) {
            System.out.println("Não há reservas canceladas no período.");
            return;
        }

        System.out.println("\nTotal de Cancelamentos: " + reservasCanceladas.size());
        System.out.println("\nDetalhes dos Cancelamentos:");

        for (Reserva reserva : reservasCanceladas) {
            System.out.println("\nReserva ID: " + reserva.getId());
            System.out.println("Cliente: " + reserva.getCliente().getNome());
            System.out.println("Hospedagem: " + reserva.getHospedagem().getIdentificacao());
            System.out.println("Data Prevista Check-in: " + 
                reserva.getDataCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Valor: R$ " + reserva.getValorTotal());
            System.out.println("--------------------");
        }
    }

    private static void gerarRelatorioFinanceiro() {
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.print("Data inicial (dd/MM/yyyy): ");
        String dataInicialStr = scanner.nextLine();
        System.out.print("Data final (dd/MM/yyyy): ");
        String dataFinalStr = scanner.nextLine();

        LocalDateTime dataInicial = LocalDateTime.parse(dataInicialStr + " 00:00", formatter);
        LocalDateTime dataFinal = LocalDateTime.parse(dataFinalStr + " 23:59", formatter);

        // Faturamento de hospedagens
        List<Reserva> reservasPeriodo = gerenciadorReservas.listarReservasFinalizadas().stream()
            .filter(r -> !r.getDataCheckOut().isBefore(dataInicial) && !r.getDataCheckOut().isAfter(dataFinal))
            .collect(Collectors.toList());

        BigDecimal faturamentoHospedagens = reservasPeriodo.stream()
            .map(Reserva::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Faturamento de serviços adicionais
        List<ReservaServico> servicosPeriodo = gerenciadorServicosAdicionais.listarServicosRealizados().stream()
            .filter(s -> !s.getDataHora().isBefore(dataInicial) && !s.getDataHora().isAfter(dataFinal))
            .collect(Collectors.toList());

        BigDecimal faturamentoServicos = servicosPeriodo.stream()
            .map(ReservaServico::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal faturamentoTotal = faturamentoHospedagens.add(faturamentoServicos);

        System.out.println("\nPeríodo: " + dataInicialStr + " a " + dataFinalStr);
        System.out.println("\nFaturamento de Hospedagens: R$ " + faturamentoHospedagens);
        System.out.println("Faturamento de Serviços: R$ " + faturamentoServicos);
        System.out.println("Faturamento Total: R$ " + faturamentoTotal);
        
        System.out.println("\nDetalhamento das Reservas Finalizadas:");
        for (Reserva reserva : reservasPeriodo) {
            System.out.println("\nReserva ID: " + reserva.getId());
            System.out.println("Cliente: " + reserva.getCliente().getNome());
            System.out.println("Hospedagem: " + reserva.getHospedagem().getIdentificacao());
            System.out.println("Valor: R$ " + reserva.getValorTotal());
        }

        System.out.println("\nDetalhamento dos Serviços Realizados:");
        for (ReservaServico servico : servicosPeriodo) {
            System.out.println("\nServiço ID: " + servico.getId());
            System.out.println("Tipo: " + servico.getServico().getNome());
            System.out.println("Cliente: " + servico.getReserva().getCliente().getNome());
            System.out.println("Valor: R$ " + servico.getValorTotal());
        }
    }

    private static void gerarHistoricoCliente() {
        System.out.println("\n=== HISTÓRICO DE CLIENTE ===");
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();

        List<Reserva> historicoReservas = gerenciadorReservas.buscarReservasPorCliente(cpf);
        if (historicoReservas.isEmpty()) {
            System.out.println("Nenhuma reserva encontrada para este cliente.");
            return;
        }

        Reserva primeiraReserva = historicoReservas.get(0);
        Cliente cliente = primeiraReserva.getCliente();

        System.out.println("\nDados do Cliente:");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Telefone: " + cliente.getTelefone());

        System.out.println("\nHistórico de Reservas:");
        for (Reserva reserva : historicoReservas) {
            System.out.println("\nReserva ID: " + reserva.getId());
            System.out.println("Status: " + reserva.getStatus());
            System.out.println("Hospedagem: " + reserva.getHospedagem().getIdentificacao());
            System.out.println("Check-in: " + reserva.getDataCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Check-out: " + reserva.getDataCheckOut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Valor: R$ " + reserva.getValorTotal());

            // Listar serviços adicionais associados a esta reserva
            List<ReservaServico> servicosReserva = gerenciadorServicosAdicionais.listarServicosPorReserva(reserva);
            if (!servicosReserva.isEmpty()) {
                System.out.println("\nServiços Contratados:");
                for (ReservaServico servico : servicosReserva) {
                    System.out.println("- " + servico.getServico().getNome() + 
                        " (R$ " + servico.getValorTotal() + ") - " + 
                        servico.getStatus());
                }
            }
            System.out.println("--------------------");
        }
    }
}