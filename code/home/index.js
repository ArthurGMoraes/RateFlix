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
    for (let i = 0; i < data.results.length; i++) {
      let produtos = data.results[i];
      if ( produtos.name != undefined){
        title = produtos.name;
      } else {
        title = produtos.title;
      }
      let poster = produtos.poster_path;
      str += `<h5>${title}</h5>
              <p><img src="https://image.tmdb.org/t/p/w500${poster}"></p>`;
    }
    document.getElementById('tela').innerHTML = str;
  });