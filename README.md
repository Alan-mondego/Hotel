# Sistema de Gerenciamento de Reservas e Hospedagem

Este é um sistema de gerenciamento de reservas e hospedagem desenvolvido em Java, utilizando os princípios de Programação Orientada a Objetos (POO).

## Funcionalidades

- Cadastro de clientes, funcionários, hospedagens e serviços adicionais
- Gerenciamento de reservas (criação, consulta e cancelamento)
- Check-in e check-out
- Geração de relatórios (reservas ativas, ocupação, financeiro e histórico de cliente)
- Tratamento de exceções personalizadas

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

- `entidades`: Classes que representam as entidades do sistema
  - Cliente
  - Funcionario
  - Hospedagem (e suas implementações: Quarto, Cabana, Apartamento)
  - Reserva
  - ServicoAdicional

- `servicos`: Classes que implementam a lógica de negócio
  - ServicoReserva
  - ServicoRelatorio

- `excecoes`: Classes de exceções personalizadas
  - HotelException

## Regras de Negócio

- Reservas de serviços adicionais só são permitidas com uma reserva ativa de hospedagem
- Cancelamento de reservas até 24 horas antes do check-in
- Check-in a partir das 14h e check-out até as 12h
- Cobrança baseada no número de diárias e preço da hospedagem
- Multa para cancelamentos tardios ou "no-show"

## Como Executar

1. Clone o repositório
2. Abra o projeto no IntelliJ IDEA
3. Execute a classe `Main` para ver um exemplo de uso do sistema

## Requisitos

- Java 8 ou superior
- IntelliJ IDEA (recomendado)

## Exemplo de Uso

```java
// Inicialização dos serviços
ServicoReserva servicoReserva = new ServicoReserva();
ServicoRelatorio servicoRelatorio = new ServicoRelatorio(servicoReserva);

// Cadastro de cliente
Cliente cliente = new Cliente("João Silva", "123.456.789-00", "(11) 99999-9999");
servicoReserva.cadastrarCliente(cliente);

// Cadastro de hospedagem
Quarto quarto = new Quarto("101", 2, new BigDecimal("200.00"), Quarto.TipoQuarto.STANDARD);
servicoReserva.cadastrarHospedagem(quarto);

// Criação de reserva
LocalDateTime checkIn = LocalDateTime.of(2024, 3, 20, 14, 0);
LocalDateTime checkOut = LocalDateTime.of(2024, 3, 22, 12, 0);
Reserva reserva = servicoReserva.criarReserva(cliente, quarto, checkIn, checkOut);
```

## Contribuição

Sinta-se à vontade para contribuir com o projeto através de pull requests ou reportando issues. 