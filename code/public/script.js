function toggleClassFilme(){
    //console.log("click");
     element = document.querySelector('.dropFilmes');
     //console.log(element.style.display);
      if(element.style.display === "none" || element.style.display === ''){
        element.style.display = 'block';
      } else {
        element.style.display = 'none';
      }
      
    }

    function toggleClassSerie(){
      console.log("click");
       element = document.querySelector('.dropSeries');
       //console.log(element.style.display);
        if(element.style.display === "none" || element.style.display === ''){
          element.style.display = 'block';
        } else {
          element.style.display = 'none';
        }
        
      }


      function TESTE () {
        const searchInput = document.getElementById("searchInput");
      
          console.log("click");
          const query = searchInput.value.trim();
      
          if (query !== "") {
              window.location.href = `../search/search.html?input=${query}`;
          }
      };