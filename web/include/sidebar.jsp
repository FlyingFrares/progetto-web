<div class="sidebar">
  <!-- insert your sidebar items here -->
  <h3>Cerca un prodotto</h3>
  <form name="searchForm" action="Dispatcher" method="post">
    <input class="search" type="text" id="search" name="search" placeholder="Ricerca" maxlength="40" required>
    <input type="hidden" name="controllerAction" value="Category.search"/>
  </form>
  <h3>Categorie</h3>
  <form name="buttonForm" action="Dispatcher" method="post">
    <input type="submit" id="crudi" value="Crudi">
    <input type="hidden" name="controllerAction" value="Home.view"/>
  </form>
  <form name="buttonForm" action="Dispatcher" method="post">
    <input type="submit" id="cotti" value="Cotti">
    <input type="hidden" name="controllerAction" value="Home.view"/>
  </form>
  <form name="buttonForm" action="Dispatcher" method="post">
    <input type="submit" id="salami" value="Salami">
    <input type="hidden" name="controllerAction" value="Home.view"/>
  </form>
  <form name="buttonForm" action="Dispatcher" method="post">
    <input type="submit" id="pancette" value="Pancette">
    <input type="hidden" name="controllerAction" value="Home.view"/>
  </form>
  <form name="buttonForm" action="Dispatcher" method="post">
    <input type="submit" id="special" value="Specialit&agrave">
    <input type="hidden" name="controllerAction" value="Home.view"/>
  </form>
  <form name="buttonForm" action="Dispatcher" method="post">
    <input type="submit" id="mortadelle" value="Mortadelle">
    <input type="hidden" name="controllerAction" value="Home.view"/>
  </form>
</div>