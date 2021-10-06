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

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.jsp"%>
    <style>
      .product-info {
        float: left;
        height: 420px;
        width: 360px;
        border-radius: 0 7px 10px 7px;
        background-color: #ffffff;
      }

      .product-text {
        height: 300px;
        width: 327px;
      }

      .product-text h1 {
        margin: 0 0 0 38px;
        padding-top: 52px;
        font-size: 34px;
        color: #474747;
      }

      .product-text h2 {
        margin: 0 0 47px 38px;
        font-size: 16px;
        font-family: 'Raleway', sans-serif;
        font-weight: 400;
        text-transform: uppercase;
        letter-spacing: 0.2em;
      }

      .product-text p {
        height: 125px;
        margin: 0 0 0 38px;
        color: #8d8d8d;
        line-height: 1.7em;
        font-size: 15px;
        font-weight: lighter;
        overflow: hidden;
      }

      .product-price-btn {
        height: 100px;
        width: 320px;
        position: relative;
      }

      .product-price-btn p {
        display: inline-block;
        height: 50px;
        font-family: 'Trocchi', serif;
        margin: 0 0 0 38px;
        font-size: 28px;
        font-weight: lighter;
        color: #474747;
      }

      .product-price-btn button {
        float: right;
        display: inline-block;
        height: 50px;
        width: 145px;
        margin: 0 15px 0 15px;
        box-sizing: border-box;
        border: transparent;
        border-radius: 60px;
        font-family: 'Raleway', sans-serif;
        font-size: 14px;
        font-weight: 500;
        text-transform: uppercase;
        letter-spacing: 0.2em;
        color: #000000;
        background-color: #FFC2D6;
        cursor: pointer;
        outline: none;
        padding: 0px 0px;
      }

      .product-price-btn button:hover {
        background-color: #CDB4DB;
      }
    </style>
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
            <button type="button">Acquista</button>
            <%} else {%>
            <p>Non disponibile</p>
            <%}%>
          </div>
        </div>
      <%}%>
        </div>
      </div>
    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>
