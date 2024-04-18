# Arquitetura MVVM (Model, View, ViewModel)

Foi criado por John Grossman em 2005 para ser usado em aplicativos WPF (Windows Presentation Foundation) e Silverlight, utilizando XAML para a vinculação de dados.

É composto por:

- **Model**: Representa a lógica de negócios e os dados da aplicação. É a camada que contém as regras de negócios e acesso aos dados.
- **View**: É a interface do usuário, responsável por exibir os dados ao usuário e capturar suas interações. A View se comunica com a ViewModel através de mecanismos de binding, sem conhecer a Model diretamente.
- **ViewModel**: Atua como intermediário entre a View e a Model. A ViewModel expõe propriedades e comandos para a View, implementa a lógica de apresentação e coordena as operações entre a View e a Model. Ela também pode conter lógica de validação para garantir a consistência dos dados.

Esse tipo de padrão ajuda na manutenção e testabilidade do código.

## Surgimento

Essa arquitetura surgiu como uma evoluÃ§Ã£o ao MVC (model, view, controller) com objetivo de separar ainda mais as responsabilidades e melhorar a testabilidade do cÃ³digo. Entre a arquitetura MVC que Ã© uma das mais antigas e o MVVM ainda entra o MVP (model, view, presenter) que conseguiu melhorar um pouco os pontos negativos do MVC.

## Qual problema ela resolve ?
O MVC foi uma das primeiras arquiteturas a separar a aplicaÃ§Ã£o em camadas, mas muitas vezes nessa arquitetura o controller, a lÃ³gica de negocios e manipulaÃ§Ã£o de dados, dificuldade a testailidade e manutenÃ§Ã£o do cÃ³digo.

## Quais problemas ela ainda possui ?
- Curva de aprendizagem: tende a ser um pouco desafiadora principalmente no inicio da aprendizagem por introduzir alguns novos conceitos que outras arquiteturas nÃ£o usam, como o data-binding por exemplo
- Pode exigir um grande numero de classes: ela pode acabar exigindo mais classes que outras arquiteturas pois serÃ¡ necessÃ¡rio classes principalmente para genriamento de estados
- DocumentaÃ§Ã£o: por mais que exista documentaÃ§Ãµes e artigos sobre essa arquitetura ainda Ã© mais encontrar informaÃ§Ãµes realcionadas a outras arquiteturas como MVC ou Clean Archtecture

## Fontes:
[Design Patterns: as diferenÃ§as entre MVC, MVVM e MVP](https://medium.com/@luiselbarbosa/design-patterns-as-diferen%C3%A7as-entre-mvc-mvvm-e-mvp-28b56de8698)

[Comparison between MVVM vs MVC vs MVP Architecture Patterns](https://codedamn.com/news/web-development/comparison-between-mvvm-vs-mvc-vs-mvp-architecture-patterns)
