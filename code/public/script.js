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