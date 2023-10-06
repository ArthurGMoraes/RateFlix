var url = document.URL;

var url_params = url.split('?genero=') 
var params = url_params[url_params.length-1];
var params = params.split('?type=');
var genero = params[0];
var type = params[1];
console.log(genero);
console.log(type);



var apiUrl = `https://api.themoviedb.org/3/discover/${type}?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_genres=${genero}`;

const options = {
    method: 'GET',
    headers: {
      accept: 'application/json',
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmODM5NzhhMjU4YjdkNzRmOTJkNmIwZGY1NWQ5YmI1ZCIsInN1YiI6IjY0ZThlZmU5ZTg5NGE2MDExZWY4MzRhMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4XoOXpMTJlf1R-ftk0WqFUnt4X786gguycsoLdvSDb8'
    }
  };
  
  fetch(apiUrl, options)
    .then(response => response.json())
    .then(response => console.log(response))
    .catch(err => console.error(err));