<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.math.BigDecimal" %>

<%
  int i = 0;
  BigDecimal total = new BigDecimal(0);
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Category";
%>

<!DOCTYPE html>
<html>
<head>
  <%@include file="/include/htmlHead.jsp"%>
  <style>
    .row {
      display: -ms-flexbox; /* IE10 */
      display: flex;
      -ms-flex-wrap: wrap; /* IE10 */
      flex-wrap: wrap;
    }
    .col {
      -ms-flex: 50%; /* IE10 */
      flex: 50%;
      width: 300px;
    }

    .container {
      background-color: #f2f2f2;
      padding: 5px 20px 15px 20px;
      border: 1px solid lightgrey;
      border-radius: 3px;
    }

    input[type=text], input[type=number] {
      margin-bottom: 20px;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 3px;
    }

    label {
      margin-bottom: 10px;
      display: block;
    }

    .btn {
      background-color: #FFC2D6;
      color: black;
      padding: 12px;
      margin: 10px 0;
      border: none;
      width: 100%;
      border-radius: 3px;
      cursor: pointer;
      font-size: 17px;
    }

    .btn:hover {
      background-color:#CDB4DB;
    }

    .delbtn {
      background-color: #99CEFF;
      color: black;
      padding: 12px;
      margin: 10px 0;
      border: none;
      width: 100%;
      border-radius: 3px;
      cursor: pointer;
      font-size: 17px;
    }

    .delbtn:hover {
      background-color:#CDB4DB;
    }
  </style>
</head>
<body>
<%@include file="/include/header.jsp"%>
<main class="clearfix">
  <div id="site_content">
    <div id="content">
      <div class="row">
        <div class="container">
          <form name="checkoutForm" action="Dispatcher" method="post">
            <div class="row">

              <div class="col">
                <h3>Descrizione prodotto</h3>
                <label for="name">Nome</label>
                <input type="text" id="name" name="name" required>
                <label for="brand">Marchio</label>
                <input type="text" id="brand" name="brand" required>
                <label for="cat">Categoria</label>
                <input type="text" id="cat" name="category" required>

              </div>

              <div class="col">
                <h3>Inventario</h3>
                <label for="mag">Magazzino</label>
                <input type="number" id="mag" name="magazine" step="1" required>
                <label for="price">Prezzo al Kg</label>
                <input type="number" id="price" name="price" step="0.01" required>
                <label for="weight">Peso</label>
                <input type="number" id="weight" name="weight" step="0.01" required>

              </div>

            </div>
            <label for="descr">Descrizione</label>
            <input type="text" id="descr" name="description">
            <input type="hidden" name="controllerAction" value="Category.createProduct">
            <input type="submit" value="conferma" class="btn">
          </form>
          <form name="discardForm" action="Dispatcher" method="post">
            <input type="hidden" name="controllerAction" value="Category.view">
            <input type="submit" value="annulla" class="delbtn">
          </form>
        </div>
      </div>
    </div>
  </div>
</main>
<%@include file="/include/footer.jsp"%>
</body>
</html>