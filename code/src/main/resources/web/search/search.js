var url = document.URL;

var url_params = url.split('?input=') 
var input = url_params[url_params.length-1];
console.log(input);

var movieURL = `https://api.themoviedb.org/3/search/movie?query=${input}&include_adult=false&language=en-US&page=1`
var tvURL = `https://api.themoviedb.org/3/search/tv?query=${input}&include_adult=false&language=en-US&page=1`

const options = {
    method: 'GET',
    headers: {
      accept: 'application/json',
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmODM5NzhhMjU4YjdkNzRmOTJkNmIwZGY1NWQ5YmI1ZCIsInN1YiI6IjY0ZThlZmU5ZTg5NGE2MDExZWY4MzRhMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4XoOXpMTJlf1R-ftk0WqFUnt4X786gguycsoLdvSDb8'
    }
  };
  
  fetch(movieURL, options)
    .then(response => response.json())
    .then(response => console.log(response))
    .catch(err => console.error(err));


    fetch(tvURL, options)
    .then(response => response.json())
    .then(response => console.log(response))
    .catch(err => console.error(err));