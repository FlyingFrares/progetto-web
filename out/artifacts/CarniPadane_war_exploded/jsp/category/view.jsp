<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@page import="model.mo.Prodotto" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
  int i = 0;
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Category";
  List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
%>

<script>
    function addToCart(productID) {
        let id = document.getElementById('atc');
        id.setAttribute('value',productID);
        document.addToCartForm.submit();
    }

    function modifyProduct(productID) {
        let id = document.getElementById('modify');
        id.setAttribute('value', productID);
        document.modifyProductForm.submit();
    }

    function createProduct() {
        document.createProductForm.submit();
    }
</script>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.jsp"%>
    <style>
      .product-info {
        float: left;
        height: 420px;
        width: 350px;
        margin: 10px;
        border-radius: 10px 10px 10px 10px;
        background-color: #ffffff;
        -webkit-box-shadow: 0px 14px 32px 0px rgba(0, 0, 0, 0.15);
        -moz-box-shadow: 0px 14px 32px 0px rgba(0, 0, 0, 0.15);
        box-shadow: 0px 14px 32px 0px rgba(0, 0, 0, 0.15);
      }
    </style>
  </head>
  <body>
    <%@include file="/include/header.jsp"%>
    <main class="clearfix">
      <div id="site_content">
        <div class="sidebar">
          <%@include file="/include/sidebar.jsp"%>
          <%if (loggedOn && loggedUser.getAdmin().equals("S")) {%>
          <h3>Amministrazione</h3>
          <button type="button" onclick="createProduct()">Aggiungi prodotto</button>
          <%}%>
        </div>
        <div id="content">
          <%if (prodotti.size() == 0) {%>
          <img src="images/404.png" width="800px">
          <%}for (i = 0; i<prodotti.size(); i++) {%>
          <div class="product-info">
            <div class="product-text">
              <h1><%=prodotti.get(i).getNomeProdotto()%></h1>
              <h2><%=prodotti.get(i).getCategoria()%></h2>
              <p>
                Disponibilit&agrave : <%=prodotti.get(i).getMagazzino()%><br>
                Prezzo/Kg : <%=prodotti.get(i).getPrezzoKg()%> &euro;<br>
                Peso : <%=prodotti.get(i).getPeso()%> Kg
                <%if (loggedOn && loggedUser.getAdmin().equals("S")) {%>
                <button type="button" onclick="modifyProduct(<%=prodotti.get(i).getProductID()%>)">Modifica</button>
                <%}%>
              </p>
            </div>
            <div class="product-price-btn">
              <% if (prodotti.get(i).getMagazzino() > 0) {%>
              <p><%=prodotti.get(i).getPrezzoTot()%> &euro;</p>
              <% if(!loggedOn) {%>
              <button type="button" onclick="alert('Devi effettuare il Login')">Acquista</button>
              <%} else {%>
              <button type="button" onclick='addToCart(<%=prodotti.get(i).getProductID()%>)'>Acquista</button>
              <%}} else {%>
              <p>Non disponibile</p>
              <%}%>
            </div>
          </div>
          <%}%>
        </div>
      </div>

      <form name="addToCartForm" action="Dispatcher" method="post">
        <input type="hidden" id="atc" name="productID"/>
        <input type="hidden" name="controllerAction" value="Category.addToCart"/>
      </form>

      <form name="modifyProductForm" action="Dispatcher" method="post">
        <input type="hidden" id="modify" name="productID"/>
        <input type="hidden" name="controllerAction" value="Category.modifyProductView"/>
      </form>

      <form name="createProductForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Category.createProductView"/>
      </form>

    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>
