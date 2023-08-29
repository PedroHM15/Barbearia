# Documentação do Aplicativo de Agendamento de Horários em Barbearias

Bem-vindo à documentação abrangente do aplicativo de agendamento de horários em barbearias. Este guia detalhado fornecerá instruções passo a passo para as principais funcionalidades do aplicativo, destacando os processos de agendamento de horários pelos clientes, gerenciamento de horários pelos barbeiros e cadastramento de novos barbeiros pelo Administrador. O aplicativo utiliza o Firebase como base de dados, incluindo as ferramentas Authentication, Cloud Storage e Firestore Database.
## Sumário

1. Introdução
2. Autenticação
3. Agendamento de Horários
4. Gerenciamento de Horários
5. Cadastramento de Novos Barbeiros
6. Conclusão

## 1. Introdução
O aplicativo de agendamento de horários em barbearias visa facilitar o processo de agendamento de cortes de cabelo para clientes e otimizar a gestão de horários para os barbeiros. O sistema possui três tipos de usuários: Administrador, Barbeiro e Cliente. A seguir, detalharemos as principais funcionalidades e processos do aplicativo.

## 2. Autenticação

O Firebase Authentication é utilizado para gerenciar o processo de autenticação dos usuários. Cada tipo de usuário tem seu próprio fluxo de autenticação:

- Cliente: Os clientes podem se cadastrar e fazer login usando seu endereço de e-mail e senha.
- Barbeiro: Os barbeiros também se cadastram e fazem login usando e-mail e senha.
- Administrador: O Administrador é responsável por cadastrar novos barbeiros e possui acesso exclusivo para excluir registros.

## 3. Agendamento de Horários
- Cliente:
  - Após fazer login, o cliente acessa a aba de agendamento.
  - O cliente visualiza os horários disponíveis dos barbeiros e seleciona o horário desejado.
  - O sistema confirma o agendamento e envia uma notificação ao cliente.

## 4. Gerenciamento de Horários
- Barbeiro:
  - O barbeiro faz login e acessa sua agenda.
  - O barbeiro visualiza os horários agendados.
  - O barbeiro pode marcar um horário como concluído ou cancelado.
  - Em caso de cancelamento, o sistema notifica o cliente e libera o horário para agendamentos futuros.

## 5. Cadastramento de Novos Barbeiros
- Administrador:
  - O Administrador faz login e acessa o painel de administração.
  - No painel, o Administrador pode cadastrar um novo barbeiro, fornecendo informações como nome, e-mail e especialidades.
  - O Administrador também possui a opção de excluir o registro de um barbeiro, se necessário.

## 6. Conclusão
Parabéns! Você agora está familiarizado com as principais funcionalidades do aplicativo de agendamento de horários em barbearias. Este guia detalhado proporcionou diretrizes claras para o agendamento de horários pelos clientes, o gerenciamento de horários pelos barbeiros e o cadastramento de novos barbeiros pelo Administrador. Utilizando as ferramentas Firebase, incluindo Authentication, Cloud Storage e Firestore Database, nosso aplicativo oferece uma solução eficiente e conveniente para a gestão de agendamentos em barbearias. Se você tiver alguma dúvida adicional, consulte este guia ou entre em contato com nossa equipe de suporte. Obrigado por escolher nosso aplicativo!
