var url = document.URL;

var url_params = url.split('?id=') 
var params = url_params[url_params.length-1];
var params = params.split('?type=');
var id = params[0];
var type = params[1];
console.log(id);
console.log(type);


const options = {
    method: 'GET',
    headers: {
      accept: 'application/json',
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmODM5NzhhMjU4YjdkNzRmOTJkNmIwZGY1NWQ5YmI1ZCIsInN1YiI6IjY0ZThlZmU5ZTg5NGE2MDExZWY4MzRhMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4XoOXpMTJlf1R-ftk0WqFUnt4X786gguycsoLdvSDb8'
    }
  };

 
  
  function criarPagina(){
    var apiUrl = '';
    if(type == "movie"){
         apiUrl = `https://api.themoviedb.org/3/movie/${id}?language=pt-BR`;
    } else {
         apiUrl = `https://api.themoviedb.org/3/tv/${id}?language=pt-BR`;
    }
      fetch(apiUrl, options)
      .then(res => res.json())
      .then(data => {

        console.log(data);
        console.log('Imprimindo produtos');

        let produtos = data;

        let str = '';
        let title = '';
        let poster = produtos.poster_path;
        let descricao = produtos.overview;

          
          if ( produtos.name != undefined){
            title = produtos.name;
          } else {
            title = produtos.title;
          }
          str += `<img src="https://image.tmdb.org/t/p/w500${poster}" id="imagem">
                  <div id="info">
                  <h5 id="title"> ${title} </h5>
                  <p id="descricao"> ${descricao} </p>
                  </div>`;
        
        document.getElementById('tela').innerHTML = str;
        

  });
  }


  criarPagina();