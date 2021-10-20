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
    function requestLogin() {
        alert("Devi effettuare il Login");
    }

    function addToCart(productID) {
        let id = document.getElementById('atc');
        id.setAttribute('value',productID);
        document.addToCartForm.submit();
    }
</script>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.jsp"%>
  </head>
  <body>
    <%@include file="/include/header.jsp"%>
    <main class="clearfix">
      <div id="site_content">
        <%@include file="/include/sidebar.jsp"%>
        <div id="content">
      <%for (i = 0; i<prodotti.size(); i++) {%>
        <div class="product-info">
          <div class="product-text">
            <h1><%=prodotti.get(i).getNomeProdotto()%></h1>
            <h2><%=prodotti.get(i).getCategoria()%></h2>
            <p>
              Disponibilit&agrave : <%=prodotti.get(i).getMagazzino()%><br>
              Prezzo/Kg : <%=prodotti.get(i).getPrezzoKg()%> &euro;<br>
              Peso : <%=prodotti.get(i).getPeso()%> Kg
            </p>
          </div>
          <div class="product-price-btn">
            <% if (prodotti.get(i).getMagazzino() > 0) {%>
            <p><%=prodotti.get(i).getPrezzoTot()%> &euro;</p>
            <% if(!loggedOn) {%>
            <button type="button" onclick="requestLogin()">Acquista</button>
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

    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>
