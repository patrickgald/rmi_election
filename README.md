# Eleições com Java RMI

---

## Instruções
Considere uma interface _Election_ que fornece dois métodos remotos:

- **vote**: este método possui dois parâmetros por meio dos quais o cliente fornece o nome de um candidato (um string) e o “identificador de eleitor” (um hash MD5 usado para garantir que cada usuário vote apenas uma vez).
Os identificadores de eleitor devem ser gerados a partir de uma função _MD5_ do nome completo do eleitor.

- **result**: este método possui dois parâmetros com os quais o servidor fornece para o cliente o nome de um candidato e o número de votos desse candidato.

Desenvolva um sistema para o serviço _Election_ utilizando o **_Java RMI_**, que garanta que seus registros permaneçam consistentes quando ele é acessado simultaneamente por vários clientes. O serviço _Election_ deve garantir que todos os votos sejam armazenados com segurança, mesmo quando o processo servidor falha. Considerando que o **_Java RMI_** possui semântica _at-most-once_, implemente um mecanismo de recuperação de falha no cliente que consiga obter uma semântica _exactly-once_ para o caso do serviço ser interrompido por um tempo inferior a 30 segundos.

---
## Execução
Considerando o ambiente Linux em que esse programa foi desenvolvido, voce precisa:

1. em um prompt aberto, na pasta raiz do projeto, execute o script _./run_rmiregistry_
2. em outro prompt aberto, na pasta raiz do projeto, execute o script _./run_server_
3. em um terceiro prompt aberto, na pasta raiz do projeto, execute o script _./run_client_

O rmiregistry esta configurado para rodar na porta **9091**, assim como o server. Se voce executar o rmiregistry numa porta diferente, voce devera alterar o codigo da classe Server.java para que o programa funcione ou alterar a url exibida na interface de cliente apos executar o _run_client_.


---
## Aluno

- [Patrick Rodrigues Galdino](https://www.github.com/patrickgald) - 573639


## Professor Responsável

- [Hugo Bastos de Paula](https://github.com/hugodepaula)