# Padr-es-de-projeto-com-Java-puro-desafio-DIO-

Este projeto foi feito como um desafio: criar uma solução em Java que implementasse ideias de Design Patterns do GoF. Dos padrões que existem, eu escolhi três para focar: Singleton, Strategy e Facade.

Como contexto, eu reaproveitei um exercício de conta bancária com cheque especial que eu já tinha feito. Ele já tinha regras suficientes para os três padrões aparecerem de forma natural, sem eu precisar forçar nenhum deles. A ideia aqui não é ter um sistema bancário completo, e sim praticar os padrões e entender qual problema cada um resolve.

## O que o programa faz

É um programa de console com um menu onde dá pra:
Consultar saldo
Consultar cheque especial
Depositar dinheiro
Sacar dinheiro
Pagar um boleto
Transferir valor de uma conta para outra
Verificar se a conta está usando cheque especial
Encerrar o programa

Quando o programa inicia, ele já abre duas contas para teste, um com o tipo de conta especial, e outro normal.

## Regras do cheque especial

O dinheiro que dá pra gastar não é só o saldo, e sim o saldo + o limite do cheque especial. Se o saldo fica negativo, a conta está usando o cheque especial. O limite é definido na criação da conta, a partir do saldo inicial, e a regra muda conforme o tipo da conta.

No tipo standard, se o depósito inicial for um valor menor ou igual 500, o limite será de R$ 50. Mas, acima disso, o limite é 50% do valor do depósito inicial. Além disso, a taxa do valor do limite usado é 20%. 

No tipo premium o limite será 100% do valor depositado e a taxa de uso do limite é de 10%.

A taxa é cobrada num depósito, quando a conta está usando o cheque especial. Por exemplo, na conta 1 (Standard, R$ 1500), se eu sacar R$ 2000, o saldo vai para -R$ 500. Se eu depositar R$ 800 em seguida, o saldo vai a R$ 300 e a taxa de R$ 100 (20% de R$ 500) é descontada, ficando com R$ 200.

## Os padrões
### Singleton: Bank

O programa só precisa de um banco, com um único cadastro de contas. Eu usei o lazy holder: a instância fica dentro de uma classe interna estática (Holder), então ela só é criada na primeira vez que o getInstance() é chamado.

### Strategy: OverdraftPolicy
Existe uma interface OverdraftPolicy, com dois métodos: limitFor (o limite concedido na abertura) e feeFor (a taxa sobre o valor usado). A conta recebe a política pelo construtor e só usa, sem saber qual é. Atualmente existem duas, StandardOverdraftPolicy e a política Premium, podendo ser criado uma nova política sem precisar alterar uma linha da NBankAccount.

### Facade: BankFacade

Sem a facade, o Main teria que lidar com o Bank, buscar as contas, escolher a política de cada tipo e coordenar as operações. A BankFacade esconde isso e oferece métodos simples: openAccount, deposit, withdraw, payBill, transfer e as consultas.

O transfer é o melhor exemplo: ele valida que as contas são diferentes, busca a conta de origem e a de destino, saca de uma e deposita na outra. Quem chama só vê uma linha. O Main só conversa com a facade.

A facade não tem regra de negócio. Cheque especial, limite e taxa continuam na conta e na política, e ela apenas coordena.

## Exceções

As exceções do projeto são todas checked, e eu fiz assim de propósito: eu queria forçar quem usa o código a tratar os erros com try/catch, em vez de poder ignorar sem querer. Existe uma exceção base, a BankException, e três filhas:

* **InvalidAmountException**: valor inválido (por exemplo, depósito ou saque menor que 1)

* **InsufficientFundsException**: o valor passa do saldo disponível (saldo + cheque especial)

* **AccountNotFoundException**: o ID não corresponde a nenhuma conta

Como todas herdam da BankException, o Main consegue tratar qualquer erro do banco com um único catch.

## Anedotas:
* Eu recomendo tentar rodar o projeto no IntelliJ, pois foi a IDE que eu utilizei, com a versão 27 do Java.

* A entrada do console não é validada. Se eu digitar uma letra onde o nextInt() espera um número, o programa quebra.

* Uso double para dinheiro. Em um sistema de verdade o certo seria BigDecimal, por causa dos erros de arredondamento do ponto flutuante.

* Se o saldo inicial for negativo, a conta começa zerada.

* Nada é salvo. As contas existem só em memória enquanto o programa roda.
