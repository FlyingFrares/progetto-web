<script>

  function selectCategory(category) {
      let cat = document.getElementById('bf');
      cat.setAttribute('value',category);
      document.buttonForm.submit();
  }

</script>

<h3>Cerca un prodotto</h3>
<form name="searchForm" action="Dispatcher" method="post">
  <input class="search" type="text" id="search" name="search" placeholder="Ricerca" maxlength="40">
  <input type="hidden" name="controllerAction" value="Category.search"/>
</form>
<h3>Categorie</h3>
<button type="button" name="crudi" onclick='selectCategory("crudi")'>Crudi</button>
<button type="button" name="cotti" onclick='selectCategory("cotti")'>Cotti</button>
<button type="button" name="mortadelle" onclick='selectCategory("mortadelle")'>Mortadelle</button>
<button type="button" name="pancette" onclick='selectCategory("pancette")'>Pancette</button>
<button type="button" name="salami" onclick='selectCategory("salami")'>Salami</button>
<button type="button" name="special" onclick='selectCategory("special")'>Specialit&agrave</button>

<form name="buttonForm" action="Dispatcher" method="post">
  <input type="hidden" id="bf" name="search"/>
  <input type="hidden" name="controllerAction" value="Category.search"/>
</form>