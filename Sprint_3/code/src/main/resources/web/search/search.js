var url = document.URL;

var url_params = url.split('?input=') 
var input = url_params[url_params.length-1];
console.log(input);

var movieURL = `https://api.themoviedb.org/3/search/movie?query=${input}&include_adult=false&language=pt-BR&page=1`

const options = {
    method: 'GET',
    headers: {
      accept: 'application/json',
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmODM5NzhhMjU4YjdkNzRmOTJkNmIwZGY1NWQ5YmI1ZCIsInN1YiI6IjY0ZThlZmU5ZTg5NGE2MDExZWY4MzRhMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4XoOXpMTJlf1R-ftk0WqFUnt4X786gguycsoLdvSDb8'
    }
  };
  
  fetch(movieURL, options)
  .then(res => res.json())
  .then(data => {
    console.log(data);
    console.log('Imprimindo produtos');
    
    let str = '';
    let title = '';
    let id = 0;
    let media_type ='';
    str += `
            <div class="discussao">`;
    for (let i = 0; i < data.results.length; i++) {
      let produtos = data.results[i];
      if ( produtos.name != undefined){
        title = produtos.name.length > 20 ? produtos.name.substring(0, 20) + '...' : produtos.name;
      } else {
        title = produtos.title.length > 20 ? produtos.title.substring(0, 20) + '...' : produtos.title;
      }
      console.log("ok");
      id = produtos.id;
      media_type = produtos.media_type;
      let poster = produtos.poster_path;
      str += `<div class="cards">
              <a href="/detalhes/${id}/movie">
                <img src="https://image.tmdb.org/t/p/w500${poster}">
                <h5>${title}</h5>
              </a>
              </div>`;
    }
    str += `</div>`;
    document.getElementById('tela').innerHTML = str;
  });
