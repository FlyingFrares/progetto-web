<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="model.mo.Prodotto" %>

<%
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Category";
  Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
  String action = (prodotto != null) ? "modify" : "create";
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
      justify-content: center;
    }

    .btn:hover {
      background-color:#CDB4DB;
    }

    .delbtn {
      background-color: #99CEFF;
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
          <form name="createModForm" action="Dispatcher" method="post">
            <div class="row">

              <div class="col">
                <h3>Descrizione prodotto</h3>
                <label for="name">Nome</label>
                <input type="text" id="name" name="name" value="<%=(action.equals("modify")) ? prodotto.getNomeProdotto() : ""%>" required>
                <label for="brand">Marchio</label>
                <input type="text" id="brand" name="brand" value="<%=(action.equals("modify")) ? prodotto.getMarchio() : ""%>" required>
                <label for="cat">Categoria</label>
                <input type="text" id="cat" name="category" value="<%=(action.equals("modify")) ? prodotto.getCategoria() : ""%>" required>
              </div>

              <div class="col">
                <h3>Inventario</h3>
                <label for="mag">Magazzino</label>
                <input type="number" id="mag" name="magazine" min="0" step="1" value="<%=(action.equals("modify")) ? prodotto.getMagazzino() : ""%>" required>
                <label for="price">Prezzo al Kg (&euro;)</label>
                <input type="number" id="price" name="price" min="0" step="0.01" value="<%=(action.equals("modify")) ? prodotto.getPrezzoKg() : ""%>" required>
                <label for="weight">Peso (Kg)</label>
                <input type="number" id="weight" name="weight" min="0" step="0.01" value="<%=(action.equals("modify")) ? prodotto.getPeso() : ""%>" required>

              </div>

            </div>
            <%if (action.equals("modify")) {%>
            <input type="hidden" name="productID" value="<%=prodotto.getProductID()%>">
            <%}%>
            <input type="hidden" name="controllerAction" value="Category.<%=action%>Product">
            <input type="submit" value="conferma" class="btn">
          </form>
          <form name="discardForm" action="Dispatcher" method="post">
            <input type="hidden" name="controllerAction" value="Category.view">
            <input type="submit" value="annulla" class="btn delbtn">
          </form>
        </div>
      </div>
    </div>
  </div>
</main>
<%@include file="/include/footer.jsp"%>
</body>
</html>