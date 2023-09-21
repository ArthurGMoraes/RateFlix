const options = {
    method: 'GET',
    headers: {
      accept: 'application/json',
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmODM5NzhhMjU4YjdkNzRmOTJkNmIwZGY1NWQ5YmI1ZCIsInN1YiI6IjY0ZThlZmU5ZTg5NGE2MDExZWY4MzRhMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4XoOXpMTJlf1R-ftk0WqFUnt4X786gguycsoLdvSDb8'
    }
  };


fetch('https://api.themoviedb.org/3/trending/all/day?language=en-US', options)
  .then(res => res.json())
  .then(data => {
    console.log(data);
    console.log('Imprimindo produtos');
    
    let str = '';
    let title = '';
    let id = 0;
    let media_type ='';
    str += `<h3 class="trending"> Populares do dia: </h3>
            <div class="roleta">`;
    for (let i = 0; i < data.results.length; i++) {
      let produtos = data.results[i];
      if ( produtos.name != undefined){
        title = produtos.name.length > 20 ? produtos.name.substring(0, 20) + '...' : produtos.name;
      } else {
        title = produtos.title.length > 20 ? produtos.title.substring(0, 20) + '...' : produtos.title;
      }
      id = produtos.id;
      media_type = produtos.media_type;
      let poster = produtos.poster_path;
      str += `<div class="cards">
              <a href="../detalhesFilme/detalhesFilmes.html?id=${id}?type=${media_type}">
                <img src="https://image.tmdb.org/t/p/w500${poster}">
                <h5>${title}</h5>
              </a>
              </div>`;
    }
    str += `</div>
            <a class="Back" onclick="plusSlides(-4)">&#10094;</a>
            <a class="forward" onclick="plusSlides(4)">&#10095;</a>`;
    document.getElementById('tela_roleta').innerHTML = str;
  });



  var slidePosition = 1;
  SlideShow(slidePosition);
  
  // forward/Back controls
  function plusSlides(n) {
    if(slidePosition%20 === 1 && n<0){
      SlideShow(slidePosition = 17 );
    } else  {
      SlideShow(slidePosition += n);
    }
  }

  
  function SlideShow(n) {
    var i;
    var slides = document.getElementsByClassName("cards");
    if (n > slides.length) {slidePosition = 1}
    if (n < 1) {slidePosition = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slides[(slidePosition-1)%20].style.display = "flex";
    slides[(slidePosition)%20].style.display = "flex";
    slides[(slidePosition+1)%20].style.display = "flex";
    slides[(slidePosition+2)%20].style.display = "flex";
  } 
  
  function adicionarNovoCard() {
    const discussao = document.querySelector('.discussao');

    // Criar um novo elemento div para o card
    const novoCard = document.createElement('div');
    novoCard.classList.add('cardsTexto');

    // Definir o título do card como "Tópico Teste"
    const titulo = document.createElement('h5');
    titulo.textContent = "Tópico Teste";

    // Criar um elemento de parágrafo para a descrição
    const descricao = document.createElement('p');
    descricao.textContent = gerarDescricaoAleatoria();

    // Adicionar o título e a descrição ao novo card
    novoCard.appendChild(titulo);
    novoCard.appendChild(descricao);

    // Inserir o novo card diretamente abaixo do h1 e acima da div cardsTexto
    discussao.insertBefore(novoCard, document.querySelector('.cardsTexto'));
}

// Adicione um ouvinte de evento para o botão
const botaoAdicionar = document.getElementById('adicionarCard');
botaoAdicionar.addEventListener('click', adicionarNovoCard);

// Função para gerar uma descrição aleatória de um filme genérico
function gerarDescricaoAleatoria() {
    const descricao = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in justo eu justo commodo tristique. Nulla facilisi. ";
    return descricao.repeat(3); // Repetir a descrição três vezes para criar três linhas.
}
